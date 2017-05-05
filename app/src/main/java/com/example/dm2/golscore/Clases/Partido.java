package com.example.dm2.golscore.Clases;

public class Partido {

    private int id;
    private int eq_local;
    private int eq_visitante;
    private String fecha;
    private String hora;
    private int jornada;

    public Partido() {
    }

    public Partido(int id, int eq_local, int eq_visitante, String fecha, String hora, int jornada) {
        this.id = id;
        this.eq_local = eq_local;
        this.eq_visitante=eq_visitante;
        this.fecha=fecha;
        this.hora=hora;
        this.jornada=jornada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEq_local() {
        return eq_local;
    }

    public void setEq_local(int eq_local) {
        this.eq_local = eq_local;
    }

    public int getEq_visitante() {
        return eq_visitante;
    }

    public void setEq_visitante(int eq_visitante) {
        this.eq_visitante = eq_visitante;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getJornada() {
        return jornada;
    }

    public void setJornada(int jornada) {
        this.jornada = jornada;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
