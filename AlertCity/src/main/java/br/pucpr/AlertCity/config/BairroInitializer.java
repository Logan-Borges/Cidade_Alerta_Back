package br.pucpr.AlertCity.config;

import br.pucpr.AlertCity.model.Bairro;
import br.pucpr.AlertCity.repository.BairroRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class BairroInitializer {
    @Bean
    public CommandLineRunner initBairros(BairroRepository bairroRepository) {
        return args -> {
            List<String> bairrosIniciais = List.of(
                    "Abranches",
                    "Água Verde",
                    "Ahú",
                    "Alto Boqueirão",
                    "Alto da Glória",
                    "Alto da Rua XV",
                    "Atuba",
                    "Augusta",
                    "Bacacheri",
                    "Bairro Alto",
                    "Barreirinha",
                    "Batel",
                    "Bigorrilho",
                    "Boa Vista",
                    "Bom Retiro",
                    "Boqueirão",
                    "Cabral",
                    "Cachoeira",
                    "Cajuru",
                    "Campina do Siqueira",
                    "Campo Comprido",
                    "Campo de Santana",
                    "Capão da Imbuia",
                    "Capão Raso",
                    "Cascatinha",
                    "Centro",
                    "Centro Cívico",
                    "CIC",
                    "Cristo Rei",
                    "Fanny",
                    "Fazendinha",
                    "Ganchinho",
                    "Guabirotuba",
                    "Hauer",
                    "Hugo Lange",
                    "Jardim Botânico",
                    "Jardim das Américas",
                    "Jardim Social",
                    "Juvevê",
                    "Lamenha Pequena",
                    "Lindóia",
                    "Mercês",
                    "Mossunguê",
                    "Novo Mundo",
                    "Orleans",
                    "Parolin",
                    "Pilarzinho",
                    "Pinheirinho",
                    "Portão",
                    "Prado Velho",
                    "Rebouças",
                    "Riviera",
                    "Santa Cândida",
                    "Santa Felicidade",
                    "Santa Quitéria",
                    "Santo Inácio",
                    "São Braz",
                    "São Francisco",
                    "São João",
                    "São Lourenço",
                    "São Miguel",
                    "Seminário",
                    "Sítio Cercado",
                    "Tarumã",
                    "Tatuquara",
                    "Tingui",
                    "Uberaba",
                    "Umbará",
                    "Vila Izabel",
                    "Vista Alegre",
                    "Xaxim");
            for (String nome : bairrosIniciais) {
                if (!bairroRepository.existsByNome(nome)) {
                    bairroRepository.save(new Bairro(null, nome));
                }
            }
        };
    }
}
