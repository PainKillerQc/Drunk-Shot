package com.example.drunkbattle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Activity_Score extends AppCompatActivity {
    TextView resultat;
    TextView ancienScore;
    Button ancienScoreButton;
    private EditText nomJoueur;

    private static final String FILE_NAME = "Score list";

    /**
     * Permet de créer la page de score, créer les click listeners sur les boutons désirés soit le bouton pour jouer, le bouton pour sauvegarder le score et le bouton pour afficher la donnée enregistrée
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        nomJoueur = findViewById(R.id.nomJoueur);
        resultat = findViewById(R.id.resultValue);
        Button boutonJouer = findViewById(R.id.boutonJouer);
        Button save = findViewById(R.id.boutonSauvegarder);
        ancienScore = findViewById(R.id.ancienScore);
        ancienScoreButton = findViewById(R.id.loadScore);

        resultat.setText(getIntent().getStringExtra(GamePanel.SCORE));


        boutonJouer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_Score.this, MainActivity.class);

                startActivity(intent);

                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text = resultat.getText().toString();
                FileOutputStream fos = null;

                try{
                    fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
                    fos.write(nomJoueur.getText().toString().getBytes());
                    fos.write(" ".getBytes());
                    fos.write(text.getBytes());

                    Toast.makeText(Activity_Score.this, "Saved to" + getFilesDir() + "/" + FILE_NAME, Toast.LENGTH_LONG).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(fos != null){
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        ancienScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileInputStream fis = null;

                try{
                    fis = openFileInput(FILE_NAME);
                    InputStreamReader isr = new InputStreamReader(fis);
                    BufferedReader br = new BufferedReader(isr);
                    StringBuilder sb = new StringBuilder();
                    String text;

                    while ((text = br.readLine()) != null){

                        sb.append(text).append("\n");
                    }

                    ancienScore.setText(sb.toString());
                } catch (FileNotFoundException e) {
                    Toast.makeText(Activity_Score.this, "Aucun score enregistré", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(fis != null){
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

    }

}
