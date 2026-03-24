package br.pucpr.AlertCity.dto;

import lombok.Data;

@Data
public class UsuarioResponseDTO {

    private String mensagem;
    private UsuarioDTO usuario;
}