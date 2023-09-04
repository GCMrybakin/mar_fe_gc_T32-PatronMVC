package EjercicioTA32.EjercicioTA32;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App {
    private JFrame frame;
    private JPanel contentPanel;
    private CrearCliente crearView;
    private LeerCliente leerView;
    private ActualizarCliente actualizarView;
    private EliminarCliente eliminarView;

    private void mostrarVista(JPanel vista) {
    	contentPanel.removeAll();
    	contentPanel.add(vista);
    	contentPanel.revalidate();
    	contentPanel.repaint();
    }

    public App(final Connection conexion) {
        frame = new JFrame("Gestión de Clientes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        contentPanel = new JPanel();
        contentPanel.setLayout(new CardLayout());
        frame.add(contentPanel, BorderLayout.CENTER);

        JPanel menuPanel = new JPanel();
        JButton btnCrear = new JButton("Crear Cliente");
        JButton btnLeer = new JButton("Leer Cliente");
        JButton btnActualizar = new JButton("Actualizar Cliente");
        JButton btnEliminar = new JButton("Eliminar Cliente");

        btnCrear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (crearView == null) {
                    crearView = new CrearCliente(conexion);
                }
                mostrarVista(crearView);
            }
        });

        btnLeer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (leerView == null) {
                    leerView = new LeerCliente(conexion);
                }
                mostrarVista(leerView);
            }
        });

        btnActualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (actualizarView == null) {
                    actualizarView = new ActualizarCliente(conexion);
                }
                mostrarVista(actualizarView);
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (eliminarView == null) {
                    eliminarView = new EliminarCliente(conexion);
                }
                mostrarVista(eliminarView);
            }
        });

        menuPanel.add(btnCrear);
        menuPanel.add(btnLeer);
        menuPanel.add(btnActualizar);
        menuPanel.add(btnEliminar);

        frame.add(menuPanel, BorderLayout.NORTH);
        frame.setVisible(true);
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
                new App(conexion[0]);
            }
        });
    }
}
