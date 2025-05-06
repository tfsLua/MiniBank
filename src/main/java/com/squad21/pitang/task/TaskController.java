package com.squad21.pitang.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/login")
public class TaskController {
   
    @Autowired
    private ITaskRepository taskRepository;
    @PostMapping("/client")
    public ResponseEntity<?> create(@RequestBody TaskModel taskModel, HttpServletRequest request){
        var idUser = request.getAttribute("idUser");
        System.out.println("Id do usuário recebido" + idUser);
        taskModel.setId_usuario((UUID) idUser);

        var currentDate = LocalDateTime.now();
                        
        // 10/11/2023 - Current
        // 10/10/2023 - data_inicio
        if(currentDate.isAfter(taskModel.getData_inicio())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de inicio deve ser maior que a atual!");
        }

        var task = this.taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.OK).body(task);
     }

}
