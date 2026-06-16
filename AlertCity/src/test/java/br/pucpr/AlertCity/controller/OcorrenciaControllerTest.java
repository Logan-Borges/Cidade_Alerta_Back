package br.pucpr.AlertCity.controller;

import br.pucpr.AlertCity.dto.OcorrenciaDTO;
import br.pucpr.AlertCity.dto.StatusUpdateDTO;
import br.pucpr.AlertCity.service.OcorrenciaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OcorrenciaController.class)
@AutoConfigureMockMvc(addFilters = false)
class OcorrenciaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private OcorrenciaService service;

    @Test
    void deveCriarOcorrencia() throws Exception {

        OcorrenciaDTO dto = new OcorrenciaDTO();
        dto.setId(1L);
        dto.setTitulo("Buraco");

        when(service.salvar(any(OcorrenciaDTO.class)))
                .thenReturn(dto);

        mockMvc.perform(
                        post("/ocorrencias")
                                .contentType("application/json")
                                .content(mapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("Buraco"));

        verify(service).salvar(any(OcorrenciaDTO.class));
    }

    @Test
    void deveListarOcorrencias() throws Exception {

        OcorrenciaDTO dto = new OcorrenciaDTO();
        dto.setId(1L);

        when(service.listar(null))
                .thenReturn(List.of(dto));

        mockMvc.perform(get("/ocorrencias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));

        verify(service).listar(null);
    }

    @Test
    void deveListarOcorrenciasComFiltro() throws Exception {

        when(service.listar("status=ativo"))
                .thenReturn(List.of());

        mockMvc.perform(
                        get("/ocorrencias")
                                .param("filter", "status=ativo")
                )
                .andExpect(status().isOk());

        verify(service).listar("status=ativo");
    }

    @Test
    void deveAtualizarOcorrencia() throws Exception {

        OcorrenciaDTO dto = new OcorrenciaDTO();
        dto.setId(1L);
        dto.setTitulo("Atualizado");

        when(service.atualizar(eq(1L), any(OcorrenciaDTO.class)))
                .thenReturn(dto);

        mockMvc.perform(
                        put("/ocorrencias/1")
                                .contentType("application/json")
                                .content(mapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Atualizado"));

        verify(service).atualizar(eq(1L), any(OcorrenciaDTO.class));
    }

    @Test
    void deveAtualizarStatus() throws Exception {

        StatusUpdateDTO statusDto = new StatusUpdateDTO();
        statusDto.setStatus("resolvido");

        OcorrenciaDTO retorno = new OcorrenciaDTO();
        retorno.setStatus("resolvido");

        when(service.atualizarStatus(1L, "resolvido"))
                .thenReturn(retorno);

        mockMvc.perform(
                        patch("/ocorrencias/1/status")
                                .contentType("application/json")
                                .content(mapper.writeValueAsString(statusDto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("resolvido"));

        verify(service).atualizarStatus(1L, "resolvido");
    }

    @Test
    void deveDeletarOcorrencia() throws Exception {

        doNothing().when(service).deletar(1L);

        mockMvc.perform(delete("/ocorrencias/1"))
                .andExpect(status().isOk());

        verify(service).deletar(1L);
    }
}