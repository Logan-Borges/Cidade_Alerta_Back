package br.pucpr.AlertCity.controller;

import br.pucpr.AlertCity.dto.AnalyticsDTO;
import br.pucpr.AlertCity.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService service;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AnalyticsDTO> getDashboard() {
        return ResponseEntity.ok(service.calcular());
    }
}