package br.com.programaestagio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CoordenadorDAO {

    public void inserir(Coordenador coordenador) {
        String sqlPessoa = "INSERT INTO pessoa (nome, logradouro, numero, bairro, municipio, uf, cep, telefone, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlCoordenador = "INSERT INTO coordenador (id, siape) VALUES (?, ?)";

        try (Connection conn = ConexaoDB.conectar()) {
            conn.setAutoCommit(false); // Início da transação

            // Inserção na tabela pessoa
            PreparedStatement psPessoa = conn.prepareStatement(sqlPessoa, PreparedStatement.RETURN_GENERATED_KEYS);
            psPessoa.setString(1, coordenador.getNome());
            psPessoa.setString(2, coordenador.getLogradouro());
            psPessoa.setInt(3, coordenador.getNumero());
            psPessoa.setString(4, coordenador.getBairro());
            psPessoa.setString(5, coordenador.getMunicipio());
            psPessoa.setString(6, coordenador.getUf());
            psPessoa.setString(7, coordenador.getCep());
            psPessoa.setString(8, coordenador.getTelefone());
            psPessoa.setString(9, coordenador.getEmail());
            psPessoa.executeUpdate();

            // Recupera o ID gerado
            ResultSet generatedKeys = psPessoa.getGeneratedKeys();
            int pessoaId = 0;
            if (generatedKeys.next()) {
                pessoaId = generatedKeys.getInt(1);
            }

            // Inserção na tabela coordenador
            PreparedStatement psCoord = conn.prepareStatement(sqlCoordenador);
            psCoord.setInt(1, pessoaId);
            psCoord.setString(2, coordenador.getSiape());
            psCoord.executeUpdate();

            conn.commit(); // Finaliza a transação

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erro ao inserir coordenador no banco de dados.");
        }
    }

    private static class Coordenador {

        public Coordenador() {
        }

        private String getNome() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        private String getLogradouro() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        private int getNumero() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        private String getBairro() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        private String getMunicipio() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        private String getUf() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        private String getCep() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        private String getTelefone() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        private String getEmail() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        private String getSiape() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    }
}
