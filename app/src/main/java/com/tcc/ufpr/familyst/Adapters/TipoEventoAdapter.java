package com.tcc.ufpr.familyst.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tcc.ufpr.familyst.Model.TipoEvento;
import com.tcc.ufpr.familyst.Model.TipoItem;
import com.tcc.ufpr.familyst.R;

import java.util.ArrayList;

/**
 * Created by jdfid on 30/10/2016.
 */
public class TipoEventoAdapter extends ArrayAdapter<TipoEvento> {

    private Context context;
    int layoutResourceId;
    ArrayList<TipoEvento> dados = null;

  public TipoEventoAdapter(Context context, int layoutResourceId, ArrayList<TipoEvento> dados) {
      super(context, layoutResourceId, dados);
      this.context = context;
      this.layoutResourceId = layoutResourceId;
      this.dados = dados;
  }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        TipoEventoHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new TipoEventoHolder();
            holder.txtNome = (TextView) row.findViewById(R.id.nome);

            row.setTag(holder);
        }
        else {
            holder = (TipoEventoHolder)row.getTag();
        }

        TipoEvento tipoEvento = dados.get(position);
        holder.txtNome.setText(tipoEvento.getNome());

        return holder.txtNome;
    }

    @Override
    public TipoEvento getItem(int position)
    {
        return dados.get(position);
    }

    static class TipoEventoHolder
    {
        TextView txtNome;
    }
}
