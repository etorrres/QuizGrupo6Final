package com.uth.personasapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import Configuracion.SQLiteConexion;
import Configuracion.Transacciones;

public class MainActivity extends AppCompatActivity {

    EditText nombres;
    EditText apellidos;
    EditText edad;
    EditText correo;
    EditText direccion;
    Button salvar, eliminar, actualizar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombres = (EditText) findViewById(R.id.nombre_txt);
        apellidos = (EditText) findViewById(R.id.apellido_txt);
        edad = (EditText) findViewById(R.id.edad_txt);
        correo = (EditText) findViewById(R.id.email_txt);
        direccion = (EditText) findViewById(R.id.direccion_txt);
        salvar = (Button) findViewById(R.id.salvar_btn);
        eliminar = (Button) findViewById(R.id.btnEliminar);
        actualizar = (Button) findViewById(R.id.btnActualizar);

        salvar.setVisibility(View.VISIBLE);
        eliminar.setVisibility(View.INVISIBLE);
        actualizar.setVisibility(View.INVISIBLE);

        String id = getIntent().getStringExtra("id");
        String nombre = getIntent().getStringExtra("nombres");
        String apellido = getIntent().getStringExtra("apellidos");
        String edad2 = getIntent().getStringExtra("edad");
        String correo2 = getIntent().getStringExtra("correo");
        String direccion2 = getIntent().getStringExtra("direccion");


        if (nombre != null) {
            // Aquí puedes hacer lo que necesites con la información extra recibida
            // Por ejemplo, puedes mostrarla en un TextView
            nombres.setText(nombre);
            apellidos.setText(apellido);
            edad.setText(edad2);
            correo.setText(correo2);
            direccion.setText(direccion2);
            salvar.setVisibility(View.INVISIBLE);
            eliminar.setVisibility(View.VISIBLE);
            actualizar.setVisibility(View.VISIBLE);
        }

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                salvarData();
            }

        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarPersona(id);
            }
        });

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarPersona(id, nombres.getText().toString(), apellidos.getText().toString()
                        , edad.getText().toString(), correo.getText().toString(), direccion.getText().toString());
            }
        });

    }

    public void salvarData() {
        SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.DBName, null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();
        ContentValues datos = new ContentValues();
        datos.put(Transacciones.nombres, nombres.getText().toString());
        datos.put(Transacciones.apellidos, apellidos.getText().toString());
        datos.put(Transacciones.edad, edad.getText().toString());
        datos.put(Transacciones.correo, correo.getText().toString());
        datos.put(Transacciones.direccion, direccion.getText().toString());

        Long resultado = db.insert(Transacciones.TablePersonas, Transacciones.id, datos);

        Toast.makeText(getApplicationContext(), "Persona ingresada correctamente " + resultado.toString(),
                Toast.LENGTH_LONG).show();

    }

    private void eliminarPersona(String idPersona) {
        SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.DBName, null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();
        Long resultado = Long.valueOf(db.delete(Transacciones.TablePersonas, Transacciones.id + "=?", new String[]{String.valueOf(idPersona)}));
        db.close();
        Toast.makeText(getApplicationContext(), "Registro eliminado correctamente " + resultado.toString(),
                Toast.LENGTH_LONG).show();
        Intent intent = new Intent(MainActivity.this, ActivityList.class);
        startActivity(intent);
        finish();
    }

    public void actualizarPersona(String idPersona, String nombres, String apellidos, String edad, String correo, String direccion) {
        SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.DBName, null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(Transacciones.nombres, nombres);
        valores.put(Transacciones.apellidos, apellidos);
        valores.put(Transacciones.edad, edad);
        valores.put(Transacciones.correo, correo);
        valores.put(Transacciones.direccion, direccion);

        Long resultado = Long.valueOf(db.update(Transacciones.TablePersonas, valores, Transacciones.id + "=?", new String[]{String.valueOf(idPersona)}));
        db.close();
        Toast.makeText(getApplicationContext(), "Registro actualizado correctamente " + resultado.toString(),
                Toast.LENGTH_LONG).show();
        Intent intent = new Intent(MainActivity.this, ActivityList.class);
        startActivity(intent);
        finish();
    }
}