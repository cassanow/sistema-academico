package br.edu.faex.academico;

import br.edu.faex.academico.controller.CursoController;
import br.edu.faex.academico.controller.ProfessorController;
import br.edu.faex.academico.model.Curso;
import br.edu.faex.academico.model.Professor;
import br.edu.faex.academico.repository.CursoRepository;
import br.edu.faex.academico.repository.ProfessorRepository;
import br.edu.faex.academico.service.CursoService;
import br.edu.faex.academico.service.ProfessorService;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        CursoRepository cursoRepository = new CursoRepository();
        CursoService cursoService = new CursoService(cursoRepository);
        CursoController cursoController = new CursoController(cursoService);

        cursoController.cadastrar(new Curso("Análise e Desenvolvimento de Sistemas", "Presencial"));
        cursoController.cadastrar(new Curso("Sistemas de Informação", "EAD"));

        System.out.println("\n===== LISTA DE CURSOS =====");
        List<Curso> cursos = cursoController.listar();
        for (Curso curso : cursos) {
            System.out.println("ID: " + curso.getId());
            System.out.println("Nome: " + curso.getNome());
            System.out.println("Modalidade: " + curso.getModalidade());
            System.out.println("--------------------------");
        }
        if (!cursos.isEmpty()) {
            Curso curso = cursoController.buscarPorId(cursos.get(0).getId());
            if (curso != null) {
                curso.setModalidade("EAD");
                cursoController.atualizar(curso);
                cursoController.excluir(curso.getId());
            }
        }

        ProfessorRepository professorRepository = new ProfessorRepository();
        ProfessorService professorService = new ProfessorService(professorRepository);
        ProfessorController professorController = new ProfessorController(professorService);

        professorController.cadastrar(new Professor("Carlos Oliveira", "carlos.oliveira@faex.edu.br"));
        professorController.cadastrar(new Professor("Ana Souza", "ana.souza@faex.edu.br"));

        System.out.println("\n===== LISTA DE PROFESSORES =====");
        List<Professor> professores = professorController.listar();
        for (Professor professor : professores) {
            System.out.println("ID: " + professor.getId());
            System.out.println("Nome: " + professor.getNome());
            System.out.println("E-mail: " + professor.getEmail());
            System.out.println("--------------------------");
        }
        if (!professores.isEmpty()) {
            Professor professor = professorController.buscarPorId(professores.get(0).getId());
            if (professor != null) {
                professor.setNome(professor.getNome() + " (atualizado)");
                professorController.atualizar(professor);
                professorController.excluir(professor.getId());
            }
        }
    }
}
