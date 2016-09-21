package org.ricetable;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class edita extends AppCompatActivity {
    public static ArrayList<String> adapterArray;
    public static ArrayList<String> teremArray;
    public static SharedPreferences.Editor editor;
    public static String from;
    static customadapter adapter;
    ListView lve;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //asda
//        int boa;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edita);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            from = extras.getString("fromK");
            System.out.println(from);
        } else {
            from = "monday";
        }
        lve = (ListView) findViewById(R.id.lve);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = sharedPref.edit();
        String replace = sharedPref.getString(from, "").replace("[", "");
        String replace1 = replace.replace("]", "");
        adapterArray = new ArrayList<String>(Arrays.asList(replace1.split(", ")));
        if (sharedPref.getString(from, null) == null) {
            adapterArray.remove(0);
        }
        System.out.println(adapterArray.toString());
        replace = sharedPref.getString(from + "terem", "").replace("[", "");
        replace1 = replace.replace("]", "");
        teremArray = new ArrayList<String>(Arrays.asList(replace1.split(", ")));
        if (sharedPref.getString(from + "terem", null) == null) {
            teremArray.remove(0);
        }
        System.out.println(teremArray.toString());
        adapter = new customadapter(adapterArray, this);
        editor.putString(from, adapterArray.toString());
        lve.setAdapter(adapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog();
            }
        });
        lve.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                dialogEdit(position);
            }
        });
    }

    public void dialog() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("New");
        final LinearLayout layoutEdit = new LinearLayout(this);
        layoutEdit.setOrientation(LinearLayout.VERTICAL);
        final EditText input = new EditText(this);
        final EditText inputTerem = new EditText(this);
        layoutEdit.addView(input);
        layoutEdit.addView(inputTerem);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
        dialogBuilder.setView(layoutEdit);
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                adapterArray.add(String.valueOf(input.getText()));
                teremArray.add(String.valueOf(inputTerem.getText()));

                System.out.println(adapterArray);
                editor.putString(from, adapterArray.toString());
                editor.putString(from + "terem", teremArray.toString());
                editor.commit();
                refAdap();
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(input.getWindowToken(), 0);
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(input.getWindowToken(), 0);
            }
        });
        input.setImeOptions(EditorInfo.IME_ACTION_DONE);
        AlertDialog dlog = dialogBuilder.create();
        dlog.show();
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    public void dialogEdit(final int index) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Edit");
        final LinearLayout layoutEdit = new LinearLayout(this);
        layoutEdit.setOrientation(LinearLayout.VERTICAL);
        final EditText input = new EditText(this);
        final EditText inputTerem = new EditText(this);
        layoutEdit.addView(input);
        layoutEdit.addView(inputTerem);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
        dialogBuilder.setView(layoutEdit);
        input.setText(adapterArray.get(index));
        inputTerem.setText(teremArray.get(index));
        input.setSelection(input.getText().length());
        inputTerem.setSelection(inputTerem.getText().length());
        dialogBuilder.setPositiveButton("Rename", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                adapterArray.set(index, input.getText().toString());
                teremArray.set(index, inputTerem.getText().toString());
                editor.putString(from, adapterArray.toString());
                editor.putString(from + "terem", teremArray.toString());
                editor.commit();
                refAdap();
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(input.getWindowToken(), 0);
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(input.getWindowToken(), 0);

            }
        });
        AlertDialog dlog = dialogBuilder.create();
        dlog.show();
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    public void refAdap() {
        adapter = new customadapter(adapterArray, this);
        lve.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(edita.this, MainActivity.class);
        intent.putExtra("fromK", from);
        startActivity(intent);
    }
}
