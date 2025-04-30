package com.squad21.pitang.User.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.squad21.pitang.User.User;
@Repository
public interface IUserRepository extends JpaRepository<User, Long>{
    User findBynome(String nome);
    User findByCpf(String cpf);  
}
