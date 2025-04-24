/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.programaestagio;

import java.sql.*;

public class UsuarioDAO {
    private Connection conexao;

    public UsuarioDAO() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/programa_estagio";
        String usuario = "root";
        String senha = "SUA_SENHA_AQUI";
        conexao = DriverManager.getConnection(url, usuario, senha);
    }

    public void inserirUsuario(String nome, String email, String senhaUsuario) throws SQLException {
        String sql = "INSERT INTO usuarios (nome, email, senha) VALUES (?, ?, ?)";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setString(1, nome);
        stmt.setString(2, email);
        stmt.setString(3, senhaUsuario);
        stmt.executeUpdate();
    }

    public void fechar() throws SQLException {
        conexao.close();
    }
}
