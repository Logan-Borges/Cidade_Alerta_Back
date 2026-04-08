    package br.pucpr.AlertCity.model;

    import br.pucpr.AlertCity.security.Role;
    import jakarta.persistence.*;

    import lombok.Getter;
    import lombok.Setter;
    import lombok.NoArgsConstructor;
    import lombok.AllArgsConstructor;

    @Entity
    @Table(name = "usuarios")

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor

    public class Usuario {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String nome;

        @Column(unique = true)
        private String email;

        private String senha;

        @Enumerated(EnumType.STRING)
        private Role role = Role.USER;
    }
