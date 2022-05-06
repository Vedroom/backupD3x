package com.example.duxeles.pbebidas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.duxeles.AdminSQLiteOpenHelper;
import com.example.duxeles.R;
import com.example.duxeles.pingredientes.ingrediente;

import java.util.ArrayList;

public class bebidas extends AppCompatActivity {


    private String nom, precio, desc;
    private int img;

    public bebidas() {
    }


    public bebidas(String nom, String precio, String desc, int foto) {
        this.nom = nom;
        this.precio = precio;
        this.desc = desc;
        this.img = img;
    }

    public String getNom() {
        return nom;
    }

    public String getPrecio() {
        return precio;
    }

    public String getDesc() {
        return desc;
    }

    public int getImg() {
        return img;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setImg(int img) {
        this.img = img;
    }

    //ag_bebida.this,null,null,1);
    AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(bebidas.this,null,null,1);



//https://stackoverflow.com/questions/38392359/could-not-find-method-in-parent-or-ancestor-context
    private ImageButton btnAgregarB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ag_bebida);

        btnAgregarB = findViewById(R.id.btnAgregarB);

        btnAgregarB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                bebidas.this.AgregarBebida();
            }
        });
        }


    public void Modificar (View view){
        Intent i = new Intent(bebidas.this, mod_bebida.class);
        //ENCARGADO DE ESTA PANTALLA
        // PASAR COMO VALUE, EL ID CORRESPONDIENTE A LA BEBIDA SELECCIONADA
        //NOTA: VALOR ACTUAL USADO PARA PRUEBA, MODIFICARLO
        int id = 1;
        i.putExtra("id_modificar", id);
        startActivity(i);
    }

    private void AgregarBebida(){
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
}
