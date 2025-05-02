package com.squad21.pitang.User.Client.ClientController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import com.squad21.pitang.User.Client.ClientModel.ClientModel;
import com.squad21.pitang.User.Client.ClientService.ClientService;
import com.squad21.pitang.User.UserDTO.UserDTO;

@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping("/users")
    public List <ClientModel> getUsers(){
        return clientService.getAllUsers();
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody UserDTO data){
    return clientService.createUser(data);
    }
}
