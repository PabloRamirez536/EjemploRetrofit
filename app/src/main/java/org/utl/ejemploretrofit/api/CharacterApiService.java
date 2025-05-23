package org.utl.ejemploretrofit.api;

import org.utl.ejemploretrofit.model.ApiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CharacterApiService {
    @GET("characters?page=1")
    Call<ApiResponse> getAll();


}
