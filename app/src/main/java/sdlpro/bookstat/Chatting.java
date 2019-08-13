package sdlpro.bookstat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
//        import static sars.booktopia3.R.layout.list_item;


public class Chatting extends AppCompatActivity {


    private static int SIGN_IN_REQUEST_CODE=1;
    Intent intent1;
    String uid_img;
    // private FirebaseListAdapter<ChatMessage> adapter;
    Button fab;
    RelativeLayout activity_chat;
    ArrayList<String> arr;
    String name;
    String[] nm;
    String curruid,uid;
    private DatabaseReference root;


    @Override
    public void onBackPressed() {
        DatabaseReference mRef=FirebaseDatabase.getInstance().getReference().child("Users").child(curruid).child("phno");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Intent intent=new Intent(Chatting.this,Start.class);
                intent.putExtra("phone",dataSnapshot.getValue().toString());
                startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        final Intent intent=getIntent();
        curruid=intent.getStringExtra("uid");

        root=FirebaseDatabase.getInstance().getReference().getRoot().child("Users").child(curruid).child("Messages");
        activity_chat=(RelativeLayout)findViewById(R.id.activity_chat);
        fab=(Button)findViewById(R.id.fab);
        arr=new ArrayList<>();
        final EditText room_name=(EditText)findViewById(R.id.input);
        final ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(Chatting.this,android.R.layout.simple_list_item_1,arr);
        final ListView listView=(ListView)findViewById(R.id.list_of_message);
        listView.setAdapter(arrayAdapter);


        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator i=dataSnapshot.getChildren().iterator();
                Set<String> set=new HashSet<String>();
              //  Map<String,Object> map=new HashMap<String, Object>();

                //root.updateChildren(map);
                while (i.hasNext())
                {
                    String s=i.next().toString();
                    String[] str=s.split("_");
                    //map.put(str[1],"");
                    nm=str[1].split(",");
                    set.add(nm[0]);
                }
                arr.clear();
                arr.addAll(set);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {


                final DatabaseReference myRef=FirebaseDatabase.getInstance().getReference().child("Users").child(curruid).child("name");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        intent1=new Intent(getApplicationContext(),Chat_Room.class);
                        intent1.putExtra("curruid",curruid);
                        intent1.putExtra("user_name",dataSnapshot.getValue().toString());

                        DatabaseReference mRef=FirebaseDatabase.getInstance().getReference().child("Users");
                        mRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                                {

                                    if(dataSnapshot1.child("name").getValue().equals(listView.getItemAtPosition(i).toString()))
                                    {
                                        uid_img=dataSnapshot1.getKey().toString();
                                        intent1.putExtra("uid",uid_img);
                                       


                                    }
                                }

                               startActivity(intent1);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });




            }
        });
    }

}
