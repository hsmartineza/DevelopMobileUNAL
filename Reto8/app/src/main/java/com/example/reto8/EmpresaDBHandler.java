package com.example.reto8;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EmpresaDBHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "empresas.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_EMPRESAS = "employees";
    public static final String COLUMN_ID = "empId";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_URL = "url";
    public static final String COLUMN_TELEFONO = "telefono";
    public static final String COLUMN_EMAIL= "email";
    public static final String COLUMN_PRODUCTOS= "pruductos";
    public static final String COLUMN_CLASIFICACION= "clasificacion";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_EMPRESAS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NOMBRE + " TEXT, " +
                    COLUMN_URL + " TEXT, " +
                    COLUMN_TELEFONO + " NUMERIC, " +
                    COLUMN_EMAIL + " NUMERIC, " +
                    COLUMN_PRODUCTOS + " TEXT, " +
                    COLUMN_CLASIFICACION + " TEXT " +

                    ")";


    public EmpresaDBHandler(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_EMPRESAS);
        db.execSQL(TABLE_CREATE);
    }


}
