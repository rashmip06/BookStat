package sdlpro.bookstat;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import java.util.List;

public class RV2Adapter extends RecyclerView.Adapter<RV2Adapter.myviewholder> {

    static private Context mcontext;
    static private List<Ad> mData;
    static private String curruid;
    CardView mcardview;

    public RV2Adapter(Context mcontext, List<Ad> mData, String curruid) {
        this.mcontext = mcontext;
        this.mData = mData;
        this.curruid=curruid;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view;
        final int a=i;
        final LayoutInflater myflater=LayoutInflater.from(mcontext);
        view=myflater.inflate(R.layout.cardview_ads,viewGroup,false);

        return new myviewholder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull myviewholder myviewholder, int i) {

        Ad ad=mData.get(i);
        myviewholder.title.setText(ad.getAdId());
        Picasso.with(this.mcontext).load(ad.getUrl()).centerCrop().resize(140,150).into(myviewholder.imag);

    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class myviewholder extends RecyclerView.ViewHolder
    {
        TextView title;
        ImageView imag;


        public myviewholder(@NonNull final View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.booktitle);
            imag=(ImageView)itemView.findViewById(R.id.bookimg);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int a=getAdapterPosition();
                    Ad ad=mData.get(a);
                    Intent intent=new Intent(mcontext.getApplicationContext(),AdDesc.class);
                    intent.putExtra("uid",curruid);
                    intent.putExtra("uid_img",ad.getUD());
                    intent.putExtra("adid",ad.getAdId());

                    itemView.getContext().startActivity(intent);
                }
            });


        }
    }
}
