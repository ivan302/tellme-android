package sv.edu.udb.com.cuentame.Interface;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import sv.edu.udb.com.cuentame.Plain.Story;

public interface GetDataService {

    @GET("data")
    Call<List<Story>> getData();


}