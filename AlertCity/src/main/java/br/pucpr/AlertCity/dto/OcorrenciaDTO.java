package br.pucpr.AlertCity.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class OcorrenciaDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private String tipo;
    private String urgencia;
    private String status;
    private Long usuarioId;
    private Long bairroId;
    private String fotoBase64;
    private long totalUrgencia;
    private String cep;
    private String rua;
    private String bairroNome;
    private Double lat;
    private Double lng;
    private LocalDateTime data;
}