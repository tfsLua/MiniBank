package com.squad21.pitang.User.Manager.ManagerRepository;

import java.util.UUID;
import com.squad21.pitang.User.Manager.ManagerModel.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface MnRepository extends JpaRepository<MnModel, UUID>{
    MnModel findBynome(String nome);
    MnModel findByCpf(String cpf);
}
