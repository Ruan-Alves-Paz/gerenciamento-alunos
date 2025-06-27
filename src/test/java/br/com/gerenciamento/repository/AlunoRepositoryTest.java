package br.com.gerenciamento.repository;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {
    @Autowired
    private AlunoRepository alunoRepository;

    @Test
    public void buscarNome() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Maria");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.CONTABILIDADE);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        alunoRepository.save(aluno);

        List<Aluno> encontrados = alunoRepository.findByNomeContainingIgnoreCase("Guilherme");
        assertFalse(encontrados.isEmpty());
        assertEquals("Guilherme", encontrados.get(0).getNome());
    }

    @Test
    public void buscarNomeInexistente() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Thiago");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.BIOMEDICINA);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        alunoRepository.save(aluno);

        List<Aluno> encontrados = alunoRepository.findByNomeContainingIgnoreCase("Joao");
        assertTrue(encontrados.isEmpty());
    }

    @Test
    public void buscarAtivo() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Guilherme");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.DIREITO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        alunoRepository.save(aluno);

        List<Aluno> ativos = alunoRepository.findByStatusAtivo();
        assertEquals(1, ativos.size());
        assertEquals(Status.ATIVO, ativos.get(0).getStatus());
    }

    @Test
    public void buscarInativo() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Clara");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.INATIVO);
        aluno.setMatricula("123456");
        alunoRepository.save(aluno);

        List<Aluno> inativos = alunoRepository.findByStatusInativo();
        assertEquals(1, inativos.size());
        assertEquals(Status.INATIVO, inativos.get(0).getStatus());
    }
}
