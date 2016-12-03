package com.tcc.ufpr.familyst.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.tcc.ufpr.familyst.R;

import java.io.IOException;

public class CadastroFotoActivity extends AppCompatActivity {

    Button btnTirarFoto;
    Button btnEscolherFoto;
    Button btnSalvarFoto;
    ImageView imgSelecionada;
    EditText txtDescricaoFoto;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            Bitmap imagem = (Bitmap) data.getExtras().get("data");
            imgSelecionada.setImageBitmap(imagem);

            //TODO Salvar imagem retornada
        }
        else if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK)
            {
                if (data != null)
                {
                    try
                    {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                        imgSelecionada.setImageBitmap(bitmap);
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED)
            {
                Toast.makeText(getApplicationContext(), "Operação Cancelada", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_foto);

        btnTirarFoto = (Button) findViewById(R.id.btn_tirar_foto);
        btnEscolherFoto = (Button) findViewById(R.id.btn_foto_galeria);
        btnSalvarFoto = (Button) findViewById(R.id.btn_cadastrar_foto);
        txtDescricaoFoto = (EditText) findViewById(R.id.txt_descricao_foto_cadastro);
        imgSelecionada = (ImageView) findViewById(R.id.img_cadastro_foto);

        btnTirarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Abrir intent de camera
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });



        btnEscolherFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentGaleria = new Intent();
                intentGaleria.setType("image/*");
                intentGaleria.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intentGaleria, "Selecione a foto"), 1);
            }
        });


        btnSalvarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO procedimentos de salvamento de foto
            }
        });
    }
}
