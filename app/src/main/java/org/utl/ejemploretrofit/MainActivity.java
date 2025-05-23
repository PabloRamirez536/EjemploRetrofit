package org.utl.ejemploretrofit;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import org.utl.ejemploretrofit.api.CharacterApiService;
import org.utl.ejemploretrofit.model.ApiResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import org.utl.ejemploretrofit.model.Character;

public class MainActivity extends AppCompatActivity {
    Button btnCall, btnAtras;

    ImageView imgCharacter;

    TextView txtName, txtDescripcion, txtGenero;
    List<Character> lista = new ArrayList<>();

    int contador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getAllCharacters();
        btnCall = findViewById(R.id.btnCall);
        btnAtras = findViewById(R.id.btnAtras);
        txtName = findViewById(R.id.txtName);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        imgCharacter = findViewById(R.id.imgCharacter);
        txtGenero = findViewById(R.id.txtGenero);
        btnCall.setOnClickListener(view ->{
            if (contador < lista.size() - 1) {
                contador++;
                showCharacter(contador);
            } else {
                contador = 0;
                showCharacter(contador);
            }

        });

        btnAtras.setOnClickListener(view ->{
            if (contador == 0) {
                contador = lista.size() - 1;
            }else{
                contador--;
            }
            showCharacter(contador);
        });
    }

    private void showImgae(String urlImage){
        Glide.with(this)
                .load(urlImage)
                .into(imgCharacter);

    }

    private void showCharacter(int pos){
        Character c = lista.get(pos);
        showImgae(c.getImage());
        txtName.setText(c.getName());
        txtDescripcion.setText(c.getDescription());
        txtGenero.setText(c.getGender());
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
                System.out.println(response.message());
                System.out.println(response.body().toString());
                if (response.code() == 200){
                    //Ejecutar el flijo normal
                    lista = response.body().getItems();
                    lista.forEach(character -> {
                        System.out.println(character.getName());
                    });
                }else{
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