package sv.edu.udb.com.cuentame.Fragment;

import android.app.Fragment;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sv.edu.udb.com.cuentame.Adapter.StoryAdapter;
import sv.edu.udb.com.cuentame.Interface.GetDataService;
import sv.edu.udb.com.cuentame.Models.ResponseStory;
import sv.edu.udb.com.cuentame.Models.SectionsItem;
import sv.edu.udb.com.cuentame.Models.StoriesItem;
import sv.edu.udb.com.cuentame.Network.RetrofitClientInstance;
import sv.edu.udb.com.cuentame.Plain.Story;
import sv.edu.udb.com.cuentame.R;

public class ShowStory extends Fragment {

    View mView;
    RecyclerView recyclerView;
    List<SectionsItem> stories;
    StoryAdapter adapter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView =  inflater.inflate(R.layout.fragment_show_story, container, false);

        /**Recycler*/
        /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class );
        Call<ResponseStory> call = service.getStories();
        call.enqueue( new Callback<ResponseStory>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onResponse(Call <ResponseStory> call, Response<ResponseStory> response) {
                generateDataList(response.body().getData());
            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onFailure(Call <ResponseStory> call, Throwable t) {
                Toast.makeText( getContext(), "Parece que tenemos problemas, porfavor intente mas tarde", Toast.LENGTH_SHORT ).show();
            }
        } );



        /**Finish Recycler**/



      return mView;
    }



    @RequiresApi(api = Build.VERSION_CODES.M)
    private void generateDataList(List<StoriesItem> body) {
        recyclerView = mView.findViewById(R.id.mrecycler);
        adapter = new StoryAdapter(ShowStory.this.getContext(),body);                  //number of columns
        recyclerView.setLayoutManager(new GridLayoutManager(ShowStory.this.getContext(), 3 ));
        recyclerView.setAdapter(new AlphaInAnimationAdapter(adapter));



    }


}
