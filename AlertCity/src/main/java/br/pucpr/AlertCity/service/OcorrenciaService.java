package br.pucpr.AlertCity.service;

import br.pucpr.AlertCity.dto.OcorrenciaDTO;
import br.pucpr.AlertCity.model.Bairro;
import br.pucpr.AlertCity.model.Ocorrencia;
import br.pucpr.AlertCity.model.Usuario;
import br.pucpr.AlertCity.repository.BairroRepository;
import br.pucpr.AlertCity.repository.OcorrenciaRepository;
import br.pucpr.AlertCity.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OcorrenciaService {

    private final OcorrenciaRepository ocorrenciaRepository;
    private final UsuarioRepository usuarioRepository;
    private final BairroRepository bairroRepository;


    public OcorrenciaDTO salvar(OcorrenciaDTO dto) {

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Bairro bairro = bairroRepository.findById(dto.getBairroId())
                .orElseThrow(() -> new RuntimeException("Bairro não encontrado"));

        Ocorrencia ocorrencia = new Ocorrencia();
        ocorrencia.setTitulo(dto.getTitulo());
        ocorrencia.setDescricao(dto.getDescricao());
        ocorrencia.setTipo(dto.getTipo());
        ocorrencia.setUsuario(usuario);
        ocorrencia.setBairro(bairro);

        Ocorrencia salva = ocorrenciaRepository.save(ocorrencia);

        return converterParaDTO(salva);
    }


    public List<OcorrenciaDTO> listar() {
        return ocorrenciaRepository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }


    private OcorrenciaDTO converterParaDTO(Ocorrencia o) {
        OcorrenciaDTO dto = new OcorrenciaDTO();
        dto.setId(o.getId());
        dto.setTitulo(o.getTitulo());
        dto.setDescricao(o.getDescricao());
        dto.setTipo(o.getTipo());
        dto.setUsuarioId(o.getUsuario().getId());
        dto.setBairroId(o.getBairro().getId());
        return dto;
    }

    public OcorrenciaDTO atualizar(Long id, OcorrenciaDTO dto) {

        Ocorrencia o = ocorrenciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ocorrência não encontrada"));

        o.setTitulo(dto.getTitulo());
        o.setDescricao(dto.getDescricao());
        o.setTipo(dto.getTipo());

        return converterParaDTO(ocorrenciaRepository.save(o));

    }

    public void deletar(Long id) {
        if (!ocorrenciaRepository.existsById(id)) {
            throw new RuntimeException("Ocorrência não encontrada");
        }
        ocorrenciaRepository.deleteById(id);
    }
}