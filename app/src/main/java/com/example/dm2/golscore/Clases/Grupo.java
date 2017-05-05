package com.example.dm2.golscore.Clases;

public class Grupo {

    private int id;
    private int categoria;
    private String nombre;

    public Grupo() {
    }

    public Grupo(int id, int categoria, String nombre) {
        this.id = id;
        this.categoria = categoria;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
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
