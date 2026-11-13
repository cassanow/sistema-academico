package br.edu.faex.academico.repository;
import br.edu.faex.academico.config.Conexao;
import br.edu.faex.academico.model.Professor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfessorRepository {
    public void salvar(Professor item) {
        String sql = "INSERT INTO professores (nome, email) VALUES (?, ?)";
        try (Connection conexao = Conexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            comando.setString(1, item.getNome());
            comando.setString(2, item.getEmail());
            comando.executeUpdate();
            try (ResultSet resultado = comando.getGeneratedKeys()) {
                if (resultado.next()) item.setId(resultado.getLong(1));
            }
            System.out.println("Professor cadastrado com sucesso.");
        } catch (SQLException ex) {
            throw new IllegalStateException("Erro ao cadastrar Professor: " + ex.getMessage(), ex);
        }
    }

    public List<Professor> listar() {
        List<Professor> itens = new ArrayList<>();
        try (Connection conexao = Conexao.conectar();
             PreparedStatement comando = conexao.prepareStatement("SELECT id, nome, email FROM professores");
             ResultSet resultado = comando.executeQuery()) {
            while (resultado.next()) {
                Professor professor = new Professor();
                professor.setId(resultado.getLong("id"));
                professor.setNome(resultado.getString("nome"));
                professor.setEmail(resultado.getString("email"));
                itens.add(professor);
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Erro ao listar professores: " + ex.getMessage(), ex);
        }
        return itens;
    }

    public Professor buscarPorId(Long id) {
        try (Connection conexao = Conexao.conectar();
             PreparedStatement comando = conexao.prepareStatement("SELECT id, nome, email FROM professores WHERE id = ?")) {
            comando.setLong(1, id);
            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    Professor professor = new Professor();
                    professor.setId(resultado.getLong("id"));
                    professor.setNome(resultado.getString("nome"));
                    professor.setEmail(resultado.getString("email"));
                    return professor;
                }
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Erro ao buscar Professor: " + ex.getMessage(), ex);
        }
        return null;
    }

    public void excluir(Long id) {
        try (Connection conexao = Conexao.conectar();
             PreparedStatement comando = conexao.prepareStatement("DELETE FROM professores WHERE id = ?")) {
            comando.setLong(1, id);
            comando.executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalStateException("Erro ao excluir Professor: " + ex.getMessage(), ex);
        }
    }

    public void atualizar(Professor item) {
        try (Connection conexao = Conexao.conectar();
             PreparedStatement comando = conexao.prepareStatement("UPDATE professores SET nome = ?, email = ? WHERE id = ?")) {
            comando.setString(1, item.getNome());
            comando.setString(2, item.getEmail());
            comando.setLong(3, item.getId());
            comando.executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalStateException("Erro ao atualizar Professor: " + ex.getMessage(), ex);
        }
    }
}
