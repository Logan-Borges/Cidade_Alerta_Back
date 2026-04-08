package br.pucpr.AlertCity.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bairros")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bairro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;
}
