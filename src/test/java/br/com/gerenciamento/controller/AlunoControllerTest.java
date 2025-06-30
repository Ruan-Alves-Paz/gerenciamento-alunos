package br.com.gerenciamento.controller;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.repository.AlunoRepository;
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
public class AlunoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AlunoRepository alunoRepository;

    @BeforeEach
    public void limparBanco() {
        alunoRepository.deleteAll(); // Limpa antes de cada teste
    }

    @Test
    public void testInserirAlunoComSucesso() throws Exception {
        mockMvc.perform(post("/InsertAlunos")
                        .param("nome", "Carlos")
                        .param("matricula", "123")
                        .param("curso", "ADMINISTRACAO")
                        .param("turno", "NOTURNO")
                        .param("status", "ATIVO"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/alunos-adicionados"));
    }

    @Test
    public void testInserirAlunoComErroDeValidacao() throws Exception {
        // Falta o nome e matrícula
        mockMvc.perform(post("/InsertAlunos")
                        .param("curso", "ADMINISTRACAO")
                        .param("turno", "NOTURNO")
                        .param("status", "ATIVO"))
                .andExpect(status().isOk()) // volta pro form
                .andExpect(view().name("Aluno/formAluno"))
                .andExpect(model().attributeExists("aluno"));
    }

    @Test
    public void testEditarAluno() throws Exception {
        // Primeiro salva um aluno
        Aluno aluno = new Aluno();
        aluno.setNome("Marcos");
        aluno.setMatricula("999");
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setTurno(Turno.NOTURNO);
        aluno.setStatus(Status.ATIVO);

        Aluno salvo = alunoRepository.save(aluno);

        // Agora edita
        mockMvc.perform(post("/editar")
                        .param("id", String.valueOf(salvo.getId()))
                        .param("nome", "Marcos")
                        .param("matricula", "999")
                        .param("curso", "INFORMATICA")
                        .param("turno", "MATUTINO")
                        .param("status", "INATIVO"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/alunos-adicionados"));
    }

    @Test
    public void testRemoverAluno() throws Exception {
        // Cria e salva um aluno
        Aluno aluno = new Aluno();
        aluno.setNome("Maria");
        aluno.setMatricula("555");
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setTurno(Turno.NOTURNO);
        aluno.setStatus(Status.INATIVO);

        Aluno salvo = alunoRepository.save(aluno);

        mockMvc.perform(get("/remover/" + salvo.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/alunos-adicionados"));
    }
}