package com.squad21.pitang.User.Client.ClientController.ClientRepository;


import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.squad21.pitang.User.Client.ClientModel.ClientModel;
@Repository
public interface ClientRepository extends JpaRepository<ClientModel, UUID>{
    ClientModel findBynome(String nome);
    ClientModel findByNumeroConta(Long numeroConta);
    ClientModel findByCpf(String cpf);  
}
