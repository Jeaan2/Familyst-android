package com.tcc.ufpr.familyst.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tcc.ufpr.familyst.Model.Video;
import com.tcc.ufpr.familyst.R;

/**
 * Created by jeaan_000 on 02/11/2016.
 */
public class VideoAdapter extends ArrayAdapter<Video> {

    private Context context;
    int layoutResourceId;
    Video dados[] = null;

    public VideoAdapter(Context context, int layoutResourceId, Video[] dados ) {
        super(context, layoutResourceId, dados);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.dados = dados;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        VideoHolder holder = null;

        if(row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new VideoHolder();
            holder.txtDescricao = (TextView) row.findViewById(R.id.nome_video);
            holder.txtData = (TextView) row.findViewById(R.id.data_video);

            row.setTag(holder);
        }
        else
        {
            holder = (VideoHolder) row.getTag();
        }

        Video video = dados[position];
        holder.txtDescricao.setText(video.getDescricao());
        holder.txtData.setText(video.getDataCriacao().toString());

        return row;

    }

    static class VideoHolder
    {
        TextView txtDescricao;
        TextView txtData;
    }
}
