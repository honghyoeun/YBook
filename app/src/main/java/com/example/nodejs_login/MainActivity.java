package com.example.nodejs_login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nodejs_login.Retrofit.INodeJS;
import com.example.nodejs_login.Retrofit.RetrofitClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    INodeJS myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    EditText edt_email, edt_password;
    Button btn_login;
    TextView txw_register;

    @Override
    protected void onStop(){
        compositeDisposable.clear();
        super.onStop();
    }
    
    @Override
    protected void onDestroy(){
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init API
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);

        //View
        btn_login = (Button) findViewById(R.id.loginBtn);
        txw_register = (TextView) findViewById(R.id.registerTview);

        edt_email = (EditText) findViewById(R.id.emailedt);
        edt_password = (EditText) findViewById(R.id.passwordedt);

        //Event
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser(edt_email.getText().toString(), edt_password.getText().toString());

                Intent intent = new Intent(MainActivity.this, MainPageActivity.class);
                startActivity(intent);
                finish();
            }
        });
//this is for register account textview
        txw_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }

    private void loginUser(String email, String password) {
        compositeDisposable.add(myAPI.loginUser(email,password)
        .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        //in our login method, if we enter true password, we will gt full user info, so we in our function, just check if String return result have 'encrypted_password'or'salt'or'email in String -> that mean we login success
                        if(s.contains("encrypted_password"))
                            Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(MainActivity.this, ""+s, Toast.LENGTH_SHORT).show(); //else just show error from API

                    }
                })
        );
    }
}