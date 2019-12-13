package com.example.drunkbattle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Activity_Score extends AppCompatActivity {

    private EditText nomJoueur;
    private TextView resultat;
    private TextView ancienScore;
    private Button boutonJouer;
    private Button save;
    private CheckBox saveInfo;

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        nomJoueur = (EditText) findViewById(R.id.nomJoueur);
        resultat = (TextView) findViewById(R.id.résultat);
        boutonJouer = (Button) findViewById(R.id.boutonJouer);
        save = (Button) findViewById(R.id.boutonSauvegarder);
        saveInfo = (CheckBox) findViewById(R.id.checkBox);
        ancienScore = (TextView) findViewById(R.id.ancienResultat);

        resultat.setText("38");

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mPreferences = getSharedPreferences("myData", Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();

        checkSharedPreferences();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(saveInfo.isChecked())
                {
                    mEditor.putString(getString(R.string.se_souvenir_de_moi), "True");
                    mEditor.commit();

                    String name  = nomJoueur.getText().toString();
                    mEditor.putString(getString(R.string.nom_du_joueur), name);
                    mEditor.commit();

                    String score  = resultat.getText().toString();
                    mEditor.putString(getString(R.string.r_sultat), score);
                    mEditor.commit();

                    String ancienResult = resultat.getText().toString();
                    mEditor.putString(getString(R.string.ancien_r_sultat), ancienResult);
                    mEditor.commit();
                }
                else
                {
                    mEditor.putString(getString(R.string.se_souvenir_de_moi), "False");
                    mEditor.commit();

                    mEditor.putString(getString(R.string.nom_du_joueur), "");
                    mEditor.commit();

                    mEditor.putString(getString(R.string.r_sultat), "");
                    mEditor.commit();

                    mEditor.putString(getString(R.string.ancien_r_sultat), "");
                    mEditor.commit();
                }
            }
        });

    }

    private void checkSharedPreferences() {
        String checkbox = mPreferences.getString(getString(R.string.se_souvenir_de_moi), "False");
        String name = mPreferences.getString(getString(R.string.nom_du_joueur), "");
        String score = mPreferences.getString(getString(R.string.r_sultat), "");
        String ancienResultat = mPreferences.getString(getString(R.string.ancien_r_sultat), "");

        nomJoueur.setText(name);
        resultat.setText(score);

        if(Integer.valueOf(resultat.getText().toString()) > Integer.valueOf(ancienScore.getText().toString()))
        {
            ancienScore.setText(score);
        }
        else
        {
            ancienScore.setText(ancienResultat);
        }

        if (checkbox.equals(true)) {
            saveInfo.setChecked(true);
        } else
        {
            saveInfo.setChecked(false);
        }

    }
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity__score);
//    }
}
