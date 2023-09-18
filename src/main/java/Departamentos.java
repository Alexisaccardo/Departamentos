import java.sql.*;
import java.util.Scanner;

public class Departamentos {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("*****DEPARTAMENTOS*****");

        Select();

        System.out.println("Deseas registrar un funcionario?: ");
        String register = scanner.nextLine();

        while (register.equals("si")) {

            System.out.println("Ingrese su documento: ");
            String document = scanner.nextLine();

            String newdocument = Select_admin(document);
            if (newdocument.equals("Lider")) {

                System.out.println("Ingrese documento del funcionario: ");
                document = scanner.nextLine();

                String documentbd = Select_bd(document);

                if (documentbd.equals("")) {

                    System.out.println("Ingrese el cargo asignado al funcionario: ");
                    String charge = scanner.nextLine();

                    String newcharge = Select_One(charge);


                    if (newcharge.equals("")) {
                        System.out.println("No se encontró un departamento para este cargo");

                    } else {

                        System.out.print("Ingrese el nombre del funcionario: ");
                        String name = scanner.nextLine();

                        if (name.equals("")) {
                            System.out.println("Este campo es obligatorio");
                        }else {

                            Insert(document, charge, name);
                        }
                    }
                }else{
                    System.out.println("Ya se encuentra un funcionario con este documento");
                }
            }else{
                System.out.println("tu cargo: " + newdocument + " no coincide con el cargo de Lider");
            }
            }
        }

    private static void Select() throws ClassNotFoundException, SQLException {

        String driver2 = "com.mysql.cj.jdbc.Driver";
        String url2 = "jdbc:mysql://localhost:3306/departamentos";
        String username2 = "root";
        String pass2 = "";

        Class.forName(driver2);
        Connection connection2 = DriverManager.getConnection(url2, username2, pass2);

        Statement statement2 = connection2.createStatement();

        ResultSet resultSet2 = statement2.executeQuery("SELECT * FROM departamento");

        while(resultSet2.next()){

            String codigo = resultSet2.getString("codigo");
            String nombre = resultSet2.getString("nombre");

            System.out.println("Estos son los nombres de los departamentos: " + nombre);
        }
    }

    private static String Select_bd(String document) throws ClassNotFoundException, SQLException {

        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/departamentos";
        String username = "root";
        String password = "";

        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, username, password);

        String consultaSQL = "SELECT * FROM funcionarios WHERE documento = ?";

        PreparedStatement statement = connection.prepareStatement(consultaSQL);
        statement.setString(1, document); // Establecer el valor del parámetro

        // Ejecutar la consulta
        ResultSet resultSet = statement.executeQuery();

        // Procesar el resultado si existe
        if (resultSet.next()) {
            String documento = resultSet.getString("documento");
            String cargo = resultSet.getString("cargo");

            // Cerrar recursos
            resultSet.close();
            statement.close();
            connection.close();

            return cargo;

        }

        return "";
    }

    private static String Select_One(String charge) throws ClassNotFoundException, SQLException {

        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/departamentos";
        String username = "root";
        String password = "";

        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, username, password);

        String consultaSQL = "SELECT * FROM departamento WHERE nombre = ?";

        PreparedStatement statement = connection.prepareStatement(consultaSQL);
        statement.setString(1, charge); // Establecer el valor del parámetro

        // Ejecutar la consulta
        ResultSet resultSet = statement.executeQuery();

        // Procesar el resultado si existe
        if (resultSet.next()) {
            String codigo = resultSet.getString("codigo");
            String nombre = resultSet.getString("nombre");

            // Cerrar recursos
            resultSet.close();
            statement.close();
            connection.close();

            return nombre;

        }

        return "";

    }

    private static void Insert(String document, String charge, String name) {

        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/departamentos";
        String username = "root";
        String password = "";

        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM funcionarios");


            // Sentencia INSERT
            String sql = "INSERT INTO funcionarios (documento, cargo, nombre) VALUES (?, ?, ?)";

            // Preparar la sentencia
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, document);
            preparedStatement.setString(2, charge);
            preparedStatement.setString(3, name);

            // Ejecutar la sentencia
            int filasAfectadas = preparedStatement.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Funcionario registrado de exitosamente.");
            } else {
                System.out.println("No se pudo registrar el funcionario");
            }

            preparedStatement.close();
            connection.close();
            statement.close();
            resultSet.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static String Select_admin(String document) throws ClassNotFoundException, SQLException {

            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/departamentos";
            String username = "root";
            String password = "";

            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, username, password);

            String consultaSQL = "SELECT * FROM funcionarios WHERE documento = ?";

            PreparedStatement statement = connection.prepareStatement(consultaSQL);
            statement.setString(1, document); // Establecer el valor del parámetro

            // Ejecutar la consulta
            ResultSet resultSet = statement.executeQuery();

            // Procesar el resultado si existe
            if (resultSet.next()) {
                String documento = resultSet.getString("documento");
                String cargo = resultSet.getString("cargo");

                // Cerrar recursos
                resultSet.close();
                statement.close();
                connection.close();

                return cargo;

            } else {
                System.out.println("Este documento no se encuentra registrado como funcionario");
            }

            return "";

        }
}
