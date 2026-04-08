package br.pucpr.AlertCity.security;

import br.pucpr.AlertCity.model.Usuario;
import br.pucpr.AlertCity.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository repository;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;

    public AuthResponse login(AuthRequest request) {

        try {

            Usuario user = repository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            System.out.println("ROLE: " + user.getRole());

            System.out.println("SENHA DIGITADA: " + request.getSenha());
            System.out.println("SENHA BANCO: " + user.getSenha());

            if (!encoder.matches(request.getSenha(), user.getSenha())) {
                throw new RuntimeException("Senha inválida");
            }

            UserAuthentication auth = new UserAuthentication();
            auth.setEmail(user.getEmail());
            auth.setRole(user.getRole());

            System.out.println("ANTES DO TOKEN");

            String token = jwtService.generateToken(auth);

            System.out.println("DEPOIS DO TOKEN");

            AuthResponse response = new AuthResponse();
            response.setEmail(user.getEmail());
            response.setToken(token);

            return response;

        } catch (Exception e) {
            e.printStackTrace(); // 🔥 ISSO AQUI É O SEGREDO
            throw e;
        }
    }
}