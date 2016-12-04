package com.tcc.ufpr.familyst.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.tcc.ufpr.familyst.R;
import com.tcc.ufpr.familyst.Services.JsonRestRequest;
import com.tcc.ufpr.familyst.Services.RestService;

import org.json.JSONObject;

import java.util.Map;

public class CadastroActivity extends BaseActivity {

    EditText txtNome;
    EditText txtEmail;
    EditText txtSenha;
    Button btnConfirmar;

    private  boolean isEdicao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        txtNome = (EditText) findViewById(R.id.txt_nome_cadastro);
        txtEmail = (EditText) findViewById(R.id.txt_email_cadastro);
        txtSenha = (EditText) findViewById(R.id.txt_senha_cadastro);
        btnConfirmar = (Button) findViewById(R.id.btn_cadastrar);
        btnConfirmar.setOnClickListener((v) -> CadastrarUsuario());
        
        if(isEdicao){
            //// TODO: 04/12/2016 procedimentos para edicao de cadastro 
        }

    }

    private void CadastrarUsuario() {
        try {

            //monta url requisicao
            String url = "usuarios";

            //monta headers adicionais
            Map headers = new ArrayMap();

            //monta body
            JSONObject postBody = new JSONObject();
            postBody.put("nome", txtNome.getText());
            postBody.put("email", txtEmail.getText());
            postBody.put("senha", txtSenha.getText());

            //abre dialog
            final ProgressDialog dialogProgresso = ProgressDialog.show(CadastroActivity.this, "Aguarde", "Cadastrando Usuario.");
            dialogProgresso.setCancelable(false);

            //monta requisicao
            JsonRestRequest jsonRequest = new JsonRestRequest(getApplication(), Request.Method.POST, false, url, headers, postBody,
                    new Response.Listener<JsonRestRequest.JsonRestResponse>() {
                        @Override
                        public void onResponse(JsonRestRequest.JsonRestResponse jsonRestResponse) {
                            if (jsonRestResponse.get_httpStatusCode() == 201) //created
                            {
                                String localizacaoRecurso = jsonRestResponse.get_headers().get("Location").toString();
                                int idRecursoCriado = Integer.parseInt(localizacaoRecurso.substring(localizacaoRecurso.lastIndexOf('/') + 1));
                                onSucessoCadastro(idRecursoCriado);
                                dialogProgresso.dismiss();
                            }
                            else //erros
                            {
                                onFalhaCadastro("Retorno HTTP não esperado.");
                            }
                        }
                    },
                    error -> onFalhaCadastro(error.getMessage())
            );

            //envia requisicao
            RestService.getInstance(this).addToRequestQueue(jsonRequest);
        }
        catch (Exception ex){
            Log.d("Error", "Erro ao cadastrar usuario: " + ex.getLocalizedMessage());
        }
    }

    private void onFalhaCadastro(String msgError) {
        runOnUiThread(()->{
            new AlertDialog.Builder(this)
                    .setTitle("Falha")
                    .setMessage("Falha ao cadastrar usuário. Tente novamente.")
                    .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
//            Toast.makeText(this, "Falha ao cadastrar usuário: " + msgError, Toast.LENGTH_SHORT).show();

            //Falha no cadastro
            setResult(RESULT_CANCELED);
            finish();
        });
    }

    private void onSucessoCadastro(int idRecursoCriado) {
        runOnUiThread(()->{
            //Toast.makeText(this, "Usuário cadastrado com sucesso.", Toast.LENGTH_SHORT).show(); //Id: " + idRecursoCriado, Toast.LENGTH_SHORT).show();

            //Cadastrado com sucesso
            Bundle bundle = new Bundle();
            bundle.putString("nome", txtNome.getText().toString());
            bundle.putString("email", txtEmail.getText().toString());
            bundle.putString("senha", txtSenha.getText().toString());
            Intent it = new Intent();
            it.putExtras(bundle);
            setResult(RESULT_OK, it);
            finish();
        });
    }
}
