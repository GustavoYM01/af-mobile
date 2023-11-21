package com.example.af_mobile.models;

public class Serie {
    private String id;
    private String nome;
    private String diaSemana;
    private String plataforma;
    private int temporadaAtual, ultimoEp;

    public Serie() {}

    public Serie(String id, String nome, String diaSemana, String plataforma, int temporadaAtual, int ultimoEp) {
        this.id = id;
        this.nome = nome;
        this.diaSemana = diaSemana;
        this.plataforma = plataforma;
        this.temporadaAtual = temporadaAtual;
        this.ultimoEp = ultimoEp;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public int getTemporadaAtual() {
        return temporadaAtual;
    }

    public void setTemporadaAtual(int temporadaAtual) {
        this.temporadaAtual = temporadaAtual;
    }

    public int getUltimoEp() {
        return ultimoEp;
    }

    public void setUltimoEp(int ultimoEp) {
        this.ultimoEp = ultimoEp;
    }
}
