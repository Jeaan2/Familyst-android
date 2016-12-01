package com.tcc.ufpr.familyst.Activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;

import com.tcc.ufpr.familyst.Adapters.ViewPagerAdapter;
import com.tcc.ufpr.familyst.Fragments.CadastroEventoFragment;
import com.tcc.ufpr.familyst.Fragments.ItensCadastroEventoFragment;
import com.tcc.ufpr.familyst.Fragments.MembrosFragment;
import com.tcc.ufpr.familyst.R;

public class TabHostEventosActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_host_eventos);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new CadastroEventoFragment(), "Evento");
        viewPagerAdapter.addFragments(new ItensCadastroEventoFragment(), "Itens");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
