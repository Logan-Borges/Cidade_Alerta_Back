package br.pucpr.AlertCity.service;

import br.pucpr.AlertCity.dto.UsuarioDTO;
import br.pucpr.AlertCity.model.Usuario;
import br.pucpr.AlertCity.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioDTO criarUsuario(UsuarioDTO dto) {

        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());

        Usuario salvo = repository.save(usuario);

        UsuarioDTO response = new UsuarioDTO();
        response.setId(salvo.getId());
        response.setNome(salvo.getNome());
        response.setEmail(salvo.getEmail());

        return response;
    }
}