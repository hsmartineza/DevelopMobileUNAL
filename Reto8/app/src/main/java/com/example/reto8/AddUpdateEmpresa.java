package com.example.reto8;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddUpdateEmpresa extends AppCompatActivity {


    private static final String EXTRA_EMP_ID = "com.androidtutorialpoint.empId";
    private static final String EXTRA_ADD_UPDATE = "com.androidtutorialpoint.add_update";
    private static final String DIALOG_DATE = "DialogDate";
    private EditText nombreEditText;
    private EditText urlEditText;
    private EditText telefonoEditText;
    private EditText emailEditText;
    private EditText productosEditText;
    private EditText clasificacionEditText;

    private Button addUpdateButton;
    private Empresa nuevaEmpresa;
    private Empresa antiguaEmpresa;
    private String mode;
    private long empId;
    private EmpresaOperations empresaData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_employee);
        nuevaEmpresa = new Empresa();
        antiguaEmpresa = new Empresa();
        nombreEditText = (EditText)findViewById(R.id.edit_text_telefono);
        urlEditText = (EditText)findViewById(R.id.edit_text_url);
        telefonoEditText = (EditText) findViewById(R.id.edit_text_telefono);
        emailEditText = (EditText) findViewById(R.id.edit_text_email);
        productosEditText = (EditText) findViewById(R.id.edit_text_productos);
        clasificacionEditText = (EditText) findViewById(R.id.edit_text_productos);

        addUpdateButton = (Button)findViewById(R.id.button_add_update_empresa);
        empresaData = new EmpresaOperations(this);
        empresaData.open();


        mode = getIntent().getStringExtra(EXTRA_ADD_UPDATE);
        if(mode.equals("Update")){

            addUpdateButton.setText("Actualizar Empresa");
            empId = getIntent().getLongExtra(EXTRA_EMP_ID,0);

            initializeEmpresa(empId);

        }

        addUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mode.equals("Add")) {
                    nuevaEmpresa.setNombre(nombreEditText.getText().toString());
                    nuevaEmpresa.setUrl(urlEditText.getText().toString());
                    nuevaEmpresa.setTelefono(telefonoEditText.getText().toString());
                    nuevaEmpresa.setEmailContacto(emailEditText.getText().toString());
                    nuevaEmpresa.setProductos(productosEditText.getText().toString());
                    nuevaEmpresa.setClasificacion(clasificacionEditText.getText().toString());

                    empresaData.agregarEmpresa(nuevaEmpresa);
                    Toast t = Toast.makeText(AddUpdateEmpresa.this, "Empresa "+ nuevaEmpresa.getNombre() + "ha sido agregada !", Toast.LENGTH_SHORT);
                    t.show();
                    Intent i = new Intent(AddUpdateEmpresa.this,MainActivity.class);
                    startActivity(i);
                }else {
                    antiguaEmpresa.setNombre(nombreEditText.getText().toString());
                    antiguaEmpresa.setUrl(urlEditText.getText().toString());
                    antiguaEmpresa.setTelefono(telefonoEditText.getText().toString());
                    antiguaEmpresa.setEmailContacto(emailEditText.getText().toString());
                    antiguaEmpresa.setProductos(productosEditText.getText().toString());
                    antiguaEmpresa.setClasificacion(clasificacionEditText.getText().toString());


                    empresaData.actualizarEmpresa(antiguaEmpresa);
                    Toast t = Toast.makeText(AddUpdateEmpresa.this, "Empresa "+ antiguaEmpresa.getNombre() + " ha sido actualizada!", Toast.LENGTH_SHORT);
                    t.show();
                    Intent i = new Intent(AddUpdateEmpresa.this,MainActivity.class);
                    startActivity(i);

                }


            }
        });


    }

    private void initializeEmpresa(long empId) {
        antiguaEmpresa = empresaData.getEmpresa(empId);
        nombreEditText.setText(antiguaEmpresa.getNombre());
        urlEditText.setText(antiguaEmpresa.getUrl());
        telefonoEditText.setText(antiguaEmpresa.getTelefono());
        emailEditText.setText(antiguaEmpresa.getEmailContacto());
        productosEditText.setText(antiguaEmpresa.getProductos());
        clasificacionEditText.setText(antiguaEmpresa.getClasificacion());

    }





}
