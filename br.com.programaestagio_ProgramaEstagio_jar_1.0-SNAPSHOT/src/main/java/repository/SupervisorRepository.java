package br.com.programaestagio.repository;

import br.com.programaestagio.model.*;
import br.com.programaestagio.config.DatabaseConfig;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupervisorRepository {
    private Connection conexao;

    public SupervisorRepository() {
        this.conexao = DatabaseConfig.getConnection();
    }

    // Método genérico para salvar qualquer tipo de supervisor
    public void salvar(Supervisor supervisor) {
        String sql = "INSERT INTO supervisores (id, nome, email, telefone, tipo, "
                   + "inscricao_conselho, conselho_classe, uf_conselho) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, supervisor.getId());
            stmt.setString(2, supervisor.getNome());
            stmt.setString(3, supervisor.getEmail());
            stmt.setString(4, supervisor.getTelefone());
            
            // Define o tipo específico
            if (supervisor instanceof SupervisorAcademico) {
                stmt.setString(5, "ACADEMICO");
            } else {
                stmt.setString(5, "CAMPO");
            }
            
            stmt.setString(6, supervisor.getInscricaoConselho());
            stmt.setString(7, supervisor.getConselhoClasse());
            stmt.setString(8, supervisor.getUfConselho());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar supervisor: " + e.getMessage(), e);
        }
    }

    // Busca por ID (retorna o tipo específico)
    public Supervisor buscarPorId(String id) {
        String sql = "SELECT * FROM supervisores WHERE id = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapearSupervisor(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar supervisor: " + e.getMessage(), e);
        }
    }

    // Busca específica para Supervisor Acadêmico
    public SupervisorAcademico buscarAcademicoPorId(String id) {
        Supervisor supervisor = buscarPorId(id);
        if (supervisor instanceof SupervisorAcademico) {
            return (SupervisorAcademico) supervisor;
        }
        return null;
    }

    // Busca específica para Supervisor de Campo
    public SupervisorCampo buscarCampoPorId(String id) {
        Supervisor supervisor = buscarPorId(id);
        if (supervisor instanceof SupervisorCampo) {
            return (SupervisorCampo) supervisor;
        }
        return null;
    }

    // Listar todos os supervisores (genérico)
    public List<Supervisor> listarTodos() {
        String sql = "SELECT * FROM supervisores";
        List<Supervisor> supervisores = new ArrayList<>();
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                supervisores.add(mapearSupervisor(rs));
            }
            return supervisores;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar supervisores: " + e.getMessage(), e);
        }
    }

    // Listar por tipo
    public List<Supervisor> listarPorTipo(String tipo) {
        String sql = "SELECT * FROM supervisores WHERE tipo = ?";
        List<Supervisor> supervisores = new ArrayList<>();
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, tipo);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                supervisores.add(mapearSupervisor(rs));
            }
            return supervisores;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar supervisores por tipo: " + e.getMessage(), e);
        }
    }

    // Atualizar supervisor
    public void atualizar(Supervisor supervisor) {
        String sql = "UPDATE supervisores SET nome = ?, email = ?, telefone = ?, "
                   + "inscricao_conselho = ?, conselho_classe = ?, uf_conselho = ? "
                   + "WHERE id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, supervisor.getNome());
            stmt.setString(2, supervisor.getEmail());
            stmt.setString(3, supervisor.getTelefone());
            stmt.setString(4, supervisor.getInscricaoConselho());
            stmt.setString(5, supervisor.getConselhoClasse());
            stmt.setString(6, supervisor.getUfConselho());
            stmt.setString(7, supervisor.getId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar supervisor: " + e.getMessage(), e);
        }
    }

    // Deletar supervisor
    public void deletar(String id) {
        String sql = "DELETE FROM supervisores WHERE id = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar supervisor: " + e.getMessage(), e);
        }
    }

    // Método auxiliar para mapear ResultSet → Supervisor
    private Supervisor mapearSupervisor(ResultSet rs) throws SQLException {
        String tipo = rs.getString("tipo");
        
        if ("ACADEMICO".equals(tipo)) {
            SupervisorAcademico supervisor = new SupervisorAcademico();
            setarAtributosComuns(supervisor, rs);
            return supervisor;
        } else {
            SupervisorCampo supervisor = new SupervisorCampo();
            setarAtributosComuns(supervisor, rs);
            return supervisor;
        }
    }

    private void setarAtributosComuns(Supervisor supervisor, ResultSet rs) throws SQLException {
        supervisor.setId(rs.getString("id"));
        supervisor.setNome(rs.getString("nome"));
        supervisor.setEmail(rs.getString("email"));
        supervisor.setTelefone(rs.getString("telefone"));
        supervisor.setInscricaoConselho(rs.getString("inscricao_conselho"));
        supervisor.setConselhoClasse(rs.getString("conselho_classe"));
        supervisor.setUfConselho(rs.getString("uf_conselho"));
    }

    // Métodos específicos do domínio
    public List<SupervisorAcademico> listarAcademicos() {
        List<Supervisor> supervisores = listarPorTipo("ACADEMICO");
        List<SupervisorAcademico> academicos = new ArrayList<>();
        
        for (Supervisor s : supervisores) {
            academicos.add((SupervisorAcademico) s);
        }
        return academicos;
    }

    public List<SupervisorCampo> listarSupervisoresCampo() {
        List<Supervisor> supervisores = listarPorTipo("CAMPO");
        List<SupervisorCampo> campo = new ArrayList<>();
        
        for (Supervisor s : supervisores) {
            campo.add((SupervisorCampo) s);
        }
        return campo;
    }

    public List<Supervisor> buscarPorConselho(String conselhoClasse) {
        String sql = "SELECT * FROM supervisores WHERE conselho_classe = ?";
        List<Supervisor> supervisores = new ArrayList<>();
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, conselhoClasse);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                supervisores.add(mapearSupervisor(rs));
            }
            return supervisores;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar por conselho: " + e.getMessage(), e);
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