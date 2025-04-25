package com.squad21.pitang.User.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.squad21.pitang.User.User;
import com.squad21.pitang.User.Repository.IUserRepository;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private IUserRepository userRepository;

    @GetMapping("/users")
    public List <User> getAllUsers(){
        return userRepository.findAll();
    }
    @PostMapping("/criar")
    public User create(@RequestBody User userModel){
    var user_cpf = userRepository.findByCpf(userModel.getCpf());
    if(user_cpf != null){   
        System.out.println("Cpf já cadastrado!");
        return null;
    }
    userModel.setBonus(50);
    var userCreated = this.userRepository.save(userModel);
    return userCreated;
    }
}
