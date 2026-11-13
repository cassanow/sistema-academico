package br.edu.faex.academico.repository;
import br.edu.faex.academico.config.Conexao;
import br.edu.faex.academico.model.Curso;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoRepository {
    public void salvar(Curso item) {
        String sql = "INSERT INTO cursos (nome, modalidade) VALUES (?, ?)";
        try (Connection conexao = Conexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            comando.setString(1, item.getNome());
            comando.setString(2, item.getModalidade());
            comando.executeUpdate();
            try (ResultSet resultado = comando.getGeneratedKeys()) {
                if (resultado.next()) item.setId(resultado.getLong(1));
            }
            System.out.println("Curso cadastrado com sucesso.");
        } catch (SQLException ex) {
            throw new IllegalStateException("Erro ao cadastrar Curso: " + ex.getMessage(), ex);
        }
    }

    public List<Curso> listar() {
        List<Curso> itens = new ArrayList<>();
        try (Connection conexao = Conexao.conectar();
             PreparedStatement comando = conexao.prepareStatement("SELECT id, nome, modalidade FROM cursos");
             ResultSet resultado = comando.executeQuery()) {
            while (resultado.next()) {
                Curso curso = new Curso();
                curso.setId(resultado.getLong("id"));
                curso.setNome(resultado.getString("nome"));
                curso.setModalidade(resultado.getString("modalidade"));
                itens.add(curso);
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Erro ao listar cursos: " + ex.getMessage(), ex);
        }
        return itens;
    }

    public Curso buscarPorId(Long id) {
        try (Connection conexao = Conexao.conectar();
             PreparedStatement comando = conexao.prepareStatement("SELECT id, nome, modalidade FROM cursos WHERE id = ?")) {
            comando.setLong(1, id);
            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    Curso curso = new Curso();
                    curso.setId(resultado.getLong("id"));
                    curso.setNome(resultado.getString("nome"));
                    curso.setModalidade(resultado.getString("modalidade"));
                    return curso;
                }
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Erro ao buscar Curso: " + ex.getMessage(), ex);
        }
        return null;
    }

    public void excluir(Long id) {
        try (Connection conexao = Conexao.conectar();
             PreparedStatement comando = conexao.prepareStatement("DELETE FROM cursos WHERE id = ?")) {
            comando.setLong(1, id);
            comando.executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalStateException("Erro ao excluir Curso: " + ex.getMessage(), ex);
        }
    }

    public void atualizar(Curso item) {
        try (Connection conexao = Conexao.conectar();
             PreparedStatement comando = conexao.prepareStatement("UPDATE cursos SET nome = ?, modalidade = ? WHERE id = ?")) {
            comando.setString(1, item.getNome());
            comando.setString(2, item.getModalidade());
            comando.setLong(3, item.getId());
            comando.executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalStateException("Erro ao atualizar Curso: " + ex.getMessage(), ex);
        }
    }
}
