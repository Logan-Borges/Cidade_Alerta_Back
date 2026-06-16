package br.pucpr.AlertCity.service;

import br.pucpr.AlertCity.dto.OcorrenciaDTO;
import br.pucpr.AlertCity.exception.FiltroInvalidoException;
import br.pucpr.AlertCity.exception.StatusInvalidoException;
import br.pucpr.AlertCity.model.Bairro;
import br.pucpr.AlertCity.model.Ocorrencia;
import br.pucpr.AlertCity.model.Usuario;
import br.pucpr.AlertCity.repository.BairroRepository;
import br.pucpr.AlertCity.repository.OcorrenciaRepository;
import br.pucpr.AlertCity.repository.UrgenciaRepository;
import br.pucpr.AlertCity.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OcorrenciaServiceTest {

    @Mock
    private OcorrenciaRepository ocorrenciaRepository;

    @Mock
    private UrgenciaRepository urgenciaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private BairroRepository bairroRepository;

    @InjectMocks
    private OcorrenciaService service;

    @Test
    void deveListarTodasOcorrenciasSemFiltro() {

        Usuario usuario = new Usuario();
        usuario.setId(1L);

        Bairro bairro = new Bairro();
        bairro.setId(1L);

        Ocorrencia ocorrencia = new Ocorrencia();
        ocorrencia.setId(1L);
        ocorrencia.setTitulo("Buraco");
        ocorrencia.setUsuario(usuario);
        ocorrencia.setBairro(bairro);

        when(ocorrenciaRepository.findAll())
                .thenReturn(List.of(ocorrencia));

        when(urgenciaRepository.countByOcorrencia_Id(1L))
                .thenReturn(0L);

        List<OcorrenciaDTO> resultado = service.listar();

        assertEquals(1, resultado.size());
        assertEquals("Buraco", resultado.get(0).getTitulo());
    }

    @Test
    void deveFiltrarPorTitulo() {

        Usuario usuario = new Usuario();
        usuario.setId(1L);

        Bairro bairro = new Bairro();
        bairro.setId(1L);

        Ocorrencia ocorrencia = new Ocorrencia();
        ocorrencia.setId(1L);
        ocorrencia.setTitulo("Buraco na rua");
        ocorrencia.setUsuario(usuario);
        ocorrencia.setBairro(bairro);

        when(ocorrenciaRepository.findAll())
                .thenReturn(List.of(ocorrencia));

        when(urgenciaRepository.countByOcorrencia_Id(1L))
                .thenReturn(0L);

        List<OcorrenciaDTO> resultado =
                service.listar("titulo=buraco");

        assertEquals(1, resultado.size());
    }

    @Test
    void deveLancarExcecaoQuandoFiltroForInvalido() {

        assertThrows(
                FiltroInvalidoException.class,
                () -> service.listar("titulo")
        );
    }

    @Test
    void deveLancarExcecaoQuandoCampoFiltroNaoExistir() {

        Usuario usuario = new Usuario();
        usuario.setId(1L);

        Bairro bairro = new Bairro();
        bairro.setId(1L);

        Ocorrencia ocorrencia = new Ocorrencia();
        ocorrencia.setId(1L);
        ocorrencia.setUsuario(usuario);
        ocorrencia.setBairro(bairro);

        when(ocorrenciaRepository.findAll())
                .thenReturn(List.of(ocorrencia));

        assertThrows(
                FiltroInvalidoException.class,
                () -> service.listar("campoinvalido=123")
        );
    }

    @Test
    void deveSalvarOcorrenciaComImagemBase64() {

        Usuario usuario = new Usuario();
        usuario.setId(1L);

        Bairro bairro = new Bairro();
        bairro.setId(1L);

        String imagem =
                Base64.getEncoder()
                        .encodeToString("teste".getBytes());

        OcorrenciaDTO dto = new OcorrenciaDTO();
        dto.setUsuarioId(1L);
        dto.setBairroId(1L);
        dto.setFotoBase64(imagem);
        dto.setFotoBase64(null);
        dto.setFotoBase64("");
        dto.setFotoBase64(
                "data:image/png;base64," +
                        Base64.getEncoder()
                                .encodeToString("teste".getBytes())
        );

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(usuario));

        when(bairroRepository.findById(1L))
                .thenReturn(Optional.of(bairro));

        when(ocorrenciaRepository.save(any()))
                .thenAnswer(i -> i.getArgument(0));

        assertDoesNotThrow(() -> service.salvar(dto));
    }

    @Test
    void deveDeletarOcorrenciaExistente() {

        when(ocorrenciaRepository.existsById(1L))
                .thenReturn(true);

        service.deletar(1L);

        verify(ocorrenciaRepository)
                .deleteById(1L);
    }

    @Test
    void deveLancarErroAoDeletarOcorrenciaInexistente() {

        when(ocorrenciaRepository.existsById(1L))
                .thenReturn(false);

        assertThrows(
                RuntimeException.class,
                () -> service.deletar(1L)
        );
    }

    @Test
    void deveAtualizarOcorrencia() {

        Usuario usuario = new Usuario();
        usuario.setId(1L);

        Bairro bairro = new Bairro();
        bairro.setId(1L);

        Ocorrencia ocorrencia = new Ocorrencia();
        ocorrencia.setId(1L);
        ocorrencia.setUsuario(usuario);
        ocorrencia.setBairro(bairro);

        OcorrenciaDTO dto = new OcorrenciaDTO();
        dto.setTitulo("Novo titulo");
        dto.setDescricao("Nova descricao");

        when(ocorrenciaRepository.findById(1L))
                .thenReturn(Optional.of(ocorrencia));

        when(ocorrenciaRepository.save(any()))
                .thenAnswer(i -> i.getArgument(0));

        when(urgenciaRepository.countByOcorrencia_Id(1L))
                .thenReturn(0L);

        OcorrenciaDTO resultado =
                service.atualizar(1L, dto);

        assertEquals("Novo titulo", resultado.getTitulo());
    }

    @Test
    void deveAtualizarStatus() {

        Usuario usuario = new Usuario();
        usuario.setId(1L);

        Bairro bairro = new Bairro();
        bairro.setId(1L);

        Ocorrencia ocorrencia = new Ocorrencia();
        ocorrencia.setId(1L);
        ocorrencia.setUsuario(usuario);
        ocorrencia.setBairro(bairro);

        when(ocorrenciaRepository.findById(1L))
                .thenReturn(Optional.of(ocorrencia));

        when(ocorrenciaRepository.save(any()))
                .thenAnswer(i -> i.getArgument(0));

        when(urgenciaRepository.countByOcorrencia_Id(1L))
                .thenReturn(0L);

        OcorrenciaDTO dto =
                service.atualizarStatus(1L, "resolvido");

        assertEquals("resolvido", dto.getStatus());
    }

    @Test
    void deveFiltrarPorTodosCampos() {

        Usuario usuario = new Usuario();
        usuario.setId(10L);

        Bairro bairro = new Bairro();
        bairro.setId(20L);

        Ocorrencia o = new Ocorrencia();
        o.setId(1L);
        o.setTitulo("Buraco");
        o.setDescricao("Descricao");
        o.setTipo("Infraestrutura");
        o.setUrgencia("alta");
        o.setStatus("ativo");
        o.setCep("80000000");
        o.setRua("Rua A");
        o.setBairroNome("Centro");
        o.setUsuario(usuario);
        o.setBairro(bairro);

        when(ocorrenciaRepository.findAll())
                .thenReturn(List.of(o));

        when(urgenciaRepository.countByOcorrencia_Id(1L))
                .thenReturn(0L);

        assertEquals(1, service.listar("id=1").size());
        assertEquals(1, service.listar("descricao=Descricao").size());
        assertEquals(1, service.listar("tipo=Infraestrutura").size());
        assertEquals(1, service.listar("urgencia=alta").size());
        assertEquals(1, service.listar("status=ativo").size());
        assertEquals(1, service.listar("cep=80000000").size());
        assertEquals(1, service.listar("rua=Rua A").size());
        assertEquals(1, service.listar("bairronome=Centro").size());
        assertEquals(1, service.listar("usuarioid=10").size());
        assertEquals(1, service.listar("bairroid=20").size());
    }

    @Test
    void deveLancarErroQuandoUsuarioNaoExiste() {

        OcorrenciaDTO dto = new OcorrenciaDTO();
        dto.setUsuarioId(999L);

        when(usuarioRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> service.salvar(dto)
        );
    }

    @Test
    void deveLancarErroQuandoBairroNaoExiste() {

        Usuario usuario = new Usuario();
        usuario.setId(1L);

        OcorrenciaDTO dto = new OcorrenciaDTO();
        dto.setUsuarioId(1L);
        dto.setBairroId(999L);

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(usuario));

        when(bairroRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> service.salvar(dto)
        );
    }

    @Test
    void deveLancarErroAoAtualizarOcorrenciaInexistente() {

        when(ocorrenciaRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> service.atualizar(
                        999L,
                        new OcorrenciaDTO()
                )
        );
    }

    @Test
    void deveLancarErroAoAtualizarStatusDeOcorrenciaInexistente() {

        when(ocorrenciaRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> service.atualizarStatus(
                        999L,
                        "ativo"
                )
        );
    }

    @Test
    void deveLancarErroQuandoStatusInvalido() {

        assertThrows(
                StatusInvalidoException.class,
                () -> service.atualizarStatus(
                        1L,
                        "banana"
                )
        );
    }
}