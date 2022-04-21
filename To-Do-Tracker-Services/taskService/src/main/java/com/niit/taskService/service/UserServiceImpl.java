package com.niit.taskService.service;

import com.niit.taskService.Exception.TaskAlreadyExist;
import com.niit.taskService.Exception.TaskNotFoundException;
import com.niit.taskService.Exception.UserAlreadyExistsException;
import com.niit.taskService.Exception.UserNotExistsException;
import com.niit.taskService.RabbitMQ.TaskDTO;
import com.niit.taskService.config.Producer;
import com.niit.taskService.model.Task;
import com.niit.taskService.model.User;
import com.niit.taskService.proxy.UserArchiveProxy;
import com.niit.taskService.proxy.UserAuthProxy;
import com.niit.taskService.repository.UserRepository;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    UserRepository userTaskRepository;
    UserAuthProxy proxy;
    UserArchiveProxy proxyArchive;
    Producer producer;
    private JavaMailSender javaMailSender;
    private SequenceGenerator sequenceGenerator;

    @Autowired
    public UserServiceImpl(UserRepository userTaskRepository, UserAuthProxy proxy, Producer producer, SequenceGenerator sequenceGenerator,JavaMailSender javaMailSender,UserArchiveProxy proxyArchive) {
        this.userTaskRepository = userTaskRepository;
        this.proxy = proxy;
        this.proxyArchive=proxyArchive;
        this.producer = producer;
        this.sequenceGenerator = sequenceGenerator;
        this.javaMailSender=javaMailSender;
    }

    @Override
    public User registerUser(User user) throws UserAlreadyExistsException {
        if (userTaskRepository.findById(user.getEmailId()).isPresent()) {

            throw new UserAlreadyExistsException();
        }
        proxy.saveUser(user);
        proxyArchive.saveUser(user);

        return userTaskRepository.save(user);
    }

    @Override
    public User updateUserData(String emailId, User user1) throws UserNotExistsException {
        if (userTaskRepository.findById(emailId).isEmpty()) {
            throw new UserNotExistsException();
        }
        User user = userTaskRepository.findById(emailId).get();
        user.setUserPassword(user1.getUserPassword());
        userTaskRepository.save(user);
        proxy.updateUser(emailId, user1);
        return user;
    }

    @Override
    public User returnUserDetails(String emailId) throws UserNotExistsException {
        if (userTaskRepository.findById(emailId).isEmpty()){
            throw new UserNotExistsException();
        }
        userTaskRepository.findById(emailId).get().setUserTask(null);
        User user=userTaskRepository.findById(emailId).get();
        user.setUserTask(null);
        return user;
    }

    @Override
    public String getUserName(String emailId) throws UserNotExistsException {
        if (userTaskRepository.findById(emailId).isEmpty()) {
            throw new UserNotExistsException();
        }
        User user=userTaskRepository.findById(emailId).get();
        return user.getFirstName()+" "+user.getLastName()+" "+user.getDateOfBirth()+" "+user.getGender();
    }

    @Override
    public User updateProfile(String emailId, User user1) throws UserNotExistsException {
        if (userTaskRepository.findById(emailId).isEmpty()) {
            throw new UserNotExistsException();
        }
        User user = userTaskRepository.findById(emailId).get();
        user.setFirstName(user1.getFirstName());
        user.setLastName(user1.getLastName());
        user.setDateOfBirth(user1.getDateOfBirth());
        user.setGender(user1.getGender());
        userTaskRepository.save(user);
        proxy.updateUser(emailId, user);
        return user;
    }

    @Override
    public User addTaskToUser(Task task, String userEmail) throws TaskAlreadyExist, UserNotExistsException { //throws UserNotExist
        if (userTaskRepository.findById(userEmail).isEmpty()) {
            throw new UserNotExistsException();
        }
        User user = userTaskRepository.findById(userEmail).get();
        System.out.println(user);
        task.setTaskId(sequenceGenerator.getSequenceNumber(Task.SEQUENCE_NAME));
        System.out.println(Task.SEQUENCE_NAME);
        List<Task> tasks;
        if (user.getUserTask() == null) {
            tasks = new ArrayList<>();
        } else {
            tasks = user.getUserTask();
            for (Task task1 : tasks) {
                if (task1.getTaskHeading().equalsIgnoreCase(task.getTaskHeading())) {
                    throw new TaskAlreadyExist();
                }
            }
        }
        tasks.add(task);
        user.setUserTask(tasks);
        return userTaskRepository.save(user);
    }

    @Override
    public User modifyUserTask(Task task, String userEmail) throws TaskNotFoundException, UserNotExistsException {

        if (userTaskRepository.findById(userEmail).isPresent()) {
            User user = userTaskRepository.findById(userEmail).get();
            List<Task> tasks = user.getUserTask();
            System.out.println(tasks);
            Task task2 = null;
            for (Task task1 : tasks) {
                if (task1.getTaskId() == task.getTaskId()) {
                    task2 = task1;
                    break;
                }
            }
            if (task2 == null) {
                throw new TaskNotFoundException();
            } else {
                user.getUserTask().remove(task2);
                tasks.add(task);
                user.setUserTask(tasks);
                return userTaskRepository.save(user);
            }
        } else {
            throw new UserNotExistsException();
        }
    }

    @Override
    public List<Task> getAllTask(String emailId) throws UserNotExistsException {
        if (userTaskRepository.findById(emailId).isEmpty()) {
            throw new UserNotExistsException();
        }
        User user = userTaskRepository.findById(emailId).get();

        return user.getUserTask();
    }

    @Override
    public String removeUserTask(int taskId, String userEmail, String operation) throws UserNotExistsException, TaskNotFoundException {

        User user;
        String result = "";
        System.out.println(operation);
        try {

            if (userTaskRepository.existsById(userEmail)) {
                user = userTaskRepository.findById(userEmail).get();
                List<Task> tasks = user.getUserTask();
                Task task2 = null;
                for (Task task1 : tasks) {
                    if (task1.getTaskId() == taskId) {
                        task2 = task1;
                        break;
                    }
                }
                if (task2 == null) {
                    throw new TaskNotFoundException();
                } else {
                    TaskDTO taskDTO = new TaskDTO();
                    if (operation.equals("del")) {

                        taskDTO.setTaskId(task2.getTaskId());
                        taskDTO.setTaskHeading(task2.getTaskHeading());
                        taskDTO.setCategoryName(task2.getCategoryName());
                        taskDTO.setTaskStartDate(task2.getTaskStartDate());
                        taskDTO.setTaskEndDate(task2.getTaskEndDate());
                        taskDTO.setTaskDescription(task2.getTaskDescription());
                        taskDTO.setStatus(task2.isStatus());
                        taskDTO.setPriority(task2.priority.toString());
                        taskDTO.setEmailId(userEmail);
                        user.getUserTask().remove(task2);
                        user.setUserTask(tasks);
                        result = "task is deleted successfully";
                    } else {
                        task2.setStatus(true);
                        taskDTO.setTaskId(task2.getTaskId());
                        taskDTO.setTaskHeading(task2.getTaskHeading());
                        taskDTO.setCategoryName(task2.getCategoryName());
                        taskDTO.setTaskStartDate(task2.getTaskStartDate());
                        taskDTO.setTaskEndDate(task2.getTaskEndDate());
                        taskDTO.setTaskDescription(task2.getTaskDescription());
                        taskDTO.setStatus(task2.isStatus());
                        taskDTO.setPriority(task2.priority.toString());
                        taskDTO.setEmailId(userEmail);
                        user.getUserTask().remove(task2);
                        user.setUserTask(tasks);
                    }
                        producer.sendMessageToRabbitMQ(taskDTO);
                        userTaskRepository.save(user);
                        result = "task is update successfully";
                    }

            } else {
                throw new UserNotExistsException();
            }
        }catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Task> categorizeByStartDate(String userEmail) throws UserNotExistsException {

        if (userTaskRepository.existsById(userEmail)) {
            return userTaskRepository.findById(userEmail).get().getUserTask()
                    .stream().sorted(Comparator.comparing(Task::getTaskStartDate)).collect(Collectors.toList());

        }
        throw new UserNotExistsException();
    }

    @Override
    public List<Task> categorizeByEndDate(String userEmail) throws UserNotExistsException {
        if (userTaskRepository.existsById(userEmail)) {
            return userTaskRepository.findById(userEmail).get().getUserTask()
                    .stream().sorted(Comparator.comparing(Task::getTaskEndDate)).collect(Collectors.toList());
        }
        throw new UserNotExistsException();
    }

    @Override
    public List<Task> categorizeByPriority(String userEmail) throws UserNotExistsException {
        if (userTaskRepository.existsById(userEmail)) {
            return userTaskRepository.findById(userEmail).get().getUserTask()
                    .stream().sorted(Comparator.comparing(Task::getPriority)).collect(Collectors.toList());
        }
        throw new UserNotExistsException();
    }


    @Override
    public Task getTask(String userEmail, int taskId) throws TaskNotFoundException, UserNotExistsException {

        if (userTaskRepository.findById(userEmail).isPresent()) {
            User user = userTaskRepository.findById(userEmail).get();
            List<Task> tasks = user.getUserTask();
            Task task2 = null;
            for (Task task1 : tasks) {
                if (task1.getTaskId() == taskId) {
                    task2 = task1;
                    break;
                }
            }
            if (task2 == null) {
                throw new TaskNotFoundException();
            }
            return task2;
        } else {
            throw new UserNotExistsException();
        }
    }

    Comparator<Task> dueDateComparator = ((task1, task2) -> {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        LocalDate dueDate1 = task1.getTaskEndDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate dueDate2 = task2.getTaskEndDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        if (dueDate1.isBefore(dueDate2)) {
            return -1;
        }
        if (dueDate1.isAfter(dueDate2)) {
            return 1;
        }
        return 0;
    });
    @Override
    public List<Task> getTasksWithNearDueDate(String userEmail) throws UserNotExistsException {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Optional<User> user = userTaskRepository.findById(userEmail);
        if (user.isEmpty()) {
            throw new UserNotExistsException();
        }
        List<Task> allTasks = user.get().getUserTask();
        List<Task> tasksNearingDueDate = new ArrayList<>();
        for (Task task : allTasks) {
            if (task.isStatus()) {
                continue;
            }
            LocalDate dueDate =task.getTaskEndDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            LocalDate tomorrowDate = LocalDate.now().plusDays(1);
            if (dueDate.isAfter(tomorrowDate) || dueDate.isBefore(LocalDate.now())){
                continue;
            } else {
                tasksNearingDueDate.add(task);
            }
        }
        return tasksNearingDueDate.stream().sorted(dueDateComparator).collect(Collectors.toList());
    }
    @Override
    public List<Task> getTasksWithOverDue(String userEmail) throws UserNotExistsException {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Optional<User> user = userTaskRepository.findById(userEmail);
        if (user.isEmpty()) {
            throw new UserNotExistsException();
        }
        List<Task> allTasks = user.get().getUserTask();
        List<Task> tasksOverDue = new ArrayList<>();
        for (Task task : allTasks) {
            if (task.isStatus()) {
                continue;
            }
            LocalDate dueDate =task.getTaskEndDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            if (dueDate.isBefore(LocalDate.now())) {
                tasksOverDue.add(task);
            }
        }
        return tasksOverDue.stream().sorted(dueDateComparator).collect(Collectors.toList());
    }
    @Override
    public String emailRemindersForTask() throws UserNotExistsException, MessagingException {

        List<User> users = userTaskRepository.findAll();
        StringBuilder outputMessage = new StringBuilder("\n");

        for (User user: users) {
            List<Task> overDueTasks = getTasksWithOverDue(user.getEmailId());
            List<Task> nearDueTasks = getTasksWithNearDueDate(user.getEmailId());

            if (overDueTasks.isEmpty() && nearDueTasks.isEmpty()) {
                outputMessage.append("\nThe user [userID: " + user.getEmailId() + "] currently has no overdue/neardue tasks to notify!");
                continue;
            }

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

            messageHelper.setTo(user.getEmailId());
            messageHelper.setSubject("From ToDo Reminder");

            StringBuilder mailBody = new StringBuilder("    "+"<h2>Dear " + user.getFirstName() +"  "+ user.getLastName() + "! This email is a reminder from 'ToDo Tracker' regarding your important pending tasks....</h2>");

            if (!overDueTasks.isEmpty()) {
                mailBody.append("<br><h3 style=\"color: red;\">Over Due Tasks</h3><ol>");
                overDueTasks.forEach((t) -> mailBody.append("<li style=\"color: red;\"> Task Heading : '" + t.getTaskHeading() + "'   <br>   Due date : " + t.getTaskEndDate() + "   <br>   Priority Level : " + t.getPriority() + "</li>"));
                mailBody.append("</ol>");
            }

            if (!nearDueTasks.isEmpty()) {

                mailBody.append("<br><h3 style=\"color: #4945d6;\">Nearing Due Tasks</h3><ol>");
                nearDueTasks.forEach((t) -> mailBody.append("<li> Task Heading : '" + t.getTaskHeading() + "'  <br>   Due date : " + t.getTaskEndDate() + "   <br>   Priority Level : " + t.getPriority() + "</li>"));
                mailBody.append("</ol>");
            }

            mailBody.append("<br><p>To view more details of the above notified tasks or to update them, please login to your 'ToDo Tracker' application..</p><br><h4>with Regards, </h4><br><h4>from ToDo Tracker Team</h4>");

            messageHelper.setText(mailBody.toString(), true);

            javaMailSender.send(mimeMessage);
            outputMessage.append("\nThe user EmailId: " + user.getEmailId() + " has been successfully notified of their tasks via email!");

        }

        return outputMessage.toString() + "\n";
    }
}