package br.pucpr.AlertCity.repository;

import br.pucpr.AlertCity.model.Ocorrencia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Long> {
}