package br.pucpr.AlertCity.service;

import br.pucpr.AlertCity.dto.AnalyticsDTO;
import br.pucpr.AlertCity.model.Ocorrencia;
import br.pucpr.AlertCity.repository.OcorrenciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final OcorrenciaRepository ocorrenciaRepository;

    public AnalyticsDTO calcular() {
        List<Ocorrencia> todas = ocorrenciaRepository.findAll();

        AnalyticsDTO dto = new AnalyticsDTO();

        // ── Contadores ─────────────────────────────────────────────────
        dto.setTotal(todas.size());

        dto.setAtivos(todas.stream()
                .filter(o -> "ativo".equalsIgnoreCase(o.getStatus()))
                .count());

        dto.setCriticos(todas.stream()
                .filter(o -> "critica".equalsIgnoreCase(o.getUrgencia()))
                .count());

        dto.setResolvidos(todas.stream()
                .filter(o -> "resolvido".equalsIgnoreCase(o.getStatus()))
                .count());

        // ── Por tipo ───────────────────────────────────────────────────
        dto.setPorTipo(agrupar(todas, Ocorrencia::getTipo));

        // ── Por status ─────────────────────────────────────────────────
        dto.setPorStatus(agrupar(todas, Ocorrencia::getStatus));

        // ── Por urgência ───────────────────────────────────────────────
        dto.setPorUrgencia(agrupar(todas, Ocorrencia::getUrgencia));

        // ── Timeline – últimos 7 dias ──────────────────────────────────
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<AnalyticsDTO.DayCount> timeline = new ArrayList<>();
        LocalDate hoje = LocalDate.now();

        for (int i = 6; i >= 0; i--) {
            LocalDate dia = hoje.minusDays(i);
            String diaStr = dia.format(fmt);
            long count = todas.stream()
                    .filter(o -> o.getData() != null &&
                            o.getData().toLocalDate().equals(dia))
                    .count();
            timeline.add(new AnalyticsDTO.DayCount(diaStr, count));
        }
        dto.setTimelineSemana(timeline);

        // ── Top 7 bairros ──────────────────────────────────────────────
        Map<Long, long[]> bairroMap = new LinkedHashMap<>();
        Map<Long, String> bairroNomes = new LinkedHashMap<>();

        for (Ocorrencia o : todas) {
            if (o.getBairro() == null) continue;
            Long bid = o.getBairro().getId();
            bairroMap.computeIfAbsent(bid, k -> new long[]{0})[0]++;
            bairroNomes.putIfAbsent(bid,
                    o.getBairroNome() != null ? o.getBairroNome() : o.getBairro().getNome());
        }

        List<AnalyticsDTO.BairroCount> topBairros = bairroMap.entrySet().stream()
                .sorted((a, b) -> Long.compare(b.getValue()[0], a.getValue()[0]))
                .limit(7)
                .map(e -> new AnalyticsDTO.BairroCount(
                        e.getKey(),
                        bairroNomes.getOrDefault(e.getKey(), "Desconhecido"),
                        e.getValue()[0]))
                .collect(Collectors.toList());

        dto.setTopBairros(topBairros);

        return dto;
    }

    // ── Helpers ────────────────────────────────────────────────────────
    private Map<String, Long> agrupar(List<Ocorrencia> lista,
                                      java.util.function.Function<Ocorrencia, String> campo) {
        return lista.stream()
                .collect(Collectors.groupingBy(
                        o -> Optional.ofNullable(campo.apply(o)).orElse("outros"),
                        Collectors.counting()));
    }
}