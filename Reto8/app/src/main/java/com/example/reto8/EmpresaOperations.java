package com.example.reto8;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class EmpresaOperations {
    public static final String LOGTAG = "EMP_MNGMNT_SYS";

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase database;

    private static final String[] allColumns = {
            EmpresaDBHandler.COLUMN_ID,
            EmpresaDBHandler.COLUMN_NOMBRE,
            EmpresaDBHandler.COLUMN_URL,
            EmpresaDBHandler.COLUMN_TELEFONO,
            EmpresaDBHandler.COLUMN_EMAIL,
            EmpresaDBHandler.COLUMN_PRODUCTOS,
            EmpresaDBHandler.COLUMN_CLASIFICACION

    };

    public EmpresaOperations(Context context){
        dbhandler = new EmpresaDBHandler(context);
    }

    public void open(){
        Log.i(LOGTAG,"Database Opened");
        database = dbhandler.getWritableDatabase();


    }
    public void close(){
        Log.i(LOGTAG, "Database Closed");
        dbhandler.close();

    }
    public Empresa agregarEmpresa(Empresa Empresa){
        ContentValues values  = new ContentValues();
        values.put(EmpresaDBHandler.COLUMN_NOMBRE,Empresa.getNombre());
        values.put(EmpresaDBHandler.COLUMN_URL,Empresa.getUrl());
        values.put(EmpresaDBHandler.COLUMN_TELEFONO, Empresa.getTelefono());
        values.put(EmpresaDBHandler.COLUMN_EMAIL, Empresa.getEmailContacto());
        values.put(EmpresaDBHandler.COLUMN_PRODUCTOS, Empresa.getProductos());
        values.put(EmpresaDBHandler.COLUMN_CLASIFICACION, Empresa.getClasificacion());
        long insertid = database.insert(EmpresaDBHandler.TABLE_EMPRESAS,null,values);
        Empresa.setEmpId(insertid);
        return Empresa;

    }

    // Obtener una Empresa
    public Empresa getEmpresa(long id) {
        String val = Long.toString(id);
        String[] whereArgs = new String[] {
                val
        };
        Cursor cursor = database.query(EmpresaDBHandler.TABLE_EMPRESAS,allColumns,EmpresaDBHandler.COLUMN_ID + "=?",whereArgs,null,null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Empresa empresa = new Empresa(Long.parseLong(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5), cursor.getString(6));
        // return Employee
        return empresa;
    }

    public List<Empresa> obtenerTodasEmpresas() {

        Cursor cursor = database.query(EmpresaDBHandler.TABLE_EMPRESAS,allColumns,null,null,null, null, null);

        List<Empresa> empresas = new ArrayList<>();
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                Empresa empresa = new Empresa();
                empresa.setEmpId(cursor.getLong(cursor.getColumnIndex(EmpresaDBHandler.COLUMN_ID)));
                empresa.setNombre(cursor.getString(cursor.getColumnIndex(EmpresaDBHandler.COLUMN_NOMBRE)));
                empresa.setUrl(cursor.getString(cursor.getColumnIndex(EmpresaDBHandler.COLUMN_URL)));
                empresa.setTelefono(cursor.getString(cursor.getColumnIndex(EmpresaDBHandler.COLUMN_TELEFONO)));
                empresa.setEmailContacto(cursor.getString(cursor.getColumnIndex(EmpresaDBHandler.COLUMN_EMAIL)));
                empresa.setProductos(cursor.getString(cursor.getColumnIndex(EmpresaDBHandler.COLUMN_PRODUCTOS)));
                empresa.setClasificacion(cursor.getString(cursor.getColumnIndex(EmpresaDBHandler.COLUMN_CLASIFICACION)));

                empresas.add(empresa);
            }
        }
        // return All Employees
        return empresas;
    }




    // Actualizar Empresa
    public int actualizarEmpresa(Empresa empresa) {

        ContentValues values = new ContentValues();
        values.put(EmpresaDBHandler.COLUMN_NOMBRE, empresa.getNombre());
        values.put(EmpresaDBHandler.COLUMN_URL, empresa.getUrl());
        values.put(EmpresaDBHandler.COLUMN_TELEFONO, empresa.getTelefono());
        values.put(EmpresaDBHandler.COLUMN_EMAIL, empresa.getEmailContacto());
        values.put(EmpresaDBHandler.COLUMN_PRODUCTOS, empresa.getProductos());
        values.put(EmpresaDBHandler.COLUMN_CLASIFICACION, empresa.getClasificacion());


        // updating row
        return database.update(EmpresaDBHandler.TABLE_EMPRESAS, values,
                EmpresaDBHandler.COLUMN_ID + "=?",new String[] { String.valueOf(empresa.getEmpId())});
    }

    // Eliminar Empresa
    public void removeEmpresa(Empresa empresa) {

        database.delete(EmpresaDBHandler.TABLE_EMPRESAS, EmpresaDBHandler.COLUMN_ID + "=" + empresa.getEmpId(), null);
    }

}
