package sdlpro.bookstat;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Chat_Room extends AppCompatActivity {

    FloatingActionButton send;
    EditText typemsg;
    TextView disp_msg;
    private  String user_name,room_name,temp_key,uid_img,curruid;
    private DatabaseReference root,root2,myRef;
    String currname;
    DatabaseReference message_root,message_root2;

    String sender;
    FirebaseUser currUser= FirebaseAuth.getInstance().getCurrentUser();

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent=new Intent(Chat_Room.this,Chatting.class);
        intent.putExtra("uid",curruid);

        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat__room);

        //LinearLayout layout = (LinearLayout) findViewById(R.id.lay1);
        send = (FloatingActionButton) findViewById(R.id.send);
        typemsg = (EditText) findViewById(R.id.msg);
        disp_msg = (TextView) findViewById(R.id.disp_msg);
        curruid = getIntent().getStringExtra("curruid");
        user_name = getIntent().getStringExtra("user_name");
        //Toast.makeText(Chat_Room.this, "username" + user_name, Toast.LENGTH_LONG).show();
        uid_img = getIntent().getStringExtra("uid");

        //Toast.makeText(Chat_Room.this,uid_img,Toast.LENGTH_LONG).show();

        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid_img).child("name");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currname = dataSnapshot.getValue().toString();
                setTitle(currname);
                root = FirebaseDatabase.getInstance().getReference().child("Users").child(curruid).child("Messages").child(user_name + "_" + currname);
                root2 = FirebaseDatabase.getInstance().getReference().child("Users").child(uid_img).child("Messages").child(currname + "_" + user_name);
                send.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map=new HashMap<String, Object>();
                        temp_key=root.push().getKey();
                        root.updateChildren(map);
                        root2.updateChildren(map);
                        message_root=root.child(temp_key);
                        message_root2=root2.child(temp_key);
                        Map<String,Object> map2=new HashMap<String, Object>();
                        map2.put("name",user_name);
                        map2.put("msg",typemsg.getText().toString());

                        message_root.updateChildren(map2);
                        message_root2.updateChildren(map2);
                        typemsg.setText("");
                    }
                });

                root.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        append_chat(dataSnapshot);
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        append_chat(dataSnapshot);
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

            private String chat_msg,chat_user_name;
            private void append_chat(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren())
                {

                    Iterator i=dataSnapshot.getChildren().iterator();
                while (i.hasNext()) {

                    chat_msg = (String) ((DataSnapshot) i.next()).getValue();
                    chat_user_name = (String) ((DataSnapshot) i.next()).getValue();

                     if (chat_user_name.equals(user_name) ) {

                        disp_msg.append("----------\nYou :\n " + chat_msg + "\n----------\n");


                    } else {

                        disp_msg.append("-------------\n"+currname+ " :\n" + chat_msg + "\n----------\n" +
                                "");

                    }

                }




                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
