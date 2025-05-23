package org.utl.ejemploretrofit;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import org.utl.ejemploretrofit.api.CharacterApiService;
import org.utl.ejemploretrofit.model.ApiResponse;
import org.utl.ejemploretrofit.model.Character;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListCharacters extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Character> data;
    org.utl.ejemploretrofit.adapter.ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_characters);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        data = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new org.utl.ejemploretrofit.adapter.ListAdapter(data);
        recyclerView.setAdapter(adapter);

        getAllCharacters();
    }

    private void getAllCharacters() {
        String url_base = "https://dragonball-api.com/api/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url_base)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CharacterApiService service = retrofit.create(CharacterApiService.class);
        Call<ApiResponse> call = service.getAll();

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.clear();
                    data.addAll(response.body().getItems());
                    adapter.notifyDataSetChanged();
                } else {
                    //en caso de haber un error en la respuesta
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                System.out.println(t.getMessage());
                System.out.println("Error al hacer la peticion");
            }
        });
    }
}