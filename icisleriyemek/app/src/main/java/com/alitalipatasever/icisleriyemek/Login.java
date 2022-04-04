package com.alitalipatasever.icisleriyemek;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    Button giris;
    FirebaseAuth auth;
    EditText username,password;
    final Context context = this;
    String alertMesajText="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        giris = (Button) findViewById(R.id.btnGiris);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strUsername1 = username.getText().toString();
                String strPassword = password.getText().toString();
                String strUsername =  strUsername1.trim();
                if(strUsername.isEmpty() || strPassword.isEmpty()){
                    //Toast.makeText(getApplicationContext(),"Kullanıcı Adı Boş Bırakılamaz!",Toast.LENGTH_SHORT).show();
                    alertMesajText = "Kullanıcı Adı veya Şifre Boş Bırakılamaz!";
                    alertMesaj(alertMesajText);
                }else if(strPassword.length()<6){
                    //Toast.makeText(getApplicationContext(),"Şifre en az 6 haneli olmalıdır!",Toast.LENGTH_SHORT).show();
                    alertMesajText = "Şifre en az 6 haneli olmalıdır!";
                    alertMesaj(alertMesajText);
                }else{
                    auth.signInWithEmailAndPassword(strUsername,strPassword).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                //Toast.makeText(getApplicationContext(),"Kullanıcı adı veya Şifre geçersiz!",Toast.LENGTH_SHORT).show();
                                alertMesajText = "Kullanıcı adı veya Şifre geçersiz!";
                                alertMesaj(alertMesajText);
                            }else{
                                startActivity(new Intent(Login.this,Ekle.class));
                                finish();
                            }
                        }
                    });
                }

            }
        });
    }

    public void alertMesaj(String alertMesajText){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.tamam_dialog);
        TextView tvTitle = (TextView) dialog.findViewById(R.id.TVtitle);
        tvTitle.setText(alertMesajText);
        Button dialogButton = (Button) dialog.findViewById(R.id.btnTamam);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}