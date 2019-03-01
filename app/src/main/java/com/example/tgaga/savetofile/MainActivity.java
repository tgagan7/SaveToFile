package com.example.tgaga.savetofile;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    EditText fname,content;
    Button save;
    boolean ch;
    File file,textFile;
    AlertDialog alert;
    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fname =(EditText)findViewById(R.id.filename);
        content =(EditText)findViewById(R.id.content);
        save = (Button)findViewById(R.id.save);

        requestPermissions(permissions,3);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fileName = fname.getText().toString();
                try {
                textFile = new File(file,fileName+".txt");
                if(textFile.exists()) {
                    if(prepAlert()){
                        FileWriter writer = new FileWriter(textFile,true);
                        writer.append(content.getText().toString()+"\n\n");
                        writer.flush();
                        writer.close();
                    }
                }else{
                    textFile.createNewFile();
                    FileWriter writer = new FileWriter(textFile,true);
                    writer.append(content.getText().toString()+"\n\n");
                    writer.flush();
                    writer.close();
                }
                } catch (IOException e) {
                    Toast.makeText(MainActivity.this, "Hi", Toast.LENGTH_SHORT).show();
                }
                fname.setText("");
                content.setText("");
                Toast.makeText(MainActivity.this, "Nicely Done ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 3:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //Granted.
                    //Toast.makeText(this, "granted", Toast.LENGTH_SHORT).show();
                    file = new File(Environment.getExternalStorageDirectory(), "MyApp");
                    if(file.mkdirs()){
                        Toast.makeText(this, "Setup Done", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    //Denied.
                    Toast.makeText(this, "denied", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public boolean prepAlert(){
        AlertDialog.Builder build = new AlertDialog.Builder(this);
        build.setTitle("Duplicate File Name");
        build.setMessage("Do you want to delete the previous file ");
        build.setCancelable(false);
        build.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                    textFile.delete();
                    ch = true;
            }
        });
        build.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                ch = false;
            }
        });

        alert = build.create();
        alert.show();
        return ch;
    }
}
