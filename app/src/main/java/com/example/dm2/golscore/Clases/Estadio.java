package com.example.dm2.golscore.Clases;

/**
 * Created by iban on 29/04/2017.
 */

public class Estadio {
    private double longitud;
    private String equipo;
    private double latitud;


    public Estadio(double latitud, double longitud,String equipo) {
        this.longitud = longitud;
        this.equipo=equipo;
        this.latitud = latitud;
    }

    public Estadio() {
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public double getLatitud() {

        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}
