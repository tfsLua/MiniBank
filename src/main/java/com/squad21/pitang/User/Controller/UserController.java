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

import at.favre.lib.crypto.bcrypt.BCrypt;

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
    public ResponseEntity create(@RequestBody User userModel){
    var user_cpf = userRepository.findByCpf(userModel.getCpf());
    if(user_cpf != null){   
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe!");
    }
    userModel.setBonus(50);
    var passwordHashred = BCrypt.withDefaults().
    hashToString(12, userModel.getSenha().toCharArray());
    userModel.setSenha(passwordHashred);
    var userCreated = this.userRepository.save(userModel);
    return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }
}
