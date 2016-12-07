package com.tcc.ufpr.familyst.Activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.tcc.ufpr.familyst.FamilystApplication;
import com.tcc.ufpr.familyst.Interfaces.RestCallback;
import com.tcc.ufpr.familyst.Interfaces.RestObjectReceiveCallback;
import com.tcc.ufpr.familyst.Model.Familia;
import com.tcc.ufpr.familyst.Model.Usuario;
import com.tcc.ufpr.familyst.R;
import com.tcc.ufpr.familyst.Services.RestService;

import java.util.ArrayList;
import java.util.Objects;

public class CadastroMembroActivity extends AppCompatActivity {

    EditText emailMembro;
    Button btnCadastrarMembro;
    ImageButton btnRemoverMembro;
    private boolean isEdicao = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_membro);

        emailMembro = (EditText) findViewById(R.id.txt_email_membro_cadastro);
        btnCadastrarMembro = (Button) findViewById(R.id.btn_cadastrar_membro);
        btnRemoverMembro = (ImageButton) findViewById(R.id.btn_remover_membro);
        btnRemoverMembro.setVisibility(View.GONE);


        if(isEdicao)
        {
            btnRemoverMembro.setVisibility(View.VISIBLE);
            //// TODO: 04/12/2016 procedimentos para edicao de membro
        }
        btnRemoverMembro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //// TODO: 04/12/2016 procedimentos para remocao de membro
            }
        });



        btnCadastrarMembro.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                if (emailMembro.getText().toString().length() <= 0) {
                    emailMembro.setError("Este campo deve ser preenchido.");

                } else {
                    //Verifica se o usuário já existe na familia
                    boolean membroFamilia = false;
                    ArrayList<Usuario> membrosFamilia = getMembrosFamilia();
                    for (int i = 0; i < membrosFamilia.size(); i++) {
                        Usuario membro = membrosFamilia.get(i);
                        if (membro.getEmail().equals(emailMembro.getText().toString())) {
                            membroFamilia = true;
                            break;
                        }
                    }
                    if (membroFamilia) {
                        Toast.makeText(getApplicationContext(), getResources().getText(R.string.falha_relacionamento_membro_ja_pertencente), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    //ve se o usuario do email ja foi cadastrado na plataforma
                    RestService.getInstance(CadastroMembroActivity.this).ReceberUsuario(emailMembro.getText().toString(), new RestObjectReceiveCallback() {
                        @Override
                        public void onRestResult(Object response) {
                            if (response != null) {
                                Usuario usuario = (Usuario) response;
                                relacionarUsuarioFamilia(usuario, getFamiliaAtual());
                            } else {
                                Toast.makeText(getApplicationContext(), getResources().getText(R.string.falha_relacionamento_membro_inexistente), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void relacionarUsuarioFamilia(Usuario usuario, Familia familiaAtual) {
        final ProgressDialog dialogProgresso = ProgressDialog.show(CadastroMembroActivity.this, "Aguarde", "Cadastrando Membro.");
        dialogProgresso.setCancelable(false);

        RestService.getInstance(CadastroMembroActivity.this).RelacionarUsuarioFamilia( usuario, familiaAtual, new RestCallback(){
            @Override
            public void onRestResult(boolean success) {
                if (success){
                    Toast.makeText(CadastroMembroActivity.this,getResources().getText(R.string.sucesso_relacionamento_membro), Toast.LENGTH_SHORT).show();
                    dialogProgresso.dismiss();
                    CadastroMembroActivity.this.finish();
                }
                else
                {
                    Toast.makeText(CadastroMembroActivity.this,getResources().getText(R.string.falha_relacionamento_membro), Toast.LENGTH_SHORT).show();
                    dialogProgresso.dismiss();
                }
            }
        });
    }

    private ArrayList<Usuario> getMembrosFamilia()
    {
        FamilystApplication familystApplication = ((FamilystApplication)getApplication());
        return familystApplication.getFamiliaAtual().getUsuarios();
    }

    private Familia getFamiliaAtual()
    {
        FamilystApplication familystApplication = ((FamilystApplication)getApplication());
        return familystApplication.getFamiliaAtual();
    }
}
