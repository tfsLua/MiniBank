package com.squad21.pitang.User.UserRepository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.squad21.pitang.User.User;
@Repository
public interface UserRepository extends JpaRepository<User, UUID>{
    User findBynome(String nome);
    User findByCpf(String cpf);  
}