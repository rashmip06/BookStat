package sdlpro.bookstat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Chat extends Fragment {
    private String curruid;
    Button fab;
    RelativeLayout activity_chat;
    ArrayList<String> arr;
    String name;
    private DatabaseReference root= FirebaseDatabase.getInstance().getReference().getRoot().child("Users").child("Messages");
    public static Chat newInstance()
    {
        return new Chat();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_chat,container,false);
        curruid=getArguments().getString("uid");
        activity_chat=(RelativeLayout)view.findViewById(R.id.activity_chat);
        /*
        fab=(Button)view.findViewById(R.id.fab);
        arr=new ArrayList<>();
        final EditText room_name=(EditText)view.findViewById(R.id.input);
        final ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,arr);
        ListView listView=(ListView)view.findViewById(R.id.list_of_message);
        listView.setAdapter(arrayAdapter);

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator i=dataSnapshot.getChildren().iterator();
                Set<String> set=new HashSet<String>();
                while (i.hasNext())
                {
                    String s=i.next().toString();
                    String[] str=s.split("_");

                    set.add(str[0]);
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
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getContext(),Chat_Room.class);
                intent.putExtra("room_name",(((TextView)view).getText().toString()));
                intent.putExtra("user_name",name);

                startActivity(intent);

            }
        });

*/
     Intent intent=new Intent(getActivity(),Chatting.class);
     intent.putExtra("uid",curruid);
     getActivity().startActivity(intent);



        return view;
    }

}
