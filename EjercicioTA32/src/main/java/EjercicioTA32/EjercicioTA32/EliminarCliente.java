package EjercicioTA32.EjercicioTA32;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EliminarCliente extends JPanel {
    private JTextField idField;
    private JButton btnEliminar;

    public EliminarCliente(final Connection conexion) {
        setLayout(new GridLayout(1, 2));

        JLabel lblID = new JLabel("ID del Cliente:");
        idField = new JTextField();
        btnEliminar = new JButton("Eliminar");

        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(idField.getText());
                try {
                    String deleteQuery = "DELETE FROM cliente WHERE id = ?";
                    PreparedStatement preparedStatement = conexion.prepareStatement(deleteQuery);
                    preparedStatement.setInt(1, id);
                    int rowCount = preparedStatement.executeUpdate();

                    if (rowCount > 0) {
                        JOptionPane.showMessageDialog(null, "Cliente eliminado exitosamente.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Cliente no encontrado o no se pudo eliminar.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        add(lblID);
        add(idField);
        add(btnEliminar);
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
                new EliminarCliente(conexion[0]);
            }
        });
    }
}
