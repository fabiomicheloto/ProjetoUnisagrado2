/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.programaestagio;

/**
 *
 * @author fabio
 */
public class TesteUsuarioDAO {
    public static void main(String[] args) {
        try {
            UsuarioDAO dao = new UsuarioDAO();
            dao.inserirUsuario("João Silva", "joao@email.com", "123456");
            System.out.println("Usuário inserido com sucesso!");
            dao.fechar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
