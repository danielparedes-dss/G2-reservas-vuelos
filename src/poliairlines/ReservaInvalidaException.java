package poliairlines;

public class ReservaInvalidaException extends Exception {
    private String motivoError;
    private Integer codigoError;

    public ReservaInvalidaException(String mensaje) {
        super(mensaje);
        this.motivoError = mensaje;
        this.codigoError = 400; // Código por defecto para errores de validación
    }

    public String getMotivoError() {
        return motivoError;
    }

    public Integer getCodigoError() {
        return codigoError;
    }
}