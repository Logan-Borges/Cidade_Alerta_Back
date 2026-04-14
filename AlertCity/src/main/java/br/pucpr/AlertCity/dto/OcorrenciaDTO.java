package br.pucpr.AlertCity.dto;

import lombok.Data;

@Data
public class OcorrenciaDTO {
    private String titulo;
    private String descricao;
    private String tipo;
    private Long usuarioId;
    private Long bairroId;
}