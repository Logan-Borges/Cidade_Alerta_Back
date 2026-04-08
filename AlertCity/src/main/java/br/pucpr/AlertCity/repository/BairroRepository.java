package br.pucpr.AlertCity.repository;

import br.pucpr.AlertCity.model.Bairro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BairroRepository extends JpaRepository<Bairro, Long> {
    boolean existsByNome(String nome);
    Bairro findByNome(String nome);
}
