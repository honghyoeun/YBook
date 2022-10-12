package com.example.nodejs_login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nodejs_login.Retrofit.INodeJS;
import com.example.nodejs_login.Retrofit.RetrofitClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity {
    INodeJS myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    EditText edt_email, edt_password, edt_name;
    Button btn_register;
    TextView txw_login;

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
        setContentView(R.layout.activity_register);



        //init API
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);

        //View
        btn_register = (Button) findViewById(R.id.registerBtn1);
        txw_login = (TextView) findViewById(R.id.loginTextview);

        edt_email = (EditText) findViewById(R.id.emailedt2);
        edt_password = (EditText) findViewById(R.id.passwordedt2);
        edt_name = (EditText) findViewById(R.id.nameedt2);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser(edt_name.getText().toString(), edt_email.getText().toString(), edt_password.getText().toString());

                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });











    }

    private void registerUser(String name, String email, String password) {
        compositeDisposable.add(myAPI.registerUser(name,email,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Toast.makeText(RegisterActivity.this, ""+s, Toast.LENGTH_SHORT).show(); //else just show error from API

                    }
                })
        );
    }
}