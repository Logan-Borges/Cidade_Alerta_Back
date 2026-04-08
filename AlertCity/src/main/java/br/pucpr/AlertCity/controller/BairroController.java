package br.pucpr.AlertCity.controller;

import br.pucpr.AlertCity.model.Bairro;
import br.pucpr.AlertCity.repository.BairroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bairros")
@RequiredArgsConstructor
public class BairroController {
    private final BairroRepository bairroRepository;

    @GetMapping
    public List<Bairro> listarBairros() {
        return bairroRepository.findAll();
    }
}
