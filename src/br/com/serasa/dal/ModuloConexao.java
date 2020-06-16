/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.serasa.dal;

import java.sql.*;

/**
 *
 * @author chsfi
 */
public class ModuloConexao {

    //metodo responsavel por estabelecer a conexao com o banco
    public static Connection conector() {
        java.sql.Connection conexao = null;
        //a linha abaixo chama o driver
        String driver = "com.mysql.jdbc.Driver";
        // armazenando informações referente ao banco
        String url = "jdbc:mysql://localhost:3306/serasa";
        String user = "root";
        String password = "";
        //estabelecer a conexao com o banco de dados
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);
            return conexao;
        } catch (Exception e) {
            //a linha abaixo serve de apoio para esclarecer o erro
            System.out.println(e);
            return null;
        }
    }

}
