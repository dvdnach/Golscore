package com.example.dm2.golscore.Clases;

public class Club {

    private long Id;
    private String Nombre;
    private int SubCategoria;

    public Club() {
    }

    public Club(long id, String nombre, int subcategoria) {
        this.Id = id;
        this.Nombre = nombre;
        this.SubCategoria=subcategoria;
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

    public int getSubcategoria() {
        return SubCategoria;
    }

    public void setSubcategoria(int subcategoria) {
        SubCategoria = subcategoria;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}