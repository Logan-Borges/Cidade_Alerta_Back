package br.pucpr.AlertCity.repository;

import br.pucpr.AlertCity.model.Ocorrencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Long> {

    // Conta por status
    long countByStatus(String status);

    // Conta por urgencia
    long countByUrgencia(String urgencia);

    // Ocorrências em um intervalo de datas (usado para timeline)
    List<Ocorrencia> findByDataBetween(LocalDateTime inicio, LocalDateTime fim);

    // Top bairros (retorna [bairro_id, bairro_nome, count] ordenado desc)
    @Query("""
        SELECT o.bairro.id, o.bairroNome, COUNT(o)
        FROM Ocorrencia o
        WHERE o.bairro IS NOT NULL
        GROUP BY o.bairro.id, o.bairroNome
        ORDER BY COUNT(o) DESC
        """)
    List<Object[]> findTopBairros();
}