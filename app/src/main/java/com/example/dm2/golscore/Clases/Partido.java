package com.example.dm2.golscore.Clases;

public class Partido {

    private int id;
    private int eq_local;
    private int eq_visitante;
    private int grupo;
    private String hora;
    private String fecha;
    private int jornada;
    private int gol_local;
    private int gol_visitante;

    public Partido() {
    }

    public Partido(int id, int eq_local, int eq_visitante,int grupo,String hora, String fecha, int jornada,int gol_local,int
                   gol_visitante) {
        this.id = id;
        this.eq_local = eq_local;
        this.eq_visitante=eq_visitante;
        this.grupo=grupo;
        this.fecha=fecha;
        this.hora=hora;
        this.jornada=jornada;
        this.gol_local=gol_local;
        this.gol_visitante=gol_visitante;
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

    public int getGrupo() {
        return grupo;
    }

    public void setGrupo(int grupo) {
        this.grupo = grupo;
    }

    public int getGol_local() {
        return gol_local;
    }

    public int getGol_visitante() {
        return gol_visitante;
    }

    public void setGol_local(int gol_local) {
        this.gol_local = gol_local;
    }

    public void setGol_visitante(int gol_visitante) {
        this.gol_visitante = gol_visitante;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
