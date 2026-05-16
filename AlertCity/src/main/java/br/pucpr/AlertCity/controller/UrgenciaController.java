package br.pucpr.AlertCity.controller;

import br.pucpr.AlertCity.dto.UrgenciaRequestDTO;
import br.pucpr.AlertCity.dto.UrgenciaResponseDTO;
import br.pucpr.AlertCity.service.UrgenciaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/urgencia")
@RequiredArgsConstructor
public class UrgenciaController {

    private final UrgenciaService service;

    @PostMapping
    public UrgenciaResponseDTO toggle(@RequestBody @Valid UrgenciaRequestDTO dto, Authentication authentication) {
        return service.toggle(dto, authentication);
    }
}
