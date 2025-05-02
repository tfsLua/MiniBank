package com.squad21.pitang.User.Manager.ManagerRepository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.squad21.pitang.User.Manager.ManagerModel.Manager;

public interface ManagerRepository extends JpaRepository<Manager, UUID> {
    Manager findByCpf(String cpf);
}