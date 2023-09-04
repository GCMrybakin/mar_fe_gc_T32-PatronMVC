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

public class LeerCliente extends JPanel {
    private JTextField idField;
    private JTextArea resultadoArea;
    private JButton btnBuscar;

    public LeerCliente(final Connection conexion) {
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2));

        JLabel lblID = new JLabel("ID del Cliente:");
        idField = new JTextField();
        btnBuscar = new JButton("Buscar");

        resultadoArea = new JTextArea(5, 20);
        resultadoArea.setEditable(false);

        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(idField.getText());
                try {
                    String query = "SELECT * FROM cliente WHERE id = ?";
                    PreparedStatement preparedStatement = conexion.prepareStatement(query);
                    preparedStatement.setInt(1, id);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        String nombre = resultSet.getString("nombre");
                        String apellido = resultSet.getString("apellido");
                        String direccion = resultSet.getString("direccion");
                        int dni = resultSet.getInt("dni");

                        resultadoArea.setText("Nombre: " + nombre + "\nApellido: " + apellido + "\nDirección: " + direccion + "\nDNI: " + dni);
                    } else {
                        resultadoArea.setText("Cliente no encontrado.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        inputPanel.add(lblID);
        inputPanel.add(idField);
        inputPanel.add(btnBuscar);

        add(inputPanel, BorderLayout.NORTH);
        add(resultadoArea, BorderLayout.CENTER);
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
                new LeerCliente(conexion[0]);
            }
        });
    }
}
