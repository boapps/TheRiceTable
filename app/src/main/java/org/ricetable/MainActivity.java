package org.ricetable;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ArrayList<String> adapterArray;
    static ArrayList<String> teremArray;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    String from = "monday";
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Bundle extras = getIntent().getExtras();
//        if (extras == null) {
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
        adapterArray = new ArrayList<String>(Arrays.asList(sharedPref.getString(from, "").replace("[", "").replace("]", "").split(", ")));
        if (sharedPref.getString(from, null) == null) {
            adapterArray.remove(0);
        }
        teremArray = new ArrayList<String>(Arrays.asList(sharedPref.getString(from + "terem", "").replace("[", "").replace("]", "").split(", ")));
        if (sharedPref.getString(from + "terem", null) == null) {
            teremArray.remove(0);
        }

        if (!teremArray.isEmpty()) {
            if (teremArray.get(0).equals("")) {
                teremArray.remove(0);
            }
            if (adapterArray.get(0).equals("")) {
                adapterArray.remove(0);
            }
        }
        System.out.println(teremArray.toString());

        customMain adapter = new customMain(adapterArray, this);
        lv.setAdapter(adapter);
        System.out.println(adapterArray.toString());
        notification();
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
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void notification() {
        if (!adapterArray.isEmpty() && sharedPref.getBoolean("notifications_new_message", true)) {
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.ic_add_black_24dp)
                            .setContentTitle(adapterArray.get(0) + " in " + teremArray.get(0))
                            .setContentText("");

            Intent resultIntent = new Intent(this, MainActivity.class);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
// Adds the back stack for the Intent (but not the Intent itself)
            stackBuilder.addParentStack(MainActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(
                            0,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
            mBuilder.setOngoing(true);
            NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
            String bText = "";
            for (int n = 0; n != adapterArray.size(); n++) {
                if (n != adapterArray.size() - 1) {
                    bText = bText + adapterArray.get(n) + "\n";
                } else {
                    bText = bText + adapterArray.get(n);
                }
            }
            bigText.bigText(bText);
            bigText.setBigContentTitle("Lessons: ");
            mBuilder.setStyle(bigText);
            mBuilder.setContentIntent(resultPendingIntent);
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(1, mBuilder.build());
        } else {
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.cancelAll();
        }
    }

}
