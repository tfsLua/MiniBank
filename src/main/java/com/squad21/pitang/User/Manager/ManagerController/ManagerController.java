package com.squad21.pitang.User.Manager.ManagerController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import com.squad21.pitang.User.Manager.ManagerModel.Manager;
import com.squad21.pitang.User.Manager.ManagerService.ManagerService;
import com.squad21.pitang.User.UserDTO.UserDTO;

@RestController
@RequestMapping("/managers")
public class ManagerController {
    @Autowired
    private ManagerService managerService;

    @GetMapping("/users")
    public List <Manager> getManagers(){
        return managerService.getAllManagers();
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody UserDTO data){
    return managerService.createManager(data);
    }
}
