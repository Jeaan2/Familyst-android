package com.tcc.ufpr.familyst.Model;

import android.graphics.Bitmap;

import java.util.Date;

/**
 * Created by jeaan_000 on 02/11/2016.
 */
public class Foto {

    private int idImagem;
    private Bitmap imagem;
    private String descricao;
    private Date dataCriacao;

    public Foto(int idImagem, Bitmap imagem, String descricao, Date dataCriacao) {
        this.idImagem = idImagem;
        this.imagem = imagem;
        this.descricao = descricao;
        this.dataCriacao = dataCriacao;
    }

    public int getIdImagem() {
        return idImagem;
    }

    public void setIdImagem(int idImagem) {
        this.idImagem = idImagem;
    }

    public Bitmap getImagem() {
        return imagem;
    }

    public void setImagem(Bitmap imagem) {
        this.imagem = imagem;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
