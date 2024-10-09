package com.example.albornoz_lab3tp1.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.albornoz_lab3tp1.databinding.ActivityFormularioUsuarioBinding;
import com.example.albornoz_lab3tp1.modelo.Usuario;

public class FormularioUsuarioActivity extends AppCompatActivity {

    private FormularioUsuarioActivityViewModel viewModel;
    private ActivityFormularioUsuarioBinding binding;
    private boolean esEdicion; // Esta variable se obtiene del Intent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFormularioUsuarioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication())
                .create(FormularioUsuarioActivityViewModel.class);

        // Obtener el extra del Intent para determinar si es edición o registro
        Intent intent = getIntent();
        String modo = intent.getStringExtra("modo");

        if ("editar".equals(modo)) {
            esEdicion = true;
        } else if ("registrar".equals(modo)) {
            esEdicion = false;
        }

        // Observador para actualizar la vista con el Usuario si es edición
        // Utilizo lambdas porque las clases anónimas internas son un dolor de ojos
        viewModel.getUsuario().observe(this, usuario -> {
            if (usuario != null && esEdicion) {
                binding.etRegdni.setText(String.valueOf(usuario.getDni()));
                binding.etRegAp.setText(usuario.getApellido());
                binding.etRegNom.setText(usuario.getNombre());
                binding.etRegMail.setText(usuario.getMail());
                binding.etRegPass.setText(usuario.getPassword());
            }
        });

        // Acción del botón Guardar/Registrar
        binding.btRegGuardar.setOnClickListener(v -> {
            int dni = Integer.parseInt(binding.etRegdni.getText().toString());
            String nombre = binding.etRegNom.getText().toString();
            String apellido = binding.etRegAp.getText().toString();
            String mail = binding.etRegMail.getText().toString();
            String pass = binding.etRegPass.getText().toString();

            Usuario usuario = new Usuario(dni, nombre, apellido, mail, pass);

            if (esEdicion) {
                viewModel.Editar(usuario);  // Guardar los cambios del usuario
            } else {
                viewModel.Guardar(usuario);  // Registrar un nuevo usuario
            }
        });

        if (esEdicion) {
            viewModel.setUsuario();  // Cargar datos del usuario existente
            binding.btRegGuardar.setText("Guardar Cambios");
            setTitle("Editar Usuario");
        } else {
            binding.btRegGuardar.setText("Registrar Usuario");
            setTitle("Registrar Usuario");
        }
    }
}
