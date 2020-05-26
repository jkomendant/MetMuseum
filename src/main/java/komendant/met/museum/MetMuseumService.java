package komendant.met.museum;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MetMuseumService {
    @GET("/public/collection/v1/departments")
    Call<MetMuseum.DepartmentList> getDepartments();

    @GET("/public/collection/v1/objects")
    Call<MetMuseum.DepartmentObjects> getDepartmentObjects(@Query("departmentIds")int departmentId);

    @GET("/public/collection/v1/objects/{objectID}")
    Call<MetMuseum.Object> getObject(@Path("objectID")int objectID);
}
