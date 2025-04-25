/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.programaestagio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SupervisorAcademicoDAO {

    public void inserir(SupervisorAcademico coordenador) {
        String sqlPessoa = "INSERT INTO pessoa (nome, logradouro, numero, bairro, municipio, uf, cep, telefone, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlCoordenador = "INSERT INTO aluno (id, cpf) VALUES (?, ?)";

        try (Connection conn = ConexaoDB.conectar()) {
            conn.setAutoCommit(false); // Para garantir transação

            // Inserir na tabela pessoa
            PreparedStatement psPessoa = conn.prepareStatement(sqlPessoa, PreparedStatement.RETURN_GENERATED_KEYS);
            psPessoa.setString(1, coordenador.getNome());
            psPessoa.setString(2, coordenador.getLogradouro());
            psPessoa.setInt(3, coordenador.getNumero());
            psPessoa.setString(4, coordenador.getBairro());
            psPessoa.setString(5, coordenador.getMunicípio());
            psPessoa.setString(6, coordenador.getUF());
            psPessoa.setString(7, String.valueOf(coordenador.getCEP()));
            psPessoa.setString(7, String.valueOf(coordenador.getTelefone()));
            psPessoa.setString(8, coordenador.getEmail());
            psPessoa.executeUpdate();

            // Recuperar o ID gerado
            var generatedKeys = psPessoa.getGeneratedKeys();
            int pessoaId = 0;
            if (generatedKeys.next()) {
                pessoaId = generatedKeys.getInt(1);
            }
            String sqlAluno = null;

            // Inserir na tabela aluno
            PreparedStatement psAluno = conn.prepareStatement(sqlAluno);
            psAluno.setInt(1, pessoaId);
            psAluno.setString(2, String.valueOf(aluno.getCpf()));
            psAluno.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void inserir(Aluno aluno) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private static class aluno {

        private static Object getCpf() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        public aluno() {
        }
    }
}
