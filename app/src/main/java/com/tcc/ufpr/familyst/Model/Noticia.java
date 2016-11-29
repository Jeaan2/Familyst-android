package com.tcc.ufpr.familyst.Model;

import java.util.Date;
import java.util.List;

/**
 * Created by jeaan_000 on 12/10/2016.
 */
public class Noticia {

    //TODO Adicionar Comentários;
    protected int idNoticia;
    protected String descricao;
    protected Date dataCriacao;
    protected List<Comentario> comentarios;

    public Noticia(int idNoticia, String descricao)
    {
        this.idNoticia = idNoticia;
        this.descricao = descricao;
    }

    public Noticia(){} //remover

    public List<Comentario> getComentarios() { return comentarios; }

    public void setComentarios(List<Comentario> comentarios) { this.comentarios = comentarios; }

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
