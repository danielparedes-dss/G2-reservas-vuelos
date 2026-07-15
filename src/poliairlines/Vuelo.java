package poliairlines;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class Vuelo {
    protected String codigoVuelo;
    protected String origen;
    protected String destino;
    protected LocalDateTime fechaHora;
    protected List<Asiento> listaAsientos;

    public Vuelo(String codigoVuelo, String origen, String destino, LocalDateTime fechaHora) {
        this.codigoVuelo = codigoVuelo;
        this.origen = origen;
        this.destino = destino;
        this.fechaHora = fechaHora;
        this.listaAsientos = new ArrayList<>();
        generarAsientosBase();
    }

    private void generarAsientosBase() {
        String[] letras = {"A", "B", "C", "D"};

        // Creamos 10 filas en total
        for (int fila = 1; fila <= 10; fila++) {
            String clase;

            // Asignamos la clase dependiendo de la fila
            if (fila <= 2) {
                clase = "Primera Clase";      // Filas 1 y 2
            } else if (fila <= 5) {
                clase = "Clase Ejecutiva";    // Filas 3, 4 y 5
            } else {
                clase = "Clase Económica";    // Filas 6 a 10
            }

            for (String letra : letras) {
                listaAsientos.add(new Asiento(fila + letra, clase));
            }
        }
    }

    public abstract Double calcularPrecioBase();

    public Boolean verificarDisponibilidad() {
        for (Asiento asiento : listaAsientos) {
            if (!asiento.getEstaOcupado()) {
                return true;
            }
        }
        return false;
    }

    public List<Asiento> obtenerAsientosLibres() {
        List<Asiento> libres = new ArrayList<>();
        for (Asiento asiento : listaAsientos) {
            if (!asiento.getEstaOcupado()) {
                libres.add(asiento);
            }
        }
        return libres;
    }

    public String getCodigoVuelo() { return codigoVuelo; }
    public String getOrigen() { return origen; }
    public String getDestino() { return destino; }
    public LocalDateTime getFechaHora() { return fechaHora; }

    // Método para buscar un asiento específico en la lista
    public Asiento buscarAsiento(String numero) {
        for (Asiento a : listaAsientos) {
            if (a.getNumeroAsiento().equals(numero)) return a;
        }
        return null;
    }

    @Override
    public String toString() {
        return codigoVuelo + " : " + origen + " -> " + destino;
    }

    public List<Asiento> getListaAsientos() {
        return listaAsientos;
    }

    // Método para calcular el precio final dependiendo de la categoría del asiento
    public Double calcularPrecioAsiento(Asiento asiento) {
        double precioBase = calcularPrecioBase(); // Trae el precio con impuestos o tasas

        if (asiento.getClaseAsiento().equals("Primera Clase")) {
            return precioBase * 2.0;      // Primera Clase cuesta el doble
        } else if (asiento.getClaseAsiento().equals("Clase Ejecutiva")) {
            return precioBase * 1.5;      // Ejecutiva cuesta un 50% más
        } else {
            return precioBase;            // Económica paga la tarifa normal
        }
    }
}