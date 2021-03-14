package com.github.Coaxus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {

    CheckBox termsConditions;
    EditText nameUser, EmailUser, passwordUser;
    Button RegisterButton;
    TextView loginBack;

    RequestQueue requestQueue;
    private static final String URL1 ="http://192.168.0.11/scholae/save.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //Listen
        nameUser = (EditText)findViewById(R.id.nameRegister);
        EmailUser = (EditText)findViewById(R.id.EmailAddress);
        passwordUser = (EditText)findViewById(R.id.passwordRegister);
        termsConditions = (CheckBox)findViewById(R.id.conditionsTerms);
        RegisterButton = (Button)findViewById(R.id.registerButtonRegister);
        loginBack = (TextView)findViewById(R.id.loginBack);

        requestQueue = Volley.newRequestQueue(this);
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Validate() && ValidateTerms()){
                    String name = nameUser.getText().toString().trim();
                    String email = EmailUser.getText().toString().trim();
                    String password = passwordUser.getText().toString().trim();

                    createUser(name, email, password);
                }
            }
        });
        loginBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), login.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    private void createUser(final String name, final String email, final String password) {

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(register.this, "All is correct", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }

    public boolean Validate(){
        boolean Validate = false;
        if(nameUser.getText().toString().isEmpty() && EmailUser.getText().toString().isEmpty()
                && passwordUser.getText().toString().isEmpty()){
            Toast.makeText(this, "Missing information", Toast.LENGTH_SHORT).show();
        }else{
            Validate = true;
        }
        return Validate;
    }

    public boolean ValidateTerms(){
        boolean Validate = false;
        if(termsConditions.isChecked()){
            Validate = true;
        }else{
            Toast.makeText(this, "Accept the terms, please", Toast.LENGTH_SHORT).show();
        }
        return Validate;
    }

}