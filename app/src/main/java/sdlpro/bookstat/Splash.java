package sdlpro.bookstat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Splash extends AppCompatActivity {
    TextView name;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);





        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
/*
       if(user!=null)
        {
            finish();
            startActivity(new Intent(Splash.this,Start.class));
        }
        else*/
        {
            name = (TextView) findViewById(R.id.name);
            logo = (ImageView) findViewById(R.id.logo);
            Animation myAni = AnimationUtils.loadAnimation(this, R.anim.mytrans);
            logo.startAnimation(myAni);
            Thread timer = new Thread() {
                public void run() {
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    finally {
                        startActivity(new Intent(Splash.this,Slider.class));
                        finish();
                    }
                }
            };
            timer.start();

        }


    }
}
