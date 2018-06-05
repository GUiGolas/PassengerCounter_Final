package com.example.guilherme.passengercounter_final;

import android.content.Context;

public class Viagem {

    private final String onibus;
    private final String cor;
    private final String origem;
    private final String destino;
    private final int id_Viagem;


    public Viagem(String onibus, String cor, String origem, String destino, int id_Viagem) {
        this.onibus = onibus;
        this.cor = cor;
        this.origem = origem;
        this.destino = destino;
        this.id_Viagem = id_Viagem;
    }


    public String getOnibus() {
        return onibus;
    }

    public String getCor() {
        return cor;
    }

    public String getOrigem() {
        return origem;
    }

    public String getDestino() {
        return destino;
    }

    public int getId_Viagem() {
        return id_Viagem;
    }


}

