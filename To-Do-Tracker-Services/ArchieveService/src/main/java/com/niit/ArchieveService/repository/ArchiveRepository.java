package com.niit.ArchieveService.repository;

import com.niit.ArchieveService.model.Task;
import com.niit.ArchieveService.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArchiveRepository extends MongoRepository<User,String> {
}
