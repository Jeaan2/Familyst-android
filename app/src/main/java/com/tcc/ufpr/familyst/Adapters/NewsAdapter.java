package com.tcc.ufpr.familyst.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tcc.ufpr.familyst.Model.Noticia;
import com.tcc.ufpr.familyst.R;

import java.util.ArrayList;

/**
 * Created by jeaan_000 on 12/10/2016.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private ArrayList<Noticia> listaNoticias = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Context contexto;

    public NewsAdapter (Context context) {
        layoutInflater = LayoutInflater.from(context);
        contexto = context;
    }

    @Override
    public int getItemCount() { return listaNoticias.size();}

    @Override
    public void onBindViewHolder(NewsViewHolder newsViewHolder, int i) {
        Noticia noticia = listaNoticias.get(i);

        //TODO Adicionar restante das informações de noticia
        String descricao = noticia.getDescricao();
        newsViewHolder.vDescricao.setText(descricao);
    }

    public void setListaNoticia(ArrayList<Noticia> listaNoticias) {

        this.listaNoticias = listaNoticias;
        notifyItemChanged(0, listaNoticias.size());
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.news_cardview, viewGroup, false);

        return new NewsViewHolder(itemView, contexto);
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder{

        protected TextView vDescricao;
        protected View view;

        public NewsViewHolder(View itemView, final Context context) {
            super(itemView);
            view = itemView;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context.getApplicationContext(), "Clicou no cardview", Toast.LENGTH_LONG).show();
                }
            });


            vDescricao = (TextView) itemView.findViewById(R.id.txt_descricao_noticia);
        }
    }
}
