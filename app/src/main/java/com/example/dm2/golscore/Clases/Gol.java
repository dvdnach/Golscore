package com.example.dm2.golscore.Clases;

public class Gol {

    private int id;
    private int id_jugador;
    private int id_partido;
    private int minuto;

    public Gol() {
    }

    public Gol(int id, int id_jugador, int id_partido, int minuto) {
        this.id = id;
        this.id_jugador = id_jugador;
        this.id_partido=id_partido;
        this.minuto=minuto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_jugador() {
        return id_jugador;
    }

    public void setId_jugador(int id_jugador) {
        this.id_jugador = id_jugador;
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
