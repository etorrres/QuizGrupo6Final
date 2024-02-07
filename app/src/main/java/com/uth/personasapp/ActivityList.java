package com.uth.personasapp;


import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import Configuracion.SQLiteConexion;
import Configuracion.Transacciones;
import Models.Personas;

import android.content.Intent;

public class ActivityList extends AppCompatActivity {

    SQLiteConexion conexion;
    ListView listpersonas;
    ArrayList<Personas> lista;
    ArrayList<String> Arreglo;
EditText nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        conexion = new SQLiteConexion(this, Transacciones.DBName, null, 1);
        listpersonas = (ListView) findViewById(R.id.listpersonas);

        obtenerDatos();
        ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_list_item_1, Arreglo);
        listpersonas.setAdapter(adp);


        // Manejo del clic en el ListView
        listpersonas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Obtener la persona seleccionada
                Personas personaSeleccionada = lista.get(position);

                // Aquí puedes definir la información que deseas pasar
                //String informacionExtra = "Información adicional: " + personaSeleccionada.getNombres();

                // Crear un Intent para abrir una nueva actividad
                Intent intent = new Intent(ActivityList.this, MainActivity.class);

                // Pasar la información a través del Intent
                intent.putExtra("id", personaSeleccionada.getId().toString());
                intent.putExtra("nombres", personaSeleccionada.getNombres());
                intent.putExtra("apellidos", personaSeleccionada.getApellidos());
                intent.putExtra("edad", personaSeleccionada.getEdad().toString());
                intent.putExtra("correo", personaSeleccionada.getCorreo());
                intent.putExtra("direccion", personaSeleccionada.getDireccion());

                // Iniciar la nueva actividad
                startActivity(intent);
                finish();
            }
        });
    }


    private void obtenerDatos() {
        SQLiteDatabase db = conexion.getReadableDatabase();
        Personas person = null;
        lista = new ArrayList<Personas>();

        //Cursor de base de datos para recorrer los datos
        Cursor cursor = db.rawQuery(Transacciones.SelectAllPersonas, null);

        while (cursor.moveToNext()) {
            person = new Personas();
            person.setId(cursor.getInt(0));
            person.setNombres(cursor.getString(1));
            person.setApellidos(cursor.getString(2));
            person.setEdad(cursor.getInt(3));
            person.setCorreo(cursor.getString(4));
            person.setDireccion(cursor.getString(5));

            lista.add(person);
        }

        cursor.close();
        LlenarData();
    }

    private void LlenarData() {
        Arreglo = new ArrayList<String>();
        for (int i = 0; i < lista.size(); i++) {
            Arreglo.add(lista.get(i).getId() + "\n" +
                    lista.get(i).getNombres() + " " + lista.get(i).getApellidos() + "\n" +
                    lista.get(i).getEdad() + "\n" +
                    lista.get(i).getCorreo() + "\n" +
                    lista.get(i).getDireccion() + "\n\n");
        }
    }


}