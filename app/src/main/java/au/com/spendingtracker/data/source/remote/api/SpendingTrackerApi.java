package au.com.spendingtracker.data.source.remote.api;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface SpendingTrackerApi {


    String API_BASE_URL = "https://api.test.org/3/";
    String REMOTE_DATA_URL = "https://www.dropbox.com/s/tewg9b71x0wrou9/data.json?dl=1";

    @GET
    Single<ResponseBody> downloadFileData(@Url String fileUrl);
}
