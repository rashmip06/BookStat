package sdlpro.bookstat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;
    public SliderAdapter(Context context)
    {
        this.context=context;
    }
    public int[] slide_imgs={R.drawable.slide1,R.drawable.slide2,R.drawable.slider3};
    public String[] slide_headings={
            "Sell your used books",
            "Chat with the buyer",
            "Buy used books at low price"
    };
    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==(RelativeLayout)o;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        layoutInflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.slidelay1,null);
        ImageView slideimage=(ImageView)view.findViewById(R.id.img1);
        TextView slidetxt=(TextView)view.findViewById(R.id.text1);
        slideimage.setBackgroundResource(slide_imgs[position]);
        slidetxt.setText(slide_headings[position]);
        container.addView(view,0);
        return view;
    }
    @Override
    public void destroyItem(ViewGroup container,int position,Object object)
    {
        container.removeView((RelativeLayout)object);
    }

}
