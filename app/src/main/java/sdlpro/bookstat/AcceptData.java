package sdlpro.bookstat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AcceptData extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    String yr,clg,phno;
    EditText coll,no;
    Button save;
    Spinner year;
    User user=new User();
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        yr=adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_data);



        year= (Spinner) findViewById(R.id.year);
        coll=(EditText)findViewById(R.id.college);
        no=(EditText)findViewById(R.id.phno);
        save=(Button)findViewById(R.id.save);


        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(AcceptData.this,R.array.Year,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(adapter);
        year.setOnItemSelectedListener(AcceptData.this);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference mRootRef= FirebaseDatabase.getInstance().getReference();
                DatabaseReference usersRef=mRootRef.child("Phone");
                ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(AcceptData.this,R.array.Year,android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Intent intent=getIntent();

                user=(User)intent.getSerializableExtra("users") ;
                year.setAdapter(adapter);
                year.setOnItemSelectedListener(AcceptData.this);

                clg=coll.getText().toString();
                phno=no.getText().toString();
                user.setClg(clg);
                user.setYear(yr);
                user.setPhno(phno);
                usersRef.child(phno).setValue(user.getUid());
                Intent intent1=new Intent(AcceptData.this,ProfilePhoto.class);
                intent1.putExtra("users",user);
                mRootRef.child("Users").child(user.getUid()).setValue(user);
                startActivity(intent1 );
            }
        });


    }


}
