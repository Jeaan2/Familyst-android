package com.tcc.ufpr.familyst.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.tcc.ufpr.familyst.FamilystApplication;
import com.tcc.ufpr.familyst.Model.Usuario;
import com.tcc.ufpr.familyst.R;
import com.tcc.ufpr.familyst.Services.JsonRestRequest;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tcc.ufpr.familyst.Services.RestService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class LoginActivity extends BaseActivity{

    private EditText txtEmail;
    private EditText txtSenha;
    private Button btnEntrar;
    private TextView btnCadastrar;
    private TextView btnEsqueceuSenha;
    private String accessToken;
    private int idUsuario;
    private final String baseURI = "http://192.168.1.7:8084/Familyst";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtEmail = (EditText) findViewById(R.id.txt_email_login);
        txtSenha = (EditText) findViewById(R.id.txt_senha_login);
        btnEntrar = (Button) findViewById(R.id.btn_entrar);
        btnCadastrar = (TextView) findViewById(R.id.txt_cadastrar);
        btnEsqueceuSenha = (TextView) findViewById(R.id.txt_esqueceu_senha);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(txtEmail.getText().toString().length() <= 0) {
                    txtEmail.setError("Este campo deve ser preenchido.");
                }
                if (txtSenha.getText().toString().length() <= 0) {
                    txtSenha.setError("Este campo deve ser preenchido.");
                } else
                {
                    efetuarLogin();
                }
            }
        });

        btnEsqueceuSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RedefinirSenhaActivity.class);
                startActivityForResult(intent, 0);
            }
        });
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CadastroActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    private void efetuarLogin() {
        try {
            //monta url requisicao
            String url = "accesstokens";

            //monta headers adicionais
            Map headers = new ArrayMap();

            //ABRE DIALOG DE PROGRESSO
            final ProgressDialog dialogProgresso = ProgressDialog.show(LoginActivity.this, "Aguarde", "Conectando ao servidor.");
            dialogProgresso.setCancelable(true);

            //monta body
            JSONObject postBody = new JSONObject();
            postBody.put("email", txtEmail.getText());
            postBody.put("senha", txtSenha.getText());

            //monta requisicao
            JsonRestRequest jsonRequest = new JsonRestRequest(getApplication(), Request.Method.POST, false, url, headers, postBody,
                    new Response.Listener<JsonRestRequest.JsonRestResponse>() {
                        @Override
                        public void onResponse(JsonRestRequest.JsonRestResponse jsonRestResponse) {
                            if (jsonRestResponse.get_httpStatusCode() == 200) //ok
                            {
                                dialogProgresso.dismiss();
                                JSONObject bodyRetorno = jsonRestResponse.get_bodyResponse();
                                try {
                                    idUsuario = Integer.parseInt(bodyRetorno.getString("idUsuario"));
                                    accessToken = bodyRetorno.getString("accessToken");
                                    ((FamilystApplication)getApplication()).set_accessToken(accessToken);
                                    onSucessoAccessToken();
                                } catch (JSONException e) {
                                    onFalhaAccessToken(e.getMessage());
                                }
                            }
                            else //erros
                            {
                                onFalhaAccessToken("Retorno HTTP não esperado.");
                            }

                            dialogProgresso.dismiss();
                        }
                    },
                    error -> {
                        onFalhaAccessToken(error.getMessage());
                        dialogProgresso.dismiss();
                    }

            );

            RestService.getInstance(this).addToRequestQueue(jsonRequest);
        }
        catch (Exception ex){
            Log.d("Error", "Erro ao requisitar AccessToken: " + ex.getLocalizedMessage());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                txtEmail.setText(data.getExtras().getString("email"));
                txtSenha.setText(data.getExtras().getString("senha"));
            }
        }
    }

    private void onFalhaAccessToken(String msgError) {

        new AlertDialog.Builder(LoginActivity.this)
                .setTitle("Falha ao efetuar login")
                .setMessage("Verifique seus dados e tente novamente.")
                .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

//        runOnUiThread(()->{
//            Toast.makeText(this, "Falha ao efetuar login: " + msgError, Toast.LENGTH_SHORT).show();
//        });
    }

    private void onSucessoAccessToken() {
        requisitarDadosUsuario();
    }

    private void requisitarDadosUsuario() {
        try {

            //monta url requisicao
            String url = "usuarios/" + idUsuario;

            //monta headers adicionais
            Map headers = new ArrayMap();

            //monta body
            JSONObject postBody = new JSONObject();

            //chama progressDialog
            final ProgressDialog dialogProgressoDadosUsuario = ProgressDialog.show(LoginActivity.this, "Aguarde", "Recebendo seus dados.");
            dialogProgressoDadosUsuario.setCancelable(true);

            //monta requisicao
            JsonRestRequest jsonRequest = new JsonRestRequest(getApplication(), Request.Method.GET, true, url, headers, postBody,
                    new Response.Listener<JsonRestRequest.JsonRestResponse>() {
                        @Override
                        public void onResponse(JsonRestRequest.JsonRestResponse jsonRestResponse) {
                            if (jsonRestResponse.get_httpStatusCode() == 200) //ok
                            {
                                JSONObject bodyRetorno = jsonRestResponse.get_bodyResponse();
                                try {
                                    String nome = bodyRetorno.getString("nome");
                                    Usuario usuario = new Usuario(idUsuario, nome, txtEmail.getText().toString());
                                    ((FamilystApplication)getApplication()).set_usuarioLogado(usuario);
                                    ((FamilystApplication)getApplication()).setLoginAutomatico(true);

                                    abrirTelaCarregarDados();
                                    dialogProgressoDadosUsuario.dismiss();
                                } catch (JSONException e) {
                                    onFalhaUsuario(e.getMessage());
                                }
                            }
                            else //erros
                            {
                                dialogProgressoDadosUsuario.dismiss();
                                onFalhaUsuario("Retorno HTTP não esperado.");
                            }
                        }
                    },
                    error -> onFalhaUsuario(error.getMessage())
            );

            //envia requisicao
            RestService.getInstance(this).addToRequestQueue(jsonRequest);
        }
        catch (Exception ex){

            Log.d("Error", "Erro ao efetuar login: " + ex.getLocalizedMessage());
        }
    }

    private void abrirTelaCarregarDados() {
        runOnUiThread(()->{

            Intent intent = new Intent(getApplicationContext(), LoadingDataActivity.class);
            startActivity(intent);

            //TODO fechar essa activity.
            finish();
        });

    }


    private void onFalhaUsuario(String msgError) {

        new AlertDialog.Builder(LoginActivity.this)
                .setTitle("Falha ao efetuar login")
                .setMessage("Verifique seus dados e tente novamente.")
                .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

//        runOnUiThread(()->{
//            Toast.makeText(this, "Falha ao efetuar login: " + msgError, Toast.LENGTH_SHORT).show();
//        });
    }
}
