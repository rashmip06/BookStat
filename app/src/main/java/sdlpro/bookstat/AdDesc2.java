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

public class AdDesc2 extends AppCompatActivity {

    TextView title,price;
    ImageView adimg;

    String adid,uid,curruid;
    String name,phoneno;
    String adtitle,adprice,adimag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_desc);
        Intent intent=getIntent();
        curruid=intent.getStringExtra("uid");
        uid=intent.getStringExtra("uid_img");
        adid=intent.getStringExtra("adid");
        title=(TextView)findViewById(R.id.description);
        price=(TextView)findViewById(R.id.price1);
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
                Picasso.with(AdDesc2.this).load(adimag).centerCrop().resize(380,267).into(adimg);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}
