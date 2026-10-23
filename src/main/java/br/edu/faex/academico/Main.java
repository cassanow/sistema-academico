package br.edu.faex.academico;

import br.edu.faex.academico.controller.AlunoController;
import br.edu.faex.academico.model.Aluno;
import br.edu.faex.academico.repository.AlunoRepository;
import br.edu.faex.academico.service.AlunoService;

public class Main {
        static void main() {
        AlunoRepository alunoRepository = new AlunoRepository();
        AlunoService alunoService = new AlunoService(alunoRepository);
        AlunoController alunoController = new AlunoController(alunoService);
 
        System.out.println("===== TESTE 1: Cadastrar dois alunos com e-mails diferentes =====");
        alunoController.cadastrar(new Aluno("Aleandro Ribeiro de Lima", "aleandro.lima@faex.edu.br"));
        alunoController.cadastrar(new Aluno("Maria Helena de Lima", "maria.lima@faex.edu.br"));
 
        System.out.println();
        System.out.println("===== TESTE 2: Cadastrar aluno com e-mail já existente =====");
        alunoController.cadastrar(new Aluno("João da Silva", "ALEANDRO.LIMA@faex.edu.br"));
 
        System.out.println();
        System.out.println("===== TESTE 3: Atualizar o nome mantendo o próprio e-mail =====");
        Aluno teste3 = new Aluno("Aleandro Lima Atualizado", "aleandro.lima@faex.edu.br");
        teste3.setId(1L);
        alunoController.atualizar(teste3);
 
        System.out.println();
        System.out.println("===== TESTE 4: Atualizar e-mail para um já usado por outro aluno =====");
        Aluno teste4 = new Aluno("Maria Helena de Lima", "Aleandro.Lima@faex.edu.br");
        teste4.setId(2L);
        alunoController.atualizar(teste4);
 
        System.out.println();
        System.out.println("===== TESTE 5: Atualizar com e-mail inválido =====");
        Aluno teste5 = new Aluno("Maria Helena de Lima", "maria.limafaex.edu.br");
        teste5.setId(2L);
        alunoController.atualizar(teste5);
 
        System.out.println();
        System.out.println("===== TESTE 6: Atualizar aluno com ID inexistente =====");
        Aluno teste6 = new Aluno("Aluno Fantasma", "fantasma@faex.edu.br");
        teste6.setId(99L);
        alunoController.atualizar(teste6);
 
        System.out.println();
        System.out.println("===== TESTE 7: Listar alunos após os testes =====");
        for (Aluno aluno : alunoController.listar()) {
            System.out.println("ID: " + aluno.getId());
            System.out.println("Nome: " + aluno.getNome());
            System.out.println("E-mail: " + aluno.getEmail());
            System.out.println("Ativo: " + aluno.isAtivo());
            System.out.println("-------------------------");
        }
    }

}
