package poliairlines;

public class ReservaInvalidaException extends Exception {
    private String motivoError;

    public ReservaInvalidaException(String mensaje) {
        super(mensaje);
        this.motivoError = mensaje;
    }

    public String getMotivoError() {
        return motivoError;
    }
}