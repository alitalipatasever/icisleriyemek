package com.alitalipatasever.icisleriyemek;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    TextView txttarih,txtcesit1,txtcesit2,txtcesit3,txtcesit4,txtcesit5,txtBilgi;
    ImageView ekle;
    Button btnPzt,btnSali,btnCrs,btnPrs,btnCuma;
    FirebaseDatabase db;
    String pazartesi="Pazartesi", sali="Salı", carsamba="Çarşamba", persembe="Perşembe", cuma="Cuma";
    final Context context = this;
    String dayName;
    Drawable btn_bg_kirmizi, btn_bg_koyugri;

    ArrayList yemekListesi = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkNetwork();

        db = FirebaseDatabase.getInstance();

        elementDefinion();

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

        btn_bg_kirmizi = getResources().getDrawable(R.drawable.buton_kirmizi);
        btn_bg_koyugri = getResources().getDrawable(R.drawable.footer_bg);
        //Goster(pazartesi);
        dayName();
        dayKontrol();
        navigationBar();

    }

    public void checkNetwork(){

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            //Toast.makeText(getApplicationContext(),"net açık",Toast.LENGTH_SHORT).show();
        }
        else
            alertMesaj("Lütfen İnternet Bağlantınızı Kontrol Edin!");
    }

    public void elementDefinion(){
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
    }

    public void dayKontrol(){
        if(dayName.equals(pazartesi)){
            Goster(pazartesi);
            btnPzt.setBackgroundDrawable(btn_bg_kirmizi);
            btnSali.setBackgroundDrawable(btn_bg_koyugri);
            btnCrs.setBackgroundDrawable(btn_bg_koyugri);
            btnPrs.setBackgroundDrawable(btn_bg_koyugri);
            btnCuma.setBackgroundDrawable(btn_bg_koyugri);
        }else if(dayName.equals(sali)){
            Goster(sali);
            btnPzt.setBackgroundDrawable(btn_bg_koyugri);
            btnSali.setBackgroundDrawable(btn_bg_kirmizi);
            btnCrs.setBackgroundDrawable(btn_bg_koyugri);
            btnPrs.setBackgroundDrawable(btn_bg_koyugri);
            btnCuma.setBackgroundDrawable(btn_bg_koyugri);
        }else if(dayName.equals(carsamba)){
            Goster(carsamba);
            btnPzt.setBackgroundDrawable(btn_bg_koyugri);
            btnSali.setBackgroundDrawable(btn_bg_koyugri);
            btnCrs.setBackgroundDrawable(btn_bg_kirmizi);
            btnPrs.setBackgroundDrawable(btn_bg_koyugri);
            btnCuma.setBackgroundDrawable(btn_bg_koyugri);
        }else if(dayName.equals(persembe)){
            Goster(persembe);
            btnPzt.setBackgroundDrawable(btn_bg_koyugri);
            btnSali.setBackgroundDrawable(btn_bg_koyugri);
            btnCrs.setBackgroundDrawable(btn_bg_koyugri);
            btnPrs.setBackgroundDrawable(btn_bg_kirmizi);
            btnCuma.setBackgroundDrawable(btn_bg_koyugri);
        }else if(dayName.equals(cuma)){
            Goster(cuma);
            btnPzt.setBackgroundDrawable(btn_bg_koyugri);
            btnSali.setBackgroundDrawable(btn_bg_koyugri);
            btnCrs.setBackgroundDrawable(btn_bg_koyugri);
            btnPrs.setBackgroundDrawable(btn_bg_koyugri);
            btnCuma.setBackgroundDrawable(btn_bg_kirmizi);
        }
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


                yemekListesi.clear();

                String strCesit1 = txtcesit1.getText().toString();
                String strCesit2 = txtcesit2.getText().toString();
                String strCesit3 = txtcesit3.getText().toString();
                String strCesit4 = txtcesit4.getText().toString();
                String strCesit5 = txtcesit5.getText().toString();

                cesitBosMu(strCesit1,txtcesit1);
                cesitBosMu(strCesit2,txtcesit2);
                cesitBosMu(strCesit3,txtcesit3);
                cesitBosMu(strCesit4,txtcesit4);
                cesitBosMu(strCesit5,txtcesit5);

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

    public void cesitBosMu(String strCesit, TextView txtcesit){
        if (strCesit.isEmpty() || strCesit.equals("-")){
            txtcesit.setVisibility(View.GONE);
        }else{
            txtcesit.setVisibility(View.VISIBLE);
        }
    }

    public void dayName(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        //System.out.println("Today's date: "+ simpleDateFormat.format(calendar.getTime()));
        Format f = new SimpleDateFormat("EEEE");
        dayName = f.format(new Date());

    }

    public void navigationBar(){
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

    public void alertMesaj(String alertMesajText){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.tamam_dialog);
        TextView tvTitle = (TextView) dialog.findViewById(R.id.TVtitle);
        tvTitle.setText(alertMesajText);
        Button dialogButton = (Button) dialog.findViewById(R.id.btnTamam);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dialog.dismiss();
                finish();
            }
        });
        dialog.show();
    }


}