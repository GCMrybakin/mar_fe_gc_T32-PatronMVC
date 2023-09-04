package EjercicioTA32.EjercicioTA32;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CrearCliente extends JPanel {
    private JTextField nombreField;
    private JTextField apellidoField;
    private JTextField direccionField;
    private JTextField dniField;
    private JButton btnCrear;

    public CrearCliente(final Connection conexion) {
        setLayout(new GridLayout(6, 2));

        JLabel lblNombre = new JLabel("Nombre:");
        nombreField = new JTextField();

        JLabel lblApellido = new JLabel("Apellido:");
        apellidoField = new JTextField();

        JLabel lblDireccion = new JLabel("Dirección:");
        direccionField = new JTextField();

        JLabel lblDNI = new JLabel("DNI:");
        dniField = new JTextField();

        btnCrear = new JButton("Crear");
        btnCrear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nombre = nombreField.getText();
                String apellido = apellidoField.getText();
                String direccion = direccionField.getText();
                int dni = Integer.parseInt(dniField.getText());

                try {
                    String insertQuery = "INSERT INTO cliente (nombre, apellido, direccion, dni) VALUES (?, ?, ?, ?)";
                    PreparedStatement preparedStatement = conexion.prepareStatement(insertQuery);
                    preparedStatement.setString(1, nombre);
                    preparedStatement.setString(2, apellido);
                    preparedStatement.setString(3, direccion);
                    preparedStatement.setInt(4, dni);
                    preparedStatement.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Cliente creado exitosamente.");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al crear el cliente.");
                }
            }
        });

        add(lblNombre);
        add(nombreField);
        add(lblApellido);
        add(apellidoField);
        add(lblDireccion);
        add(direccionField);
        add(lblDNI);
        add(dniField);
        add(btnCrear);
    }


    public static void main(String[] args) {
        final String url = "jdbc:mysql://localhost:3306/EjerciciosTA31";
        final String usuario = "root";
        final String contraseña = "root9";
        final Connection[] conexion = { null };

        try {
            conexion[0] = DriverManager.getConnection(url, usuario, contraseña);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CrearCliente(conexion[0]);
            }
        });
    }
}
