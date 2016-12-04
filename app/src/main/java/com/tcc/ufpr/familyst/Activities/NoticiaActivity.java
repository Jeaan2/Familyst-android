package com.tcc.ufpr.familyst.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tcc.ufpr.familyst.Adapters.ComentarioAdapter;
import com.tcc.ufpr.familyst.Adapters.ItemEventoAdapter;
import com.tcc.ufpr.familyst.FamilystApplication;
import com.tcc.ufpr.familyst.Interfaces.RestCallback;
import com.tcc.ufpr.familyst.Model.Comentario;
import com.tcc.ufpr.familyst.Model.Familia;
import com.tcc.ufpr.familyst.Model.Noticia;
import com.tcc.ufpr.familyst.R;
import com.tcc.ufpr.familyst.Services.RestService;

import java.util.ArrayList;
import java.util.Date;

public class NoticiaActivity extends AppCompatActivity {

    private TextView txtCriadorNoticia;
    private TextView txtNoticiaAberta;
    private ImageButton btnEnviarComentario;
    private EditText edtComentarioEnviar;
    private ListView lvComentariosNoticia;
    private Noticia _noticia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticia);

        int idNoticia = getIntent().getExtras().getInt("idNoticia");
        carregarNoticia(idNoticia);

        txtCriadorNoticia = (TextView) findViewById(R.id.criador_noticia);
        txtCriadorNoticia.setText(_noticia.getUsuarioCriador().getNome());
        lvComentariosNoticia = (ListView) findViewById(R.id.listview_comentarios_noticia);
        txtNoticiaAberta = (TextView) findViewById(R.id.txt_noticia_aberta);
        txtNoticiaAberta.setText(_noticia.getDescricao());
        btnEnviarComentario = (ImageButton) findViewById(R.id.btn_enviar_comentario);
        btnEnviarComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Envia comentário para o REST
                //Adiciona objeto Comentário na listView
            }
        });

        CarregarListaComentarios();
    }

    private void carregarNoticia(int idNoticia) {
        FamilystApplication familystApplication = ((FamilystApplication)getApplication());
        Familia familiaAtual = familystApplication.getFamiliaAtual();
        ArrayList<Noticia> noticias = familiaAtual.getNoticias();
        for (int i = 0; i < noticias.size() ; i++)
        {
            Noticia noticia = noticias.get(i);
            if (noticia.getIdNoticia() == idNoticia)
            {
                _noticia = noticia;
                break;
            }
        }
    }

    private void CarregarListaComentarios() {

        ComentarioAdapter adapter = new ComentarioAdapter(this,
                R.layout.item_lista_comentarios, _noticia.getComentarios());

        lvComentariosNoticia.setAdapter(adapter);
        lvComentariosNoticia.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO Implementar surgimento do botão "levar"
            }
        });

    }

    @Override
    public void onStart(){
        super.onStart();

        //TODO chamar progressdialog
        RestService.getInstance(this).CarregarComentariosNoticiasFamiliasAsync(new RestCallback(){
            @Override
            public void onRestResult(boolean success) {
                if (success){
                    Toast.makeText(NoticiaActivity.this,getResources().getText(R.string.sucesso_atualizar_comentarios), Toast.LENGTH_SHORT).show();
                    CarregarListaComentarios();
                }
                else
                {
                    Toast.makeText(NoticiaActivity.this,getResources().getText(R.string.falha_atualizar_comentarios), Toast.LENGTH_SHORT).show();
                }
                //TODO dismiss progressdialog
            }
        });
    }
}
