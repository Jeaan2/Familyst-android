package com.tcc.ufpr.familyst.Activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;

import com.tcc.ufpr.familyst.Adapters.ViewPagerAdapter;
import com.tcc.ufpr.familyst.Fragments.CadastroEventoFragment;
import com.tcc.ufpr.familyst.Fragments.EventoFragment;
import com.tcc.ufpr.familyst.Fragments.ItensCadastroEventoFragment;
import com.tcc.ufpr.familyst.Fragments.MembrosFragment;
import com.tcc.ufpr.familyst.Model.Evento;
import com.tcc.ufpr.familyst.R;

public class TabHostEventosActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    boolean isEdicao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_host_eventos);

        int idEvento = getIntent().getIntExtra("idEvento", 0);
        isEdicao = getIntent().getBooleanExtra("isEdicao", false);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        CadastroEventoFragment cadastroEventoFragment = new CadastroEventoFragment();
        Bundle data = new Bundle();
        data.putInt("idEvento", idEvento);
        data.putBoolean("isEdicao", isEdicao);
        cadastroEventoFragment.setArguments(data);

        viewPagerAdapter.addFragments(cadastroEventoFragment, "Evento");

        ItensCadastroEventoFragment itensCadastroEventoFragment = new ItensCadastroEventoFragment();
        data = new Bundle();
        data.putInt("idEvento", idEvento);
        data.putBoolean("isEdicao", isEdicao);
        itensCadastroEventoFragment.setArguments(data);

        viewPagerAdapter.addFragments(itensCadastroEventoFragment, "Itens");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
