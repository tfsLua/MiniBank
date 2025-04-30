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

import com.squad21.pitang.User.dto.UserDTO;
import jakarta.validation.Valid;
import java.math.BigDecimal;

import com.squad21.pitang.User.User;
import com.squad21.pitang.User.Repository.IUserRepository;

import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping("/teste")
public class UserController {
    @Autowired
    private IUserRepository userRepository;

    @GetMapping("/users")
    public List <User> getAllUsers(){
        return userRepository.findAll();
    }

    @PostMapping("/criar")
    public ResponseEntity create(@Valid @RequestBody UserDTO data){

        var user_cpf = userRepository.findByCpf(data.cpf());
        if(user_cpf != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\": \"CPF já cadastrado!\"}");
        }

        User newUser = new User();
        newUser.setNome(data.nome());
        newUser.setCpf(data.cpf());
        newUser.setEmail(data.email());
        newUser.setEndereco(data.endereco());
        newUser.setSaldo(new BigDecimal("50.00"));

        var passwordHashred = BCrypt.withDefaults().
            hashToString(12, data.senha().toCharArray());
        newUser.setSenha(passwordHashred);

        var userCreated = this.userRepository.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }
}
