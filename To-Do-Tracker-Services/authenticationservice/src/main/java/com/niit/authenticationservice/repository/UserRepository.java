package com.niit.authenticationservice.repository;

import com.niit.authenticationservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    User findByEmailIdAndUserPassword(String emailId,String userPassword);

}
