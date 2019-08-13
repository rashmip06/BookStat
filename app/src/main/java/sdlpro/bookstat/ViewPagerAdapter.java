package sdlpro.bookstat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {
    private ArrayList<Integer> imgs;
    private LayoutInflater inflater;
    private Context context;
    public ViewPagerAdapter(Context context, ArrayList<Integer> list ) {
        this.context=context;
        this.imgs=list;
        inflater=LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
       View v=inflater.inflate(R.layout.imaglist,container,false);
       assert  v!=null;
        ImageView imageView=(ImageView)v.findViewById(R.id.img);
        imageView.setImageResource(imgs.get(position));
        container.addView(v,0);
        return  v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return false;
    }
}
