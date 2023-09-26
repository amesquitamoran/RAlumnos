package proyecto2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ventanaPrincipal extends JFrame{
    Connection connection;
    PreparedStatement ps;// preparamos una sentencia(PreparesStatement)
    DefaultListModel dm = new DefaultListModel<>();
    ResultSet r;
    //Statement st;
    private JPanel Jpanel;
    private JTextField textid;
    private JTextField textnombre;
    private JTextField textapellido;
    private JTextField texttelefono;
    private JTextField textdireccion;
    private JList listmostrar;
    private JButton btnagregar;
    private JButton btnmodificar;
    private JButton btnbuscar;
    private JButton btneliminar;
    private JButton btnconsultar;



    public ventanaPrincipal() {
        btnconsultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    listar();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        btnagregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    agregar();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        btneliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    eliminar();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
    public void listar() throws SQLException {
        conectar();

        String query = "SELECT ID, nombre, apellido FROM estudiantes";
        ps = connection.prepareStatement(query);
        r = ps.executeQuery();

        DefaultListModel<String> modelo = new DefaultListModel<>();
        while (r.next()) {
            String elemento = r.getString(1) + " " + r.getString(2) + " " + r.getString(3);
            modelo.addElement(elemento);
        }
        listmostrar.setModel(modelo);
    }
    public void agregar() throws SQLException {

        conectar();//aca jala la conexion de la bd
        ps = connection.prepareStatement("INSERT INTO estudiantes VALUES(?,?,?,?,?)");//hacemnos la consulta para insertar los datos
        ps.setInt(1,Integer.parseInt(textid.getText()));
        ps.setString(2,textnombre.getText());
        ps.setString(3,textapellido.getText());
        ps.setString(4,texttelefono.getText());
        ps.setString(5,textdireccion.getText());

        //creamos una condicion donde se diga que si se inreso mas de 0 filas esta bien

        if(ps.executeUpdate()>0){
          listmostrar.setModel(dm);
          dm.removeAllElements();
          dm.addElement("se Ingreso con exito");
          textid.setText("");
          textnombre.setText("");
          textapellido.setText("");
          texttelefono.setText("");
          textdireccion.setText("");
        }
    }

    public void eliminar() throws SQLException {
        conectar(); // Establece la conexión a la base de datos

        // Verifica si se ha seleccionado un elemento de la lista
        int indiceSeleccionado = listmostrar.getSelectedIndex();
        if (indiceSeleccionado >= 0) {
            String nombreSeleccionado = listmostrar.getSelectedValue().toString();
            int idEliminar = Integer.parseInt(nombreSeleccionado.split(" ")[0]);

            // Prepara la consulta para eliminar el registro
            ps = connection.prepareStatement("DELETE FROM estudiantes WHERE ID = ?");
            ps.setInt(1, idEliminar);

            // Ejecuta la consulta de eliminación
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Registro eliminado correctamente");
                textid.setText("");
                textnombre.setText("");
                textapellido.setText("");
                texttelefono.setText("");
                textdireccion.setText("");

                // Vuelve a listar los registros actualizados
                listar();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento de la lista");
        }
    }

    public static void main(String[] args) {
        ventanaPrincipal v = new ventanaPrincipal();
        v.setContentPane(new ventanaPrincipal().Jpanel);
        v.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        v.setVisible(true);
        v.pack();
    }

    public void conectar(){
        try {
            connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=USUARIO","sa","123");
            System.out.println("conexion Exitosa");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
