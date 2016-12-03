package com.tcc.ufpr.familyst.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tcc.ufpr.familyst.Model.TipoItem;
import com.tcc.ufpr.familyst.R;

import java.util.ArrayList;

/**
 * Created by jdfid on 30/10/2016.
 */
public class TipoItemAdapter extends ArrayAdapter<TipoItem> {

    private Context context;
    int layoutResourceId;
    ArrayList<TipoItem> dados = null;

  public TipoItemAdapter(Context context, int layoutResourceId, ArrayList<TipoItem> dados) {
      super(context, layoutResourceId, dados);
      this.context = context;
      this.layoutResourceId = layoutResourceId;
      this.dados = dados;
  }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        TipoItemHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new TipoItemHolder();
            holder.txtNome = (TextView) row.findViewById(R.id.nome);

            row.setTag(holder);
        }
        else {
            holder = (TipoItemHolder)row.getTag();
        }

        TipoItem tipoItem = dados.get(position);
        holder.txtNome.setText(tipoItem.getNome());

        return holder.txtNome;
    }

    @Override
    public TipoItem getItem(int position)
    {
        return dados.get(position);
    }

    static class TipoItemHolder
    {
        TextView txtNome;
    }
}
