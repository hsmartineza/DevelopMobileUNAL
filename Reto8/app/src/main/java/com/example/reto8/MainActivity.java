package com.example.reto8;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button addEmpresaButton;
    private Button editEmpresaButton;
    private Button deleteEmpresaButton;
    private Button viewAllEmpresaButton;
    private EmpresaOperations empresaOps;
    private static final String EXTRA_EMP_ID = "com.androidtutorialpoint.empId";
    private static final String EXTRA_ADD_UPDATE = "com.androidtutorialpoint.add_update";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        addEmpresaButton = (Button) findViewById(R.id.button_add_empresa);
        editEmpresaButton = (Button) findViewById(R.id.button_edit_empresa);
        deleteEmpresaButton = (Button) findViewById(R.id.button_delete_empresa);
        viewAllEmpresaButton = (Button)findViewById(R.id.button_view_empresa);



        addEmpresaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,AddUpdateEmpresa.class);
                i.putExtra(EXTRA_ADD_UPDATE, "Add");
                startActivity(i);
            }
        });
        editEmpresaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEmpIdAndUpdateEmp();
            }
        });
        deleteEmpresaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEmpIdAndRemoveEmp();
            }
        });
        viewAllEmpresaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ViewAllEmpresas.class);
                startActivity(i);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.empresa_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_item_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void getEmpIdAndUpdateEmp(){

        LayoutInflater li = LayoutInflater.from(this);
        View getEmpIdView = li.inflate(R.layout.dialog_get_emp_id, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // set dialog_get_emp_id.xml to alertdialog builder
        alertDialogBuilder.setView(getEmpIdView);

        final EditText userInput = (EditText) getEmpIdView.findViewById(R.id.editTextDialogUserInput);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // get user input and set it to result
                        // edit text
                        Intent i = new Intent(MainActivity.this,AddUpdateEmpresa.class);
                        i.putExtra(EXTRA_ADD_UPDATE, "Update");
                        String value = userInput.getText().toString();
                        i.putExtra(EXTRA_EMP_ID, Long.parseLong(value));
                        startActivity(i);
                    }
                }).create()
                .show();

    }


    public void getEmpIdAndRemoveEmp(){

        LayoutInflater li = LayoutInflater.from(this);
        View getEmpIdView = li.inflate(R.layout.dialog_get_emp_id, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // set dialog_get_emp_id.xml to alertdialog builder
        alertDialogBuilder.setView(getEmpIdView);

        final EditText userInput = (EditText) getEmpIdView.findViewById(R.id.editTextDialogUserInput);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // get user input and set it to result
                        // edit text
                        empresaOps = new EmpresaOperations(MainActivity.this);
                        empresaOps.open();
                        empresaOps.removeEmpresa(empresaOps.getEmpresa(Long.parseLong(userInput.getText().toString())));
                        Toast t = Toast.makeText(MainActivity.this,"Emepresa eliminadad exitosamente!",Toast.LENGTH_SHORT);
                        t.show();
                    }
                }).create()
                .show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        empresaOps = new EmpresaOperations(MainActivity.this);
        empresaOps.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        empresaOps = new EmpresaOperations(MainActivity.this);
        empresaOps.close();

    }
}
