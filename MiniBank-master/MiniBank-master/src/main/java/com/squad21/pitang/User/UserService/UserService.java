package com.squad21.pitang.User.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.squad21.pitang.User.GenericUser;
import com.squad21.pitang.User.User;
import com.squad21.pitang.User.UserDTO.UserDTO;
import com.squad21.pitang.User.UserRepository.*;

import at.favre.lib.crypto.bcrypt.BCrypt;

@Service
    public class UserService {
    @Autowired
        private UserRepository userRepository;

        public List<User> getAllUsers() {
            return userRepository.findAll();
        }

        public ResponseEntity<?> createUser(UserDTO data){
        
        var user_cpf = userRepository.findByCpf(data.cpf());
        if(user_cpf != null){
            return ResponseEntity.
            status(HttpStatus.BAD_REQUEST)
            .body("{\"error\": \"CPF já cadastrado!\"}");
        }

        User newUser = new GenericUser();
        newUser.setNome(data.nome());
        newUser.setCpf(data.cpf());
        newUser.setEmail(data.email());
        newUser.setEndereco(data.endereco());
        
        var passwordHashred = BCrypt.withDefaults().
            hashToString(12, 
            data.senha().
            toCharArray());
            newUser.setSenha(passwordHashred);

        var userCreated = this.userRepository.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }
    }