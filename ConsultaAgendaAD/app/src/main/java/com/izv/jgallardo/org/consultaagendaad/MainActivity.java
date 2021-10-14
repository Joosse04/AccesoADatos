package com.izv.jgallardo.org.consultaagendaad;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.ContactsContract;
import android.provider.UserDictionary;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.izv.jgallardo.org.consultaagendaad.settings.SettingsActivity;

public class MainActivity extends AppCompatActivity {

    private final int CONTACT_PERMISSION = 1;
    private final String TAG = "xyzyx";

    private Button btSearch; //=findViewById(R.id.btSearch); no se puede hacer
    private EditText etPhone;
    private TextView tvResult;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v(TAG, "onCreate"); //verbose
        initialize();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onDestroy");//verbose
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            viewSettings();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(TAG, "onPause");//verbose
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.v(TAG, "onRequestPermissionsResult");//verbose
        switch (requestCode) {
            case CONTACT_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permiso
                    search();
                } else {
                    //sin permiso
                }
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.v(TAG, "onRestart");//verbose
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG, "onResume");//verbose
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v(TAG, "onStart");//verbose
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v(TAG, "onStop");//verbose
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    //

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void explain() {
        showRationaleDialog(getString(R.string.title), getString(R.string.message), Manifest.permission.READ_CONTACTS, CONTACT_PERMISSION);
        requestPermission();
    }

    private void initialize() {
        btSearch = findViewById(R.id.btSearch);
        etPhone = findViewById(R.id.etPhone);
        tvResult = findViewById(R.id.tvResult);

        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchIfPermitted();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestPermission() {
        requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, CONTACT_PERMISSION);
    }

    private void search() {
        tvResult.setText("A pelo ya si");
        //buscar entre los contactos
        //ContentProvider(Proveedor de contenidos) -> consultar
        //ContetResolver(Consultor de contenidos)
        //Queries the user dictionary and return results
        //url: https://ieszaidinvergeles.org/carpeta/carperta2/pagina.html?data=1
        //uri:: protocolo://direccion/ruta/recurso
        /*Cursor cursor = getContentResolver().query(
                UserDictionary.Words.CONTENT_URI, //The content URI of te words table
                new String[] {"projection"}, //The columns to return for each row
                "campo1 = ? and camo 2 > ? or campo3 = ?", //Selection criteria
                new String[] {"pepe", "4", "23"}, //Selection criteria
                "campo5, campo3, campo4"); //The sort order for the returned rows (order by)

        */
        /*
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String proyeccion[] = new String[] {ContactsContract.Contacts.DISPLAY_NAME};
        String seleccion = ContactsContract.Contacts.IN_VISIBLE_GROUP + " = ? and " +
                ContactsContract.Contacts.HAS_PHONE_NUMBER + "= ?";
        String argumentos[] = new String[]{"1","1"};
        seleccion = null;
        argumentos = null;
        String orden = ContactsContract.Contacts.DISPLAY_NAME + " collate localized asc";
        Cursor cursor = getContentResolver().query(uri, proyeccion, seleccion, argumentos, orden);
        String[] columnas = cursor.getColumnNames();
        for (String s: columnas) {
            Log.v(TAG, s);
        }
        String displayName;
        int columna = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
        while(cursor.moveToNext()) {
            displayName = cursor.getString(columna);
            Log.v(TAG, displayName);
        }
        */

        Uri uri2 = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String proyeccion2[] = null; //new String[] {ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};
        String seleccion2 = null; //ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?";
        String argumentos2[] = null; // new String[]{id+""};
        String orden2 = ContactsContract.CommonDataKinds.Phone.NUMBER;
        Cursor cursor2 = getContentResolver().query(uri2, proyeccion2, seleccion2, argumentos2, orden2);
        String[] columnas2 = cursor2.getColumnNames();

        for (String s : columnas2) {
            Log.v(TAG, s);
        }

        int columnaNom = cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
        int columnaNum = cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
        String nombre, numero;
        while(cursor2.moveToNext()){
            nombre = cursor2.getString(columnaNom);
            numero = cursor2.getString(columnaNum);
            //Log.v(TAG,nombre+": "+numero);
            for (String s: columnas2){
                int pos = cursor2.getColumnIndex(s);
                String valor = cursor2.getString(pos);
                numero = numero.replaceAll("[^0-9]", "");
                if(numero.equals(etPhone.getText().toString())){
                    tvResult.setText(nombre);
                    Log.v(TAG, pos + " " + s + " " + valor);
                }
            }
        }
    }

    private void searchIfPermitted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // La versión de android es posterior  a la 6 incluida
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.READ_CONTACTS) ==
                    PackageManager.PERMISSION_GRANTED) {
                // Ya tengo el permiso
                search();
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
                explain(); //2ª ejecución
            } else {
                requestPermission(); //1ª ejecución
            }
        } else {
            // La versión de android es anterior a la 6
            // Ya tengo el permiso
            search();
        }
    }

    private void showRationaleDialog(String title, String message, String permission, int requestCode) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(title)
                .setMessage(message)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //nada
                    }
                })
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        requestPermission();
                    }
                });
        builder.create().show();
    }

    private void viewSettings() {
        //intent - intención
        //intenciones explícitas o implícitas
        //explícito: definir que quiero ir  desde el contecto actual a un contexto que se crea con la clase SettingsActtivity
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }



}