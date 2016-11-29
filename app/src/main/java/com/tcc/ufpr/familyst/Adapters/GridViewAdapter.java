package com.tcc.ufpr.familyst.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.tcc.ufpr.familyst.Model.Foto;
import com.tcc.ufpr.familyst.R;

import java.util.ArrayList;

/**
 * Created by jeaan_000 on 02/11/2016.
 */
public class GridViewAdapter extends ArrayAdapter<Foto> {

    private Context context;
    private int layoutResourceId;
    ArrayList<Foto> dados = new ArrayList<>();

    public GridViewAdapter(Context context, int layoutResourceId, ArrayList<Foto> dados)
    {
        super(context, layoutResourceId, dados);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.dados = dados;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if(row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imagem = (ImageView) row.findViewById(R.id.imagem_grid);
            row.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) row.getTag();
        }

        Foto foto = dados.get(position);
        holder.imagem.setImageBitmap(foto.getImagem());

        return row;
    }

    static class ViewHolder
    {
        ImageView imagem;
    }
}
