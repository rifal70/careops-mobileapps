package com.digimaster.digicourse.digicyber.apiHelper;

/**
 * Created by Teke on 01/11/2017.
 */

public class UtilsApi {
    // http://52.40.55.251/ellaundry/APIv2/index.php/auth

    public static final String BASE_URL_API = "http://54.251.83.205/api_careops/";

    // Mendeklarasikan Interface BaseApiService
    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}
