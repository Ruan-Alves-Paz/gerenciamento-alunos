package br.com.gerenciamento.service;

import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.model.Usuario;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {
    @Autowired
    ServiceUsuario serviceUsuario;

    @Test
    public void salvarUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("Ruan8765@email.com");
        usuario.setSenha("12435");
        usuario.setUser("Ruan");

        assertDoesNotThrow(() -> serviceUsuario.salvarUsuario(usuario),
                "Não deveria lançar exceção ao salvar usuário válido.");

    }

    @Test
    public void cadastroUsuarioEmailJaExistente() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("Lilia@email.com");
        usuario.setSenha("1234");
        usuario.setUser("Lili");
        serviceUsuario.salvarUsuario(usuario);

        Usuario usuario2 = new Usuario();
        usuario2.setEmail("Lilia@email.com");
        usuario2.setSenha("1234");
        usuario2.setUser("Lili");
        Assert.assertThrows(EmailExistsException.class, () -> {
            serviceUsuario.salvarUsuario(usuario2);});
    }


    @Test
    public void LogarUsuarioCorreto() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("cassio@email.com");
        usuario.setSenha("1234");
        usuario.setUser("Cassio");
        serviceUsuario.salvarUsuario(usuario);

        Usuario usuarioRetorno = serviceUsuario.loginUser("Cassio","1234");
        usuario.equals(usuarioRetorno);
    }
    @Test
    public void logarUsuarioErrado() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("joao9876@email.com");
        usuario.setSenha("9834");
        usuario.setUser("Joao");
        serviceUsuario.salvarUsuario(usuario);

       assertNull(serviceUsuario.loginUser("Paulo","9834"));
    }

}
