package com.example.bcs421homework3;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class OverviewActivity extends AppCompatActivity {

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();


        ft.add( R.id.fragment_overview_container, new OverviewFragment() );

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;

        if(width >= 1200) {
            ft.add( R.id.fragment_detail_container, new DetailFragment());
        }


        ft.commit();

    }


}
