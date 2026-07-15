package poliairlines;

public class Reserva {
    private String codigoReserva;
    private Pasajero pasajeroAsociado;
    private Vuelo vueloAsociado;
    private Asiento asientoAsignado;
    private String estadoReserva;

    public Reserva() {
        this.estadoReserva = "Pendiente";
    }

    public void crearReserva(Pasajero pasajero, Vuelo vuelo, Asiento asiento) throws ReservaInvalidaException {
        // La clase misma verifica la integridad de la regla de negocio
        if (asiento.getEstaOcupado()) {
            throw new ReservaInvalidaException("Doble asignación detectada: El asiento " + asiento.getNumeroAsiento() + " ya está reservado en el sistema.");
        }

        this.codigoReserva = "RES-" + System.currentTimeMillis();
        this.pasajeroAsociado = pasajero;
        this.vueloAsociado = vuelo;
        this.asientoAsignado = asiento;
        this.asientoAsignado.ocuparAsiento();
    }

    public void confirmarPago() {
        this.estadoReserva = "Confirmada";
    }

    public String getCodigoReserva() {
        return codigoReserva;
    }

    public Pasajero getPasajeroAsociado() {
        return pasajeroAsociado;
    }

    public Vuelo getVueloAsociado() {
        return vueloAsociado;
    }

    public Asiento getAsientoAsignado() {
        return asientoAsignado;
    }

    public String getEstadoReserva() {
        return estadoReserva;
    }
}