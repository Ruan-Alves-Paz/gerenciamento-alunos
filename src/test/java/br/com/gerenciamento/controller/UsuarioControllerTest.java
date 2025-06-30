package br.com.gerenciamento.controller;
import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.repository.UsuarioRepository;
import br.com.gerenciamento.util.Util;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    public void limparBanco() {
        usuarioRepository.deleteAll();
    }

    @Test
    public void CadastroUsuario() throws Exception {
        mockMvc.perform(post("/salvarUsuario")
                        .param("email", "teste@email.com")
                        .param("user", "usuario123")
                        .param("senha", "senha123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void Login() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("login@email.com");
        usuario.setUser("usuarioLogin");
        usuario.setSenha(Util.md5("senha123"));
        usuarioRepository.save(usuario);

        mockMvc.perform(post("/login")
                        .param("user", "usuarioLogin")
                        .param("senha", "senha123"))
                .andExpect(status().isOk())
                .andExpect(view().name("home/index"))
                .andExpect(model().attributeExists("aluno"));
    }

    @Test
    public void LoginErrado() throws Exception {
        mockMvc.perform(post("/login")
                        .param("user", "naoexiste")
                        .param("senha", "errada"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/cadastro"))
                .andExpect(model().attributeExists("usuario"));
    }

    @Test
    public void Logout() throws Exception {
        mockMvc.perform(post("/logout"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/login"))
                .andExpect(model().attributeExists("usuario"));
    }
}