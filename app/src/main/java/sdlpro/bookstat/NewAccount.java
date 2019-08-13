package sdlpro.bookstat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NewAccount extends AppCompatActivity {

    EditText email,pass1,pass2,name;
    Button login,register;
    private static final String TAG = Splash.class.getSimpleName();

    // DatabaseReference usersRef=usersRef.child("Users");
   /* DatabaseReference mEmailRef=usersRef.child("Email");
    DatabaseReference mNameRef=usersRef.child("Name");
    DatabaseReference mPassRef=usersRef.child("Password");*/

    FirebaseAuth firebaseAuth;
    private String userID;
    String nm,eml,mpass1,mpass2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        firebaseAuth=FirebaseAuth.getInstance();

        email=(EditText)findViewById(R.id.email);
        login=(Button)findViewById(R.id.login);
        pass1=(EditText)findViewById(R.id.pass1);
        pass2=(EditText)findViewById(R.id.pass2);
        name=(EditText)findViewById(R.id.name);
        register=(Button)findViewById(R.id.register);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nm=name.getText().toString();
                eml=email.getText().toString();
                boolean chk=isEmailValid(eml);
                if(chk==false)
                {
                    email.setError(getString(R.string.error_invalid_email));
                }
                mpass1=pass1.getText().toString();
                mpass2=pass2.getText().toString();


                if(mpass1.equals(mpass2))
                {
                    createUser(nm,eml,mpass1);
                }

            }
        });

    }


    private void createUser(String nm,String eml,String mpass1)
    {
        DatabaseReference mRootRef=FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef=mRootRef.child("Users");
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(eml,mpass1).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful())
                {
                    Toast.makeText(NewAccount.this,"Invalid Data",Toast.LENGTH_SHORT);
                }
                else
                {
                    Toast.makeText(NewAccount.this,"valid Data",Toast.LENGTH_SHORT);
                }
            }
        });
        FirebaseUser currUser=FirebaseAuth.getInstance().getCurrentUser();
        User user=new User(currUser.getUid(),nm,mpass1,eml);

       // mRootRef.child("Phone").child(user.phno).setValue(currUser.getUid());
        Intent intent=new Intent(this,AcceptData.class);


        System.out.println(user);
        intent.putExtra("users",user);
        // mRootRef.child("Users").child(userID).setValue(user);
        // myRef.setValue(user);

        startActivity(intent);

    }


    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return (email.contains("@")&& email.contains(".com"));
    }

}

