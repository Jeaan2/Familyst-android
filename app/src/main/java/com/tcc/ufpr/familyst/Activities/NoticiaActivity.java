package com.tcc.ufpr.familyst.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.tcc.ufpr.familyst.Adapters.ComentarioAdapter;
import com.tcc.ufpr.familyst.Model.Comentario;
import com.tcc.ufpr.familyst.R;

import java.util.ArrayList;
import java.util.Date;

public class NoticiaActivity extends AppCompatActivity {

    private TextView txtCriadorNoticia;
    private TextView txtNoticiaAberta;
    private ImageButton btnEnviarComentario;
    private EditText edtComentarioEnviar;
    private ListView lvComentariosNoticia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticia);

        txtCriadorNoticia = (TextView) findViewById(R.id.criador_noticia);
        txtNoticiaAberta = (TextView) findViewById(R.id.txt_noticia_aberta);
        btnEnviarComentario = (ImageButton) findViewById(R.id.btn_enviar_comentario);

        btnEnviarComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Envia comentário para o REST
                //Adiciona objeto Comentário na listView
            }
        });

        lvComentariosNoticia = (ListView) findViewById(R.id.listview_comentarios_noticia);


    }
}
