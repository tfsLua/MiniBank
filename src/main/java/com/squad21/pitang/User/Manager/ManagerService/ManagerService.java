package com.squad21.pitang.User.Manager.ManagerService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.squad21.pitang.User.Manager.ManagerModel.Manager;
import com.squad21.pitang.User.Manager.ManagerRepository.ManagerRepository;
import com.squad21.pitang.User.UserDTO.UserDTO;

import at.favre.lib.crypto.bcrypt.BCrypt;

@Service
public class ManagerService {

    @Autowired
    private ManagerRepository managerRepository;

    public List<Manager> getAllManagers() {
        return managerRepository.findAll();
    }

    public ResponseEntity<?> createManager(UserDTO data) {
        var existing = managerRepository.findByCpf(data.cpf());
        if (existing != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("{\"error\": \"CPF já cadastrado!\"}");
        }

        Manager manager = new Manager();
        manager.setNome(data.nome());
        manager.setCpf(data.cpf());
        manager.setEmail(data.email());
        manager.setEndereco(data.endereco());

        var hashedPassword = BCrypt.withDefaults()
        .hashToString(12, data.senha().toCharArray());
        manager.setSenha(hashedPassword);

        var saved = managerRepository.save(manager);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}
