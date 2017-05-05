package com.example.dm2.golscore.Clases;

public class Equipo {

    private String categoria;
    private String escudo;
    private String genero;
    private int grupo;
    private int id;
    private int id_club;
    private String nombre;
    private int puntos;
    private int total_goles;

    public Equipo(String categoria, String escudo, String genero, int grupo, int id, int id_club, String nombre, int puntos, int total_goles) {
        this.categoria = categoria;
        this.escudo=escudo;
        this.genero = genero;
        this.grupo = grupo;
        this.id = id;
        this.id_club = id_club;
        this.nombre = nombre;
        this.puntos = puntos;
        this.total_goles = total_goles;
    }

    public Equipo() {
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getEscudo() {
        return escudo;
    }

    public void setEscudo(String escudo) {
        this.escudo = escudo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getGrupo() {
        return grupo;
    }

    public void setGrupo(int grupo) {
        this.grupo = grupo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_club() {
        return id_club;
    }

    public void setId_club(int id_club) {
        this.id_club = id_club;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getTotal_goles() {
        return total_goles;
    }

    public void setTotal_goles(int total_goles) {
        this.total_goles = total_goles;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
