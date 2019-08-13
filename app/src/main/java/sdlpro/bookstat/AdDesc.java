package sdlpro.bookstat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import sdlpro.bookstat.R;

public class AdDesc extends AppCompatActivity {

    TextView title,price;
    ImageView adimg;
    FloatingActionButton phone;
    FloatingActionButton chat;
    String adid,uid,curruid;
    String name,phoneno;
    String adtitle,adprice,adimag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_desc);
        /*
        Intent getdata = getIntent();
        Bundle bd = getdata.getExtras();
        int id = bd.getInt("Id");*/
        Intent intent=getIntent();
        curruid=intent.getStringExtra("uid");
        uid=intent.getStringExtra("uid_img");
        adid=intent.getStringExtra("adid");
        title=(TextView)findViewById(R.id.description);
        price=(TextView)findViewById(R.id.price);
        adimg=(ImageView)findViewById(R.id.imageView2);
        DatabaseReference mRef=FirebaseDatabase.getInstance().getReference();
        final DatabaseReference databaseReference=mRef.child("Ads").child("Books").child(adid).child("Descp");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adtitle=dataSnapshot.getValue().toString();
                title.setText(adtitle);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
       /* databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adtitle=dataSnapshot.getValue().toString();
                title.setText(adtitle);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
        final DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("Ads").child("Books").child(adid).child("Amount");
          databaseReference1.addValueEventListener(new ValueEventListener() {
              @Override
              public void onDataChange(DataSnapshot dataSnapshot) {
                  adprice=dataSnapshot.getValue().toString();
                  price.setText(adprice);
              }

              @Override
              public void onCancelled(DatabaseError databaseError) {

              }
          });
        final DatabaseReference databaseReference2=FirebaseDatabase.getInstance().getReference("Ads").child("Books").child(adid).child("url");
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adimag=dataSnapshot.getValue().toString();
                Picasso.with(AdDesc.this).load(adimag).centerCrop().resize(380,267).into(adimg);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        phone = (FloatingActionButton) findViewById(R.id.phone);
        chat = (FloatingActionButton) findViewById(R.id.chat);


        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("phno");
                databaseReference1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        phoneno=dataSnapshot.getValue().toString();
                        callIntent.setData(Uri.parse("tel:"+phoneno));
                        if (ActivityCompat.checkSelfPermission(getBaseContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }

                        startActivity(callIntent);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Users").child(curruid).child("name");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Intent intent=new Intent(AdDesc.this,Chat_Room.class);
                        name=dataSnapshot.getValue().toString();

                        intent.putExtra("user_name",name);
                        intent.putExtra("uid",uid);
                        intent.putExtra("curruid",curruid);

                        startActivity(intent);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });


    }
}
