package com.talco.brandnewapp;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    public MediaPlayer mMediaPlayer = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("result", "main activity");
        SoundPlayer(this);


    }
    public static void SoundPlayer(Context ctx){
        MediaPlayer mMediaPlayer = new MediaPlayer();
        mMediaPlayer = MediaPlayer.create(ctx, R.raw.sound);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.start();
        mMediaPlayer.setLooping(false); // Set looping
        mMediaPlayer.setVolume(100, 100);

        //player.release();
        mMediaPlayer.start();
    }


    public void loginFunc(View view) {
        EditText email_text = findViewById(R.id.emailLoginTb);
        EditText pass_text = findViewById(R.id.tbxLoginPassword);
        String email = email_text.getText().toString();
        String password = pass_text.getText().toString();

        if (password.isEmpty() || email.isEmpty()) {
            Toast.makeText(MainActivity.this, "Please fill the details below!", Toast.LENGTH_LONG).show();
            return;
        }

        Log.d("result", email + " " + password);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(MainActivity.this, "Login successful!", Toast.LENGTH_LONG).show();
                            //added:
                            //setContentView(R.layout.activity_sec);

                            Navigation.findNavController(view).navigate(R.id.action_login_to_contactFragment);


                            Log.d("result", "login done!");
                            //Navigation.findNavController(view).navigate(R.id.action_secFragment_to_afterLoginFragment);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Login failed! Try again", Toast.LENGTH_LONG).show();

                        }

                    }
                });
    }

    public boolean passwordsValidation(String pass, String validPass) {
        if (pass.equals(validPass))
            return true;
        return false;
    }



    public void RegisterFunc(View view) {


        EditText email_text = findViewById(R.id.emailRegisterTb);
        EditText pass_text = findViewById(R.id.tbRegisterPassword);
        EditText passValid_text = findViewById(R.id.tbPasswordValidation);
        EditText phone_text = findViewById(R.id.phoneTb);
        EditText name_text = findViewById(R.id.nameTb);

        String phone = phone_text.getText().toString();
        String name = name_text.getText().toString();
        String passwordValid = passValid_text.getText().toString();
        String email = email_text.getText().toString();
        String password = pass_text.getText().toString();

        if (phone.isEmpty() || name.isEmpty() || password.isEmpty() || email.isEmpty() || passwordValid.isEmpty()) {
            Toast.makeText(MainActivity.this, "Please fill all the form!", Toast.LENGTH_LONG).show();
            return;
        }

        if (!passwordsValidation(password, passwordValid)) {
            Toast.makeText(MainActivity.this, "Passwords didn't match!", Toast.LENGTH_LONG).show();
            return;
        }


        Log.d("result", email + " " + password);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(MainActivity.this, "Register successful!", Toast.LENGTH_LONG).show();
                            //setContentView(R.layout.activity_sec);
                            Navigation.findNavController(view).navigate(R.id.action_register_to_contactFragment);

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Register failed!", Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }

}