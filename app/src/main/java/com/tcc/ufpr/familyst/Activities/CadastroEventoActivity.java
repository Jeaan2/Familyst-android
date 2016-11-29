package com.tcc.ufpr.familyst.Activities;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;

import com.tcc.ufpr.familyst.R;

@SuppressWarnings("deprecation")
public class CadastroEventoActivity extends TabActivity {

    private EditText nomeEvento;
    private EditText localEvento;
    private EditText descricaoEvento;
    private Button   btnCadastrarEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_evento);

        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);

        TabHost.TabSpec tab1 = tabHost.newTabSpec("Evento");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("Itens");

        tab1.setIndicator("Evento");
        tab2.setIndicator("Itens");

        tab1.setContent(new Intent(this, CadastroVideoActivity.class));
        tabHost.addTab(tab1);
        tabHost.addTab(tab2);

        nomeEvento = (EditText) findViewById(R.id.txt_nome_evento_cadastro);
        localEvento = (EditText) findViewById(R.id.txt_local_evento_cadastro);
        descricaoEvento = (EditText) findViewById(R.id.txt_descricao_evento_cadastro);
        btnCadastrarEvento = (Button) findViewById(R.id.btn_cadastrar_evento);

        btnCadastrarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Enviar evento criado ao servidor;
                //fechar essa activity
            }
        });
    }
}
