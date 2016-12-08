package com.tcc.ufpr.familyst.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jeaan_000 on 12/10/2016.
 */
public class Noticia implements Serializable {

    //TODO Adicionar Comentários;
    protected int idNoticia;
    protected String descricao;
    protected Date dataCriacao;
    protected int idUsuario;

    //propriedades exclusivas do client
    protected ArrayList<Comentario> comentarios = new ArrayList<>();
    protected Usuario _usuarioCriador;

    public Noticia(int idNoticia, String descricao, int idUsuario)
    {
        this.idNoticia = idNoticia;
        this.descricao = descricao;
        this.idUsuario = idUsuario;
    }

    public int getIdUsuario()
    {
        return idUsuario;
    }

    public void setUsuarioCriador(Usuario usuarioCriador)
    {
        _usuarioCriador = usuarioCriador;
    }

    public Usuario getUsuarioCriador()
    {
        return _usuarioCriador;
    }

    public Noticia(){} //remover

    public ArrayList<Comentario> getComentarios() { return comentarios; }

    public void setComentarios(ArrayList<Comentario> comentarios) { this.comentarios = comentarios; }

    public Date getDataCriacao() { return dataCriacao;}

    public void setDataCriacao(Date dataCriacao) { this.dataCriacao = dataCriacao;  }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getIdNoticia() {
        return idNoticia;
    }

    public void setIdNoticia(int idNoticia) {
        this.idNoticia = idNoticia;
    }
}
