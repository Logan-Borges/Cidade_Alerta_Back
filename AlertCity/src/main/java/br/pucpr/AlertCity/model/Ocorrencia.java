package br.pucpr.AlertCity.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ocorrencias")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ocorrencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @Column(length = 1000)
    private String descricao;

    private String tipo;

    private String urgencia;

    private String status;

    @Column(name = "foto_url")
    private String fotoUrl;



    private LocalDateTime data = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "bairro_id")
    private Bairro bairro;
}