package com.tcc.ufpr.familyst.Activities;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.tcc.ufpr.familyst.Adapters.GridViewAdapter;
import com.tcc.ufpr.familyst.Model.Foto;
import com.tcc.ufpr.familyst.R;

import java.sql.Date;
import java.util.ArrayList;

public class AlbumActivity extends BaseActivity{

    private GridView gridView;
    private GridViewAdapter gridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_album);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_album);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

            gridView = (GridView) findViewById(R.id.gridView);
            gridAdapter = new GridViewAdapter(this, R.layout.item_gridview_album, getDados());
            gridView.setAdapter(gridAdapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Foto foto = (Foto) parent.getItemAtPosition(position);

                //Criar Intent
                Intent intent = new Intent(getApplicationContext(), DetalheFotoActivity.class);
                intent.putExtra("descricao", foto.getDescricao());
                //intent.putExtra("imagem", foto.getImagem());

                startActivity(intent);
            }
        });

}

    //preparando dados escrotos para a gridView
    private ArrayList<Foto> getDados(){
        final ArrayList<Foto> imagemItens = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
        for (int i = 0; i < imgs.length(); i++)
        {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
            imagemItens.add(new Foto(1, bitmap, "fotinha", new Date(1, 1, 1)));

        }

        return imagemItens;
    }

}
