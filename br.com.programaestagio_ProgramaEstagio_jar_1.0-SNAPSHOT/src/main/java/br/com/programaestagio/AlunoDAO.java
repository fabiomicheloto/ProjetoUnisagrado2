package br.com.programaestagio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class AlunoDAO {

    public void inserir(Aluno aluno) {
        String sqlPessoa = "INSERT INTO pessoa (nome, logradouro, numero, bairro, municipio, uf, cep, telefone, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlAluno = "INSERT INTO aluno (id, cpf, rg, data_nascimento, curso, termo, supervisor_academico, area_estagio, data_inicio, data_termino) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoDB.conectar()) {
            conn.setAutoCommit(false); // Início da transação

            // 1. Inserir na tabela pessoa
            PreparedStatement psPessoa = conn.prepareStatement(sqlPessoa, Statement.RETURN_GENERATED_KEYS);
            psPessoa.setString(1, aluno.getNome());
            psPessoa.setString(2, aluno.getLogradouro());
            psPessoa.setInt(3, aluno.getNumero());
            psPessoa.setString(4, aluno.getBairro());
            psPessoa.setString(5, aluno.getMunicipio());
            psPessoa.setString(6, aluno.getUF());
            psPessoa.setDouble(7, aluno.getCEP());
            psPessoa.setString(8, aluno.getTelefone());
            psPessoa.setString(9, aluno.getEmail());
            psPessoa.executeUpdate();

            // 2. Recuperar o ID da pessoa inserida
            int pessoaId = 0;
            var generatedKeys = psPessoa.getGeneratedKeys();
            if (generatedKeys.next()) {
                pessoaId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Erro ao obter o ID da pessoa inserida.");
            }

            // 3. Inserir na tabela aluno
            PreparedStatement psAluno = conn.prepareStatement(sqlAluno);
            psAluno.setInt(1, pessoaId);
            psAluno.setDouble(2, aluno.getCpf());
            psAluno.setString(3, aluno.getRg());
            psAluno.setString(4, aluno.getDataNascimento());
            psAluno.setString(5, aluno.getCurso());
            psAluno.setString(6, aluno.getTermo());
            psAluno.setString(7, aluno.getSupervisorAcademico());
            psAluno.setString(8, aluno.getAreaEstagio());
            psAluno.setString(9, aluno.getDataInicio());
            psAluno.setString(10, aluno.getDataTermino());
            psAluno.executeUpdate();

            conn.commit(); // Finaliza a transação

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

