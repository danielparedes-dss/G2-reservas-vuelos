package poliairlines;

public class Pasajero {
    private String idPasajero;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;

    public Pasajero(String idPasajero, String nombre, String apellido, String email, String telefono) {
        this.idPasajero = idPasajero;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
    }

    public String getIdPasajero() { return idPasajero; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getEmail() { return email; }
    public String getTelefono() { return telefono; }

    public Boolean registrarPasajero() {
        // En este alcance, simulamos que el registro en memoria es exitoso
        return true;
    }
}