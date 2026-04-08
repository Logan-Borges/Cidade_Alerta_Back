package br.pucpr.AlertCity.service;

import br.pucpr.AlertCity.security.Role;
import br.pucpr.AlertCity.dto.LoginDTO;
import br.pucpr.AlertCity.dto.UsuarioDTO;
import br.pucpr.AlertCity.dto.UsuarioResponseDTO;
import br.pucpr.AlertCity.exception.EmailJaCadastradoException;
import br.pucpr.AlertCity.exception.SenhaInvalidaException;
import br.pucpr.AlertCity.exception.UsuarioNaoEncontradoException;
import br.pucpr.AlertCity.model.Usuario;
import br.pucpr.AlertCity.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UsuarioResponseDTO criarUsuario(UsuarioDTO dto) {

        if (repository.existsByEmail(dto.getEmail())) {
            throw new EmailJaCadastradoException("Email já cadastrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(encoder.encode(dto.getSenha()));
        usuario.setRole(Role.USER);

        Usuario salvo = repository.save(usuario);

        UsuarioResponseDTO response = new UsuarioResponseDTO();
        response.setMensagem("Cadastro concluído com sucesso");
        response.setUsuario(converterParaDTO(salvo));

        return response;
    }

    public List<UsuarioDTO> listarUsuarios() {
        return repository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    public UsuarioDTO atualizarUsuario(Long id, UsuarioDTO dto) {

        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));

        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());

        if (dto.getSenha() != null && !dto.getSenha().isEmpty()) {
            usuario.setSenha(encoder.encode(dto.getSenha()));
        }

        Usuario atualizado = repository.save(usuario);

        return converterParaDTO(atualizado);
    }

    public void deletarUsuario(Long id) {

        if (!repository.existsById(id)) {
            throw new UsuarioNaoEncontradoException("Usuário não encontrado");
        }

        repository.deleteById(id);
    }

    private UsuarioDTO converterParaDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setSenha(usuario.getSenha()); // hash aparece no Postman

        return dto;
    }

    public UsuarioResponseDTO fazerLogin(LoginDTO loginDTO) {
        Usuario usuario = repository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado com este email"));

        if (!encoder.matches(loginDTO.getSenha(), usuario.getSenha())) {
            throw new SenhaInvalidaException("Senha inválida");
        }

        UsuarioResponseDTO response = new UsuarioResponseDTO();
        response.setMensagem("Login realizado com sucesso");
        response.setUsuario(converterParaDTO(usuario));

        return response;
    }
}