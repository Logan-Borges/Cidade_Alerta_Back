package br.pucpr.AlertCity.controller;

import br.pucpr.AlertCity.dto.OcorrenciaDTO;
import br.pucpr.AlertCity.dto.StatusUpdateDTO;
import br.pucpr.AlertCity.service.OcorrenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ocorrencias")
@RequiredArgsConstructor
public class OcorrenciaController {

    private final OcorrenciaService service;

    @PostMapping
    public OcorrenciaDTO criar(@RequestBody OcorrenciaDTO dto) {
        return service.salvar(dto);
    }

    @GetMapping
    public List<OcorrenciaDTO> listar(@RequestParam(value = "filter", required = false) String filter) {
        return service.listar(filter);
    }

    @PutMapping("/{id}")
    public OcorrenciaDTO atualizar(@PathVariable Long id, @RequestBody OcorrenciaDTO dto) {
        return service.atualizar(id, dto);
    }

    // ── T49: Atualizar apenas o status (exclusivo ADM) ────────────────────────
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public OcorrenciaDTO atualizarStatus(@PathVariable Long id,
                                         @RequestBody StatusUpdateDTO dto) {
        return service.atualizarStatus(id, dto.getStatus());
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
