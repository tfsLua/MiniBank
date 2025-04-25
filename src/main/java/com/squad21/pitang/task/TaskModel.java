package com.squad21.pitang.task;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "task users")
public class TaskModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;
    private Long id_usuario;
    private String descricao;
    @Column(length = 50)
    private String titulo;
    
    private LocalDateTime data_inicio;
    private LocalDateTime data_fim;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private String prioridade;
    
}