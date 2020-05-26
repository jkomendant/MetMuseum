package komendant.met.museum;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MetMuseumServiceFactory {

    public MetMuseumService getInstance(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://collectionapi.metmuseum.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MetMuseumService service = retrofit.create((MetMuseumService.class));

        return  service;
    }
}
