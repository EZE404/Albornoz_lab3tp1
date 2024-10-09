package com.example.albornoz_lab3tp1.ui;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.albornoz_lab3tp1.modelo.Usuario;
import com.example.albornoz_lab3tp1.request.ApiClient;

public class FormularioUsuarioActivityViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<Usuario> usuarioMutable;

    public FormularioUsuarioActivityViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public LiveData<Usuario> getUsuario() {
        if (usuarioMutable == null) {
            usuarioMutable = new MutableLiveData<>();
        }
        return usuarioMutable;
    }

    public void setUsuario() {
        usuarioMutable.setValue(ApiClient.leer(context));
    }

    public void Guardar(Usuario u) {
        Usuario usuario = ApiClient.leer(context);
        if (usuario == null || !(u.getMail().equalsIgnoreCase(usuario.getMail()))) {
            ApiClient.guardar(context, u);

            Intent intent = new Intent(context, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Toast.makeText(context.getApplicationContext(), "Usuario registrado", Toast.LENGTH_SHORT).show();
            context.startActivity(intent);
        } else {
            Toast.makeText(context.getApplicationContext(), "El usuario ya existe", Toast.LENGTH_SHORT).show();
        }
    }

    public void Editar(Usuario u) {
        ApiClient.guardar(context, u);

        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Toast.makeText(context.getApplicationContext(), "Usuario Editado", Toast.LENGTH_SHORT).show();
        context.startActivity(intent);
    }
}
