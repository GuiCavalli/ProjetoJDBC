package br.unipar;

import java.sql.*;

public class Main {

    private static final String url = "jdbc:postgresql://localhost:5432/Exemplo1";
    private static final String user = "postgres";
    private static final String password = "admin123";

    public static void main(String[] args) {
        criartabelaUsuario();

        //inserirUsuario("Gui", "12345","Guilherme","2005-01-01");

        listarTodosUsuario();
        inserirProduto("mouse", 280.00);

        inserirCliente("Gui","135.945.059.05");
    }

    public static Connection connection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public static void criartabelaUsuario(){
        try {
            // Cria conexão com o banco
            Connection conn = connection();

            Statement statement = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS usuarios( "
                    + " codigo SERIAL PRIMARY KEY,"
                    + " username VARCHAR(50) UNIQUE NOT NULL, "
                    + " password VARCHAR(300) NOT NULL, "
                    + " nome VARCHAR(50) UNIQUE NOT NULL,"
                    + " nascimento DATE )";

            statement.execute(sql);

            System.out.println("TABELA CRIADA COM SUCESSO!");

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static void inserirUsuario(String username, String password, String nome, String dataNascimento) {
        try {

            // Cria conexão com o banco
            Connection conn = connection();

            PreparedStatement preparedStatement = conn.prepareStatement(
                    "INSERT INTO usuarios (username, password, nome, nascimento) "
                            + "VALUES (?, ?, ?, ?)"
            );

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, nome);
            preparedStatement.setDate(4, java.sql.Date.valueOf(dataNascimento));

            preparedStatement.executeUpdate();

            System.out.println("USUARIO INSERIDO COM SUCESSO!");


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void listarTodosUsuario(){

        try {
            // Cria conexão com o banco
            Connection conn = connection();

            Statement statement = conn.createStatement();
            System.out.println("Listando usuarios");

            ResultSet result = statement.executeQuery("SELECT * FROM usuarios");

            while (result.next()) {
                System.out.println(result.getInt("codigo"));
                System.out.println(result.getString("username"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void inserirProduto(String descricao, double valor) {
        try {

            // Cria conexão com o banco
            Connection conn = connection();

            PreparedStatement preparedStatement = conn.prepareStatement(
                    "INSERT INTO produto (descricao, valor) "
                            + "VALUES (?, ?)"
            );

            preparedStatement.setString(1, descricao);

            // preparedStatement.setObject(2, valor,Types.OTHER); faz com que o double se transforme em money
            preparedStatement.setObject(2, valor,Types.OTHER);

            preparedStatement.executeUpdate();

            System.out.println("PRODUTO INSERIDO COM SUCESSO!");
            System.out.println("O valor do Produto " + descricao + " é " + valor);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void inserirCliente(String nome, String cpf) {
        try {

            // Cria conexão com o banco
            Connection conn = connection();

            PreparedStatement preparedStatement = conn.prepareStatement(
                    "INSERT INTO cliente (nome, cpf) "
                            + "VALUES (?, ?)"
            );

            preparedStatement.setString(1, nome);


            preparedStatement.setString(2, cpf);

            preparedStatement.executeUpdate();


//            ERROR AQUI 
            System.out.println("CLIENTE INSERIDO COM SUCESSO!");



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}