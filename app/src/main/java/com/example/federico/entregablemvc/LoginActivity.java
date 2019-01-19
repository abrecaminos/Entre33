package com.example.federico.entregablemvc;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.federico.entregablemvc.model.POJO.Paint;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private PaintAdapter paintAdapter;
    private LinearLayoutManager linearLayoutManager;
    private CallbackManager callbackManager;
    private LoginButton loginButtonFacebook;
    private ImageView imageLogin;
    private TextView textViewLogin;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mFireBaseAuth;
    private static final String TAG_FACEBOOK = "TAG_FACEBOOK";

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        imageLogin = findViewById(R.id.imageLogin);
        textViewLogin = findViewById(R.id.textView_imageLogin);


        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);


        mFireBaseAuth = FirebaseAuth.getInstance();

        callbackManager = CallbackManager.Factory.create();

        loginButtonFacebook = (LoginButton) findViewById(R.id.login_button);
        loginButtonFacebook.setReadPermissions("email", "public_profile");


        loginButtonFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG_FACEBOOK, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "Login cancelado ", Toast.LENGTH_SHORT).show();
                Log.d(TAG_FACEBOOK, "fcacebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginActivity.this, "Ha Ocurrido un Horror", Toast.LENGTH_SHORT).show();
                Log.d(TAG_FACEBOOK, "facebook:onError", error);
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    cargarImagen();
                    Log.d("facebook", "onAuthStateChanged:signed_out");

                } else {
                    // User is signed out
                    Log.d("facebook", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mFireBaseAuth.getCurrentUser();

        if (currentUser != null) {
            irAlMain();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mFireBaseAuth.removeAuthStateListener(mAuthListener);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

    }



    private void handleFacebookAccessToken(AccessToken token) {

        Log.d(TAG_FACEBOOK, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mFireBaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG_FACEBOOK, "signInWithCredential:success");
                            FirebaseUser user = mFireBaseAuth.getCurrentUser();
                            cargarImagen();


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG_FACEBOOK, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            mFireBaseAuth.signOut();
                            FirebaseAuth.getInstance().signOut();

                        }

                        // ...
                    }


                });
    }

    public void irAlMain() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void cargarImagen(){
        imageLogin.setImageResource(R.drawable.mrbean);
        textViewLogin.setText("CLICK ME");
        Toast.makeText(LoginActivity.this, "LOGIN CORRECTO", Toast.LENGTH_SHORT).show();
        imageLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irAlMain();

            }
        });
    }
}





