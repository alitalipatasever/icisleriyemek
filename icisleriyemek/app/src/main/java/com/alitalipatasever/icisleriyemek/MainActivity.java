package com.alitalipatasever.icisleriyemek;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView txttarih,txtcesit1,txtcesit2,txtcesit3,txtcesit4,txtcesit5,txtBilgi;
    ImageView ekle;
    Button btnPzt,btnSali,btnCrs,btnPrs,btnCuma;
    FirebaseDatabase db;
    String pazartesi="Pazartesi", sali="Salı", carsamba="Çarşamba", persembe="Perşembe", cuma="Cuma";
    final Context context = this;

    ArrayList yemekListesi = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseDatabase.getInstance();

        btnPzt = (Button)findViewById(R.id.btnPzt);
        btnSali = (Button)findViewById(R.id.btnSali);
        btnCrs = (Button)findViewById(R.id.btnCrs);
        btnPrs = (Button)findViewById(R.id.btnPrs);
        btnCuma = (Button)findViewById(R.id.btnCuma);

        txttarih = (TextView)findViewById(R.id.tarih);
        txtcesit1 = (TextView)findViewById(R.id.cesit1);
        txtcesit2 = (TextView)findViewById(R.id.cesit2);
        txtcesit3 = (TextView)findViewById(R.id.cesit3);
        txtcesit4 = (TextView)findViewById(R.id.cesit4);
        txtcesit5 = (TextView)findViewById(R.id.cesit5);
        txtBilgi = (TextView)findViewById(R.id.txtBilgi);
        ekle = (ImageView)findViewById(R.id.ekle);

        ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(),Ekle.class);
//                startActivity(intent);
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.info_dialog);
                TextView tvTitle = (TextView) dialog.findViewById(R.id.TVtitle);
                Button btnEvet = (Button) dialog.findViewById(R.id.BtnEvet);

                Button dialogButton = (Button) dialog.findViewById(R.id.BtnHayir);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        ekle.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                return true;
            }
        });

        Drawable btn_bg_kirmizi = getResources().getDrawable(R.drawable.buton_kirmizi);
        Drawable btn_bg_koyugri = getResources().getDrawable(R.drawable.footer_bg);
        Goster(pazartesi);
        btnPzt.setBackgroundDrawable(btn_bg_kirmizi);
        btnSali.setBackgroundDrawable(btn_bg_koyugri);
        btnCrs.setBackgroundDrawable(btn_bg_koyugri);
        btnPrs.setBackgroundDrawable(btn_bg_koyugri);
        btnCuma.setBackgroundDrawable(btn_bg_koyugri);
        btnPzt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goster(pazartesi);
                btnPzt.setBackgroundDrawable(btn_bg_kirmizi);
                btnSali.setBackgroundDrawable(btn_bg_koyugri);
                btnCrs.setBackgroundDrawable(btn_bg_koyugri);
                btnPrs.setBackgroundDrawable(btn_bg_koyugri);
                btnCuma.setBackgroundDrawable(btn_bg_koyugri);
            }
        });
        btnSali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goster(sali);
                btnPzt.setBackgroundDrawable(btn_bg_koyugri);
                btnSali.setBackgroundDrawable(btn_bg_kirmizi);
                btnCrs.setBackgroundDrawable(btn_bg_koyugri);
                btnPrs.setBackgroundDrawable(btn_bg_koyugri);
                btnCuma.setBackgroundDrawable(btn_bg_koyugri);
            }
        });
        btnCrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goster(carsamba);
                btnPzt.setBackgroundDrawable(btn_bg_koyugri);
                btnSali.setBackgroundDrawable(btn_bg_koyugri);
                btnCrs.setBackgroundDrawable(btn_bg_kirmizi);
                btnPrs.setBackgroundDrawable(btn_bg_koyugri);
                btnCuma.setBackgroundDrawable(btn_bg_koyugri);
            }
        });
        btnPrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goster(persembe);
                btnPzt.setBackgroundDrawable(btn_bg_koyugri);
                btnSali.setBackgroundDrawable(btn_bg_koyugri);
                btnCrs.setBackgroundDrawable(btn_bg_koyugri);
                btnPrs.setBackgroundDrawable(btn_bg_kirmizi);
                btnCuma.setBackgroundDrawable(btn_bg_koyugri);
            }
        });
        btnCuma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goster(cuma);
                btnPzt.setBackgroundDrawable(btn_bg_koyugri);
                btnSali.setBackgroundDrawable(btn_bg_koyugri);
                btnCrs.setBackgroundDrawable(btn_bg_koyugri);
                btnPrs.setBackgroundDrawable(btn_bg_koyugri);
                btnCuma.setBackgroundDrawable(btn_bg_kirmizi);
            }
        });


    }



    public void Goster(String gunAdi){

        DatabaseReference okuma = db.getReference(gunAdi);
        okuma.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                txtcesit1.setText("");
                Iterable<DataSnapshot> keys = snapshot.getChildren();
                for(DataSnapshot key: keys){
                    //txtcesit1.append(key.getValue().toString()+"\n");
                    yemekListesi.add(key.getValue().toString());
                }

                txtcesit1.setText(yemekListesi.get(0).toString());
                txtcesit2.setText(yemekListesi.get(1).toString());
                txtcesit3.setText(yemekListesi.get(2).toString());
                txtcesit4.setText(yemekListesi.get(3).toString());
                txtcesit5.setText(yemekListesi.get(4).toString());
                txttarih.setText(yemekListesi.get(5).toString());
                //deneme

                yemekListesi.clear();

                String strCesit1 = txtcesit1.getText().toString();
                String strCesit2 = txtcesit2.getText().toString();
                String strCesit3 = txtcesit3.getText().toString();
                String strCesit4 = txtcesit4.getText().toString();
                String strCesit5 = txtcesit5.getText().toString();

                BosMu(strCesit1,txtcesit1);
                BosMu(strCesit2,txtcesit2);
                BosMu(strCesit3,txtcesit3);
                BosMu(strCesit4,txtcesit4);
                BosMu(strCesit5,txtcesit5);

                if (strCesit1.isEmpty() && strCesit2.isEmpty() && strCesit3.isEmpty() && strCesit4.isEmpty() && strCesit5.isEmpty()){
                    txtBilgi.setText("Yemek Yoktur!");
                }else{
                    txtBilgi.setText("Yemek Listesi Haftalık Güncellenmektedir!");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void BosMu(String strCesit, TextView txtcesit){
        if (strCesit.isEmpty() || strCesit.equals("-")){
            txtcesit.setVisibility(View.GONE);
        }else{
            txtcesit.setVisibility(View.VISIBLE);
        }
    }


}