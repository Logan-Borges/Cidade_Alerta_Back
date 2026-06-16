package br.pucpr.AlertCity.service;

import br.pucpr.AlertCity.dto.AnalyticsDTO;
import br.pucpr.AlertCity.model.Bairro;
import br.pucpr.AlertCity.model.Ocorrencia;
import br.pucpr.AlertCity.repository.OcorrenciaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnalyticsServiceTest {

    @Mock
    private OcorrenciaRepository repository;

    @InjectMocks
    private AnalyticsService service;

    @Test
    void deveCalcularAnalyticsCorretamente() {

        Bairro centro = new Bairro();
        centro.setId(1L);
        centro.setNome("Centro");

        Ocorrencia o1 = new Ocorrencia();
        o1.setTipo("Buraco");
        o1.setStatus("ativo");
        o1.setUrgencia("critica");
        o1.setData(LocalDateTime.now());
        o1.setBairro(centro);

        Ocorrencia o2 = new Ocorrencia();
        o2.setTipo("Iluminacao");
        o2.setStatus("resolvido");
        o2.setUrgencia("media");
        o2.setData(LocalDateTime.now());
        o2.setBairro(centro);

        when(repository.findAll())
                .thenReturn(List.of(o1, o2));

        AnalyticsDTO dto = service.calcular();

        assertEquals(2, dto.getTotal());
        assertEquals(1, dto.getAtivos());
        assertEquals(1, dto.getCriticos());
        assertEquals(1, dto.getResolvidos());

        assertEquals(1L, dto.getPorTipo().get("Buraco"));
        assertEquals(1L, dto.getPorTipo().get("Iluminacao"));

        assertEquals(1L, dto.getPorStatus().get("ativo"));
        assertEquals(1L, dto.getPorStatus().get("resolvido"));

        assertEquals(1L, dto.getPorUrgencia().get("critica"));
        assertEquals(1L, dto.getPorUrgencia().get("media"));

        assertFalse(dto.getTimelineSemana().isEmpty());

        assertEquals(1, dto.getTopBairros().size());
        assertEquals("Centro", dto.getTopBairros().get(0).bairroNome());
        assertEquals(2, dto.getTopBairros().get(0).count());
    }

    @Test
    void deveRetornarAnalyticsVazioQuandoNaoExistemOcorrencias() {

        when(repository.findAll()).thenReturn(List.of());

        AnalyticsDTO dto = service.calcular();

        assertEquals(0, dto.getTotal());
        assertEquals(0, dto.getAtivos());
        assertEquals(0, dto.getCriticos());
        assertEquals(0, dto.getResolvidos());

        assertTrue(dto.getPorTipo().isEmpty());
        assertTrue(dto.getPorStatus().isEmpty());
        assertTrue(dto.getPorUrgencia().isEmpty());

        assertEquals(7, dto.getTimelineSemana().size());

        assertTrue(dto.getTopBairros().isEmpty());
    }

    @Test
    void deveCobrirCasosNulos() {

        Bairro bairro = new Bairro();
        bairro.setId(1L);
        bairro.setNome("Centro");

        Ocorrencia o = new Ocorrencia();

        o.setTipo(null);
        o.setStatus(null);
        o.setUrgencia(null);

        o.setBairro(bairro);
        o.setBairroNome("Nome Personalizado");

        when(repository.findAll())
                .thenReturn(List.of(o));

        AnalyticsDTO dto = service.calcular();

        assertEquals(1L, dto.getPorTipo().get("outros"));
        assertEquals(1L, dto.getPorStatus().get("outros"));
        assertEquals(1L, dto.getPorUrgencia().get("outros"));

        assertEquals(
                "Nome Personalizado",
                dto.getTopBairros().get(0).bairroNome()
        );
    }

    @Test
    void deveIgnorarOcorrenciaSemBairro() {

        Ocorrencia o = new Ocorrencia();
        o.setTipo("Buraco");

        when(repository.findAll())
                .thenReturn(List.of(o));

        AnalyticsDTO dto = service.calcular();

        assertTrue(dto.getTopBairros().isEmpty());
    }
}