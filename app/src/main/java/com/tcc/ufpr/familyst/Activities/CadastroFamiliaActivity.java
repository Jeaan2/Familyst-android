package com.tcc.ufpr.familyst.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tcc.ufpr.familyst.R;

public class CadastroFamiliaActivity extends BaseActivity {

    private EditText nomeFamilia;
    private EditText localFamilia;
    private EditText descricaoFamilia;
    private Button btnCadastrarFamilia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_familia);

        nomeFamilia = (EditText) findViewById(R.id.txt_nome_familia_cadastro);
        localFamilia = (EditText) findViewById(R.id.txt_local_familia_cadastro);
        descricaoFamilia = (EditText) findViewById(R.id.txt_descricao_familia_cadastro);
        btnCadastrarFamilia = (Button) findViewById(R.id.btn_cadastrar_familia);

        btnCadastrarFamilia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Enviar familia cadastrada
                //fechar essa activity
            }
        });

    }
}
