package br.pucpr.AlertCity.dto;

import br.pucpr.AlertCity.model.Bairro;
import lombok.Data;

@Data
public class UsuarioDTO {

    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private Bairro bairro;
    private String senha;
}