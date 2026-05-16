package br.pucpr.AlertCity.service;

import br.pucpr.AlertCity.dto.UrgenciaRequestDTO;
import br.pucpr.AlertCity.dto.UrgenciaResponseDTO;
import br.pucpr.AlertCity.model.Ocorrencia;
import br.pucpr.AlertCity.model.Urgencia;
import br.pucpr.AlertCity.model.UrgenciaId;
import br.pucpr.AlertCity.model.Usuario;
import br.pucpr.AlertCity.repository.OcorrenciaRepository;
import br.pucpr.AlertCity.repository.UrgenciaRepository;
import br.pucpr.AlertCity.repository.UsuarioRepository;
import br.pucpr.AlertCity.security.UserAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UrgenciaService {

    private final UrgenciaRepository urgenciaRepository;
    private final UsuarioRepository usuarioRepository;
    private final OcorrenciaRepository ocorrenciaRepository;

    public UrgenciaResponseDTO toggle(UrgenciaRequestDTO dto, Authentication authentication) {
        Usuario usuario = resolverUsuario(dto, authentication);

        Ocorrencia ocorrencia = ocorrenciaRepository.findById(dto.getOcorrenciaId())
                .orElseThrow(() -> new RuntimeException("Ocorrencia nao encontrada"));

        return urgenciaRepository.findByUsuario_IdAndOcorrencia_Id(usuario.getId(), ocorrencia.getId())
                .map(existente -> {
                    urgenciaRepository.delete(existente);
                    long total = urgenciaRepository.countByOcorrencia_Id(ocorrencia.getId());
                    return new UrgenciaResponseDTO(usuario.getId(), ocorrencia.getId(), false, total);
                })
                .orElseGet(() -> {
                    Urgencia urgencia = new Urgencia();
                    urgencia.setId(new UrgenciaId(usuario.getId(), ocorrencia.getId()));
                    urgencia.setUsuario(usuario);
                    urgencia.setOcorrencia(ocorrencia);
                    urgenciaRepository.save(urgencia);

                    long total = urgenciaRepository.countByOcorrencia_Id(ocorrencia.getId());
                    return new UrgenciaResponseDTO(usuario.getId(), ocorrencia.getId(), true, total);
                });
    }

    private Usuario resolverUsuario(UrgenciaRequestDTO dto, Authentication authentication) {
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserAuthentication userAuthentication) {
                return usuarioRepository.findByEmail(userAuthentication.getEmail())
                        .orElseThrow(() -> new RuntimeException("Usuario autenticado nao encontrado"));
            }

            if (authentication.getName() != null) {
                return usuarioRepository.findByEmail(authentication.getName())
                        .orElseThrow(() -> new RuntimeException("Usuario autenticado nao encontrado"));
            }
        }

        if (dto.getUsuarioId() == null) {
            throw new RuntimeException("Usuario e obrigatorio quando nao houver autenticacao");
        }

        return usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));
    }
}
