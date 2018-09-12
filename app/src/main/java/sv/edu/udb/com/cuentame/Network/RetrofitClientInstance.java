package sv.edu.udb.com.cuentame.Network;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {

    private static Retrofit retrofit;


    //private static final String BASE_URL = "http://demo8434239.mockable.io";
    //private static final String BASE_URL = "https://api-coupons1.herokuapp.com";

    private static final String BASE_URL = "http://demo7450099.mockable.io/";


    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}