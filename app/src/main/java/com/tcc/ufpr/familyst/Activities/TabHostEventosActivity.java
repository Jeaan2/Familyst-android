package com.tcc.ufpr.familyst.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;

import com.tcc.ufpr.familyst.R;

public class TabHostEventosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_host_eventos);

        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        //Define nomes e activities de cada tab.
        TabHost.TabSpec tab1 = tabHost.newTabSpec("tab1");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("tab2");

        tab1.setIndicator("Evento");
        Intent intentEvento = new Intent(this, CadastroEventoActivity.class);
        tab1.setContent(intentEvento);

        tab2.setIndicator("Itens");
        Intent intentItens = new Intent(this, ItensCadastroEventoActivity.class);
        tab2.setContent(intentItens);


        //Adiciona as tabs ao TabHost
        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
    }
}
