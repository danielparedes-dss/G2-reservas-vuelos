package poliairlines;

import java.time.LocalDateTime;

public class VueloInternacional extends Vuelo {
    private Double tasaInternacional;

    public VueloInternacional(String codigoVuelo, String origen, String destino, LocalDateTime fechaHora, Double tasaInternacional) {
        super(codigoVuelo, origen, destino, fechaHora);
        this.tasaInternacional = tasaInternacional;
    }

    @Override
    public Double calcularPrecioBase() {
        double precioBase = 200.0; // Tarifa base simulada
        return precioBase + tasaInternacional;
    }
}