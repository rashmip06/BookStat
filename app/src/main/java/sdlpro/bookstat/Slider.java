package sdlpro.bookstat;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class Slider extends AppCompatActivity {
    private ViewPager vpager;
    private LinearLayout dotlay;
    private  SliderAdapter sliderAdapter;
    private TextView[] dots;
    private Button prevB,nextB;
    private int currpage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_slider);
        vpager=(ViewPager)findViewById(R.id.vp1);
        dotlay=(LinearLayout)findViewById(R.id.dotslayout);
        prevB=(Button)findViewById(R.id.prev);
        nextB=(Button)findViewById(R.id.next);
        nextB.setVisibility(View.INVISIBLE);
        sliderAdapter=new SliderAdapter(this);
        vpager.setAdapter(sliderAdapter);
        addDotsIndicator(0);
        vpager.addOnPageChangeListener(viewListener);

    }
    public void addDotsIndicator(int position)
    {
        dots=new TextView[3];
        dotlay.removeAllViews();
        for(int i=0;i<dots.length;i++)
        {
            dots[i]=new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.transparentwhite));
            dotlay.addView(dots[i]);
        }
        if(dots.length>0)
        {
            dots[position].setTextColor(getResources().getColor(R.color.white));
        }
    }
    ViewPager.OnPageChangeListener viewListener= new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);
            currpage=i;
            if(i==0)
            {
                //nextB.setEnabled(true);
               // prevB.setEnabled(false);
                prevB.setVisibility(View.INVISIBLE);
                nextB.setVisibility(View.INVISIBLE);

                //nextB.setText("Next");
                //prevB.setText("");
            }
            else if(i==dots.length-1)
            {
                //nextB.setEnabled(true);
               // prevB.setEnabled(true);
               // prevB.setVisibility(View.VISIBLE);
                prevB.setVisibility(View.INVISIBLE);
                nextB.setVisibility(View.VISIBLE);

                nextB.setText("Finish");
                //prevB.setText("Back");
                nextB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Slider.this,Login.class));
                    }
                });
            }
            else
            {
                /*
                nextB.setEnabled(true);
                prevB.setEnabled(true);
                prevB.setVisibility(View.VISIBLE);

                nextB.setText("Next");
                prevB.setText("Back");*/
                prevB.setVisibility(View.INVISIBLE);
                nextB.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };
}
