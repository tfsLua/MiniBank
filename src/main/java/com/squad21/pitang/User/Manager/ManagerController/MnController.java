package com.squad21.pitang.User.Manager.ManagerController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.squad21.pitang.User.Manager.ManagerModel.MnModel;
import com.squad21.pitang.User.Manager.DTO.ManagerDTO;
import com.squad21.pitang.User.Manager.ManagerService.ManagerService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/manager")
public class MnController {
    @Autowired
    private ManagerService managerService;

    @GetMapping("/users")
    public List <MnModel> getUsers(){
        return managerService.getAllUsers();
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody ManagerDTO data){
    return managerService.createUser(data);
    }
}
