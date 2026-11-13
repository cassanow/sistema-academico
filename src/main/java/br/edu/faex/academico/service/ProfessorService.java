package br.edu.faex.academico.service;
import br.edu.faex.academico.model.Professor;
import br.edu.faex.academico.repository.ProfessorRepository;
import java.util.List;

public class ProfessorService {
    private ProfessorRepository repository;

    public ProfessorService(ProfessorRepository repository) {
        this.repository = repository;
    }

    public void cadastrar(Professor item) {
        if (validar(item)) repository.salvar(item);
    }

    public List<Professor> listar() {
        return repository.listar();
    }

    public Professor buscarPorId(Long id) {
        if (id == null || id <= 0) {
            System.out.println("ID inválido.");
            return null;
        }
        return repository.buscarPorId(id);
    }

    public void excluir(Long id) {
        if (buscarPorId(id) == null) {
            System.out.println("Professor não encontrado.");
            return;
        }
        repository.excluir(id);
        System.out.println("Professor excluído com sucesso.");
    }

    public void atualizar(Professor item) {
        if (item == null || buscarPorId(item.getId()) == null) {
            System.out.println("Professor não encontrado.");
            return;
        }
        if (validar(item)) {
            repository.atualizar(item);
            System.out.println("Professor atualizado com sucesso.");
        }
    }

    private boolean validar(Professor item) {
        if (item == null) {
            System.out.println("Informe os dados de Professor.");
            return false;
        }
        if (item.getNome() == null || item.getNome().isBlank()) {
            System.out.println("O nome é obrigatório.");
            return false;
        }
        if (item.getEmail() == null || item.getEmail().isBlank()) {
            System.out.println("O campo email é obrigatório.");
            return false;
        }
        if (!item.getEmail().contains("@")) {
            System.out.println("E-mail inválido.");
            return false;
        }
        if (repository.listar().stream().anyMatch(p ->
                p.getEmail().equalsIgnoreCase(item.getEmail()) && !p.getId().equals(item.getId()))) {
            System.out.println("E-mail já cadastrado.");
            return false;
        }
        return true;
    }
}
