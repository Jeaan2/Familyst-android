package com.tcc.ufpr.familyst.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tcc.ufpr.familyst.Model.Comentario;
import com.tcc.ufpr.familyst.R;

import java.util.ArrayList;

/**
 * Created by jeaan_000 on 31/10/2016.
 */
public class ComentarioAdapter extends ArrayAdapter<Comentario> {

    private Context context;
    int layoutResourceId;
    ArrayList<Comentario> dados = null;

    public ComentarioAdapter(Context context, int layoutResourceId, ArrayList<Comentario> dados){
        super(context, layoutResourceId, dados);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.dados = dados;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ComentarioHolder holder = null;

        if (row == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ComentarioHolder();
            holder.txtNome = (TextView) row.findViewById(R.id.nome_comentario_item);
            holder.txtDescricao = (TextView) row.findViewById(R.id.descricao_comentario_item);
            holder.txtData = (TextView) row.findViewById(R.id.data_comentario_item);

            row.setTag(holder);
        }
        else
        {
            holder = (ComentarioHolder)row.getTag();
        }

        Comentario comentario = dados.get(position);
        //holder.txtNome.setText(comentario.getNome());
        holder.txtDescricao.setText(comentario.getDescricao());
        holder.txtData.setText(comentario.getDataCriacao().toString());

        return row;
    }

    static class ComentarioHolder
    {
        TextView txtNome;
        TextView txtDescricao;
        TextView txtData;
    }
}
