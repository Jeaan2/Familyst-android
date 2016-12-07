package com.tcc.ufpr.familyst.Activities;

import android.app.ProgressDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.tcc.ufpr.familyst.Adapters.GridViewAdapter;
import com.tcc.ufpr.familyst.FamilystApplication;
import com.tcc.ufpr.familyst.Interfaces.RestCallback;
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
    private TextView txtNomeAlbum;
    private TextView txtDescricaoAlbum;
    private Album album;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        gridView = (GridView) findViewById(R.id.gridView);
        txtNomeAlbum = (TextView) findViewById(R.id.txt_nome_album);
        txtDescricaoAlbum = (TextView) findViewById(R.id.txt_descricao_album);

        album = carregarAlbum(getIntent().getExtras().getInt("idAlbum"));

        txtNomeAlbum.setText(album.getNome());
        txtDescricaoAlbum.setText(album.getDescricao());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_album);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),CadastroFotoActivity.class);
                intent.putExtra("idAlbum", album.getIdAlbum());
                startActivity(intent);
            }
        });

        carregarListaFotos();
    }

    private void carregarListaFotos() {
        gridAdapter = new GridViewAdapter(this, R.layout.item_gridview_album, carregarFotos());
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Foto foto = (Foto) parent.getItemAtPosition(position);

                //Criar Intent
                Intent intent = new Intent(getApplicationContext(), DetalheFotoActivity.class);
                intent.putExtra("idFoto", foto.getIdImagem());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();

        // chamar progressdialog
        final ProgressDialog dialogProgresso = ProgressDialog.show(this, "Aguarde", "Atualizando Fotos.");
        dialogProgresso.setCancelable(false);
        RestService.getInstance(this).CarregarFotosAlbunsFamiliasAsync(new RestCallback(){
            @Override
            public void onRestResult(boolean success) {
                if (success){
                    Toast.makeText(AlbumActivity.this,getResources().getText(R.string.sucesso_atualizar_fotos), Toast.LENGTH_SHORT).show();
                    carregarListaFotos();
                }
                else
                {
                    Toast.makeText(AlbumActivity.this,getResources().getText(R.string.falha_atualizar_fotos), Toast.LENGTH_SHORT).show();
                }
                // dismiss progressdialog
                dialogProgresso.dismiss();
            }
        });
    }

    //preparando dados escrotos para a gridView
    private ArrayList<Foto> carregarFotos(){
        ArrayList<Foto> fotos = album.getFotos();
        if (fotos == null)
            return new ArrayList<>();
        return fotos;
    }

    private Album carregarAlbum(int idAlbum) {
        FamilystApplication familystApplication = ((FamilystApplication)getApplication());
        Familia familiaSelecionada = familystApplication.getFamiliaAtual();

        //acha album por id (Melhor implementacao seria Map<Int,Album> ao inves de Arraylist... para todas as nossas listas)
        for (int i = 0 ; i < familiaSelecionada.getAlbuns().size() ; i++) {
            Album albumFor = familiaSelecionada.getAlbuns().get(i);
            if (albumFor.getIdAlbum() == idAlbum){
                return albumFor;
            }
        }
        return null;
    }
}
