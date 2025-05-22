package com.squad21.pitang.User.Manager.ManagerService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.squad21.pitang.User.Manager.ManagerModel.MnModel;
import com.squad21.pitang.User.Manager.ManagerRepository.MnRepository;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.annotation.PostConstruct;

@Component
    public class CreateManager {
    @Autowired
        private MnRepository managerRepository;
        @PostConstruct
        public void Create_Manager(){
        if(managerRepository.findBynome("gerente") == null){
        MnModel newClient = new MnModel();
        newClient.setNome("gerente");
        newClient.setSenha("gerencia");

        var passwordHashred = BCrypt.withDefaults().
            hashToString(12, 
            newClient.getSenha().
            toCharArray());
            newClient.setSenha(passwordHashred);

            managerRepository.save(newClient);
        }
    }
    }