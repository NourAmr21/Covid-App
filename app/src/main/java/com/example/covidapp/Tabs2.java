package com.example.covidapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;


public class Tabs2 extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager2 pager2;
    VPAdapter1 adapter1;
    DatabaseReference reference;
    Date date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs2);
        tabLayout=findViewById(R.id.tab_layout1);
        pager2 =findViewById(R.id.view_pager21);
        FragmentManager fm =getSupportFragmentManager();
        adapter1=new VPAdapter1(fm,getLifecycle());
        pager2.setAdapter(adapter1);
        if(getIntent().getExtras()!=null)     {//M3
            date=new Date();
            double new_lat=(double)(Double.parseDouble(getIntent().getStringExtra("lat")));
            double new_lang=(double)(Double.parseDouble(getIntent().getStringExtra("lang")));
            String name=getIntent().getStringExtra("name");
            reference= FirebaseDatabase.getInstance().getReference("Hospitals");
            Hospital hospital=new Hospital(name,new_lat,new_lang);
            System.out.println(hospital.toString());
            hospital.setDate(date.toString());
            reference.child(date.toString()).setValue(hospital);

        }//M3
        tabLayout.addTab(tabLayout.newTab().setText("Profile"));
        tabLayout.addTab(tabLayout.newTab().setText("Requests"));
        tabLayout.addTab(tabLayout.newTab().setText("Hospitals"));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));

            }
        });
    }
}