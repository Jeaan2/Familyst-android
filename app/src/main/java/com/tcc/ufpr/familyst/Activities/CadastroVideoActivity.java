package com.tcc.ufpr.familyst.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tcc.ufpr.familyst.FamilystApplication;
import com.tcc.ufpr.familyst.Interfaces.RestCallback;
import com.tcc.ufpr.familyst.Model.Album;
import com.tcc.ufpr.familyst.Model.Familia;
import com.tcc.ufpr.familyst.Model.Video;
import com.tcc.ufpr.familyst.R;
import com.tcc.ufpr.familyst.Services.RestService;

public class CadastroVideoActivity extends BaseActivity {

    private EditText txtDescricao;
    private EditText txtLink;
    private Button  btnCadastrar;
    private ImageButton btnRemoverVideo;
    private TextView cabecalho;
    private boolean isEdicao = false;
    private Video video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_video);

        txtDescricao = (EditText) findViewById(R.id.txt_descricao_video_cadastro);
        txtLink = (EditText) findViewById(R.id.txt_link_video_cadastro);
        cabecalho = (TextView) findViewById(R.id.txt_cadastro_video_cabecalho);
        btnCadastrar = (Button) findViewById(R.id.btn_cadastrar_video);
        btnRemoverVideo = (ImageButton) findViewById(R.id.btn_remover_video);
        btnRemoverVideo.setVisibility(View.GONE);

        isEdicao = getIntent().getBooleanExtra("isEdicao", false);
        if(isEdicao)
        {
            video = carregarVideo(getIntent().getExtras().getInt("idVideo"));
            txtDescricao.setText(video.getDescricao());
            txtLink.setText(video.getLink());

            btnCadastrar.setText("Salvar");
            btnRemoverVideo.setVisibility(View.VISIBLE);
            cabecalho.setText("Edite o item selecionado:");
        }
        
        btnRemoverVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(CadastroVideoActivity.this)
                        .setTitle("Alerta!")
                        .setMessage("Deseja remover o video selecionado?")
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final ProgressDialog dialogProgresso = ProgressDialog.show(CadastroVideoActivity.this, "Aguarde", "Excluindo item.");
                                dialogProgresso.setCancelable(false);
                                RestService.getInstance(CadastroVideoActivity.this).RemoverVideo( video.getIdVideo(), new RestCallback(){
                                    @Override
                                    public void onRestResult(boolean success) {
                                        if (success){
                                            dialogProgresso.dismiss();
                                            finish();
                                        }
                                        else
                                        {
                                            Toast.makeText(getApplicationContext(),getResources().getText(R.string.falha_remover_video), Toast.LENGTH_SHORT).show();
                                        }
                                        dialogProgresso.dismiss();
                                    }
                                });
                                dialog.dismiss();
                            }
                        }).show();


            }
        });
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txtDescricao.getText().toString().length() <= 0) {
                    txtDescricao.setError("Este campo deve ser preenchido.");

                }
                if (txtLink.getText().toString().length() <= 0) {
                    txtLink.setError("Este campo deve ser preenchido.");
                } else {
                    if (!isEdicao) {
                        final ProgressDialog dialogProgresso = ProgressDialog.show(CadastroVideoActivity.this, "Aguarde", "Cadastrando item.");
                        dialogProgresso.setCancelable(false);
                        RestService.getInstance(CadastroVideoActivity.this).EnviarVideo(txtDescricao.getText().toString(), txtLink.getText().toString(), new RestCallback() {
                            @Override
                            public void onRestResult(boolean success) {
                                if (success) {
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), getResources().getText(R.string.falha_cadastro_video), Toast.LENGTH_SHORT).show();
                                }
                                dialogProgresso.dismiss();
                            }
                        });
                    } else {
                        final ProgressDialog dialogProgresso = ProgressDialog.show(CadastroVideoActivity.this, "Aguarde", "Editando item.");
                        dialogProgresso.setCancelable(false);
                        RestService.getInstance(CadastroVideoActivity.this).EditarVideo(txtDescricao.getText().toString(), txtLink.getText().toString(), video.getIdVideo(), new RestCallback() {
                            @Override
                            public void onRestResult(boolean success) {
                                if (success) {
                                    dialogProgresso.dismiss();
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), getResources().getText(R.string.falha_editar_video), Toast.LENGTH_SHORT).show();
                                }
                                dialogProgresso.dismiss();
                            }
                        });
                    }
                }
            }
        });
    }

    private Video carregarVideo(int idVideo) {
        FamilystApplication familystApplication = ((FamilystApplication)getApplication());
        Familia familiaSelecionada = familystApplication.getFamiliaAtual();

        for (int i = 0 ; i < familiaSelecionada.getVideos().size() ; i++) {
            Video video = familiaSelecionada.getVideos().get(i);
            if (video.getIdVideo() == idVideo){
                return video;
            }
        }
        return null;
    }
}
