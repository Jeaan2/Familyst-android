package com.tcc.ufpr.familyst.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tcc.ufpr.familyst.R;

public class CadastroMembroActivity extends AppCompatActivity {

    EditText emailMembro;
    Button btnCadastrarMembro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_membro);

        emailMembro = (EditText) findViewById(R.id.txt_email_membro_cadastro);
        btnCadastrarMembro = (Button) findViewById(R.id.btn_cadastrar_membro);

        btnCadastrarMembro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Verifica se o usuário já existe e atribui à familia
                //Envia email ao membro da família caso não haja cadastro
            }
        });
    }
}
