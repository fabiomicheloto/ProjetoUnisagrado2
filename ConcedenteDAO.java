package br.com.programaestagio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ConcedenteDAO {

    public void inserir(Concedente concedente) {
        String sqlPessoa = "INSERT INTO pessoa (nome, logradouro, numero, bairro, municipio, uf, cep, telefone, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlConcedente = "INSERT INTO concedente (id, cnpj) VALUES (?, ?)";

        try (Connection conn = ConexaoDB.conectar()) {
            conn.setAutoCommit(false); // Início da transação

            // Inserção na tabela pessoa
            PreparedStatement psPessoa = conn.prepareStatement(sqlPessoa, PreparedStatement.RETURN_GENERATED_KEYS);
            psPessoa.setString(1, concedente.getNome());
            psPessoa.setString(2, concedente.getLogradouro());
            psPessoa.setInt(3, concedente.getNumero());
            psPessoa.setString(4, concedente.getBairro());
            psPessoa.setString(5, concedente.getMunicipio());
            psPessoa.setString(6, concedente.getUf());
            psPessoa.setString(7, concedente.getCep());
            psPessoa.setString(8, concedente.getTelefone());
            psPessoa.setString(9, concedente.getEmail());
            psPessoa.executeUpdate();

            // Recupera o ID gerado
            ResultSet generatedKeys = psPessoa.getGeneratedKeys();
            int pessoaId = 0;
            if (generatedKeys.next()) {
                pessoaId = generatedKeys.getInt(1);
            }

            // Inserção na tabela concedente
            PreparedStatement psConcedente = conn.prepareStatement(sqlConcedente);
            psConcedente.setInt(1, pessoaId);
            psConcedente.setString(2, concedente.getCnpj());
            psConcedente.executeUpdate();

            conn.commit(); // Confirma a transação

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erro ao inserir concedente no banco de dados.");
        }
    }

    List<Concedente> buscarPorCNPJ(String cnpjDigitado) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}