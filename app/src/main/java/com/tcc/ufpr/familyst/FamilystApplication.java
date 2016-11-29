package com.tcc.ufpr.familyst;

import android.app.Application;

import com.tcc.ufpr.familyst.Model.Familia;
import com.tcc.ufpr.familyst.Model.Usuario;

import java.util.ArrayList;

/**
 * Created by jdfid on 26/11/2016.
 */
public class FamilystApplication extends Application {

    private String _accessToken;
    private Usuario _usuarioLogado;
    private int idFamiliaSelecionada = 0; //algum evento precisa dizer quando esse id foi trocado para toda aplicacao recarregar dados //aki ta 0 de id mas ta errado porque na verdade ta mocado nas chamadas a ele procurando o index no vetor de familias e nao o id da familia

    public String get_accessToken() {
        return _accessToken;
    }

    public void set_accessToken(String _accessToken) {
        this._accessToken = _accessToken;
    }

    public Usuario get_usuarioLogado() {
        return _usuarioLogado;
    }

    public void set_usuarioLogado(Usuario _usuarioLogado) {
        this._usuarioLogado = _usuarioLogado;
    }

    public int getIdFamiliaSelecionada() {
        return idFamiliaSelecionada;
    }

    public void setIdFamiliaSelecionada(int idFamiliaSelecionada) {
        this.idFamiliaSelecionada = idFamiliaSelecionada;
    }
}
