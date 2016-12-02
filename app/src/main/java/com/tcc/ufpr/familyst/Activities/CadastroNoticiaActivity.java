package com.tcc.ufpr.familyst.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tcc.ufpr.familyst.Interfaces.RestCallback;
import com.tcc.ufpr.familyst.R;
import com.tcc.ufpr.familyst.Services.RestService;

public class CadastroNoticiaActivity extends BaseActivity {

    private EditText descricao;
    private Button btnEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_noticia);

        descricao = (EditText) findViewById(R.id.txt_descricao_noticia_cadastro);
        btnEnviar = (Button) findViewById(R.id.btn_enviar_noticia_cadastro);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Envia notícia para o servido
                //retorna para a NewsFragment.
                RestService.getInstance(CadastroNoticiaActivity.this).EnviarNoticia( descricao.getText().toString(), new RestCallback(){
                    @Override
                    public void onRestResult(boolean success) {
                        if (success){
                            Toast.makeText(getApplicationContext(),"Notícia enviada com sucesso!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Falha ao enviar notícia!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
