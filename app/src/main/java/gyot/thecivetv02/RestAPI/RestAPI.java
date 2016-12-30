package gyot.thecivetv02.RestAPI;

import java.util.List;

import gyot.thecivetv02.model.Models;
import gyot.thecivetv02.model.order;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by gyot on 06/12/16.
 */

public interface RestAPI {
    @GET("menu")
    Call<List<Models>> getMenu();
    @FormUrlEncoded
    @POST("order.php")
    Call<order> ORDER_CALL(@Field("id") int id, @Field("price") int price,@Field("qty") int qty, @Field("user") String user,@Field("table") String table);
}
