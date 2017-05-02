package com.example.dm2.golscore.Clases;

public class Subcategoria {

    private long Id;
    private String Nombre;
    private int Categoria;

    public Subcategoria() {
    }

    public Subcategoria(long id, String nombre, int categoria) {
        this.Id = id;
        this.Nombre = nombre;
        this.Categoria=categoria;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        this.Id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        this.Nombre = nombre;
    }

    public int getCategoria() {
        return Categoria;
    }

    public void setCategoria(int categoria) {
        Categoria = categoria;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
