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
                final ProgressDialog dialogProgresso = ProgressDialog.show(CadastroVideoActivity.this, "Aguarde", "Cadastrando Video.");
                dialogProgresso.setCancelable(false);
                RestService.getInstance(CadastroVideoActivity.this).EnviarVideo( txtDescricao.getText().toString(), txtLink.getText().toString(), new RestCallback(){
                    @Override
                    public void onRestResult(boolean success) {
                        if (success){
                            Toast.makeText(getApplicationContext(),getResources().getText(R.string.sucesso_cadastro_video), Toast.LENGTH_SHORT).show();
                            dialogProgresso.dismiss();
                            finish();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),getResources().getText(R.string.falha_cadastro_video), Toast.LENGTH_SHORT).show();
                            dialogProgresso.dismiss();
                        }
                    }
                });
            }
        });
    }
}
