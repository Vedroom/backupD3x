package com.example.duxeles.pbebidas;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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

    AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,null,null,1);
    public void Modificar (View view){
        Intent i = new Intent(this, mod_bebida.class);
        //ENCARGADO DE ESTA PANTALLA
        // PASAR COMO VALUE, EL ID CORRESPONDIENTE A LA BEBIDA SELECCIONADA
        //NOTA: VALOR ACTUAL USADO PARA PRUEBA, MODIFICARLO
        int id = 1;
        i.putExtra("id_modificar", id);
        startActivity(i);
    }

    public void Agregar(View view){
        Intent a = new Intent(this, ag_bebida.class);
        startActivity(a);
    }

    public void Eliminar (View view){
        //ENCARGADO DE ESTA PANTALLA
        // PASAR COMO VALUE, EL ID CORRESPONDIENTE A LA BEBIDA SELECCIONADA
        //NOTA: VALOR ACTUAL USADO PARA PRUEBA, MODIFICARLO
        int id = 1;
        final String [] Sid = {String.valueOf(id)};
        new AlertDialog.Builder(this)
                .setTitle("Eliminacion")
                .setMessage("¿Desea eliminar este articulo?")
                .setNegativeButton(R.string.cancelarEliminacion, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .setPositiveButton(R.string.aceptarEliminacion, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try{
                            SQLiteDatabase Base = admin.getWritableDatabase();
                            Base.delete("bebidas","id_bebida=?",Sid);
                            Toast.makeText(getApplicationContext(), "Articulo Eliminado", Toast.LENGTH_LONG).show();
                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(), "Error al eliminar", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .show();
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
