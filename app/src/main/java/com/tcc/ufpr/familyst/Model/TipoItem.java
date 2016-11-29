package com.tcc.ufpr.familyst.Model;

/**
 * Created by jdfid on 27/11/2016.
 */

public class TipoItem {

    private final int idTipoItem;
    private final String nome;

    public TipoItem(int idTipoItem, String nome) {
        this.idTipoItem = idTipoItem;
        this.nome = nome;
    }

    public int getIdTipoItem() {
        return idTipoItem;
    }

    public String getNome() {
        return nome;
    }
}
