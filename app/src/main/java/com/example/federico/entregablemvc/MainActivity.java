package com.example.federico.entregablemvc;

import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.federico.entregablemvc.controller.PaintController;
import com.example.federico.entregablemvc.model.POJO.MoMA;
import com.example.federico.entregablemvc.model.POJO.Paint;
import com.example.federico.entregablemvc.util.ResultListener;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity implements FragmentRecycler.ListenerFragmentRecycler{


    private PaintAdapter paintAdapter;
    private LinearLayoutManager linearLayoutManager;
    private CallbackManager callbackManager;
    private LoginButton signOut;

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mFireBaseAuth;
    private static final String TAG_FACEBOOK = "TAG_FACEBOOK";

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signOut = findViewById(R.id.signout_button);


        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);


        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contenedorRecycler,new FragmentRecycler()).commit();

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    if (AccessToken.getCurrentAccessToken() != null) {
                        LoginManager.getInstance().logOut();
                    }
                    FirebaseAuth.getInstance().signOut();
                    irAlLogin();
                }
            }
        });
    }

    @Override
    protected void onStart() {
            super.onStart();

            FirebaseUser currentUser = mAuth.getCurrentUser();

            AccessToken accessToken = AccessToken.getCurrentAccessToken();
            boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

            if (currentUser == null & !isLoggedIn) {
                irAlLogin();
            }
    }
        public void irAlLogin() {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }


        @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mFireBaseAuth.removeAuthStateListener(mAuthListener);
        }
    }


    @Override
    public void informarSeleccion(Paint paint) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentDetalle fragmentDetalle = new FragmentDetalle();

        Bundle bundle = new Bundle();
        bundle.putSerializable(FragmentDetalle.KEY_PAINT,paint);

        fragmentDetalle.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.contenedorRecycler, fragmentDetalle).addToBackStack(null).commit();
        Toast.makeText(this, paint.getName(), Toast.LENGTH_SHORT).show();
    }


}


