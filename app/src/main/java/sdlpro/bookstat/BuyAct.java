package sdlpro.bookstat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BuyAct extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private RecyclerView recyclerView;
    private List<Ad> mylist;
    private String curruid;
   private String cat;
   private Spinner category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        Intent intent=getIntent();
        curruid =intent.getStringExtra("uid");
        recyclerView=(RecyclerView)findViewById(R.id.recyview);
        mylist=new ArrayList<>();

        final DatabaseReference dref= FirebaseDatabase.getInstance().getReference("Ads");
        dref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    Ad ad=dataSnapshot1.getValue(Ad.class);


                    if(!ad.getUD().equals(curruid))
                    mylist.add(ad);
                }

                RVAdapter myAdp=new RVAdapter(BuyAct.this,mylist,curruid);
                recyclerView.setLayoutManager(new GridLayoutManager(BuyAct.this,2));
                recyclerView.setAdapter(myAdp);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        cat=parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
