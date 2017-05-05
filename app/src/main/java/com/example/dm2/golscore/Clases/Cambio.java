package com.example.dm2.golscore.Clases;

public class Cambio {

    private int id;
    private int id_jugador_entra;
    private int id_jugador_sale;
    private int id_partido;
    private int minuto;

    public Cambio() {
    }

    public Cambio(int id, int id_jugador_entra, int id_jugador_sale, int id_partido, int minuto) {
        this.id = id;
        this.id_jugador_entra = id_jugador_entra;
        this.id_jugador_sale = id_jugador_sale;
        this.id_partido = id_partido;
        this.minuto = minuto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_jugador_entra() {
        return id_jugador_entra;
    }

    public void setId_jugador_entra(int id_jugador_entra) {
        this.id_jugador_entra = id_jugador_entra;
    }

    public int getId_jugador_sale() {
        return id_jugador_sale;
    }

    public void setId_jugador_sale(int id_jugador_sale) {
        this.id_jugador_sale = id_jugador_sale;
    }

    public int getId_partido() {
        return id_partido;
    }

    public void setId_partido(int id_partido) {
        this.id_partido = id_partido;
    }

    public int getMinuto() {
        return minuto;
    }

    public void setMinuto(int minuto) {
        this.minuto = minuto;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
