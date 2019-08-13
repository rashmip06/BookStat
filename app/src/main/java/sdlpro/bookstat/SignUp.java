package sdlpro.bookstat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener{

    GoogleApiClient mGoogleApiClient;
    private static  final int RC_SIGN_IN=9001;
    private static final String TAG="SignIn Activity";
    DatabaseReference mRootRef= FirebaseDatabase.getInstance().getReference();
    DatabaseReference usersRef=mRootRef.child("Users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);



        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleApiClient=new GoogleApiClient.Builder(this).enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();

        Button google,newacc;
        google=(Button)findViewById(R.id.google);

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        newacc=(Button) findViewById(R.id.button3);
        newacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this,NewAccount.class));
            }
        });
    }

    private void signIn() {
        Intent signInIntent=Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent,RC_SIGN_IN);

    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode==RC_SIGN_IN)
        {
            GoogleSignInResult result=Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        int d = Log.d(TAG, "handleSignInResult: " + result.isSuccess());

        if(result.isSuccess()){
            GoogleSignInAccount acct=result.getSignInAccount();
            FirebaseUser currUser= FirebaseAuth.getInstance().getCurrentUser();

            Intent intent=new Intent(this,AcceptData.class);
            String  nm=acct.getDisplayName();
            String mpass1=acct.getServerAuthCode();
            String eml= acct.getEmail();
            User user=new User(currUser.getUid(),nm,mpass1,eml);
            System.out.println(user);
            intent.putExtra("users",user);

            startActivity(intent);
        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

