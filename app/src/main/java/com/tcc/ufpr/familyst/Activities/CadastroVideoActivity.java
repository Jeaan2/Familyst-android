package com.tcc.ufpr.familyst.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tcc.ufpr.familyst.R;

public class CadastroVideoActivity extends BaseActivity {

    private EditText txtDescricao;
    private EditText txtLink;
    private Button  btnCadastrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_video);

        txtDescricao = (EditText) findViewById(R.id.txt_descricao_video_cadastro);
        txtLink = (EditText) findViewById(R.id.txt_link_video_cadastro);

        btnCadastrar = (Button) findViewById(R.id.btn_cadastrar_video);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ENVIAR cadastro ao servidor
                //Fechar essa activity
            }
        });
    }
}
