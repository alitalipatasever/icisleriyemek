package com.alitalipatasever.icisleriyemek;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Ekle extends AppCompatActivity {

    FirebaseDatabase db;
    EditText eTtarih,eTcesit1,eTcesit2,eTcesit3,eTcesit4,eTcesit5;
    Button btnPzt,btnSali,btnCrs,btnPrs,btnCuma;
    TextView cikis,gunYemekListesi;
    Button kaydet,getir;
    String cesit1, cesit2, cesit3, cesit4, cesit5;
    String pazartesi="Pazartesi", sali="Salı", carsamba="Çarşamba", persembe="Perşembe", cuma="Cuma";
    String tarih="23 Mart 2022";
    String gunAdi="";
    ArrayList yemekListesi = new ArrayList();
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ekle);

        elementDefinition();

        db = FirebaseDatabase.getInstance();

        cikis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //System.exit(0);
            }
        });

        Drawable btn_bg_kirmizi = getResources().getDrawable(R.drawable.buton_kirmizi);
        Drawable btn_bg_koyugri = getResources().getDrawable(R.drawable.footer_bg);
        btnPzt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnPzt.setBackgroundDrawable(btn_bg_kirmizi);
                btnSali.setBackgroundDrawable(btn_bg_koyugri);
                btnCrs.setBackgroundDrawable(btn_bg_koyugri);
                btnPrs.setBackgroundDrawable(btn_bg_koyugri);
                btnCuma.setBackgroundDrawable(btn_bg_koyugri);
                gunYemekListesi.setText(pazartesi);
                eTtarih.setText("");
                eTcesit1.setText("");
                eTcesit2.setText("");
                eTcesit3.setText("");
                eTcesit4.setText("");
                eTcesit5.setText("");
                gunAdi=pazartesi;
            }
        });
        btnSali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnPzt.setBackgroundDrawable(btn_bg_koyugri);
                btnSali.setBackgroundDrawable(btn_bg_kirmizi);
                btnCrs.setBackgroundDrawable(btn_bg_koyugri);
                btnPrs.setBackgroundDrawable(btn_bg_koyugri);
                btnCuma.setBackgroundDrawable(btn_bg_koyugri);
                gunYemekListesi.setText(sali);
                eTtarih.setText("");
                eTcesit1.setText("");
                eTcesit2.setText("");
                eTcesit3.setText("");
                eTcesit4.setText("");
                eTcesit5.setText("");
                gunAdi=sali;
            }
        });
        btnCrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnPzt.setBackgroundDrawable(btn_bg_koyugri);
                btnSali.setBackgroundDrawable(btn_bg_koyugri);
                btnCrs.setBackgroundDrawable(btn_bg_kirmizi);
                btnPrs.setBackgroundDrawable(btn_bg_koyugri);
                btnCuma.setBackgroundDrawable(btn_bg_koyugri);
                gunYemekListesi.setText(carsamba);
                eTtarih.setText("");
                eTcesit1.setText("");
                eTcesit2.setText("");
                eTcesit3.setText("");
                eTcesit4.setText("");
                eTcesit5.setText("");
                gunAdi=carsamba;
            }
        });
        btnPrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnPzt.setBackgroundDrawable(btn_bg_koyugri);
                btnSali.setBackgroundDrawable(btn_bg_koyugri);
                btnCrs.setBackgroundDrawable(btn_bg_koyugri);
                btnPrs.setBackgroundDrawable(btn_bg_kirmizi);
                btnCuma.setBackgroundDrawable(btn_bg_koyugri);
                gunYemekListesi.setText(persembe);
                eTtarih.setText("");
                eTcesit1.setText("");
                eTcesit2.setText("");
                eTcesit3.setText("");
                eTcesit4.setText("");
                eTcesit5.setText("");
                gunAdi=persembe;
            }
        });
        btnCuma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnPzt.setBackgroundDrawable(btn_bg_koyugri);
                btnSali.setBackgroundDrawable(btn_bg_koyugri);
                btnCrs.setBackgroundDrawable(btn_bg_koyugri);
                btnPrs.setBackgroundDrawable(btn_bg_koyugri);
                btnCuma.setBackgroundDrawable(btn_bg_kirmizi);
                gunYemekListesi.setText(cuma);
                eTtarih.setText("");
                eTcesit1.setText("");
                eTcesit2.setText("");
                eTcesit3.setText("");
                eTcesit4.setText("");
                eTcesit5.setText("");
                gunAdi=cuma;
            }
        });

        kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strTarih = eTtarih.getText().toString();
                if(strTarih.isEmpty()){
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.tamam_dialog);
                    TextView tvTitle = (TextView) dialog.findViewById(R.id.TVtitle);
                    tvTitle.setText("Lütfen Veri Girişi Yapınız!\nYemek olmayan günün kaydını yapmak için tarih alanını doldurunuz!");
                    Button dialogButton = (Button) dialog.findViewById(R.id.btnTamam);
                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }else{
                    gunAdi = gunYemekListesi.getText().toString();

                    tarih = eTtarih.getText().toString();
                    cesit1 = eTcesit1.getText().toString();
                    cesit2 = eTcesit2.getText().toString();
                    cesit3 = eTcesit3.getText().toString();
                    cesit4 = eTcesit4.getText().toString();
                    cesit5 = eTcesit5.getText().toString();

                    if(gunAdi.equals(pazartesi)){
                        Ekle(pazartesi,tarih,cesit1,cesit2,cesit3,cesit4,cesit5);
                    }if(gunAdi.equals(sali)){
                        Ekle(sali,tarih,cesit1,cesit2,cesit3,cesit4,cesit5);
                    }if(gunAdi.equals(carsamba)){
                        Ekle(carsamba,tarih,cesit1,cesit2,cesit3,cesit4,cesit5);
                    }if(gunAdi.equals(persembe)){
                        Ekle(persembe,tarih,cesit1,cesit2,cesit3,cesit4,cesit5);
                    }if(gunAdi.equals(cuma)){
                        Ekle(cuma,tarih,cesit1,cesit2,cesit3,cesit4,cesit5);
                    }

                    //Toast.makeText(getApplicationContext(),"Başarılı",Toast.LENGTH_SHORT).show();
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.tamam_dialog);
                    TextView tvTitle = (TextView) dialog.findViewById(R.id.TVtitle);
                    tvTitle.setText(gunAdi+"\nYemek Listesi Veri Tabanına Kaydedildi!");
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
        });

        getir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gunAdi = gunYemekListesi.getText().toString();

                if (gunAdi.equals(pazartesi)){
                    Goster(pazartesi);
                }if (gunAdi.equals(sali)){
                    Goster(sali);
                }if (gunAdi.equals(carsamba)){
                    Goster(carsamba);
                }if (gunAdi.equals(persembe)){
                    Goster(persembe);
                }if (gunAdi.equals(cuma)){
                    Goster(cuma);
                }
            }
        });

        eTtarih.setInputType(InputType.TYPE_NULL);
        eTtarih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                // date picker dialog
                DatePickerDialog picker = new DatePickerDialog(Ekle.this,R.style.DialogTheme,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                Calendar cal = Calendar.getInstance();
                                cal.set(Calendar.YEAR,year);
                                cal.set(Calendar.MONTH,monthOfYear);
                                cal.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                                Date chosenDate = cal.getTime();

                                DateFormat df_full = DateFormat.getDateInstance(DateFormat.FULL);
                                String df_full_str = df_full.format(chosenDate);

                                eTtarih.setText(df_full_str);

                                //eTtarih.setText(dayOfMonth + "." + (monthOfYear + 1) + "." + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

    }

    public void elementDefinition(){
        btnPzt = (Button)findViewById(R.id.btnPzt);
        btnSali = (Button)findViewById(R.id.btnSali);
        btnCrs = (Button)findViewById(R.id.btnCrs);
        btnPrs = (Button)findViewById(R.id.btnPrs);
        btnCuma = (Button)findViewById(R.id.btnCuma);

        eTtarih = (EditText)findViewById(R.id.eTtarih);
        eTcesit1 = (EditText)findViewById(R.id.eTcesit1);
        eTcesit2 = (EditText)findViewById(R.id.eTcesit2);
        eTcesit3 = (EditText)findViewById(R.id.eTcesit3);
        eTcesit4 = (EditText)findViewById(R.id.eTcesit4);
        eTcesit5 = (EditText)findViewById(R.id.eTcesit5);
        cikis = (TextView)findViewById(R.id.cikis);
        gunYemekListesi = (TextView)findViewById(R.id.gunYemekListesi);
        kaydet = (Button)findViewById(R.id.kaydet);
        getir = (Button)findViewById(R.id.getir);
    }

    public void Ekle (String gunAdi,String tarih, String cesit1, String cesit2, String cesit3,String cesit4, String cesit5){

        DatabaseReference dbRef = db.getReference(gunAdi);
        DatabaseReference dbRefTarih = db.getReference(gunAdi+"/"+"Tarih");
        dbRefTarih.setValue(tarih);
        DatabaseReference dbRefCesit1 = db.getReference(gunAdi+"/"+"Cesit1");
        dbRefCesit1.setValue(cesit1);
        DatabaseReference dbRefCesit2 = db.getReference(gunAdi+"/"+"Cesit2");
        dbRefCesit2.setValue(cesit2);
        DatabaseReference dbRefCesit3 = db.getReference(gunAdi+"/"+"Cesit3");
        dbRefCesit3.setValue(cesit3);
        DatabaseReference dbRefCesit4 = db.getReference(gunAdi+"/"+"Cesit4");
        dbRefCesit4.setValue(cesit4);
        DatabaseReference dbRefCesit5 = db.getReference(gunAdi+"/"+"Cesit5");
        dbRefCesit5.setValue(cesit5);
    }

    public void Goster(String gunAdi){

        DatabaseReference okuma = db.getReference(gunAdi);
        okuma.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //txtcesit1.setText("");
                Iterable<DataSnapshot> keys = snapshot.getChildren();
                for(DataSnapshot key: keys){
                    //txtcesit1.append(key.getValue().toString()+"\n");
                    yemekListesi.add(key.getValue().toString());
                }

                eTcesit1.setText(yemekListesi.get(0).toString());
                eTcesit2.setText(yemekListesi.get(1).toString());
                eTcesit3.setText(yemekListesi.get(2).toString());
                eTcesit4.setText(yemekListesi.get(3).toString());
                eTcesit5.setText(yemekListesi.get(4).toString());
                eTtarih.setText(yemekListesi.get(5).toString());

                yemekListesi.clear();

                String strCesit1 = eTcesit1.getText().toString();
                String strCesit2 = eTcesit2.getText().toString();
                String strCesit3 = eTcesit3.getText().toString();
                String strCesit4 = eTcesit4.getText().toString();
                String strCesit5 = eTcesit5.getText().toString();

                if (strCesit1.isEmpty() && strCesit2.isEmpty() && strCesit3.isEmpty() && strCesit4.isEmpty() && strCesit5.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Kayıt Bulunamadı!",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        //System.exit(0);
    }
}