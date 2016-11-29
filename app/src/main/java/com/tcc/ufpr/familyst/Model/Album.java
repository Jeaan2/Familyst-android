package com.tcc.ufpr.familyst.Model;

import java.util.Date;

/**
 * Created by jeaan_000 on 30/10/2016.
 */
public class Album {

    private int idAlbum;
    private String nome;
    private Date dataCriacao;


    public Album(int idAlbum, String nome, Date dataCriacao) {
        this.idAlbum = idAlbum;
        this.nome = nome;
        this.dataCriacao = dataCriacao;
    }

    public String getNome() {
        return nome;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }
}
