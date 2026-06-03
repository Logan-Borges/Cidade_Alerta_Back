package br.pucpr.AlertCity.service;

import br.pucpr.AlertCity.dto.OcorrenciaDTO;
import br.pucpr.AlertCity.model.Bairro;
import br.pucpr.AlertCity.model.Ocorrencia;
import br.pucpr.AlertCity.model.Usuario;
import br.pucpr.AlertCity.exception.FiltroInvalidoException;
import br.pucpr.AlertCity.repository.BairroRepository;
import br.pucpr.AlertCity.repository.OcorrenciaRepository;
import br.pucpr.AlertCity.repository.UrgenciaRepository;
import br.pucpr.AlertCity.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OcorrenciaService {

    private final OcorrenciaRepository ocorrenciaRepository;
    private final UrgenciaRepository urgenciaRepository;
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
        ocorrencia.setUrgencia(dto.getUrgencia());
        ocorrencia.setStatus(dto.getStatus());
        ocorrencia.setUsuario(usuario);
        ocorrencia.setBairro(bairro);
        ocorrencia.setCep(dto.getCep());
        ocorrencia.setRua(dto.getRua());
        ocorrencia.setBairroNome(dto.getBairroNome());
        ocorrencia.setLat(dto.getLat());
        ocorrencia.setLng(dto.getLng());

        if (dto.getFotoBase64() != null) {
            ocorrencia.setFoto(Base64.getDecoder().decode(dto.getFotoBase64()));
        }

        return converterParaDTO(ocorrenciaRepository.save(ocorrencia));
    }

    public List<OcorrenciaDTO> listar() {
        return listar(null);
    }

    public List<OcorrenciaDTO> listar(String filter) {
        if (filter == null || filter.isBlank()) {
            return ocorrenciaRepository.findAll()
                    .stream()
                    .map(this::converterParaDTO)
                    .toList();
        }

        String[] parts = filter.split("=", 2);
        if (parts.length != 2 || parts[0].isBlank()) {
            throw new FiltroInvalidoException("Filtro inválido. Use o formato campo=valor.");
        }

        String field = parts[0].trim().toLowerCase();
        String value = parts[1].trim();
        if (value.isBlank()) {
            throw new FiltroInvalidoException("Filtro inválido. O valor não pode ficar em branco.");
        }

        return ocorrenciaRepository.findAll()
                .stream()
                .filter(o -> matchesFilter(o, field, value))
                .map(this::converterParaDTO)
                .toList();
    }

    private boolean matchesFilter(Ocorrencia o, String field, String value) {
        switch (field) {
            case "id":
                return o.getId() != null && o.getId().toString().equals(value);
            case "titulo":
                return matches(o.getTitulo(), value);
            case "descricao":
                return matches(o.getDescricao(), value);
            case "tipo":
                return matches(o.getTipo(), value);
            case "urgencia":
                return matches(o.getUrgencia(), value);
            case "status":
                return matches(o.getStatus(), value);
            case "cep":
                return matches(o.getCep(), value);
            case "rua":
                return matches(o.getRua(), value);
            case "bairronome":
                return matches(o.getBairroNome(), value);
            case "usuarioid":
            case "usuario_id":
                return o.getUsuario() != null && o.getUsuario().getId() != null && o.getUsuario().getId().toString().equals(value);
            case "bairroid":
            case "bairro_id":
                return o.getBairro() != null && o.getBairro().getId() != null && o.getBairro().getId().toString().equals(value);
            default:
                throw new FiltroInvalidoException("Campo de filtro inválido: " + field);
        }
    }

    private boolean matches(String source, String filter) {
        return source != null && source.toLowerCase().contains(filter.toLowerCase());
    }

    private OcorrenciaDTO converterParaDTO(Ocorrencia o) {
        OcorrenciaDTO dto = new OcorrenciaDTO();
        dto.setId(o.getId());
        dto.setTitulo(o.getTitulo());
        dto.setDescricao(o.getDescricao());
        dto.setTipo(o.getTipo());
        dto.setUrgencia(o.getUrgencia());
        dto.setStatus(o.getStatus());
        dto.setUsuarioId(o.getUsuario().getId());
        dto.setBairroId(o.getBairro().getId());
        dto.setCep(o.getCep());
        dto.setRua(o.getRua());
        dto.setBairroNome(o.getBairroNome());
        dto.setLat(o.getLat());
        dto.setLng(o.getLng());
        dto.setTotalUrgencia(urgenciaRepository.countByOcorrencia_Id(o.getId()));

        if (o.getFoto() != null) {
            dto.setFotoBase64(Base64.getEncoder().encodeToString(o.getFoto()));
        }

        return dto;
    }

    public OcorrenciaDTO atualizar(Long id, OcorrenciaDTO dto) {
        Ocorrencia o = ocorrenciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ocorrência não encontrada"));

        o.setTitulo(dto.getTitulo());
        o.setDescricao(dto.getDescricao());
        o.setTipo(dto.getTipo());
        o.setUrgencia(dto.getUrgencia());
        o.setStatus(dto.getStatus());
        o.setCep(dto.getCep());
        o.setRua(dto.getRua());
        o.setBairroNome(dto.getBairroNome());
        o.setLat(dto.getLat());
        o.setLng(dto.getLng());

        if (dto.getFotoBase64() != null) {
            o.setFoto(Base64.getDecoder().decode(dto.getFotoBase64()));
        }

        return converterParaDTO(ocorrenciaRepository.save(o));
    }

    public void deletar(Long id) {
        if (!ocorrenciaRepository.existsById(id)) {
            throw new RuntimeException("Ocorrência não encontrada");
        }
        ocorrenciaRepository.deleteById(id);
    }
}