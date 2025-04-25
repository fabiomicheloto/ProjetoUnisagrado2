package br.com.programaestagio.repository;

import br.com.programaestagio.model.EmpresaConcedente;
import br.com.programaestagio.model.ResponsavelLegal;
import br.com.programaestagio.config.DatabaseConfig;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpresaRepository {
    private Connection conexao;

    public EmpresaRepository() {
        this.conexao = DatabaseConfig.getConnection();
    }

    // Operação CREATE (com transação para responsáveis)
    public void salvar(EmpresaConcedente empresa) {
        String sqlEmpresa = "INSERT INTO empresas (cnpj, razao_social, logradouro, bairro, "
                          + "municipio, uf, cep, telefone, email) "
                          + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String sqlResponsavel = "INSERT INTO responsaveis_legais (cpf, nome, cargo, documento_identificacao, "
                             + "empresa_cnpj) VALUES (?, ?, ?, ?, ?)";

        try {
            conexao.setAutoCommit(false);  // Inicia transação

            try (PreparedStatement stmtEmpresa = conexao.prepareStatement(sqlEmpresa)) {
                // Insere a empresa
                stmtEmpresa.setString(1, empresa.getCnpj());
                stmtEmpresa.setString(2, empresa.getRazaoSocial());
                stmtEmpresa.setString(3, empresa.getLogradouro());
                stmtEmpresa.setString(4, empresa.getBairro());
                stmtEmpresa.setString(5, empresa.getMunicipio());
                stmtEmpresa.setString(6, empresa.getUf());
                stmtEmpresa.setString(7, empresa.getCep());
                stmtEmpresa.setString(8, empresa.getTelefone());
                stmtEmpresa.setString(9, empresa.getEmail());
                stmtEmpresa.executeUpdate();
            }

            // Insere os responsáveis
            try (PreparedStatement stmtResponsavel = conexao.prepareStatement(sqlResponsavel)) {
                for (ResponsavelLegal responsavel : empresa.getResponsaveis()) {
                    stmtResponsavel.setString(1, responsavel.getCpf());
                    stmtResponsavel.setString(2, responsavel.getNome());
                    stmtResponsavel.setString(3, responsavel.getCargo());
                    stmtResponsavel.setString(4, responsavel.getDocumentoIdentificacao());
                    stmtResponsavel.setString(5, empresa.getCnpj());
                    stmtResponsavel.addBatch();
                }
                stmtResponsavel.executeBatch();
            }

            conexao.commit();  // Confirma transação

        } catch (SQLException e) {
            try {
                conexao.rollback();  // Rollback em caso de erro
            } catch (SQLException ex) {
                throw new RuntimeException("Erro ao fazer rollback: " + ex.getMessage(), ex);
            }
            throw new RuntimeException("Erro ao salvar empresa: " + e.getMessage(), e);
        } finally {
            try {
                conexao.setAutoCommit(true);  // Restaura auto-commit
            } catch (SQLException e) {
                System.err.println("Erro ao restaurar auto-commit: " + e.getMessage());
            }
        }
    }

    // Operação READ (por CNPJ) com responsáveis
    public EmpresaConcedente buscarPorCnpj(String cnpj) {
        String sqlEmpresa = "SELECT * FROM empresas WHERE cnpj = ?";
        String sqlResponsaveis = "SELECT * FROM responsaveis_legais WHERE empresa_cnpj = ?";

        try (PreparedStatement stmtEmpresa = conexao.prepareStatement(sqlEmpresa)) {
            stmtEmpresa.setString(1, cnpj);
            ResultSet rsEmpresa = stmtEmpresa.executeQuery();

            if (rsEmpresa.next()) {
                EmpresaConcedente empresa = mapearEmpresa(rsEmpresa);

                // Busca responsáveis
                try (PreparedStatement stmtResponsaveis = conexao.prepareStatement(sqlResponsaveis)) {
                    stmtResponsaveis.setString(1, cnpj);
                    ResultSet rsResponsaveis = stmtResponsaveis.executeQuery();

                    while (rsResponsaveis.next()) {
                        empresa.adicionarResponsavel(mapearResponsavel(rsResponsaveis));
                    }
                }
                return empresa;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar empresa: " + e.getMessage(), e);
        }
    }

    // Operação UPDATE
    public void atualizar(EmpresaConcedente empresa) {
        String sql = "UPDATE empresas SET razao_social = ?, logradouro = ?, bairro = ?, "
                   + "municipio = ?, uf = ?, cep = ?, telefone = ?, email = ? WHERE cnpj = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, empresa.getRazaoSocial());
            stmt.setString(2, empresa.getLogradouro());
            stmt.setString(3, empresa.getBairro());
            stmt.setString(4, empresa.getMunicipio());
            stmt.setString(5, empresa.getUf());
            stmt.setString(6, empresa.getCep());
            stmt.setString(7, empresa.getTelefone());
            stmt.setString(8, empresa.getEmail());
            stmt.setString(9, empresa.getCnpj());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar empresa: " + e.getMessage(), e);
        }
    }

    // Operação DELETE (com transação para responsáveis)
    public void deletar(String cnpj) {
        String sqlDeleteResponsaveis = "DELETE FROM responsaveis_legais WHERE empresa_cnpj = ?";
        String sqlDeleteEmpresa = "DELETE FROM empresas WHERE cnpj = ?";

        try {
            conexao.setAutoCommit(false);

            try (PreparedStatement stmtResponsaveis = conexao.prepareStatement(sqlDeleteResponsaveis)) {
                stmtResponsaveis.setString(1, cnpj);
                stmtResponsaveis.executeUpdate();
            }

            try (PreparedStatement stmtEmpresa = conexao.prepareStatement(sqlDeleteEmpresa)) {
                stmtEmpresa.setString(1, cnpj);
                stmtEmpresa.executeUpdate();
            }

            conexao.commit();
        } catch (SQLException e) {
            try {
                conexao.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException("Erro ao fazer rollback: " + ex.getMessage(), ex);
            }
            throw new RuntimeException("Erro ao deletar empresa: " + e.getMessage(), e);
        } finally {
            try {
                conexao.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("Erro ao restaurar auto-commit: " + e.getMessage());
            }
        }
    }

    // LISTAR TODAS
    public List<EmpresaConcedente> listarTodas() {
        String sql = "SELECT * FROM empresas";
        List<EmpresaConcedente> empresas = new ArrayList<>();

        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                empresas.add(mapearEmpresa(rs));
            }
            return empresas;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar empresas: " + e.getMessage(), e);
        }
    }

    // Métodos auxiliares de mapeamento
    private EmpresaConcedente mapearEmpresa(ResultSet rs) throws SQLException {
        EmpresaConcedente empresa = new EmpresaConcedente();
        empresa.setCnpj(rs.getString("cnpj"));
        empresa.setRazaoSocial(rs.getString("razao_social"));
        // Campos herdados de Pessoa
        empresa.setLogradouro(rs.getString("logradouro"));
        empresa.setBairro(rs.getString("bairro"));
        empresa.setMunicipio(rs.getString("municipio"));
        empresa.setUf(rs.getString("uf"));
        empresa.setCep(rs.getString("cep"));
        empresa.setTelefone(rs.getString("telefone"));
        empresa.setEmail(rs.getString("email"));
        
        return empresa;
    }

    private ResponsavelLegal mapearResponsavel(ResultSet rs) throws SQLException {
        ResponsavelLegal responsavel = new ResponsavelLegal();
        responsavel.setCpf(rs.getString("cpf"));
        responsavel.setNome(rs.getString("nome"));
        responsavel.setCargo(rs.getString("cargo"));
        responsavel.setDocumentoIdentificacao(rs.getString("documento_identificacao"));
        return responsavel;
    }

    // Método específico do domínio
    public List<EmpresaConcedente> buscarPorMunicipio(String municipio) {
        String sql = "SELECT * FROM empresas WHERE municipio LIKE ?";
        List<EmpresaConcedente> empresas = new ArrayList<>();

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, "%" + municipio + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                empresas.add(mapearEmpresa(rs));
            }
            return empresas;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar por município: " + e.getMessage(), e);
        }
    }

    // Fechar conexão
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