package com.squad21.pitang.Login;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.squad21.pitang.User.Client.ClientController.ClientRepository.ClientRepository;
import com.squad21.pitang.User.Manager.ManagerRepository.MnRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/login")
public class LoginController {
    
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private MnRepository managerRepository;
    @GetMapping("")
    public ResponseEntity<?> getDados(HttpServletRequest request, HttpServletResponse response){
    UUID idUser = (UUID) request.getAttribute("idUser");
    var saldo = request.getAttribute("saldo");

    var cliente = clientRepository.findById(idUser);
    if(cliente.isPresent()){
        return ResponseEntity.status(201).body("saldo: " + saldo);
    }
    var gerente = managerRepository.findById(idUser);
    if(gerente.isPresent()){
        return ResponseEntity.status(201).body("Bem-vindo!");
    }
    return ResponseEntity.status(404).body("{\"error\": \"Usuário não encontrado.\"}");

}
}