package com.tcc.ufpr.familyst.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tcc.ufpr.familyst.Model.Album;
import com.tcc.ufpr.familyst.Model.Familia;
import com.tcc.ufpr.familyst.R;

import java.util.ArrayList;

/**
 * Created by jeaan_000 on 30/10/2016.
 */
public class FamiliaAdapter extends ArrayAdapter<Familia> {

    private Context context;
    int layoutResourceId;
    ArrayList<Familia> dados = null;

  public FamiliaAdapter(Context context, int layoutResourceId, ArrayList<Familia> dados) {
      super(context, layoutResourceId, dados);
      this.context = context;
      this.layoutResourceId = layoutResourceId;
      this.dados = dados;
  }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        FamiliaHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new FamiliaHolder();
            holder.txtNome = (TextView) row.findViewById(R.id.nome_familia);

            row.setTag(holder);
        }
        else {
            holder = (FamiliaHolder)row.getTag();
        }

        Familia familia = dados.get(position);
        holder.txtNome.setText(familia.getNome());

        return holder.txtNome;
    }

    static class FamiliaHolder
    {
        TextView txtNome;
    }
}
