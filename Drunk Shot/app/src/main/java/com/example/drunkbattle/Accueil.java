package com.example.drunkbattle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Accueil extends AppCompatActivity {

    MediaPlayer music;

    /**
     * Crée la page d'accueil comprenant un bouton permettant de débuter la partie
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        Button startButton = findViewById(R.id.button);

        playMusic();

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Accueil.this, MainActivity.class);

                startActivity(intent);

            }
        });
    }

    /**
     * permet de démarer la musique du jeu (qui ne s'arrêtera alors plus jamais)
     * */
    void playMusic() {
        music = MediaPlayer.create(Accueil.this, R.raw.car_chase);
        music.setLooping(true);
        music.start();
    }
}
