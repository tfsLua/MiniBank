package com.squad21.pitang.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/tasks")
public class TaskController {
   
    @Autowired
    private ITaskRepository taskRepository;
    @PostMapping("create")
    public TaskModel create(@RequestBody TaskModel taskModel){
        var task = this.taskRepository.save(taskModel);
        return task;
     }

}
