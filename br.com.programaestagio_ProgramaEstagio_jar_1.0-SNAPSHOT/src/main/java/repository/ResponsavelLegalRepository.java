package br.com.programaestagio.repository;

import br.com.programaestagio.model.ResponsavelLegal;
import br.com.programaestagio.config.DatabaseConfig;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResponsavelLegalRepository {
    private Connection conexao;

    public ResponsavelLegalRepository() {
        this.conexao = DatabaseConfig.getConnection();
    }

    // CREATE
    public void salvar(ResponsavelLegal responsavel, String empresaCnpj) {
        String sql = "INSERT INTO responsaveis_legais (cpf, nome, cargo, documento_identificacao, empresa_cnpj) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, responsavel.getCpf());
            stmt.setString(2, responsavel.getNome());
            stmt.setString(3, responsavel.getCargo());
            stmt.setString(4, responsavel.getDocumentoIdentificacao());
            stmt.setString(5, empresaCnpj);
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar responsável legal: " + e.getMessage(), e);
        }
    }

    // READ (por CPF)
    public ResponsavelLegal buscarPorCpf(String cpf) {
        String sql = "SELECT * FROM responsaveis_legais WHERE cpf = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapearResponsavel(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar responsável: " + e.getMessage(), e);
        }
    }

    // READ (por Empresa)
    public List<ResponsavelLegal> buscarPorEmpresa(String cnpj) {
        String sql = "SELECT * FROM responsaveis_legais WHERE empresa_cnpj = ?";
        List<ResponsavelLegal> responsaveis = new ArrayList<>();
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cnpj);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                responsaveis.add(mapearResponsavel(rs));
            }
            return responsaveis;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar responsáveis: " + e.getMessage(), e);
        }
    }

    // UPDATE
    public void atualizar(ResponsavelLegal responsavel) {
        String sql = "UPDATE responsaveis_legais SET nome = ?, cargo = ?, documento_identificacao = ? "
                   + "WHERE cpf = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, responsavel.getNome());
            stmt.setString(2, responsavel.getCargo());
            stmt.setString(3, responsavel.getDocumentoIdentificacao());
            stmt.setString(4, responsavel.getCpf());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar responsável: " + e.getMessage(), e);
        }
    }

    // DELETE
    public void deletar(String cpf) {
        String sql = "DELETE FROM responsaveis_legais WHERE cpf = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover responsável: " + e.getMessage(), e);
        }
    }

    // Método auxiliar
    private ResponsavelLegal mapearResponsavel(ResultSet rs) throws SQLException {
        ResponsavelLegal responsavel = new ResponsavelLegal();
        responsavel.setCpf(rs.getString("cpf"));
        responsavel.setNome(rs.getString("nome"));
        responsavel.setCargo(rs.getString("cargo"));
        responsavel.setDocumentoIdentificacao(rs.getString("documento_identificacao"));
        return responsavel;
    }

    // Métodos específicos
    public boolean existePorCpf(String cpf) {
        String sql = "SELECT 1 FROM responsaveis_legais WHERE cpf = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            return stmt.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar CPF: " + e.getMessage(), e);
        }
    }

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