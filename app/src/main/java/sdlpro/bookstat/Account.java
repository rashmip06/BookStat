package sdlpro.bookstat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
//import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class Account extends Fragment {
    String curruid;

    public static Account newInstance()
    {
        return new Account();
    }
    ImageView imageView1,imageView2;
    TextView tv1,tv2,tv3;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_account,container,false);
        imageView1=(ImageView)view.findViewById(R.id.imageView1);
        tv1=(TextView) view.findViewById(R.id.nameprofile);
        tv2=(TextView) view.findViewById(R.id.emailprofile);
        tv3=(TextView) view.findViewById(R.id.phone);
        FirebaseUser currUser=FirebaseAuth.getInstance().getCurrentUser();


        Bundle bundle=getActivity().getIntent().getExtras();
        curruid= getArguments().getString("uid");
      final DatabaseReference dref=FirebaseDatabase.getInstance().getReference("Users").child(curruid);
       dref.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               User user=dataSnapshot.getValue(User.class);
               tv1.setText("Name  :"+user.getName());
               tv2.setText("Email  :"+user.getEmail());
               tv3.setText("Phone number  :"+user.getPhno());
               DatabaseReference myref=FirebaseDatabase.getInstance().getReference("Users").child(curruid).child("Image").child("Profile").child("URL");
               myref.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(DataSnapshot dataSnapshot) {
                       Picasso.with(getContext()).load(dataSnapshot.getValue().toString()).into(imageView1);
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


        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onStart() {
        super.onStart();


    }
}
