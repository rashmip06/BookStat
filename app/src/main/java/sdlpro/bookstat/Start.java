package sdlpro.bookstat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class Start extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView navigation;
    Home home=new Home();
    Chat chat=new Chat();
    Account account=new Account();
    MyAds myAds=new MyAds();
    private String currUid;
    String phone;
    int flag=0;
    private String[] string = new String[1];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Intent intent=getIntent();
        navigation = (BottomNavigationView)findViewById(R.id.bottomnavi);
        navigation.setSelectedItemId(R.id.navi_home);
        navigation.setOnNavigationItemSelectedListener(this);
        FirebaseAuth auth=FirebaseAuth.getInstance();
        FirebaseUser currUser= auth.getCurrentUser();
        String email = null,uid;

        phone=intent.getStringExtra("phone");
                DatabaseReference myRef;

        myRef=FirebaseDatabase.getInstance().getReference().child("Phone").child(phone);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                string[0] =dataSnapshot.getValue().toString();

                currUid=string[0];
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Home frag=new Home();
        Bundle bundle=new Bundle();
        bundle.putString("phone",phone);
        bundle.putString("uid",currUid);
        frag.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,frag).addToBackStack(null).commit();

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();

        switch(id)
        {

            case R.id.about:
                Intent intent1=new Intent(Start.this,AboutAct.class);
                startActivity(intent1);
                break;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                finishAffinity();
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);

                Intent intent2=new Intent(Start.this,Login.class);

                startActivity(intent2);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        switch (menuItem.getItemId()) {
            case R.id.navi_home:
                fragment = home;
                break;
            case R.id.navi_chat:
                //fragment=chat;
                flag = 1;
                break;
            case R.id.navi_myads:
                fragment = myAds;
                break;
            case R.id.navi_account:
                fragment = account;
                break;
        }
        if (flag == 0) {
            Bundle bundle = new Bundle();
            bundle.putString("uid", currUid);
            bundle.putString("phone", phone);
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, fragment).addToBackStack(null).commit();
            return true;
        } else {
            Intent intent = new Intent(Start.this,Chatting.class);
            intent.putExtra("uid", currUid);
            startActivity(intent);

        }
        return false;
    }
}

