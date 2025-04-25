package br.com.programaestagio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SupervisorCampoDAO {

    public void inserir(SupervisorCampo supervisor) {
        String sqlPessoa = "INSERT INTO pessoa (nome, logradouro, numero, bairro, municipio, uf, cep, telefone, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlSupervisor = "INSERT INTO supervisor (id, inscricaoconselho, conselhodeclasse) VALUES (?, ?, ?)";

        try (Connection conn = ConexaoDB.conectar()) {
            conn.setAutoCommit(false); // Início da transação

            // Inserção na tabela pessoa
            PreparedStatement psPessoa = conn.prepareStatement(sqlPessoa, PreparedStatement.RETURN_GENERATED_KEYS);
            psPessoa.setString(1, supervisor.getNome());
            psPessoa.setString(2, supervisor.getLogradouro());
            psPessoa.setInt(3, supervisor.getNumero());
            psPessoa.setString(4, supervisor.getBairro());
            psPessoa.setString(5, supervisor.getMunicipio());
            psPessoa.setString(6, supervisor.getUf());
            psPessoa.setString(7, supervisor.getCep());
            psPessoa.setString(8, supervisor.getTelefone());
            psPessoa.setString(9, supervisor.getEmail());
            psPessoa.executeUpdate();

            // Recuperar o ID gerado
            ResultSet generatedKeys = psPessoa.getGeneratedKeys();
            int pessoaId = 0;
            if (generatedKeys.next()) {
                pessoaId = generatedKeys.getInt(1);
            }

            // Inserção na tabela supervisor
            PreparedStatement psSupervisor = conn.prepareStatement(sqlSupervisor);
            psSupervisor.setInt(1, pessoaId);
            psSupervisor.setInt(2, supervisor.getInscricaoconselho());
            psSupervisor.setString(3, supervisor.getConselhodeclasse());
            psSupervisor.executeUpdate();

            conn.commit(); // Finaliza a transação

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erro ao inserir supervisor no banco de dados.");
        }
    }
}
