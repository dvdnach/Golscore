package com.example.dm2.golscore.Clases;

public class Club {

    private int id;
    private String campo;
    private double latitud;
    private double longitud;
    private String nombre;

    public Club() {
    }

    public Club(int id, String campo, double latitud, double longitud, String nombre) {
        this.id = id;
        this.campo = campo;
        this.latitud=latitud;
        this.longitud=longitud;
        this.nombre=nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}