package com.example.albornoz_lab3tp1.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

// Importar el archivo generado de View Binding
import com.example.albornoz_lab3tp1.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // Declaro un objeto de la clase generada por View Binding
    private ActivityMainBinding binding;

    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inicializo el binding con el layout correspondiente
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        // En lugar de setContentView(R.layout.activity_main), uso:
        setContentView(binding.getRoot()); // binding.getRoot() obtiene la raíz de la vista inflada.

        // Inicializo el ViewModel
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MainActivityViewModel.class);

        // Inicializo los eventos de los botones
        inicializarViews();

        viewModel.getAviso().observe(this, s -> binding.tvAviso.setText(s));
        viewModel.getAvisoVisibility().observe(this, visibility -> binding.tvAviso.setVisibility(visibility));
    }

    private void inicializarViews() {
        binding.btIngresar.setOnClickListener(view -> {
            // Uso binding.etMail y binding.etPass para acceder a los EditText
            viewModel.Login(binding.etMail.getText().toString(), binding.etPass.getText().toString());
        });

        binding.btRegistrarse.setOnClickListener(view -> {
            // Iniciar form sin datos
            viewModel.Registrarse();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.etMail.setText("");
        binding.etPass.setText("");
        binding.tvAviso.setVisibility(View.INVISIBLE);
    }

    // Destruir el binding cuando la Activity se destruye para evitar fugas de memoria
    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
