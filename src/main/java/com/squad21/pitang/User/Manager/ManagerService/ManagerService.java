package com.squad21.pitang.User.Manager.ManagerService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.squad21.pitang.User.Manager.DTO.ManagerDTO;
import com.squad21.pitang.User.Manager.ManagerModel.MnModel;
import com.squad21.pitang.User.Manager.ManagerRepository.MnRepository;

import at.favre.lib.crypto.bcrypt.BCrypt;

@Service
    public class ManagerService {
    @Autowired
        private MnRepository managerRepository;

        public List<MnModel> getAllUsers() {
            return managerRepository.findAll();
        }

        public ResponseEntity<?> createUser(ManagerDTO data){
        
        var user_cpf = managerRepository.findByCpf(data.cpf());
        if(user_cpf != null){
            return ResponseEntity.
            status(HttpStatus.BAD_REQUEST)
            .body("{\"error\": \"CPF já cadastrado!\"}");
        }

        MnModel newClient = new MnModel();
        newClient.setNome(data.nome());
        newClient.setCpf(data.cpf());
        newClient.setEmail(data.email());
        newClient.setEndereco(data.endereco());
        /* 
        2792948BX422@!
        if(CodigoAcesso == 2792948BX422@!)
        */ 
        newClient.setCodigoAcesso(data.CodigoAcesso());
        if ("2792948BX422@!".equals(data.CodigoAcesso())) {
        
        var passwordHashred = BCrypt.withDefaults().
            hashToString(12, 
            data.senha().
            toCharArray());
            newClient.setSenha(passwordHashred);

        var clientCreated = this.managerRepository.save(newClient);

        return ResponseEntity.status(HttpStatus.CREATED).body(clientCreated);
        } else {
            return ResponseEntity.status(404).body("Código de acesso incorreto!");
        }
    }
}