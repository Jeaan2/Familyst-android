package com.tcc.ufpr.familyst.Activities;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.tcc.ufpr.familyst.Adapters.GridViewAdapter;
import com.tcc.ufpr.familyst.FamilystApplication;
import com.tcc.ufpr.familyst.Model.Album;
import com.tcc.ufpr.familyst.Model.Evento;
import com.tcc.ufpr.familyst.Model.Familia;
import com.tcc.ufpr.familyst.Model.Foto;
import com.tcc.ufpr.familyst.R;
import com.tcc.ufpr.familyst.Services.JsonRestRequest;
import com.tcc.ufpr.familyst.Services.RestService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Map;

public class AlbumActivity extends BaseActivity{

    private GridView gridView;
    private GridViewAdapter gridAdapter;
    private Album album;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        int idAlbum = getIntent().getExtras().getInt("idAlbum");

        FamilystApplication familystApplication = ((FamilystApplication)getApplication());
        Familia familiaSelecionada = familystApplication.getFamiliaAtual();

        //acha album por id (Melhor implementacao seria Map<Int,Album> ao inves de Arraylist... para todas as nossas listas)
        for (int i = 0 ; i < familiaSelecionada.getAlbuns().size() ; i++) {
            Album albumFor = familiaSelecionada.getAlbuns().get(i);
            if (albumFor.getIdAlbum() == idAlbum){
                album = albumFor;
                break;
            }
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_album);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        gridView = (GridView) findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(this, R.layout.item_gridview_album, getDados());
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Foto foto = (Foto) parent.getItemAtPosition(position);

                //Criar Intent
                Intent intent = new Intent(getApplicationContext(), DetalheFotoActivity.class);
                intent.putExtra("descricao", foto.getDescricao());
                //intent.putExtra("imagem", foto.getImagem());

                startActivity(intent);
            }
        });
    }

    //preparando dados escrotos para a gridView
    private ArrayList<Foto> getDados(){
        /*final ArrayList<Foto> imagemItens = new ArrayList<>();
        //TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
        TypedArray imgs = getResources().obtainTypedArray(R.array.image_new_ids);
        for (int i = 0; i < imgs.length(); i++)
        {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
            //imagemItens.add(new Foto(1, bitmap, new Date(1, 1, 1)));
            enviarfoto(bitmap);
        }

        return imagemItens;*/

        ArrayList<Foto> fotos = album.getFotos();
        if (fotos == null)
            return new ArrayList<>();

        return fotos;
    }

    private void enviarfoto(Bitmap bitmap)
    {
        try {

            //monta url requisicao
            String url = "fotos";

            //monta headers adicionais
            Map headers = new ArrayMap();

            //monta body
            JSONObject postBody = new JSONObject();
            postBody.put("descricao", "foto de teste");
            postBody.put("dados", encodeBitmap(bitmap));
            postBody.put("idAlbum", 4);

            //monta requisicao
            JsonRestRequest jsonRequest = new JsonRestRequest(getApplication(), Request.Method.POST, true, url, headers, postBody,
                    new Response.Listener<JsonRestRequest.JsonRestResponse>() {
                        @Override
                        public void onResponse(JsonRestRequest.JsonRestResponse jsonRestResponse) {
                            if (jsonRestResponse.get_httpStatusCode() == 200) //ok
                            {
                                JSONObject bodyRetorno = jsonRestResponse.get_bodyResponse();
                            } else //erros
                            {
                                onFalhaAccessToken("Retorno HTTP não esperado.");
                            }
                        }
                    },
                    error -> onFalhaAccessToken(error.getMessage())
            );

            RestService.getInstance(this).addToRequestQueue(jsonRequest);
        }catch (Exception ex)
        {}
    }

    private void onFalhaAccessToken(String message) {
        int i = 0;
    }

    private String encodeBitmap(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
}
