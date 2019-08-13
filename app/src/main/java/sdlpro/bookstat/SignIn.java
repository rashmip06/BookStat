package sdlpro.bookstat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity implements View.OnClickListener{
    private Button register;
    private EditText email,password;
    private TextView sign;
    private ProgressDialog progressDialog;
    private TextView forgot1;
    private FirebaseAuth firebaseAuth,firebaseAuth1,auth;
    String email1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_signin);

        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        register=(Button)findViewById(R.id.login);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.pass);
        sign=(TextView)findViewById(R.id.signup);
        forgot1=(TextView)findViewById(R.id.forgot);
        auth=FirebaseAuth.getInstance ();
        if(firebaseAuth.getCurrentUser() != null)
        {
            finish();

            Intent secondactivity=new Intent(SignIn.this,Start.class);


            startActivity(secondactivity);
        }
        register.setOnClickListener(this);
        sign.setOnClickListener(this);
        forgot1.setOnClickListener ( this );
    }
    protected void userLogin()
    {
        email1=email.getText().toString().trim();
        String pass=password.getText().toString().trim();
        if(TextUtils.isEmpty(email1))
        {
            Toast.makeText(this,"PLEASE ENTER EMAIL",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pass))
        {
            Toast.makeText(this,"PLEASE ENTER PASSWORD",Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Signing up...");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email1,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful())
                {
                    finish();
                    // startActivity(new Intent(login.this,HomeActivity.class));
                    Toast.makeText(SignIn.this,firebaseAuth.getCurrentUser().getUid(),Toast.LENGTH_LONG).show();

                    Intent secondactivity=new Intent(SignIn.this,Start.class);

                    // secondactivity.putExtra ("Email", email1);
                    startActivity(secondactivity);

                }
                else
                {
                    Toast.makeText(SignIn.this,"UnSuccessfull",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    @Override
    public void onClick(View view) {
        if(view == register)
        {
            userLogin();
        }
        if(view == forgot1)
        {
            String eml=email.getText ().toString ().trim ();
            if(eml.isEmpty ())
            {
                Toast.makeText ( SignIn.this, "Please enter email first", Toast.LENGTH_SHORT ).show ();
            }else {
                auth.sendPasswordResetEmail ( eml ).addOnCompleteListener ( new OnCompleteListener<Void> () {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful ()) {
                            Toast.makeText ( SignIn.this, "Email for password reset sent", Toast.LENGTH_SHORT ).show ();
                            //startActivity ( new Intent ( login.this,login.class ) );
                        } else {
                            Toast.makeText ( SignIn.this, "Error in sending mail", Toast.LENGTH_SHORT ).show ();
                        }
                    }
                } );
            }}
        if(view == sign)
        {
            finish();
            startActivity(new Intent(this,SignUp.class));

        }
    }




}
