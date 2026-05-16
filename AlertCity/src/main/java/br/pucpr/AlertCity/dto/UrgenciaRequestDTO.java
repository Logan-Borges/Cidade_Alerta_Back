package br.pucpr.AlertCity.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UrgenciaRequestDTO {

    @NotNull(message = "Ocorrencia e obrigatoria")
    @JsonAlias({"ocorrencia", "occorrencia", "ocorrenciaId"})
    private Long ocorrenciaId;

    @JsonAlias({"usuario", "usuarioId"})
    private Long usuarioId;
}
