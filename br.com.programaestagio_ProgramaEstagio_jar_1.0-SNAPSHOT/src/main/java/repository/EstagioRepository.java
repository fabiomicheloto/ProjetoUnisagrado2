package br.com.programaestagio.repository;

import br.com.programaestagio.model.*;
import br.com.programaestagio.config.DatabaseConfig;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EstagioRepository {
    private Connection conexao;
    private AlunoRepository alunoRepo;
    private EmpresaRepository empresaRepo;
    private SupervisorRepository supervisorRepo;

    public EstagioRepository() {
        this.conexao = DatabaseConfig.getConnection();
        this.alunoRepo = new AlunoRepository();
        this.empresaRepo = new EmpresaRepository();
        this.supervisorRepo = new SupervisorRepository();
    }

    // Operação CREATE com transação
    public void salvar(Estagio estagio) {
        String sql = "INSERT INTO estagios (id, aluno_id, empresa_cnpj, supervisor_academico_id, "
                   + "supervisor_campo_id, area_estagio, data_inicio, data_termino, status) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            conexao.setAutoCommit(false);

            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setString(1, estagio.getId());
                stmt.setString(2, estagio.getAluno().getId());
                stmt.setString(3, estagio.getEmpresa().getCnpj());
                stmt.setString(4, estagio.getSupervisorAcademico().getId());
                stmt.setString(5, estagio.getSupervisorCampo().getId());
                stmt.setString(6, estagio.getAreaEstagio());
                stmt.setDate(7, Date.valueOf(estagio.getDataInicio()));
                stmt.setDate(8, Date.valueOf(estagio.getDataTermino()));
                stmt.setString(9, estagio.getStatus().name());
                stmt.executeUpdate();
            }

            conexao.commit();
        } catch (SQLException e) {
            try {
                conexao.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException("Erro ao fazer rollback: " + ex.getMessage(), ex);
            }
            throw new RuntimeException("Erro ao salvar estágio: " + e.getMessage(), e);
        } finally {
            try {
                conexao.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("Erro ao restaurar auto-commit: " + e.getMessage());
            }
        }
    }

    // Operação READ (por ID)
    public Estagio buscarPorId(String id) {
        String sql = "SELECT * FROM estagios WHERE id = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapearEstagio(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar estágio: " + e.getMessage(), e);
        }
    }

    // Operação READ (por Aluno)
    public List<Estagio> buscarPorAluno(String alunoId) {
        String sql = "SELECT * FROM estagios WHERE aluno_id = ?";
        List<Estagio> estagios = new ArrayList<>();
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, alunoId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                estagios.add(mapearEstagio(rs));
            }
            return estagios;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar estágios do aluno: " + e.getMessage(), e);
        }
    }

    // Operação READ (por Empresa)
    public List<Estagio> buscarPorEmpresa(String cnpj) {
        String sql = "SELECT * FROM estagios WHERE empresa_cnpj = ?";
        List<Estagio> estagios = new ArrayList<>();
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cnpj);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                estagios.add(mapearEstagio(rs));
            }
            return estagios;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar estágios da empresa: " + e.getMessage(), e);
        }
    }

    // Operação UPDATE
    public void atualizar(Estagio estagio) {
        String sql = "UPDATE estagios SET aluno_id = ?, empresa_cnpj = ?, supervisor_academico_id = ?, "
                   + "supervisor_campo_id = ?, area_estagio = ?, data_inicio = ?, data_termino = ?, "
                   + "status = ? WHERE id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, estagio.getAluno().getId());
            stmt.setString(2, estagio.getEmpresa().getCnpj());
            stmt.setString(3, estagio.getSupervisorAcademico().getId());
            stmt.setString(4, estagio.getSupervisorCampo().getId());
            stmt.setString(5, estagio.getAreaEstagio());
            stmt.setDate(6, Date.valueOf(estagio.getDataInicio()));
            stmt.setDate(7, Date.valueOf(estagio.getDataTermino()));
            stmt.setString(8, estagio.getStatus().name());
            stmt.setString(9, estagio.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar estágio: " + e.getMessage(), e);
        }
    }

    // Operação DELETE
    public void deletar(String id) {
        String sql = "DELETE FROM estagios WHERE id = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar estágio: " + e.getMessage(), e);
        }
    }

    // LISTAR TODOS
    public List<Estagio> listarTodos() {
        String sql = "SELECT * FROM estagios";
        List<Estagio> estagios = new ArrayList<>();
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                estagios.add(mapearEstagio(rs));
            }
            return estagios;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar estágios: " + e.getMessage(), e);
        }
    }

    // Método auxiliar para mapear ResultSet → Estagio
    private Estagio mapearEstagio(ResultSet rs) throws SQLException {
        Estagio estagio = new Estagio();
        estagio.setId(rs.getString("id"));
        
        // Carrega relacionamentos
        Aluno aluno = alunoRepo.buscarPorId(rs.getString("aluno_id"));
        EmpresaConcedente empresa = empresaRepo.buscarPorCnpj(rs.getString("empresa_cnpj"));
        SupervisorAcademico supervisorAcad = supervisorRepo.buscarPorId(rs.getString("supervisor_academico_id"));
        SupervisorCampo supervisorCampo = supervisorRepo.buscarCampoPorId(rs.getString("supervisor_campo_id"));
        
        estagio.setAluno(aluno);
        estagio.setEmpresa(empresa);
        estagio.setSupervisorAcademico(supervisorAcad);
        estagio.setSupervisorCampo(supervisorCampo);
        
        // Demais campos
        estagio.setAreaEstagio(rs.getString("area_estagio"));
        estagio.setDataInicio(rs.getDate("data_inicio").toLocalDate());
        estagio.setDataTermino(rs.getDate("data_termino").toLocalDate());
        estagio.setStatus(StatusEstagio.valueOf(rs.getString("status")));
        
        return estagio;
    }

    // Métodos específicos do domínio
    public List<Estagio> buscarAtivos() {
        String sql = "SELECT * FROM estagios WHERE status = 'ATIVO'";
        List<Estagio> estagios = new ArrayList<>();
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                estagios.add(mapearEstagio(rs));
            }
            return estagios;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar estágios ativos: " + e.getMessage(), e);
        }
    }

    public List<Estagio> buscarPorPeriodo(LocalDate inicio, LocalDate fim) {
        String sql = "SELECT * FROM estagios WHERE data_inicio BETWEEN ? AND ?";
        List<Estagio> estagios = new ArrayList<>();
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(inicio));
            stmt.setDate(2, Date.valueOf(fim));
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                estagios.add(mapearEstagio(rs));
            }
            return estagios;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar por período: " + e.getMessage(), e);
        }
    }

    // Fechar conexão
    public void fecharConexao() {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
            }
            alunoRepo.fecharConexao();
            empresaRepo.fecharConexao();
            supervisorRepo.fecharConexao();
        } catch (SQLException e) {
            System.err.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }
}