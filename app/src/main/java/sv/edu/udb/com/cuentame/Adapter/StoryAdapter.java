package sv.edu.udb.com.cuentame.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

import sv.edu.udb.com.cuentame.Models.StoriesItem;
import sv.edu.udb.com.cuentame.R;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.StoryViewHolder> {


    List<StoriesItem> stories;
    Context context;
    private onItemClickListener listener;


    public StoryAdapter(Context context, List<StoriesItem> stories,onItemClickListener listener) {
        this.context = context;
        this.stories = stories;
        this.listener = listener;
    }



    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_row, parent, false);
        return new StoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryViewHolder holder, final int i) {
        holder.txtTitle.setText(stories.get(i).getName());


try {
    Picasso.Builder builder = new Picasso.Builder(context);
    builder.downloader(new OkHttp3Downloader(context));
    builder.build().load("http://ec2-54-244-63-119.us-west-2.compute.amazonaws.com/tellme/public/images/" + stories.get(i).getUrl())
            .placeholder((R.drawable.ic_launcher_background))
            .error(R.drawable.ic_launcher_background)
            .into(holder.spacePhoto);
}catch (Exception e){
    Toast.makeText(context, "Please wait.." + e.getMessage(), Toast.LENGTH_SHORT).show();
}

        holder.spacePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    listener.onItemClick(stories.get(i), i);
                }catch (Exception e){
                    Toast.makeText(context, "StoryAdapter" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    @Override
    public int getItemCount() {

        return stories.size();
    }

    public class StoryViewHolder extends RecyclerView.ViewHolder {
        View mView;

        TextView txtTitle;
        ImageView spacePhoto;


        public StoryViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

            txtTitle = mView.findViewById(R.id.name);
            spacePhoto = mView.findViewById(R.id.mimage);




        }
    }

    // Clean all elements of the recycler
    public void clear() {
        stories.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<StoriesItem> list) {
        stories.addAll(list);
        notifyDataSetChanged();
    }

    ///declaramos las interfaces con los metodos a implementar
    public interface onItemClickListener{
        void onItemClick(StoriesItem story, int position);
    }
}
