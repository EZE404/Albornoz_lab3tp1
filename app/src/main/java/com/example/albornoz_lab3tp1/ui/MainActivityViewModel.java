package com.example.albornoz_lab3tp1.ui;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.albornoz_lab3tp1.modelo.Usuario;
import com.example.albornoz_lab3tp1.request.ApiClient;

public class MainActivityViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<String> avisoMutable;
    private MutableLiveData<Integer> avisoVisibilityMutable;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public LiveData<String> getAviso() {
        if (avisoMutable == null) {
            avisoMutable = new MutableLiveData<>();
        }
        return avisoMutable;
    }

    public LiveData<Integer> getAvisoVisibility() {
        if (avisoVisibilityMutable == null) {
            avisoVisibilityMutable = new MutableLiveData<>();
        }
        return avisoVisibilityMutable;
    }

    public void Login(String mail, String pass) {
        Usuario u = ApiClient.login(context, mail, pass);
        if (u == null) {
            avisoMutable.setValue("Email o usuario incorrecto");
            avisoVisibilityMutable.setValue(View.VISIBLE);
        } else {
            // Cambiamos la Activity a FormularioUsuarioActivity con modo edición
            Intent intent = new Intent(context, FormularioUsuarioActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("modo", "editar");  // Indicamos que está en modo edición
            context.startActivity(intent);
        }
    }

    public void Registrarse() {
        Intent intent = new Intent(context, FormularioUsuarioActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("modo", "registrar");  // Indicamos que está en modo registro
        context.startActivity(intent);
    }
}
