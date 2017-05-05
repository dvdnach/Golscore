package com.example.dm2.golscore.Clases;

public class Tarjeta {

    private int id;
    private String color;
    private int id_jugador;
    private int id_partido;
    private int minuto;

    public Tarjeta() {
    }

    public Tarjeta(int id, String color, int id_jugador, int id_partido, int minuto) {
        this.id = id;
        this.color = color;
        this.id_jugador = id_jugador;
        this.id_partido = id_partido;
        this.minuto = minuto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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
