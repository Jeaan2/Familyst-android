package com.tcc.ufpr.familyst.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tcc.ufpr.familyst.R;

public class CadastroAlbumActivity extends BaseActivity {

    private EditText nomeAlbum;
    private EditText descricaoAlbum;
    private Button btnCadastrarAlbum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_album);

        nomeAlbum = (EditText) findViewById(R.id.txt_nome_album_cadastro);
        descricaoAlbum = (EditText) findViewById(R.id.txt_descricao_album_cadastro);
        btnCadastrarAlbum = (Button) findViewById(R.id.btn_cadastrar_album);

        btnCadastrarAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Enviar album cadastrado
                //fechar esta activity
            }
        });
    }
}
