package com.tcc.ufpr.familyst.Model;

/**
 * Created by jdfid on 27/11/2016.
 */

public class TipoEvento {

    private int idTipoEvento;
    private String nome;

    public TipoEvento(int idTipoEvento, String nome) {
        this.idTipoEvento = idTipoEvento;
        this.nome = nome;
    }

    public int getIdTipoEvento() {
        return idTipoEvento;
    }

    public void setIdTipoEvento(int idTipoEvento) {
        this.idTipoEvento = idTipoEvento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
