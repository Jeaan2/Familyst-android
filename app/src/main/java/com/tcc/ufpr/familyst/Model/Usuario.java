package com.tcc.ufpr.familyst.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by jeaan_000 on 20/10/2016.
 */
public class Usuario implements Serializable {

    private int idUsuario;
    private String nome;
    private String senha;
    private String email;
    private int idFacebook;

    //propriedades exclusivas do client
    private ArrayList<Familia> familias = new ArrayList<>();

    public Usuario(int idUsuario, String nome, String email) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.email = email;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdFacebook() {
        return idFacebook;
    }

    public void setIdFacebook(int idFacebook) {
        this.idFacebook = idFacebook;
    }

    public ArrayList<Familia> getFamilias() {
        return familias;
    }

    public void setFamilias(ArrayList<Familia> familias) {
        this.familias = familias;
    }

}
