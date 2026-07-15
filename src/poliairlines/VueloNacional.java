package poliairlines;

import java.time.LocalDateTime;

public class VueloNacional extends Vuelo {
    private Double impuestoLocal;
    private Double subsidioEstatal;

    public VueloNacional(String codigoVuelo, String origen, String destino, LocalDateTime fechaHora, Double impuestoLocal, Double subsidioEstatal) {
        super(codigoVuelo, origen, destino, fechaHora);
        this.impuestoLocal = impuestoLocal;
        this.subsidioEstatal = subsidioEstatal;
    }

    @Override
    public Double calcularPrecioBase() {
        double precioBase = 50.0; // Tarifa base simulada
        return precioBase + impuestoLocal - subsidioEstatal;
    }
}