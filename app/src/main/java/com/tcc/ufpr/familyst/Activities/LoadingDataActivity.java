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

    Usuario _usuarioLogado;
    String _accessToken;
    ArrayList<Familia> _familias;
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
    int contadorSincronizacao = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_data_activity);
        getSupportActionBar().hide();

        _usuarioLogado = ((FamilystApplication)getApplication()).get_usuarioLogado();
        _accessToken = ((FamilystApplication)getApplication()).get_accessToken();

        CarregarFamiliasUsuarioAsync();
    }

    private void CarregarFamiliasUsuarioAsync() {
        if (!_familiasCarregadas) CarregarFamiliasAsync();
        else if (!_eventosCarregados) CarregarEventosFamiliasAsync();
        else if (!_tiposEventosCarregados) CarregarTiposEventosFamiliasAsync();
        else if (!_usuarioEventosCarregados) CarregarUsuarioEventosFamiliasAsync();
        else if (!_itensEventosCarregados) CarregarItensEventosFamiliasAsync();
        else if (!_comentariosEventosCarregados) CarregarComentariosEventosFamiliasAsync();
        else if (!_tiposItemCarregados) CarregarTiposItensAsync();
        else if (!_usuariosCarregados) CarregarUsuariosFamiliasAsync();
        else if (!_noticiasCarregadas) CarregarNoticiasFamiliasAsync();
        else if (!_albunsCarregados) CarregarAlbunsFamiliasAsync();
        else if (!_comentariosNoticiasCarregados) CarregarComentariosNoticiasFamiliasAsync();
        else if (!_videosCarregados) CarregarVideosFamiliasAsync();
        else if (!_fotosAlbunsCarregadas) CarregarFotosAlbunsFamiliasAsync();
        else AbrirTelaPrincipal();
    }

    private void CarregarFamiliasAsync() {
        try {
            //monta url requisicao
            String url = "usuarios/" + _usuarioLogado.getIdUsuario() + "/familias";

            //monta headers adicionais
            Map headers = new ArrayMap();

            //monta body
            JSONObject postBody = new JSONObject();

            //monta requisicao
            JsonRestRequest jsonRequest = new JsonRestRequest(getApplication(), Request.Method.GET, true, url, headers, postBody,
                    new Response.Listener<JsonRestRequest.JsonRestResponse>() {
                        @Override
                        public void onResponse(JsonRestRequest.JsonRestResponse jsonRestResponse) {
                            if (jsonRestResponse.get_httpStatusCode() == 200) //ok
                            {
                                JSONObject bodyRetorno = jsonRestResponse.get_bodyResponse();
                                onSucessoFamilias(bodyRetorno);
                            }
                            else //erros
                            {
                                onFalhaFamilias("Retorno HTTP não esperado.");
                            }
                        }
                    },
                    error -> onFalhaFamilias(error.getMessage())
            );

            //envia requisicao
            RestService.getInstance(this).addToRequestQueue(jsonRequest);
        }
        catch (Exception ex){
            Log.d("Error", "Erro ao requisitar Familias: " + ex.getLocalizedMessage());
        }
    }

    private void onFalhaFamilias(String message) {
        Log.d("Error", "Erro ao requisitar Familias: " + message);
    }

    private void onSucessoFamilias(JSONObject bodyRetorno) {
        try {
            if (bodyRetorno != null)
            {
                _familias = new ArrayList<>();
                JSONArray familias = bodyRetorno.getJSONArray("familia");
                for (int i = 0 ; i < familias.length() ; i++) {
                    JSONObject familiaJson = familias.getJSONObject(i);
                    Familia familia = new Familia(familiaJson.getInt("idFamilia"), familiaJson.getString("nome"), familiaJson.getInt("idGaleria"));
                    _familias.add(familia);
                }
            }
            else
            {
                _familias = new ArrayList<>();
            }

            _familiasCarregadas = true;
            CarregarFamiliasUsuarioAsync();
        } catch (JSONException e) {
            Log.d("Error", "Erro ao requisitar Familias: " + e.getMessage());
        }
    }

    private void CarregarEventosFamiliasAsync() {
        contadorSincronizacao = _familias.size();
        for (int i = 0 ; i < _familias.size() ; i++)
        {
            CarregarEventosFamiliaAsync(_familias.get(i));
        }
    }

    private void CarregarEventosFamiliaAsync(Familia familia) {
        try {
            //monta url requisicao
            String url = "familias/" + familia.getIdFamilia() + "/eventos";

            //monta headers adicionais
            Map headers = new ArrayMap();

            //monta body
            JSONObject postBody = new JSONObject();

            //monta requisicao
            JsonRestRequest jsonRequest = new JsonRestRequest(getApplication(), Request.Method.GET, true, url, headers, postBody,
                    new Response.Listener<JsonRestRequest.JsonRestResponse>() {
                        @Override
                        public void onResponse(JsonRestRequest.JsonRestResponse jsonRestResponse) {
                            if (jsonRestResponse.get_httpStatusCode() == 200) //ok
                            {
                                JSONObject bodyRetorno = jsonRestResponse.get_bodyResponse();
                                onSucessoEventosFamilia(familia, bodyRetorno);
                            }
                            else //erros
                            {
                                onFalhaEventosFamilia("Retorno HTTP não esperado.");
                            }
                        }
                    },
                    error -> onFalhaEventosFamilia(error.getMessage())
            );

            //envia requisicao
            RestService.getInstance(this).addToRequestQueue(jsonRequest);
        }
        catch (Exception ex){
            Log.d("Error", "Erro ao requisitar Eventos de familia: " + ex.getLocalizedMessage());
        }
    }

    private void onFalhaEventosFamilia(String message) {
        Log.d("Error", "Erro ao requisitar Eventos de familia: " + message);
    }

    private void onSucessoEventosFamilia(Familia familia, JSONObject bodyRetorno) {
        try {
            contadorSincronizacao--;

            if (bodyRetorno != null)
            {
                ArrayList<Evento> eventos = new ArrayList<>();

                Object jsonBody = bodyRetorno.get("evento");
                if (jsonBody instanceof JSONArray) {
                    // se for um vetor de elementos
                    JSONArray eventosJson = (JSONArray)jsonBody;

                    for (int i = 0 ; i < eventosJson.length() ; i++) {
                        JSONObject eventoJson = eventosJson.getJSONObject(i);

                        //recuperando data do evento
                        String dateStr = eventoJson.getString("dataCriacao");
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                        Date dataEvento = sdf.parse(dateStr);

                        //criando evento
                        Evento evento = new Evento(eventoJson.getInt("idEvento"), eventoJson.getString("nome"), eventoJson.getString("descricao"), dataEvento, eventoJson.getString("local"), eventoJson.getInt("idUsuario"), eventoJson.getInt("idTipoEvento"));
                        eventos.add(evento);
                    }
                }
                else if (jsonBody instanceof JSONObject) {
                    //se for so um elemento
                    JSONObject eventoJson = (JSONObject)jsonBody;

                    //recuperando data do evento
                    String dateStr = eventoJson.getString("dataCriacao");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    Date dataEvento = sdf.parse(dateStr);

                    //criando evento
                    Evento evento = new Evento(eventoJson.getInt("idEvento"), eventoJson.getString("nome"), eventoJson.getString("descricao"), dataEvento, eventoJson.getString("local"), eventoJson.getInt("idUsuario"), eventoJson.getInt("idTipoEvento"));
                    eventos.add(evento);
                }

                familia.setEventos(eventos);
            }
            else
            {
                ArrayList<Evento> eventos = new ArrayList<>();
                familia.setEventos(eventos);
            }

            //se todos os requests foram executados
            if (contadorSincronizacao == 0)
            {
                _eventosCarregados = true;
                CarregarFamiliasUsuarioAsync();
            }
        } catch (JSONException e) {
            Log.d("Error", "Erro ao requisitar Eventos de familia: " + e.getMessage());
        } catch (ParseException e) {
            Log.d("Error", "Erro ao requisitar Eventos de familia: " + e.getMessage());
        }
    }

    private void CarregarTiposEventosFamiliasAsync() {
        for (int i = 0 ; i < _familias.size() ; i++)
        {
            Familia familia = _familias.get(i);
            contadorSincronizacao = contadorSincronizacao + familia.getEventos().size();
            for (int j = 0 ; j < familia.getEventos().size() ; j++) {
                Evento evento = familia.getEventos().get(j);
                CarregarTipoEventoAsync(evento);
            }
        }
    }

    private void CarregarTipoEventoAsync(Evento evento) {
        try {
            //monta url requisicao
            String url = "tiposEvento/" + evento.getIdTipoEvento();

            //monta headers adicionais
            Map headers = new ArrayMap();

            //monta body
            JSONObject postBody = new JSONObject();

            //monta requisicao
            JsonRestRequest jsonRequest = new JsonRestRequest(getApplication(), Request.Method.GET, true, url, headers, postBody,
                    new Response.Listener<JsonRestRequest.JsonRestResponse>() {
                        @Override
                        public void onResponse(JsonRestRequest.JsonRestResponse jsonRestResponse) {
                            if (jsonRestResponse.get_httpStatusCode() == 200) //ok
                            {
                                JSONObject bodyRetorno = jsonRestResponse.get_bodyResponse();
                                onSucessoTipoEventoFamilia(evento, bodyRetorno);
                            }
                            else //erros
                            {
                                onFalhaTipoEventoFamilia("Retorno HTTP não esperado.");
                            }
                        }
                    },
                    error -> onFalhaTipoEventoFamilia(error.getMessage())
            );

            //envia requisicao
            RestService.getInstance(this).addToRequestQueue(jsonRequest);
        }
        catch (Exception ex){
            Log.d("Error", "Erro ao requisitar Tipo Evento de familia: " + ex.getLocalizedMessage());
        }
    }

    private void onFalhaTipoEventoFamilia(String message) {
        Log.d("Error", "Erro ao requisitar Tipo Evento de familia: " + message);
    }

    private void onSucessoTipoEventoFamilia(Evento evento, JSONObject tipoEventoJson) {
        try {
            contadorSincronizacao--;

            if (tipoEventoJson != null)
            {
                //criando tipo evento
                TipoEvento tipoEvento = new TipoEvento(tipoEventoJson.getInt("idTipoEvento"), tipoEventoJson.getString("nome"));
                evento.setTipoEvento(tipoEvento);
            }

            //se todos os requests foram executados
            if (contadorSincronizacao == 0)
            {
                _tiposEventosCarregados = true;
                CarregarFamiliasUsuarioAsync();
            }
        } catch (JSONException e) {
            Log.d("Error", "Erro ao requisitar Tipo Evento de familia: " + e.getMessage());
        }
    }

    private void CarregarUsuarioEventosFamiliasAsync() {
        for (int i = 0 ; i < _familias.size() ; i++)
        {
            Familia familia = _familias.get(i);
            contadorSincronizacao = contadorSincronizacao + familia.getEventos().size();
            for (int j = 0 ; j < familia.getEventos().size() ; j++) {
                Evento evento = familia.getEventos().get(j);
                CarregarUsuarioEventoAsync(evento);
            }
        }
    }

    private void CarregarUsuarioEventoAsync(Evento evento) {
        try {
            //monta url requisicao
            String url = "usuarios/" + evento.getIdUsuario();

            //monta headers adicionais
            Map headers = new ArrayMap();

            //monta body
            JSONObject postBody = new JSONObject();

            //monta requisicao
            JsonRestRequest jsonRequest = new JsonRestRequest(getApplication(), Request.Method.GET, true, url, headers, postBody,
                    new Response.Listener<JsonRestRequest.JsonRestResponse>() {
                        @Override
                        public void onResponse(JsonRestRequest.JsonRestResponse jsonRestResponse) {
                            if (jsonRestResponse.get_httpStatusCode() == 200) //ok
                            {
                                JSONObject bodyRetorno = jsonRestResponse.get_bodyResponse();
                                onSucessoUsuarioEventoFamilia(evento, bodyRetorno);
                            }
                            else //erros
                            {
                                onFalhaUsuarioEventoFamilia("Retorno HTTP não esperado.");
                            }
                        }
                    },
                    error -> onFalhaUsuarioEventoFamilia(error.getMessage())
            );

            //envia requisicao
            RestService.getInstance(this).addToRequestQueue(jsonRequest);
        }
        catch (Exception ex){
            Log.d("Error", "Erro ao requisitar Usuario de Evento de familia: " + ex.getLocalizedMessage());
        }
    }

    private void onFalhaUsuarioEventoFamilia(String message) {
        Log.d("Error", "Erro ao requisitar Usuario de Evento de familia: " + message);
    }

    private void onSucessoUsuarioEventoFamilia(Evento evento, JSONObject usuarioJson) {
        try {
            contadorSincronizacao--;

            if (usuarioJson != null)
            {
                //criando usuario
                Usuario usuario = new Usuario(usuarioJson.getInt("idUsuario"), usuarioJson.getString("nome"), usuarioJson.getString("email"));
                evento.setUsuarioEvento(usuario);
            }

            //se todos os requests foram executados
            if (contadorSincronizacao == 0)
            {
                _usuarioEventosCarregados = true;
                CarregarFamiliasUsuarioAsync();
            }
        } catch (JSONException e) {
            Log.d("Error", "Erro ao requisitar Usuario de Evento de familia: " + e.getMessage());
        }
    }

    private void CarregarItensEventosFamiliasAsync() {
        for (int i = 0 ; i < _familias.size() ; i++)
        {
            Familia familia = _familias.get(i);
            contadorSincronizacao = contadorSincronizacao + familia.getEventos().size();
            for (int j = 0 ; j < familia.getEventos().size() ; j++) {
                Evento evento = familia.getEventos().get(j);
                CarregarItensEventoAsync(evento);
            }
        }
    }

    private void CarregarItensEventoAsync(Evento evento) {
        try {
            //monta url requisicao
            String url = "eventos/" + evento.getIdEvento() + "/itens";

            //monta headers adicionais
            Map headers = new ArrayMap();

            //monta body
            JSONObject postBody = new JSONObject();

            //monta requisicao
            JsonRestRequest jsonRequest = new JsonRestRequest(getApplication(), Request.Method.GET, true, url, headers, postBody,
                    new Response.Listener<JsonRestRequest.JsonRestResponse>() {
                        @Override
                        public void onResponse(JsonRestRequest.JsonRestResponse jsonRestResponse) {
                            if (jsonRestResponse.get_httpStatusCode() == 200) //ok
                            {
                                JSONObject bodyRetorno = jsonRestResponse.get_bodyResponse();
                                onSucessoItensEventoFamilia(evento, bodyRetorno);
                            }
                            else //erros
                            {
                                onFalhaItensEventoFamilia("Retorno HTTP não esperado.");
                            }
                        }
                    },
                    error -> onFalhaItensEventoFamilia(error.getMessage())
            );

            //envia requisicao
            RestService.getInstance(this).addToRequestQueue(jsonRequest);
        }
        catch (Exception ex){
            Log.d("Error", "Erro ao requisitar Itens de Eventos de familia: " + ex.getLocalizedMessage());
        }
    }

    private void onFalhaItensEventoFamilia(String message) {
        Log.d("Error", "Erro ao requisitar Itens de Eventos de familia: " + message);
    }

    private void onSucessoItensEventoFamilia(Evento evento, JSONObject bodyRetorno) {
        try {
            contadorSincronizacao--;

            if (bodyRetorno != null)
            {
                ArrayList<Item> itens = new ArrayList<>();

                Object jsonBody = bodyRetorno.get("item");
                if (jsonBody instanceof JSONArray) {
                    // se for um vetor de elementos
                    JSONArray itensJson = (JSONArray)jsonBody;

                    for (int i = 0 ; i < itensJson.length() ; i++) {
                        JSONObject itemJson = itensJson.getJSONObject(i);

                        //criando item
                        Item item = new Item(itemJson.getInt("idItem"), itemJson.getInt("quantidade"), itemJson.getInt("idTipoItem"));
                        itens.add(item);
                    }
                }
                else if (jsonBody instanceof JSONObject) {
                    //se for so um elemento
                    JSONObject itemJson = (JSONObject)jsonBody;

                    //criando item
                    Item item = new Item(itemJson.getInt("idItem"), itemJson.getInt("quantidade"), itemJson.getInt("idTipoItem"));
                    itens.add(item);
                }

                evento.setItensEvento(itens);
            }
            else
            {
                ArrayList<Item> itens = new ArrayList<>();
                evento.setItensEvento(itens);
            }

            //se todos os requests foram executados
            if (contadorSincronizacao == 0)
            {
                _itensEventosCarregados = true;
                CarregarFamiliasUsuarioAsync();
            }
        } catch (JSONException e) {
            Log.d("Error", "Erro ao requisitar Itens de Eventos de familia: " + e.getMessage());
        }
    }

    private void CarregarComentariosEventosFamiliasAsync() {
        for (int i = 0 ; i < _familias.size() ; i++)
        {
            Familia familia = _familias.get(i);
            contadorSincronizacao = contadorSincronizacao + familia.getEventos().size();
            for (int j = 0 ; j < familia.getEventos().size() ; j++) {
                Evento evento = familia.getEventos().get(j);
                CarregarComentariosEventoAsync(evento);
            }
        }
    }

    private void CarregarComentariosEventoAsync(Evento evento) {
        try {
            //monta url requisicao
            String url = "eventos/" + evento.getIdEvento() + "/comentarios";

            //monta headers adicionais
            Map headers = new ArrayMap();

            //monta body
            JSONObject postBody = new JSONObject();

            //monta requisicao
            JsonRestRequest jsonRequest = new JsonRestRequest(getApplication(), Request.Method.GET, true, url, headers, postBody,
                    new Response.Listener<JsonRestRequest.JsonRestResponse>() {
                        @Override
                        public void onResponse(JsonRestRequest.JsonRestResponse jsonRestResponse) {
                            if (jsonRestResponse.get_httpStatusCode() == 200) //ok
                            {
                                JSONObject bodyRetorno = jsonRestResponse.get_bodyResponse();
                                onSucessoComentariosEventoFamilia(evento, bodyRetorno);
                            }
                            else //erros
                            {
                                onFalhaComentariosEventoFamilia("Retorno HTTP não esperado.");
                            }
                        }
                    },
                    error -> onFalhaComentariosEventoFamilia(error.getMessage())
            );

            //envia requisicao
            RestService.getInstance(this).addToRequestQueue(jsonRequest);
        }
        catch (Exception ex){
            Log.d("Error", "Erro ao requisitar Comentarios de Eventos de familia: " + ex.getLocalizedMessage());
        }
    }

    private void onFalhaComentariosEventoFamilia(String message) {
        Log.d("Error", "Erro ao requisitar Comentarios de Eventos de familia: " + message);
    }

    private void onSucessoComentariosEventoFamilia(Evento evento, JSONObject bodyRetorno) {
        try {
            contadorSincronizacao--;

            if (bodyRetorno != null)
            {
                ArrayList<Comentario> comentarios = new ArrayList<>();

                Object jsonBody = bodyRetorno.get("comentario");
                if (jsonBody instanceof JSONArray) {
                    // se for um vetor de elementos
                    JSONArray itensJson = (JSONArray)jsonBody;

                    for (int i = 0 ; i < itensJson.length() ; i++) {
                        JSONObject comentarioJson = itensJson.getJSONObject(i);

                        //recuperando data do comentario
                        String dateStr = comentarioJson.getString("dataCriacao");
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                        Date dataCriacao = sdf.parse(dateStr);

                        //criando comentario
                        Comentario comentario = new Comentario(comentarioJson.getInt("idComentario"), comentarioJson.getString("descricao"), dataCriacao);
                        comentarios.add(comentario);
                    }
                }
                else if (jsonBody instanceof JSONObject) {
                    //se for so um elemento
                    JSONObject comentarioJson = (JSONObject)jsonBody;

                    //recuperando data do comentario
                    String dateStr = comentarioJson.getString("dataCriacao");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    Date dataCriacao = sdf.parse(dateStr);

                    //criando comentario
                    Comentario comentario = new Comentario(comentarioJson.getInt("idComentario"), comentarioJson.getString("descricao"), dataCriacao);
                    comentarios.add(comentario);
                }

                evento.setComentariosEvento(comentarios);
            }
            else
            {
                ArrayList<Comentario> comentarios = new ArrayList<>();
                evento.setComentariosEvento(comentarios);
            }

            //se todos os requests foram executados
            if (contadorSincronizacao == 0)
            {
                _comentariosEventosCarregados = true;
                CarregarFamiliasUsuarioAsync();
            }
        } catch (JSONException e) {
            Log.d("Error", "Erro ao requisitar Comentarios de Eventos de familia: " + e.getMessage());
        } catch (ParseException e) {
            Log.d("Error", "Erro ao requisitar Comentarios de Eventos de familia: " + e.getMessage());
        }
    }

    private void CarregarTiposItensAsync() {
        for (int i = 0 ; i < _familias.size() ; i++)
        {
            Familia familia = _familias.get(i);
            for (int j = 0 ; j < familia.getEventos().size() ; j++)
            {
                Evento evento = familia.getEventos().get(j);
                contadorSincronizacao = contadorSincronizacao + evento.getItensEvento().size();
                for (int o = 0 ; o < evento.getItensEvento().size() ; o++)
                {
                    Item item = evento.getItensEvento().get(o);
                    CarregarTipoItemAsync(item);
                }
            }
        }
    }

    private void CarregarTipoItemAsync(Item item) {
        try {
            //monta url requisicao
            String url = "tiposItem/" + item.getIdTipoItem();

            //monta headers adicionais
            Map headers = new ArrayMap();

            //monta body
            JSONObject postBody = new JSONObject();

            //monta requisicao
            JsonRestRequest jsonRequest = new JsonRestRequest(getApplication(), Request.Method.GET, true, url, headers, postBody,
                    new Response.Listener<JsonRestRequest.JsonRestResponse>() {
                        @Override
                        public void onResponse(JsonRestRequest.JsonRestResponse jsonRestResponse) {
                            if (jsonRestResponse.get_httpStatusCode() == 200) //ok
                            {
                                JSONObject bodyRetorno = jsonRestResponse.get_bodyResponse();
                                onSucessoTipoItem(item, bodyRetorno);
                            }
                            else //erros
                            {
                                onFalhaTipoItem("Retorno HTTP não esperado.");
                            }
                        }
                    },
                    error -> onFalhaTipoItem(error.getMessage())
            );

            //envia requisicao
            RestService.getInstance(this).addToRequestQueue(jsonRequest);
        }
        catch (Exception ex){
            Log.d("Error", "Erro ao requisitar Tipo Item de evento: " + ex.getLocalizedMessage());
        }
    }

    private void onFalhaTipoItem(String message) {
        Log.d("Error", "Erro ao requisitar Tipo Item de evento: " + message);
    }

    private void onSucessoTipoItem(Item item, JSONObject tipoItemJson) {
        try {
            contadorSincronizacao--;

            if (tipoItemJson != null)
            {
                //criando tipo item
                TipoItem tipoItem = new TipoItem(tipoItemJson.getInt("idTipoItem"), tipoItemJson.getString("nome"));
                item.setTipoItem(tipoItem);
            }

            //se todos os requests foram executados
            if (contadorSincronizacao == 0)
            {
                _tiposItemCarregados = true;
                CarregarFamiliasUsuarioAsync();
            }
        } catch (JSONException e) {
            Log.d("Error", "Erro ao requisitar Tipo Item de evento: " + e.getMessage());
        }
    }

    private void CarregarUsuariosFamiliasAsync() {
        contadorSincronizacao = _familias.size();
        for (int i = 0 ; i < _familias.size() ; i++)
        {
            CarregarUsuariosFamiliaAsync(_familias.get(i));
        }
    }

    private void CarregarUsuariosFamiliaAsync(Familia familia) {
        try {
            //monta url requisicao
            String url = "familias/" + familia.getIdFamilia() + "/usuarios";

            //monta headers adicionais
            Map headers = new ArrayMap();

            //monta body
            JSONObject postBody = new JSONObject();

            //monta requisicao
            JsonRestRequest jsonRequest = new JsonRestRequest(getApplication(), Request.Method.GET, true, url, headers, postBody,
                    new Response.Listener<JsonRestRequest.JsonRestResponse>() {
                        @Override
                        public void onResponse(JsonRestRequest.JsonRestResponse jsonRestResponse) {
                            if (jsonRestResponse.get_httpStatusCode() == 200) //ok
                            {
                                JSONObject bodyRetorno = jsonRestResponse.get_bodyResponse();
                                onSucessoUsuariosFamilia(familia, bodyRetorno);
                            }
                            else //erros
                            {
                                onFalhaUsuariosFamilia("Retorno HTTP não esperado.");
                            }
                        }
                    },
                    error -> onFalhaUsuariosFamilia(error.getMessage())
            );

            //envia requisicao
            RestService.getInstance(this).addToRequestQueue(jsonRequest);
        }
        catch (Exception ex){
            Log.d("Error", "Erro ao requisitar Usuarios de familia: " + ex.getLocalizedMessage());
        }
    }

    private void onFalhaUsuariosFamilia(String message) {
        Log.d("Error", "Erro ao requisitar Usuarios de familia: " + message);
    }

    private void onSucessoUsuariosFamilia(Familia familia, JSONObject bodyRetorno) {
        try {
            contadorSincronizacao--;

            if (bodyRetorno != null)
            {
                ArrayList<Usuario> usuarios = new ArrayList<>();

                Object jsonBody = bodyRetorno.get("usuario");
                if (jsonBody instanceof JSONArray) {
                    // se for um vetor de elementos
                    JSONArray eventosJson = (JSONArray)jsonBody;

                    for (int i = 0 ; i < eventosJson.length() ; i++) {
                        JSONObject usuarioJson = eventosJson.getJSONObject(i);
                        Usuario usuario = new Usuario(usuarioJson.getInt("idUsuario"), usuarioJson.getString("nome"), usuarioJson.getString("email"));
                        usuarios.add(usuario);
                    }
                }
                else if (jsonBody instanceof JSONObject) {
                    //se for so um elemento
                    JSONObject usuarioJson = (JSONObject)jsonBody;
                    Usuario usuario = new Usuario(usuarioJson.getInt("idUsuario"), usuarioJson.getString("nome"), usuarioJson.getString("email"));
                    usuarios.add(usuario);
                }

                familia.setUsuarios(usuarios);
            }
            else
            {
                ArrayList<Usuario> usuarios = new ArrayList<>();
                familia.setUsuarios(usuarios);
            }

            //se todos os requests foram executados
            if (contadorSincronizacao == 0)
            {
                _usuariosCarregados = true;
                CarregarFamiliasUsuarioAsync();
            }
        } catch (JSONException e) {
            Log.d("Error", "Erro ao requisitar Eventos de familia: " + e.getMessage());
        }
    }

    private void CarregarNoticiasFamiliasAsync() {
        contadorSincronizacao = _familias.size();
        for (int i = 0 ; i < _familias.size() ; i++)
        {
            CarregarNoticiasFamiliaAsync(_familias.get(i));
        }
    }

    private void CarregarNoticiasFamiliaAsync(Familia familia) {
        try {
            //monta url requisicao
            String url = "familias/" + familia.getIdFamilia() + "/noticias";

            //monta headers adicionais
            Map headers = new ArrayMap();

            //monta body
            JSONObject postBody = new JSONObject();

            //monta requisicao
            JsonRestRequest jsonRequest = new JsonRestRequest(getApplication(), Request.Method.GET, true, url, headers, postBody,
                    new Response.Listener<JsonRestRequest.JsonRestResponse>() {
                        @Override
                        public void onResponse(JsonRestRequest.JsonRestResponse jsonRestResponse) {
                            if (jsonRestResponse.get_httpStatusCode() == 200) //ok
                            {
                                JSONObject bodyRetorno = jsonRestResponse.get_bodyResponse();
                                onSucessoNoticiasFamilia(familia, bodyRetorno);
                            }
                            else //erros
                            {
                                onFalhaNoticiasFamilia("Retorno HTTP não esperado.");
                            }
                        }
                    },
                    error -> onFalhaNoticiasFamilia(error.getMessage())
            );

            //envia requisicao
            RestService.getInstance(this).addToRequestQueue(jsonRequest);
        }
        catch (Exception ex){
            Log.d("Error", "Erro ao requisitar Noticias de familia: " + ex.getLocalizedMessage());
        }
    }

    private void onFalhaNoticiasFamilia(String message) {
        Log.d("Error", "Erro ao requisitar Noticias de familia: " + message);
    }

    private void onSucessoNoticiasFamilia(Familia familia, JSONObject bodyRetorno) {
        try {
            contadorSincronizacao--;

            if (bodyRetorno != null)
            {
                ArrayList<Noticia> noticias = new ArrayList<>();

                Object jsonBody = bodyRetorno.get("noticia");
                if (jsonBody instanceof JSONArray) {
                    // se for um vetor de elementos
                    JSONArray noticiasJson = (JSONArray)jsonBody;

                    for (int i = 0 ; i < noticiasJson.length() ; i++) {
                        JSONObject noticiaJson = noticiasJson.getJSONObject(i);
                        Noticia noticia = new Noticia(noticiaJson.getInt("idNoticia"), noticiaJson.getString("descricao"));
                        noticias.add(noticia);
                    }
                }
                else if (jsonBody instanceof JSONObject) {
                    //se for so um elemento
                    JSONObject noticiaJson = (JSONObject)jsonBody;
                    Noticia noticia = new Noticia(noticiaJson.getInt("idNoticia"), noticiaJson.getString("descricao"));
                    noticias.add(noticia);
                }

                familia.setNoticias(noticias);
            }
            else
            {
                ArrayList<Noticia> noticias = new ArrayList<>();
                familia.setNoticias(noticias);
            }

            //se todos os requests foram executados
            if (contadorSincronizacao == 0)
            {
                _noticiasCarregadas = true;
                CarregarFamiliasUsuarioAsync();
            }
        } catch (JSONException e) {
            Log.d("Error", "Erro ao requisitar Noticias de familia: " + e.getMessage());
        }
    }

    private void CarregarComentariosNoticiasFamiliasAsync() {
        for (int i = 0 ; i < _familias.size() ; i++)
        {
            Familia familia = _familias.get(i);
            contadorSincronizacao = contadorSincronizacao + familia.getNoticias().size();
            for (int j = 0 ; j < familia.getNoticias().size() ; j++) {
                Noticia noticia = familia.getNoticias().get(j);
                CarregarComentariosNoticiaAsync(noticia);
            }
        }
    }

    private void CarregarComentariosNoticiaAsync(Noticia noticia) {
        try {
            //monta url requisicao
            String url = "noticias/" + noticia.getIdNoticia() + "/comentarios";

            //monta headers adicionais
            Map headers = new ArrayMap();

            //monta body
            JSONObject postBody = new JSONObject();

            //monta requisicao
            JsonRestRequest jsonRequest = new JsonRestRequest(getApplication(), Request.Method.GET, true, url, headers, postBody,
                    new Response.Listener<JsonRestRequest.JsonRestResponse>() {
                        @Override
                        public void onResponse(JsonRestRequest.JsonRestResponse jsonRestResponse) {
                            if (jsonRestResponse.get_httpStatusCode() == 200) //ok
                            {
                                JSONObject bodyRetorno = jsonRestResponse.get_bodyResponse();
                                onSucessoComentariosNoticiaFamilia(noticia, bodyRetorno);
                            }
                            else //erros
                            {
                                onFalhaComentariosNoticiaFamilia("Retorno HTTP não esperado.");
                            }
                        }
                    },
                    error -> onFalhaComentariosEventoFamilia(error.getMessage())
            );

            //envia requisicao
            RestService.getInstance(this).addToRequestQueue(jsonRequest);
        }
        catch (Exception ex){
            Log.d("Error", "Erro ao requisitar Comentarios de Notica de familia: " + ex.getLocalizedMessage());
        }
    }

    private void onFalhaComentariosNoticiaFamilia(String message) {
        Log.d("Error", "Erro ao requisitar Comentarios de Notica de familia: " + message);
    }

    private void onSucessoComentariosNoticiaFamilia(Noticia noticia, JSONObject bodyRetorno) {
        try {
            contadorSincronizacao--;

            if (bodyRetorno != null)
            {
                ArrayList<Comentario> comentarios = new ArrayList<>();

                Object jsonBody = bodyRetorno.get("comentario");
                if (jsonBody instanceof JSONArray) {
                    // se for um vetor de elementos
                    JSONArray itensJson = (JSONArray)jsonBody;

                    for (int i = 0 ; i < itensJson.length() ; i++) {
                        JSONObject comentarioJson = itensJson.getJSONObject(i);

                        //recuperando data do comentario
                        String dateStr = comentarioJson.getString("dataCriacao");
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                        Date dataCriacao = sdf.parse(dateStr);

                        //criando comentario
                        Comentario comentario = new Comentario(comentarioJson.getInt("idComentario"), comentarioJson.getString("descricao"), dataCriacao);
                        comentarios.add(comentario);
                    }
                }
                else if (jsonBody instanceof JSONObject) {
                    //se for so um elemento
                    JSONObject comentarioJson = (JSONObject)jsonBody;

                    //recuperando data do comentario
                    String dateStr = comentarioJson.getString("dataCriacao");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    Date dataCriacao = sdf.parse(dateStr);

                    //criando comentario
                    Comentario comentario = new Comentario(comentarioJson.getInt("idComentario"), comentarioJson.getString("descricao"), dataCriacao);
                    comentarios.add(comentario);
                }

                noticia.setComentarios(comentarios);
            }
            else
            {
                ArrayList<Comentario> comentarios = new ArrayList<>();
                noticia.setComentarios(comentarios);
            }

            //se todos os requests foram executados
            if (contadorSincronizacao == 0)
            {
                _comentariosNoticiasCarregados = true;
                CarregarFamiliasUsuarioAsync();
            }
        } catch (JSONException e) {
            Log.d("Error", "Erro ao requisitar Comentarios de Notica de familia: " + e.getMessage());
        } catch (ParseException e) {
            Log.d("Error", "Erro ao requisitar Comentarios de Notica de familia: " + e.getMessage());
        }
    }

    private void CarregarVideosFamiliasAsync() {
        contadorSincronizacao = _familias.size();
        for (int i = 0 ; i < _familias.size() ; i++)
        {
            CarregarVideosFamiliaAsync(_familias.get(i));
        }
    }

    private void CarregarVideosFamiliaAsync(Familia familia) {
        try {
            //monta url requisicao
            String url = "galerias/" + familia.getIdGaleria() + "/videos";

            //monta headers adicionais
            Map headers = new ArrayMap();

            //monta body
            JSONObject postBody = new JSONObject();

            //monta requisicao
            JsonRestRequest jsonRequest = new JsonRestRequest(getApplication(), Request.Method.GET, true, url, headers, postBody,
                    new Response.Listener<JsonRestRequest.JsonRestResponse>() {
                        @Override
                        public void onResponse(JsonRestRequest.JsonRestResponse jsonRestResponse) {
                            if (jsonRestResponse.get_httpStatusCode() == 200) //ok
                            {
                                JSONObject bodyRetorno = jsonRestResponse.get_bodyResponse();
                                onSucessoVideosFamilia(familia, bodyRetorno);
                            }
                            else //erros
                            {
                                onFalhaVideosFamilia("Retorno HTTP não esperado.");
                            }
                        }
                    },
                    error -> onFalhaVideosFamilia(error.getMessage())
            );

            //envia requisicao
            RestService.getInstance(this).addToRequestQueue(jsonRequest);
        }
        catch (Exception ex){
            Log.d("Error", "Erro ao requisitar Videos de familia: " + ex.getLocalizedMessage());
        }
    }

    private void onFalhaVideosFamilia(String message) {
        Log.d("Error", "Erro ao requisitar Videos de familia: " + message);
    }

    private void onSucessoVideosFamilia(Familia familia, JSONObject bodyRetorno) {
        try {
            contadorSincronizacao--;

            if (bodyRetorno != null)
            {
                ArrayList<Video> videos = new ArrayList<>();

                Object jsonBody = bodyRetorno.get("video");
                if (jsonBody instanceof JSONArray) {
                    // se for um vetor de elementos
                    JSONArray videosJson = (JSONArray)jsonBody;

                    for (int i = 0 ; i < videosJson.length() ; i++) {
                        JSONObject videoJson = videosJson.getJSONObject(i);

                        //recuperando data do video
                        String dateStr = videoJson.getString("dataCriacao");
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                        Date dataVideo = sdf.parse(dateStr);

                        Video video = new Video(videoJson.getInt("idVideo"), videoJson.getString("descricao"), dataVideo, videoJson.getString("link"));
                        videos.add(video);
                    }
                }
                else if (jsonBody instanceof JSONObject) {
                    //se for so um elemento
                    JSONObject videoJson = (JSONObject)jsonBody;

                    //recuperando data do video
                    String dateStr = videoJson.getString("dataCriacao");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    Date dataVideo = sdf.parse(dateStr);

                    Video video = new Video(videoJson.getInt("idVideo"), videoJson.getString("descricao"), dataVideo, videoJson.getString("link"));
                    videos.add(video);
                }

                familia.setVideos(videos);
            }
            else
            {
                ArrayList<Video> videos = new ArrayList<>();
                familia.setVideos(videos);
            }

            //se todos os requests foram executados
            if (contadorSincronizacao == 0)
            {
                _videosCarregados = true;
                CarregarFamiliasUsuarioAsync();
            }
        } catch (JSONException e) {
            Log.d("Error", "Erro ao requisitar Videos de familia: " + e.getMessage());
        } catch (ParseException e) {
            Log.d("Error", "Erro ao requisitar Videos de familia: " + e.getMessage());
        }
    }

    private void CarregarAlbunsFamiliasAsync() {
        contadorSincronizacao = _familias.size();
        for (int i = 0 ; i < _familias.size() ; i++)
        {
            CarregarAlbunsFamiliaAsync(_familias.get(i));
        }
    }

    private void CarregarAlbunsFamiliaAsync(Familia familia) {
        try {
            //monta url requisicao
            String url = "familias/" + familia.getIdFamilia() + "/albuns";

            //monta headers adicionais
            Map headers = new ArrayMap();

            //monta body
            JSONObject postBody = new JSONObject();

            //monta requisicao
            JsonRestRequest jsonRequest = new JsonRestRequest(getApplication(), Request.Method.GET, true, url, headers, postBody,
                    new Response.Listener<JsonRestRequest.JsonRestResponse>() {
                        @Override
                        public void onResponse(JsonRestRequest.JsonRestResponse jsonRestResponse) {
                            if (jsonRestResponse.get_httpStatusCode() == 200) //ok
                            {
                                JSONObject bodyRetorno = jsonRestResponse.get_bodyResponse();
                                onSucessoAlbunsFamilia(familia, bodyRetorno);
                            }
                            else //erros
                            {
                                onFalhaAlbunsFamilia("Retorno HTTP não esperado.");
                            }
                        }
                    },
                    error -> onFalhaAlbunsFamilia(error.getMessage())
            );

            //envia requisicao
            RestService.getInstance(this).addToRequestQueue(jsonRequest);
        }
        catch (Exception ex){
            Log.d("Error", "Erro ao requisitar Albuns de familia: " + ex.getLocalizedMessage());
        }
    }

    private void onFalhaAlbunsFamilia(String message) {
        Log.d("Error", "Erro ao requisitar Albuns de familia: " + message);
    }

    private void onSucessoAlbunsFamilia(Familia familia, JSONObject bodyRetorno) {
        try {
            contadorSincronizacao--;

            if (bodyRetorno != null)
            {
                ArrayList<Album> albuns = new ArrayList<>();

                Object jsonBody = bodyRetorno.get("album");
                if (jsonBody instanceof JSONArray) {
                    // se for um vetor de elementos
                    JSONArray albunsJson = (JSONArray)jsonBody;

                    for (int i = 0 ; i < albunsJson.length() ; i++) {
                        JSONObject albumJson = albunsJson.getJSONObject(i);

                        //recuperando data do album
                        String dateStr = albumJson.getString("dataCriacao");
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                        Date dataAlbum = sdf.parse(dateStr);

                        Album album = new Album(albumJson.getInt("idAlbum"), albumJson.getString("nome"), dataAlbum);
                        albuns.add(album);
                    }
                }
                else if (jsonBody instanceof JSONObject) {
                    //se for so um elemento
                    JSONObject albumJson = (JSONObject)jsonBody;

                    //recuperando data do album
                    String dateStr = albumJson.getString("dataCriacao");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    Date dataAlbum = sdf.parse(dateStr);

                    Album album = new Album(albumJson.getInt("idAlbum"), albumJson.getString("nome"), dataAlbum);
                    albuns.add(album);
                }

                familia.setAlbuns(albuns);
            }
            else
            {
                ArrayList<Album> albuns = new ArrayList<>();
                familia.setAlbuns(albuns);
            }

            //se todos os requests foram executados
            if (contadorSincronizacao == 0)
            {
                _albunsCarregados = true;
                CarregarFamiliasUsuarioAsync();
            }
        } catch (JSONException e) {
            Log.d("Error", "Erro ao requisitar Noticias de familia: " + e.getMessage());
        } catch (ParseException e) {
            Log.d("Error", "Erro ao requisitar Noticias de familia: " + e.getMessage());
        }
    }

    private void CarregarFotosAlbunsFamiliasAsync() {
        for (int i = 0 ; i < _familias.size() ; i++)
        {
            Familia familia = _familias.get(i);
            contadorSincronizacao = contadorSincronizacao + familia.getAlbuns().size();
            for (int j = 0 ; j < familia.getAlbuns().size() ; j++) {
                Album album = familia.getAlbuns().get(j);
                CarregarFotosAlbumAsync(album);
            }
        }
    }

    private void CarregarFotosAlbumAsync(Album album) {
        try {
            //monta url requisicao
            String url = "albuns/" + album.getIdAlbum() + "/fotos";

            //monta headers adicionais
            Map headers = new ArrayMap();

            //monta body
            JSONObject postBody = new JSONObject();

            //monta requisicao
            JsonRestRequest jsonRequest = new JsonRestRequest(getApplication(), Request.Method.GET, true, url, headers, postBody,
                    new Response.Listener<JsonRestRequest.JsonRestResponse>() {
                        @Override
                        public void onResponse(JsonRestRequest.JsonRestResponse jsonRestResponse) {
                            if (jsonRestResponse.get_httpStatusCode() == 200) //ok
                            {
                                JSONObject bodyRetorno = jsonRestResponse.get_bodyResponse();
                                onSucessoFotosAlbumFamilia(album, bodyRetorno);
                            }
                            else //erros
                            {
                                onFalhaFotosAlbumFamilia("Retorno HTTP não esperado.");
                            }
                        }
                    },
                    error -> onFalhaFotosAlbumFamilia(error.getMessage())
            );

            //envia requisicao
            RestService.getInstance(this).addToRequestQueue(jsonRequest);
        }
        catch (Exception ex){
            Log.d("Error", "Erro ao requisitar Fotos de Album de familia: " + ex.getLocalizedMessage());
        }
    }

    private void onFalhaFotosAlbumFamilia(String message) {
        Log.d("Error", "Erro ao requisitar Fotos de Album de familia: " + message);
    }

    private void onSucessoFotosAlbumFamilia(Album album, JSONObject bodyRetorno) {
        try {
            contadorSincronizacao--;

            if (bodyRetorno != null)
            {
                ArrayList<Foto> fotos = new ArrayList<>();

                Object jsonBody = bodyRetorno.get("foto");
                if (jsonBody instanceof JSONArray) {
                    // se for um vetor de elementos
                    JSONArray fotosJson = (JSONArray)jsonBody;

                    for (int i = 0 ; i < fotosJson.length() ; i++) {
                        JSONObject fotoJson = fotosJson.getJSONObject(i);

                        //recuperando data da foto
                        String dateStr = fotoJson.getString("dataCriacao");
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                        Date dataCriacao = sdf.parse(dateStr);

                        //criando foto
                        Foto foto = new Foto(fotoJson.getInt("idFoto"), fotoJson.getString("dados"), fotoJson.getString("descricao"), dataCriacao);
                        fotos.add(foto);
                    }
                }
                else if (jsonBody instanceof JSONObject) {
                    //se for so um elemento
                    JSONObject fotoJson = (JSONObject)jsonBody;

                    //recuperando data da foto
                    String dateStr = fotoJson.getString("dataCriacao");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    Date dataCriacao = sdf.parse(dateStr);

                    //criando foto
                    Foto foto = new Foto(fotoJson.getInt("idFoto"), fotoJson.getString("dados"), fotoJson.getString("descricao"), dataCriacao);
                    fotos.add(foto);
                }

                album.setFotos(fotos);
            }
            else
            {
                ArrayList<Foto> fotos = new ArrayList<>();
                album.setFotos(fotos);
            }

            //se todos os requests foram executados
            if (contadorSincronizacao == 0)
            {
                _fotosAlbunsCarregadas = true;
                CarregarFamiliasUsuarioAsync();
            }
        } catch (JSONException e) {
            Log.d("Error", "Erro ao requisitar Fotos de Album de familia: " + e.getMessage());
        } catch (ParseException e) {
            Log.d("Error", "Erro ao requisitar Fotos de Album de familia: " + e.getMessage());
        }
    }

    private void AbrirTelaPrincipal() {
        ((FamilystApplication)getApplication()).get_usuarioLogado().setFamilias(_familias);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

        finish();
    }
}
