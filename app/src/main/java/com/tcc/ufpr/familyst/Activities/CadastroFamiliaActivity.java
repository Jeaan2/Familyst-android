package com.tcc.ufpr.familyst.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.tcc.ufpr.familyst.R;

public class CadastroFamiliaActivity extends BaseActivity {

    private EditText nomeFamilia;
    private EditText localFamilia;
    private EditText descricaoFamilia;
    private Button btnCadastrarFamilia;
    private ImageButton btnRemoverFamilia;

    private boolean isEdicao = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_familia);

        nomeFamilia = (EditText) findViewById(R.id.txt_nome_familia_cadastro);
        localFamilia = (EditText) findViewById(R.id.txt_local_familia_cadastro);
        descricaoFamilia = (EditText) findViewById(R.id.txt_descricao_familia_cadastro);
        btnCadastrarFamilia = (Button) findViewById(R.id.btn_cadastrar_familia);
        btnRemoverFamilia = (ImageButton) findViewById(R.id.btn_remover_familia);

        btnRemoverFamilia.setVisibility(View.GONE);

        if(isEdicao)
        {
            btnRemoverFamilia.setVisibility(View.VISIBLE);
            //TODO procedimentos de edicao de galeria
        }

        btnRemoverFamilia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO procedimentos para remover família
            }
        });

        btnCadastrarFamilia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Enviar familia cadastrada
                //fechar essa activity
            }
        });

    }
}
