package com.example.dm2.golscore.Clases;

public class Jugador {

    private int id;
    private String apellido;
    private int id_equipo;
    private String nombre;
    private String posicion;

    public Jugador() {
        this.nombre="Selecciona";
        this.apellido="Jugador";
    }

    public Jugador(int id, String apellido, int id_equipo, String nombre, String posicion) {
        this.id = id;
        this.apellido = apellido;
        this.id_equipo=id_equipo;
        this.nombre=nombre;
        this.posicion=posicion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getId_equipo() {
        return id_equipo;
    }

    public void setId_equipo(int id_equipo) {
        this.id_equipo = id_equipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    @Override
    public String toString() {
        return this.getNombre()+" "+this.getApellido();
    }
}
