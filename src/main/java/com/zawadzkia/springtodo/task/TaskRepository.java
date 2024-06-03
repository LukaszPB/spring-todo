package com.zawadzkia.springtodo.task;

import com.zawadzkia.springtodo.user.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskModel, Long> {

    List<TaskModel> findAllByOwner(UserModel owner);
}
