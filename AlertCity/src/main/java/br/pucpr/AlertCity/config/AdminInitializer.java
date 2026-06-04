// TESTE

package br.pucpr.AlertCity.config;

import br.pucpr.AlertCity.model.Bairro;
import br.pucpr.AlertCity.model.Usuario;
import br.pucpr.AlertCity.repository.BairroRepository;
import br.pucpr.AlertCity.repository.UsuarioRepository;
import br.pucpr.AlertCity.security.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class AdminInitializer {

    private static final String ADMIN_EMAIL = "admin@cidadealerta.com";
    private static final String ADMIN_SENHA = "Admin@1234";
    private static final String ADMIN_NOME  = "Administrador";
    private static final String ADMIN_CPF   = "00000000000";

    @Bean
    public CommandLineRunner initAdmin(
            UsuarioRepository usuarioRepository,
            BairroRepository  bairroRepository,
            PasswordEncoder   passwordEncoder
    ) {
        return args -> {
            // Só cria se ainda não existir
            if (usuarioRepository.findByEmail(ADMIN_EMAIL).isPresent()) return;

            // Usuario precisa de um bairro (constraint NOT NULL) — usa o primeiro disponível
            Bairro bairro = bairroRepository.findAll()
                    .stream()
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException(
                            "Nenhum bairro encontrado. Execute o BairroInitializer antes."));

            Usuario admin = new Usuario();
            admin.setNome(ADMIN_NOME);
            admin.setEmail(ADMIN_EMAIL);
            admin.setSenha(passwordEncoder.encode(ADMIN_SENHA));
            admin.setCpf(ADMIN_CPF);
            admin.setBairro(bairro);
            admin.setRole(Role.ADMIN);

            usuarioRepository.save(admin);

            System.out.println("✅ Admin padrão criado → " + ADMIN_EMAIL + " / " + ADMIN_SENHA);
        };
    }
}