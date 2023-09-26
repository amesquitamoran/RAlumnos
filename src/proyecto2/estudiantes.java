package proyecto2;
import javax.swing.JOptionPane;
public class estudiantes {
    int id;
    String nombre;
    String apellido;
    String telefono;
    String direccion;

    public estudiantes(int id, String nombre,String apellido, String telefono, String direccion){
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getDireccion() {
        return direccion;
    }
}
