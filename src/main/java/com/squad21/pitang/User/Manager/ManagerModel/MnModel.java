package com.squad21.pitang.User.Manager.ManagerModel;
import com.squad21.pitang.User.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "Manager login")
public class MnModel extends User {
    @Column
    private String CodigoAcesso;
}
