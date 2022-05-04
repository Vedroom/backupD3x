package com.example.duxeles.pbebidas;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duxeles.AdminSQLiteOpenHelper;
import com.example.duxeles.R;
import com.example.duxeles.pbebidas.RecyclerViewAdaptador;
import com.example.duxeles.pbebidas.bebidas;
import com.example.duxeles.pingredientes.ingrediente;
import com.example.duxeles.pplatillos.platillo;

import java.util.ArrayList;
import java.util.List;

public class bebidasActivity extends AppCompatActivity {

    //Parte para listar los elementos
    ListView listViewBebidas;

    ArrayList<String> listaInformacion;
    ArrayList<bebidas> listaBebidas;

    AdminSQLiteOpenHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bebidas);

        conn = new AdminSQLiteOpenHelper(getApplicationContext(),"duxeles.db",null,1);

        listViewBebidas = (ListView) findViewById(R.id.listViewBebidas);

        consultarListaBebidas();

        ArrayAdapter adaptador = new ArrayAdapter(this,android.R.layout.simple_list_item_1 ,listaInformacion);

        listViewBebidas.setAdapter(adaptador);

    }

    private void consultarListaBebidas(){

        SQLiteDatabase db = conn.getReadableDatabase();

        bebidas bebidas = null;

        listaBebidas = new ArrayList<bebidas>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + AdminSQLiteOpenHelper.t_bebidas , null);

        while (cursor.moveToNext()){

            bebidas = new bebidas();

            bebidas.setNom(cursor.getString(0));
            bebidas.setPrecio(cursor.getString(1));
            bebidas.setDesc(cursor.getString(2));

            listaBebidas.add(bebidas);
        }

        obtenerLista();
        
    }

    private void obtenerLista() {
        listaInformacion= new ArrayList<String>();

        for(int i=0; i<listaBebidas.size(); i++){

            listaInformacion.add(listaBebidas.get(i).getNom()+" - "+
                                 listaBebidas.get(i).getPrecio()+" - "+
                                 listaBebidas.get(i).getDesc());
        }


    }



}
