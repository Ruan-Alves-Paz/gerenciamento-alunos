package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Usuario;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioRepositoryTest {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void buscarEmail() {
        Usuario usuario = new Usuario();
        usuario.setEmail("thiago@email.com");
        usuario.setSenha("1234");
        usuario.setUser("Thiago");

        usuarioRepository.save(usuario);

        Usuario encontrado = usuarioRepository.findByEmail("thiago@email.com");
        assertEquals("thiago@email.com", encontrado.getEmail());
    }

    @Test
    public void buscarEmailErrado() {
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@email.com");
        usuario.setSenha("1234");
        usuario.setUser("Vinicius");

        usuarioRepository.save(usuario);

        Usuario encontrado = usuarioRepository.findByEmail("testeerrado@email.com");
        Assert.assertNull(encontrado);
    }

    @Test
    public void buscarLogin() throws NoSuchAlgorithmException {
        Usuario usuario = new Usuario();
        usuario.setEmail("armando@email.com");
        usuario.setSenha("3333");
        usuario.setUser("Armando");

        usuarioRepository.save(usuario);

        Usuario encontrado = usuarioRepository.buscarLogin("Armando","3333");
        assertEquals("armando@email.com", encontrado.getEmail());
    }

    @Test
    public void buscarLoginErrado() {
        Usuario usuario = new Usuario();
        usuario.setEmail("teste2@email.com");
        usuario.setSenha("1234");
        usuario.setUser("Pedro");

        usuarioRepository.save(usuario);

        Usuario encontrado = usuarioRepository.buscarLogin("Joao", "4567");
        Assert.assertNull(encontrado);
    }
}
