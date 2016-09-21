package org.ricetable;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ArrayList<String> adapterArray;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    String from;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            from = extras.getString("fromK");
//            System.out.println(from);
//        } else {
//            from = "monday";
//        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lv = (ListView) findViewById(R.id.lv);
        loadDay(from);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, edita.class);
                intent.putExtra("fromK", from);
                startActivity(intent);
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        switch (from) {
            case "monday":
                navigationView.getMenu().getItem(0).setChecked(true);
                break;
            case "tuesday":
                navigationView.getMenu().getItem(1).setChecked(true);
                break;
            case "wednesday":
                navigationView.getMenu().getItem(2).setChecked(true);
                break;
            case "thursday":
                navigationView.getMenu().getItem(3).setChecked(true);
                break;
            case "friday":
                navigationView.getMenu().getItem(4).setChecked(true);
                break;
        }
        navigationView.setNavigationItemSelectedListener(this);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

        }
    }

    public void loadDay(String fromD) {
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = sharedPref.edit();
        from = fromD;
        String replace = sharedPref.getString(from, "").replace("[", "");
        String replace1 = replace.replace("]", "");
        adapterArray = new ArrayList<String>(Arrays.asList(replace1.split(", ")));
        if (sharedPref.getString(from, null) == null) {
            adapterArray.remove(0);
        }
        customMain adapter = new customMain(adapterArray, this);
        lv.setAdapter(adapter);
        System.out.println(adapterArray.toString());
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.mondayBtn) {
            loadDay("monday");
        } else if (id == R.id.tuesdayBtn) {
            loadDay("tuesday");
        } else if (id == R.id.wednesdayBtn) {
            loadDay("wednesday");
        } else if (id == R.id.thursdayBtn) {
            loadDay("thursday");
        } else if (id == R.id.fridayBtn) {
            loadDay("friday");
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.settings) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
