package tests.entities;

import entities.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Examples;
import utils.Global;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AlunoTest {

    private Aluno aluno;
    private Professor professor;
    private Turma turma;

    public Horario horario;

    private ControleAcademico controleAcademico = Global.getControleAcademico();
    ;

    @BeforeEach
    public void setUp() {
        ControleAcademico.setarBancoDeDados();

        this.aluno = controleAcademico.getAlunos().get(0);
        this.professor = controleAcademico.getProfessores().get(0);
        this.turma = controleAcademico.getTurmas().get(0);
    }

    @Test
    public void testObterHorario() {
        List<Horario> horarios = new ArrayList<>();

        for (Turma turma : aluno.getTurmas()) {
            horarios.addAll(turma.getHorarios());
        }

        assertEquals(aluno.getHorario(), horarios);
    }
}
