package br.com.programaestagio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResponsavelLegalDAO {

    public void inserir(ResponsavelLegal responsavel) {
        String sqlPessoa = "INSERT INTO pessoa (nome, logradouro, numero, bairro, municipio, uf, cep, telefone, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlResponsavel = "INSERT INTO responsavel_legal (id) VALUES (?)";

        try (Connection conn = ConexaoDB.conectar()) {
            conn.setAutoCommit(false); // Início da transação

            // Inserção na tabela pessoa
            PreparedStatement psPessoa = conn.prepareStatement(sqlPessoa, PreparedStatement.RETURN_GENERATED_KEYS);
            psPessoa.setString(1, responsavel.getNome());
            psPessoa.setString(2, responsavel.getLogradouro());
            psPessoa.setInt(3, responsavel.getNumero());
            psPessoa.setString(4, responsavel.getBairro());
            psPessoa.setString(5, responsavel.getMunicipio());
            psPessoa.setString(6, responsavel.getUf());
            psPessoa.setString(7, responsavel.getCep());
            psPessoa.setString(8, responsavel.getTelefone());
            psPessoa.setString(9, responsavel.getEmail());
            psPessoa.executeUpdate();

            // Recuperar o ID gerado
            ResultSet generatedKeys = psPessoa.getGeneratedKeys();
            int pessoaId = 0;
            if (generatedKeys.next()) {
                pessoaId = generatedKeys.getInt(1);
            }

            // Inserção na tabela responsavel_legal
            PreparedStatement psResp = conn.prepareStatement(sqlResponsavel);
            psResp.setInt(1, pessoaId);
            psResp.executeUpdate();

            conn.commit(); // Finaliza a transação

        } catch (SQLException e) {
            System.err.println("Erro ao inserir responsável legal no banco de dados.");
        }
    }

    private static class ConexaoDB {

        private static Connection conectar() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        public ConexaoDB() {
        }
    }
}
