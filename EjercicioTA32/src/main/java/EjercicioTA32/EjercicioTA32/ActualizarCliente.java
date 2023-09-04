package EjercicioTA32.EjercicioTA32;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ActualizarCliente extends JPanel {
    private JTextField idField;
    private JTextField nombreField;
    private JTextField apellidoField;
    private JTextField direccionField;
    private JTextField dniField;
    private JButton btnBuscar;
    private JButton btnActualizar;

    public ActualizarCliente(final Connection conexion) {
        setLayout(new GridLayout(6, 2));

        JLabel lblID = new JLabel("ID del Cliente:");
        idField = new JTextField();
        btnBuscar = new JButton("Buscar");

        JLabel lblNombre = new JLabel("Nuevo Nombre:");
        nombreField = new JTextField();

        JLabel lblApellido = new JLabel("Nuevo Apellido:");
        apellidoField = new JTextField();

        JLabel lblDireccion = new JLabel("Nueva Dirección:");
        direccionField = new JTextField();

        JLabel lblDNI = new JLabel("Nuevo DNI:");
        dniField = new JTextField();

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setEnabled(false);

        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(idField.getText());
                try {
                    String query = "SELECT * FROM cliente WHERE id = ?";
                    PreparedStatement preparedStatement = conexion.prepareStatement(query);
                    preparedStatement.setInt(1, id);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        nombreField.setText(resultSet.getString("nombre"));
                        apellidoField.setText(resultSet.getString("apellido"));
                        direccionField.setText(resultSet.getString("direccion"));
                        dniField.setText(Integer.toString(resultSet.getInt("dni")));
                        btnActualizar.setEnabled(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Cliente no encontrado.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        btnActualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(idField.getText());
                String nuevoNombre = nombreField.getText();
                String nuevoApellido = apellidoField.getText();
                String nuevaDireccion = direccionField.getText();
                int nuevoDni = Integer.parseInt(dniField.getText());

                try {
                    String updateQuery = "UPDATE cliente SET nombre = ?, apellido = ?, direccion = ?, dni = ? WHERE id = ?";
                    PreparedStatement preparedStatement = conexion.prepareStatement(updateQuery);
                    preparedStatement.setString(1, nuevoNombre);
                    preparedStatement.setString(2, nuevoApellido);
                    preparedStatement.setString(3, nuevaDireccion);
                    preparedStatement.setInt(4, nuevoDni);
                    preparedStatement.setInt(5, id);

                    int rowCount = preparedStatement.executeUpdate();

                    if (rowCount > 0) {
                        JOptionPane.showMessageDialog(null, "Cliente actualizado exitosamente.");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo actualizar el cliente.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        add(lblID);
        add(idField);
        add(btnBuscar);
        add(lblNombre);
        add(nombreField);
        add(lblApellido);
        add(apellidoField);
        add(lblDireccion);
        add(direccionField);
        add(lblDNI);
        add(dniField);
        add(btnActualizar);
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
                new ActualizarCliente(conexion[0]);
            }
        });
    }
}
