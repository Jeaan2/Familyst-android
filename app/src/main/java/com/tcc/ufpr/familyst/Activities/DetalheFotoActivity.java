package com.tcc.ufpr.familyst.Activities;

import android.os.Bundle;
import android.widget.TextView;

import com.tcc.ufpr.familyst.R;

public class DetalheFotoActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_foto);

        String descricao = getIntent().getStringExtra("descricao");
        //Bitmap bitmap = getIntent().getParcelableExtra("imagem");

        TextView txtDescricao = (TextView) findViewById(R.id.descricao_foto);
        txtDescricao.setText(descricao);

        //ImageView imageView = (ImageView) findViewById(R.id.foto);
        //imageView.setImageBitmap(bitmap);
    }
}
