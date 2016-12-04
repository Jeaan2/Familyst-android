package com.tcc.ufpr.familyst.Activities;

import android.app.ProgressDialog;
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
                final ProgressDialog dialogProgresso = ProgressDialog.show(CadastroNoticiaActivity.this, "Aguarde", "Cadastrando Noticia.");
                dialogProgresso.setCancelable(false);
                RestService.getInstance(CadastroNoticiaActivity.this).EnviarNoticia( descricao.getText().toString(), new RestCallback(){
                    @Override
                    public void onRestResult(boolean success) {
                        if (success){
                            Toast.makeText(getApplicationContext(),getResources().getText(R.string.sucesso_cadastro_noticia), Toast.LENGTH_SHORT).show();
                            dialogProgresso.dismiss();
                            finish();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),getResources().getText(R.string.falha_cadastro_noticia), Toast.LENGTH_SHORT).show();
                            dialogProgresso.dismiss();
                        }
                    }
                });
            }
        });
    }
}
