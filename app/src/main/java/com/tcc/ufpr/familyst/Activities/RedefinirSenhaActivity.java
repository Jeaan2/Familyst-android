package com.tcc.ufpr.familyst.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tcc.ufpr.familyst.R;

public class RedefinirSenhaActivity extends AppCompatActivity {


    private EditText emailRedefinir;
    private Button btnRedefinirSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redefinir_senha);

        emailRedefinir = (EditText) findViewById(R.id.txt_email_redefinir_senha);
        btnRedefinirSenha = (Button) findViewById(R.id.btn_redefinir_senha);

        btnRedefinirSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //enviar email para redefinição de senha
                //fechar essa activity
            }
        });
    }
}
