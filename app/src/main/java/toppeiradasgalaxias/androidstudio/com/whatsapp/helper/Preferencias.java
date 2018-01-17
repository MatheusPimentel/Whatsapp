package toppeiradasgalaxias.androidstudio.com.whatsapp.helper;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class Preferencias {

    private Context context;
    private SharedPreferences preferences;
    private static final String NOME_ARQUIVO = "whatsapp.preferencias";
    private static final int MODE = 0;
    private SharedPreferences.Editor editor;
    private static final String CHAVE_IDENTIFICADOR = "identificadorUsuarioLogado";

    public Preferencias() {
    }

    public Preferencias(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(NOME_ARQUIVO, MODE);
        editor = preferences.edit();
    }

    public void salvarDados(String identificadorUsuario) {
        editor.putString(CHAVE_IDENTIFICADOR, identificadorUsuario);
        editor.commit();
    }
    public String getIdentificador() {
        return preferences.getString(CHAVE_IDENTIFICADOR, null);
    }

}
