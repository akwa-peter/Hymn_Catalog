package ng.com.rad5.hymncatalog;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

public class HymnAdapter extends RecyclerView.Adapter<HymnAdapter.ViewHolder> {
    List<Hymn> mHymns;
    Context context;

    public HymnAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public HymnAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.hym_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HymnAdapter.ViewHolder holder, int position) {
        final Hymn hymn = mHymns.get(position);
        holder.hymnTitle.setText(hymn.get_HymnTitle());
        holder.viewButton.setImageResource(R.drawable.ic_keyboard_arrow_right_black_24dp);

        //set onClick listener on each of the list item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                    Toast.makeText(context, "Click a list item at position " + getAdapterPosition(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, HymnDetails.class);
                intent.putExtra("intent", hymn.get_HymnTitle());
                intent.putExtra("hymnBody", hymn.get_Hymn());
                startActivity(context, intent, null);
            }
        });
    }

    void setHymns(List<Hymn> hymns){
        mHymns = hymns;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if (mHymns != null) {
            return mHymns.size();
        }
        else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView hymnTitle;
        public ImageView viewButton;

        public ViewHolder(View itemView) {
            super(itemView);
            hymnTitle = itemView.findViewById(R.id.txt_hymn_name);
            viewButton = itemView.findViewById(R.id.img_view);
        }
    }
}
