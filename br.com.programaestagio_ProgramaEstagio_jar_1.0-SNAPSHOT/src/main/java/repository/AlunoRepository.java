package br.com.programaestagio.repository;

import br.com.programaestagio.model.Aluno;
import br.com.programaestagio.config.DatabaseConfig;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AlunoRepository {
    private Connection conexao;

    public AlunoRepository() {
        this.conexao = DatabaseConfig.getConnection();
    }

    // Operação CREATE
    public void salvar(Aluno aluno) {
        String sql = "INSERT INTO alunos (id, nome, cpf, rg, data_nascimento, curso, termo, " +
                     "logradouro, bairro, municipio, uf, cep, telefone, email) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, aluno.getId());
            stmt.setString(2, aluno.getNome());
            stmt.setString(3, aluno.getCpf());
            stmt.setString(4, aluno.getRg());
            stmt.setDate(5, Date.valueOf(aluno.getDataNascimento()));
            stmt.setString(6, aluno.getCurso());
            stmt.setString(7, aluno.getTermo());
            stmt.setString(8, aluno.getLogradouro());
            stmt.setString(9, aluno.getBairro());
            stmt.setString(10, aluno.getMunicipio());
            stmt.setString(11, aluno.getUf());
            stmt.setString(12, aluno.getCep());
            stmt.setString(13, aluno.getTelefone());
            stmt.setString(14, aluno.getEmail());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar aluno: " + e.getMessage(), e);
        }
    }

    // Operação READ (por ID)
    public Aluno buscarPorId(String id) {
        String sql = "SELECT * FROM alunos WHERE id = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapearAluno(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar aluno: " + e.getMessage(), e);
        }
    }

    // Operação READ (por CPF)
    public Aluno buscarPorCpf(String cpf) {
        String sql = "SELECT * FROM alunos WHERE cpf = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapearAluno(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar aluno por CPF: " + e.getMessage(), e);
        }
    }

    // Operação UPDATE
    public void atualizar(Aluno aluno) {
        String sql = "UPDATE alunos SET nome = ?, cpf = ?, rg = ?, data_nascimento = ?, " +
                     "curso = ?, termo = ?, logradouro = ?, bairro = ?, municipio = ?, " +
                     "uf = ?, cep = ?, telefone = ?, email = ? WHERE id = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getCpf());
            stmt.setString(3, aluno.getRg());
            stmt.setDate(4, Date.valueOf(aluno.getDataNascimento()));
            stmt.setString(5, aluno.getCurso());
            stmt.setString(6, aluno.getTermo());
            stmt.setString(7, aluno.getLogradouro());
            stmt.setString(8, aluno.getBairro());
            stmt.setString(9, aluno.getMunicipio());
            stmt.setString(10, aluno.getUf());
            stmt.setString(11, aluno.getCep());
            stmt.setString(12, aluno.getTelefone());
            stmt.setString(13, aluno.getEmail());
            stmt.setString(14, aluno.getId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar aluno: " + e.getMessage(), e);
        }
    }

    // Operação DELETE
    public void deletar(String id) {
        String sql = "DELETE FROM alunos WHERE id = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar aluno: " + e.getMessage(), e);
        }
    }

    // LISTAR TODOS
    public List<Aluno> listarTodos() {
        String sql = "SELECT * FROM alunos";
        List<Aluno> alunos = new ArrayList<>();
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                alunos.add(mapearAluno(rs));
            }
            return alunos;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar alunos: " + e.getMessage(), e);
        }
    }

    // Método auxiliar para mapear ResultSet → Aluno
    private Aluno mapearAluno(ResultSet rs) throws SQLException {
        Aluno aluno = new Aluno();
        aluno.setId(rs.getString("id"));
        aluno.setNome(rs.getString("nome"));
        aluno.setCpf(rs.getString("cpf"));
        aluno.setRg(rs.getString("rg"));
        aluno.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
        aluno.setCurso(rs.getString("curso"));
        aluno.setTermo(rs.getString("termo"));
        // Campos herdados de Pessoa
        aluno.setLogradouro(rs.getString("logradouro"));
        aluno.setBairro(rs.getString("bairro"));
        aluno.setMunicipio(rs.getString("municipio"));
        aluno.setUf(rs.getString("uf"));
        aluno.setCep(rs.getString("cep"));
        aluno.setTelefone(rs.getString("telefone"));
        aluno.setEmail(rs.getString("email"));
        
        return aluno;
    }

    // Método específico do domínio
    public List<Aluno> buscarPorCurso(String curso) {
        String sql = "SELECT * FROM alunos WHERE curso LIKE ?";
        List<Aluno> alunos = new ArrayList<>();
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, "%" + curso + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                alunos.add(mapearAluno(rs));
            }
            return alunos;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar por curso: " + e.getMessage(), e);
        }
    }

    // Fechar conexão (opcional, pode ser gerenciado externamente)
    public void fecharConexao() {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }
}