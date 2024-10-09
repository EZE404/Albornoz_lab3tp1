package com.example.albornoz_lab3tp1.request;

import android.content.Context;
import android.widget.Toast;

import com.example.albornoz_lab3tp1.modelo.Usuario;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ApiClient {

    private static final String FILE_NAME = "usuario.dat";

    public static void guardar(Context context, Usuario usuario) {
        // Al abrir el file stream en la cláusula del try, el flush se llama implícitamente al terminar el bloque
        // Aprendido del "using" en C#
        try (FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(usuario);
            //Toast.makeText(context.getApplicationContext(), "Usuario guardado", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context.getApplicationContext(), "Error al guardar el usuario", Toast.LENGTH_LONG).show();
        }
    }

    public static Usuario leer(Context context) {
        Usuario usuario = null;
        try (FileInputStream fis = context.openFileInput(FILE_NAME);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            usuario = (Usuario) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            //Toast.makeText(context.getApplicationContext(), "Error al leer el usuario", Toast.LENGTH_LONG).show();
        }
        return usuario;
    }

    public static Usuario login(Context context, String mail, String pass) {
        Usuario usuario = leer(context);

        if (usuario != null && mail.equals(usuario.getMail()) && pass.equals(usuario.getPassword())) {
            return usuario;
        } else {
            //Toast.makeText(context.getApplicationContext(), "Datos Invalidos", Toast.LENGTH_LONG).show();
            return null;
        }
    }
}
