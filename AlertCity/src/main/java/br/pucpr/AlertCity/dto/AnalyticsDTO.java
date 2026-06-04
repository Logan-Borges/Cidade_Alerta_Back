package br.pucpr.AlertCity.dto;

import java.util.List;
import java.util.Map;

public class AnalyticsDTO {

    // ── Contadores principais ────────────────────────────────────────
    private long total;
    private long ativos;
    private long criticos;
    private long resolvidos;

    // ── Distribuições (chave = valor do enum, valor = contagem) ─────
    /** Contagem por tipo: infraestrutura, seguranca, transito… */
    private Map<String, Long> porTipo;

    /** Contagem por status: ativo, em_analise, em_atendimento, resolvido */
    private Map<String, Long> porStatus;

    /** Contagem por urgencia: baixa, media, alta, critica */
    private Map<String, Long> porUrgencia;

    // ── Timeline (últimos 7 dias) ────────────────────────────────────
    private List<DayCount> timelineSemana;

    // ── Top bairros ──────────────────────────────────────────────────
    /** Top 7 bairros com mais ocorrências: {bairroId, bairroNome, count} */
    private List<BairroCount> topBairros;

    // ──────────────────────────────────────────────────────────────────
    // Sub-records
    // ──────────────────────────────────────────────────────────────────

    public record DayCount(String date, long count) {}

    public record BairroCount(Long bairroId, String bairroNome, long count) {}

    // ── Getters & Setters ────────────────────────────────────────────

    public long getTotal() { return total; }
    public void setTotal(long total) { this.total = total; }

    public long getAtivos() { return ativos; }
    public void setAtivos(long ativos) { this.ativos = ativos; }

    public long getCriticos() { return criticos; }
    public void setCriticos(long criticos) { this.criticos = criticos; }

    public long getResolvidos() { return resolvidos; }
    public void setResolvidos(long resolvidos) { this.resolvidos = resolvidos; }

    public Map<String, Long> getPorTipo() { return porTipo; }
    public void setPorTipo(Map<String, Long> porTipo) { this.porTipo = porTipo; }

    public Map<String, Long> getPorStatus() { return porStatus; }
    public void setPorStatus(Map<String, Long> porStatus) { this.porStatus = porStatus; }

    public Map<String, Long> getPorUrgencia() { return porUrgencia; }
    public void setPorUrgencia(Map<String, Long> porUrgencia) { this.porUrgencia = porUrgencia; }

    public List<DayCount> getTimelineSemana() { return timelineSemana; }
    public void setTimelineSemana(List<DayCount> timelineSemana) { this.timelineSemana = timelineSemana; }

    public List<BairroCount> getTopBairros() { return topBairros; }
    public void setTopBairros(List<BairroCount> topBairros) { this.topBairros = topBairros; }
}