package poliairlines;

public class Asiento {
    private String numeroAsiento;
    private String claseAsiento;
    private Boolean estaOcupado;

    public Asiento(String numeroAsiento, String claseAsiento) {
        this.numeroAsiento = numeroAsiento;
        this.claseAsiento = claseAsiento;
        this.estaOcupado = false;
    }

    public String getNumeroAsiento() {
        return numeroAsiento; }

    public String getClaseAsiento() {
        return claseAsiento; }

    public Boolean getEstaOcupado() {
        return estaOcupado; }

    public void ocuparAsiento() {
        this.estaOcupado = true;
    }

    @Override
    public String toString() {
        return numeroAsiento + " (" + claseAsiento + ")";
    }
}