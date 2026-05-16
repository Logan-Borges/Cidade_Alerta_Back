package br.pucpr.AlertCity.repository;

import br.pucpr.AlertCity.model.Urgencia;
import br.pucpr.AlertCity.model.UrgenciaId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrgenciaRepository extends JpaRepository<Urgencia, UrgenciaId> {
    Optional<Urgencia> findByUsuario_IdAndOcorrencia_Id(Long usuarioId, Long ocorrenciaId);

    long countByOcorrencia_Id(Long ocorrenciaId);
}
