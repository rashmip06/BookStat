package sdlpro.bookstat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MyAds extends Fragment {
    public static MyAds newInstance() {
        return new MyAds();
    }

    List<Ad> Adlist;
    public CardView cardView;
    private String curruid;
    private RecyclerView recyclerView;
    private TextView tv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myads, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyview);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        curruid = getArguments().getString("uid");
        tv=(TextView)view.findViewById(R.id.tv);
        tv.setVisibility(View.GONE);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
         recyclerView = (RecyclerView) getView().findViewById(R.id.recyview);

      Adlist = new ArrayList<>();

        final DatabaseReference dref = FirebaseDatabase.getInstance().getReference("Ads");
        dref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Ad ad = dataSnapshot1.getValue(Ad.class);
                    if (ad.getUD().equals(curruid))
                    Adlist.add(ad);
                }

                if (!Adlist.isEmpty())
                {RVAdapter myAdp = new RVAdapter(getContext(), Adlist, curruid);
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                    recyclerView.setAdapter(myAdp);}
                else
                {
                    tv.setText("Oops!! No Ads to display");
                    tv.setVisibility(View.VISIBLE);

                }


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
    public void onAttach(Context context) {
        super.onAttach(context);

    }


}
