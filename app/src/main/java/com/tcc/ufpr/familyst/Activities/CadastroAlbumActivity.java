package com.tcc.ufpr.familyst.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tcc.ufpr.familyst.Interfaces.RestCallback;
import com.tcc.ufpr.familyst.R;
import com.tcc.ufpr.familyst.Services.RestService;

public class CadastroAlbumActivity extends BaseActivity {

    private EditText nomeAlbum;
    private EditText descricaoAlbum;
    private TextView cabecalhoAlbum;
    private Button btnCadastrarAlbum;
    private ImageButton btnRemoverAlbum;

    private boolean isEdicao = false; //TODO mudar para false

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_album);

        nomeAlbum = (EditText) findViewById(R.id.txt_nome_album_cadastro);
        descricaoAlbum = (EditText) findViewById(R.id.txt_descricao_album_cadastro);
        cabecalhoAlbum = (TextView) findViewById(R.id.txt_cadastro_album_cabecalho);
        btnCadastrarAlbum = (Button) findViewById(R.id.btn_cadastrar_album);
        btnRemoverAlbum = (ImageButton) findViewById(R.id.btn_remover_album);
        btnRemoverAlbum.setVisibility(View.GONE);

        if(isEdicao)
        {
            //TODO procedimentos para edicao de album
            btnRemoverAlbum.setVisibility(View.VISIBLE);
            cabecalhoAlbum.setText("Edite o Album selecionado:");
        }
        btnRemoverAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO procedimentos para remoção de album

            }
        });

        btnCadastrarAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog dialogProgresso = ProgressDialog.show(CadastroAlbumActivity.this, "Aguarde", "Cadastrando Album.");
                dialogProgresso.setCancelable(false);
                RestService.getInstance(CadastroAlbumActivity.this).EnviarAlbum( nomeAlbum.getText().toString(), descricaoAlbum.getText().toString(), new RestCallback(){
                    @Override
                    public void onRestResult(boolean success) {
                        if (success){
                            //Toast.makeText(getApplicationContext(),getResources().getText(R.string.sucesso_cadastro_album), Toast.LENGTH_SHORT).show();
                            dialogProgresso.dismiss();
                            finish();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),getResources().getText(R.string.falha_cadastro_album), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialogProgresso.dismiss();
            }
        });
    }
}
