package br.pucpr.AlertCity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UrgenciaResponseDTO {
    private Long usuarioId;
    private Long ocorrenciaId;
    private boolean curtido;
    private long totalUrgencias;
}
