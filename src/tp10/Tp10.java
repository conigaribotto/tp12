package tp10;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Tp10 {

    public static void main(String[] args) {
        Connection con = null;
        try {
            // Cargar el controlador de MariaDB
            Class.forName("org.mariadb.jdbc.Driver");

            // Establecer la conexión
            String URL = "jdbc:mariadb://localhost:3307/tp10";
            String usuario = "root";
            String password = "";
            con = DriverManager.getConnection(URL, usuario, password);

            // Insertar 3 empleados
            insertarEmpleados(con);

            // Insertar 2 herramientas
            insertarHerramientas(con);

            // Listar todas las herramientas con stock superior a 10
            listarHerramientas(con);

            // Dar de baja al primer empleado ingresado a la base de datos
            darDeBajaPrimerEmpleado(con);

        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar el controlador");
        } catch (SQLException ex) {
            int error = ex.getErrorCode();
            if (error == 1049) {
                JOptionPane.showMessageDialog(null, "Base de datos inexistente");
            } else {
                JOptionPane.showMessageDialog(null, "Error SQL: " + ex.getMessage());
            }
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al cerrar la conexión: " + ex.getMessage());
            }
        }
    }

    private static void insertarEmpleados(Connection con) throws SQLException {
        try {
            String sql = "INSERT INTO empleado (dni, apellido, nombre, acceso, estado) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement insertarEmpleado = con.prepareStatement(sql);

            insertarEmpleado.setLong(1, 123456789);
            insertarEmpleado.setString(2, "Perez");
            insertarEmpleado.setString(3, "Josefina");
            insertarEmpleado.setInt(4, 1);
            insertarEmpleado.setBoolean(5, true);
            insertarEmpleado.executeUpdate();

            insertarEmpleado.setLong(1, 127896789);
            insertarEmpleado.setString(2, "Sosa");
            insertarEmpleado.setString(3, "Facundo");
            insertarEmpleado.setInt(4, 1);
            insertarEmpleado.setBoolean(5, true);
            insertarEmpleado.executeUpdate();

            insertarEmpleado.setLong(1, 1278929789);
            insertarEmpleado.setString(2, "Diaz");
            insertarEmpleado.setString(3, "Mauro");
            insertarEmpleado.setInt(4, 1);
            insertarEmpleado.setBoolean(5, true);
            insertarEmpleado.executeUpdate();

            JOptionPane.showMessageDialog(null, "Empleados insertados exitosamente.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al insertar empleados: " + ex.getMessage());
        }
    }

    private static void insertarHerramientas(Connection con) throws SQLException {
        try {
            String sql = "INSERT INTO herramienta (nombre, descripcion, stock, estado) VALUES (?, ?, ?, ?)";
            PreparedStatement insertarHerramienta = con.prepareStatement(sql);

            
            insertarHerramienta.setString(1, "Sierra");
            insertarHerramienta.setString(2, "Sierra electrica");
            insertarHerramienta.setInt(3, 15);
            insertarHerramienta.setBoolean(4, true);
            insertarHerramienta.executeUpdate();

         
            insertarHerramienta.setString(1, "Llave");
            insertarHerramienta.setString(2, "Llave inglesa");
            insertarHerramienta.setInt(3, 15);
            insertarHerramienta.setBoolean(4, true);
            insertarHerramienta.executeUpdate();

            JOptionPane.showMessageDialog(null, "Herramientas insertadas exitosamente.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al insertar herramientas: " + ex.getMessage());
        }
    }

    private static void listarHerramientas(Connection con) throws SQLException {
        try {
            String SQL = "SELECT * FROM herramienta WHERE stock > 10";
            PreparedStatement listarHerramientas = con.prepareStatement(SQL);
            ResultSet resultSet = listarHerramientas.executeQuery();

            while (resultSet.next()) {
                int idHerramienta = resultSet.getInt("idHerramienta");
                String nombre = resultSet.getString("nombre");
                String descripcion = resultSet.getString("descripcion");
                int stock = resultSet.getInt("stock");

                System.out.println("ID de la herramienta: " + idHerramienta);
                System.out.println("Nombre: " + nombre);
                System.out.println("Descripción: " + descripcion);
                System.out.println("Stock: " + stock);
                System.out.println("-----------------------");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al listar herramientas: " + ex.getMessage());
        }
    }

   private static void darDeBajaPrimerEmpleado(Connection conexion) throws SQLException {
    String SQL = "UPDATE empleado SET estado = 0 WHERE idEmpleado = 1";
    try (PreparedStatement preparedStatement = conexion.prepareStatement(SQL)) {
        int filasAfectadas = preparedStatement.executeUpdate();
        if (filasAfectadas > 0) {
            System.out.println("Empleado dado de baja exitosamente.");
        } else {
            System.out.println("No se encontró al empleado para dar de baja.");
        }
    }
}

}
