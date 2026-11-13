package br.edu.faex.academico.service;
import br.edu.faex.academico.model.Curso;
import br.edu.faex.academico.repository.CursoRepository;
import java.util.List;

public class CursoService {
    private CursoRepository repository;

    public CursoService(CursoRepository repository) {
        this.repository = repository;
    }

    public void cadastrar(Curso item) {
        if (validar(item)) repository.salvar(item);
    }

    public List<Curso> listar() {
        return repository.listar();
    }

    public Curso buscarPorId(Long id) {
        if (id == null || id <= 0) {
            System.out.println("ID inválido.");
            return null;
        }
        return repository.buscarPorId(id);
    }

    public void excluir(Long id) {
        if (buscarPorId(id) == null) {
            System.out.println("Curso não encontrado.");
            return;
        }
        repository.excluir(id);
        System.out.println("Curso excluído com sucesso.");
    }

    public void atualizar(Curso item) {
        if (item == null || buscarPorId(item.getId()) == null) {
            System.out.println("Curso não encontrado.");
            return;
        }
        if (validar(item)) {
            repository.atualizar(item);
            System.out.println("Curso atualizado com sucesso.");
        }
    }

    private boolean validar(Curso item) {
        if (item == null) {
            System.out.println("Informe os dados de Curso.");
            return false;
        }
        if (item.getNome() == null || item.getNome().isBlank()) {
            System.out.println("O nome é obrigatório.");
            return false;
        }
        if (item.getModalidade() == null || item.getModalidade().isBlank()) {
            System.out.println("O campo modalidade é obrigatório.");
            return false;
        }
        return true;
    }
}
