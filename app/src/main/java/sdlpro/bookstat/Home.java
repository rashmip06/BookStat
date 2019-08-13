package sdlpro.bookstat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Home extends Fragment{
    private Button sell;
    private Button buy;
      ArrayList<Integer> list=new ArrayList<>();
    private String[] string = new String[1];
private ImageView img;
    private String curruid,phone;
    public static  Home newInstance()
    {
        return new Home();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_home,container,false);
        phone =getArguments().getString("phone");
         curruid=getArguments().getString("uid");
        DatabaseReference myRef;

        myRef= FirebaseDatabase.getInstance().getReference().child("Phone").child(phone);
        myRef.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            string[0] = dataSnapshot.getValue().toString();
                                            curruid = string[0];
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });

        return  view;
    }
    @Override
    public void onStart() {
        super.onStart();
        sell = (Button) getView().findViewById(R.id.sell);
        buy = (Button) getView().findViewById(R.id.buy);
        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getContext(),SellAct.class);
                intent.putExtra("uid",curruid);
                startActivity(intent);
            }
        });
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getContext(),BuyAct.class);
                intent.putExtra("uid",curruid);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }


}
