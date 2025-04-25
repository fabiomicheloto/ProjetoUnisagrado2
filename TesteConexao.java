/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.programaestagio;

/**
 *
 * @author fabio
 */
public class TesteConexao {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver JDBC carregado com sucesso!");
        } catch (ClassNotFoundException e) {
            System.out.println("Erro ao carregar o driver JDBC.");
            e.printStackTrace();
        }
    }
}
