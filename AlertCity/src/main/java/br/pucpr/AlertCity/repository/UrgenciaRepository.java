package br.pucpr.AlertCity.repository;

import br.pucpr.AlertCity.model.Urgencia;
import br.pucpr.AlertCity.model.UrgenciaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UrgenciaRepository extends JpaRepository<Urgencia, UrgenciaId> {
    Optional<Urgencia> findByUsuario_IdAndOcorrencia_Id(Long usuarioId, Long ocorrenciaId);

    long countByOcorrencia_Id(Long ocorrenciaId);

    @Query("SELECT u.ocorrencia.id FROM Urgencia u WHERE u.usuario.id = :usuarioId")
    List<Long> findOcorrenciaIdsByUsuarioId(@Param("usuarioId") Long usuarioId);
}
