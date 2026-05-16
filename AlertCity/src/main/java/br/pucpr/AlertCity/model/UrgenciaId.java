package br.pucpr.AlertCity.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class UrgenciaId implements Serializable {

    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "ocorrencia_id")
    private Long ocorrenciaId;
}
