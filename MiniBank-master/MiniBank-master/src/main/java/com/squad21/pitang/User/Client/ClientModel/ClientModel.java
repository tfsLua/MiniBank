package com.squad21.pitang.User.Client.ClientModel;

import java.math.BigDecimal;

import com.squad21.pitang.User.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "Client")
public class ClientModel extends User{
    @Column(unique = true)
    private Long numeroConta;
    private BigDecimal saldo;
}
