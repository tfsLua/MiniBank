package com.squad21.pitang.User;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity(name = "tabela users")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;
    private String nome;
    @Column(unique = true, length = 11)
    /*
     * Usar string no cpf inves de int pois, o int não reconhece o 0.
     * Também não iria reconhecer o padrão "XX.XX.XX---X" etc.
    */
    private String cpf;
    private String endereco;
    private String email;
    private String senha;
    private BigDecimal saldo;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
}