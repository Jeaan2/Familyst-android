package com.tcc.ufpr.familyst.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.util.Base64;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.tcc.ufpr.familyst.FamilystApplication;
import com.tcc.ufpr.familyst.Interfaces.RestCallback;
import com.tcc.ufpr.familyst.Model.Album;
import com.tcc.ufpr.familyst.Model.Comentario;
import com.tcc.ufpr.familyst.Model.Evento;
import com.tcc.ufpr.familyst.Model.Familia;
import com.tcc.ufpr.familyst.Model.Foto;
import com.tcc.ufpr.familyst.Model.Item;
import com.tcc.ufpr.familyst.Model.Noticia;
import com.tcc.ufpr.familyst.Model.TipoEvento;
import com.tcc.ufpr.familyst.Model.TipoItem;
import com.tcc.ufpr.familyst.Model.Usuario;
import com.tcc.ufpr.familyst.Model.Video;
import com.tcc.ufpr.familyst.R;
import com.tcc.ufpr.familyst.Services.JsonRestRequest;
import com.tcc.ufpr.familyst.Services.RestService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class LoadingDataActivity extends BaseActivity {

    boolean _familiasCarregadas = false;
    boolean _eventosCarregados = false;
    boolean _tiposEventosCarregados = false;
    boolean _usuarioEventosCarregados = false;
    boolean _itensEventosCarregados = false;
    boolean _comentariosEventosCarregados = false;
    boolean _tiposItemCarregados = false;
    boolean _usuariosCarregados = false;
    boolean _noticiasCarregadas = false;
    boolean _comentariosNoticiasCarregados = false;
    boolean _videosCarregados = false;
    boolean _albunsCarregados = false;
    boolean _fotosAlbunsCarregadas = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_data_activity);
        getSupportActionBar().hide();

        CarregarFamiliasUsuarioAsync();
    }

    private void CarregarFamiliasUsuarioAsync() {

        if (!_familiasCarregadas)
            RestService.getInstance(this).CarregarFamiliasAsync(new RestCallback(){
                @Override
                public void onRestResult(boolean success) {
                    if (success){
                        _familiasCarregadas = true;
                        CarregarFamiliasUsuarioAsync();
                    }
                }
            });
        else if (!_eventosCarregados)
            RestService.getInstance(this).CarregarEventosFamiliasAsync(new RestCallback(){
                @Override
                public void onRestResult(boolean success) {
                    if (success){
                        _eventosCarregados = true;
                        CarregarFamiliasUsuarioAsync();
                    }
                }
            });
        else if (!_tiposEventosCarregados)
            RestService.getInstance(this).CarregarTiposEventosFamiliasAsync(new RestCallback(){
                @Override
                public void onRestResult(boolean success) {
                    if (success){
                        _tiposEventosCarregados = true;
                        CarregarFamiliasUsuarioAsync();
                    }
                }
            });
        else if (!_usuarioEventosCarregados)
            RestService.getInstance(this).CarregarUsuarioEventosFamiliasAsync(new RestCallback(){
                @Override
                public void onRestResult(boolean success) {
                    if (success){
                        _usuarioEventosCarregados = true;
                        CarregarFamiliasUsuarioAsync();
                    }
                }
            });
        else if (!_itensEventosCarregados)
            RestService.getInstance(this).CarregarItensEventosFamiliasAsync(new RestCallback(){
                @Override
                public void onRestResult(boolean success) {
                    if (success){
                        _itensEventosCarregados = true;
                        CarregarFamiliasUsuarioAsync();
                    }
                }
            });
        else if (!_comentariosEventosCarregados)
            RestService.getInstance(this).CarregarComentariosEventosFamiliasAsync(new RestCallback(){
                @Override
                public void onRestResult(boolean success) {
                    if (success){
                        _comentariosEventosCarregados = true;
                        CarregarFamiliasUsuarioAsync();
                    }
                }
            });
        else if (!_tiposItemCarregados)
            RestService.getInstance(this).CarregarTiposItensAsync(new RestCallback(){
                @Override
                public void onRestResult(boolean success) {
                    if (success){
                        _tiposItemCarregados = true;
                        CarregarFamiliasUsuarioAsync();
                    }
                }
            });
        else if (!_usuariosCarregados)
            RestService.getInstance(this).CarregarUsuariosFamiliasAsync(new RestCallback(){
                @Override
                public void onRestResult(boolean success) {
                    if (success){
                        _usuariosCarregados = true;
                        CarregarFamiliasUsuarioAsync();
                    }
                }
            });
        else if (!_noticiasCarregadas)
            RestService.getInstance(this).CarregarNoticiasFamiliasAsync(new RestCallback(){
                @Override
                public void onRestResult(boolean success) {
                    if (success){
                        _noticiasCarregadas = true;
                        CarregarFamiliasUsuarioAsync();
                    }
                }
            });
        else if (!_albunsCarregados)
            RestService.getInstance(this).CarregarAlbunsFamiliasAsync(new RestCallback(){
                @Override
                public void onRestResult(boolean success) {
                    if (success){
                        _albunsCarregados = true;
                        CarregarFamiliasUsuarioAsync();
                    }
                }
            });
        else if (!_comentariosNoticiasCarregados)
            RestService.getInstance(this).CarregarComentariosNoticiasFamiliasAsync(new RestCallback(){
                @Override
                public void onRestResult(boolean success) {
                    if (success){
                        _comentariosNoticiasCarregados = true;
                        CarregarFamiliasUsuarioAsync();
                    }
                }
            });
        else if (!_videosCarregados)
            RestService.getInstance(this).CarregarVideosFamiliasAsync(new RestCallback(){
                @Override
                public void onRestResult(boolean success) {
                    if (success){
                        _videosCarregados = true;
                        CarregarFamiliasUsuarioAsync();
                    }
                }
            });
        else if (!_fotosAlbunsCarregadas)
            RestService.getInstance(this).CarregarFotosAlbunsFamiliasAsync(new RestCallback(){
                @Override
                public void onRestResult(boolean success) {
                    if (success){
                        _fotosAlbunsCarregadas = true;
                        CarregarFamiliasUsuarioAsync();
                    }
                }
            });
        else AbrirTelaPrincipal();
    }

    private void AbrirTelaPrincipal() {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

        finish();
    }
}
