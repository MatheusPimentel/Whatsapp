package toppeiradasgalaxias.androidstudio.com.whatsapp.helper;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class Preferencias {

    private Context context;
    private SharedPreferences preferences;
    private static final String NOME_ARQUIVO = "whatsapp.preferencias";
    private static final int MODE = 0;
    private static final String CHAVE_NOME = "nome";
    private static final String CHAVE_TELEFONE = "telefone";
    private static final String CHAVE_TOKEN = "token";
    private SharedPreferences.Editor editor;

    public Preferencias() {
    }

    public Preferencias(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(NOME_ARQUIVO, MODE);
        editor = preferences.edit();
    }

    public void salvarUsuariosPreferencias(String nome, String telefone, String token) {
        editor.putString(CHAVE_NOME, nome);
        editor.putString(CHAVE_TELEFONE, telefone);
        editor.putString(CHAVE_TOKEN, token);
        editor.commit();
    }

    public HashMap<String, String> getDadosUsuario() {
        HashMap<String, String> dadosUsuario = new HashMap<>();
        dadosUsuario.put(CHAVE_NOME, preferences.getString(CHAVE_NOME, null));
        dadosUsuario.put(CHAVE_TELEFONE, preferences.getString(CHAVE_TELEFONE, null));
        dadosUsuario.put(CHAVE_TOKEN, preferences.getString(CHAVE_TOKEN, null));
        return dadosUsuario;
    }
}
