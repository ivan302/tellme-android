package sv.edu.udb.com.cuentame.Interface;

import retrofit2.Call;
import retrofit2.http.GET;
import sv.edu.udb.com.cuentame.Models.ResponseStory;

public interface GetDataService {

    @GET("/tellme/public/api/stories")
    Call<ResponseStory> getStories();


}