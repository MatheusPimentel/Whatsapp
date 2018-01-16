package toppeiradasgalaxias.androidstudio.com.whatsapp.helper;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Permissao {

    public static boolean validaPermissoes(int resquestCode, Activity activity, String[] permissoes) {

        if (Build.VERSION.SDK_INT >= 23) {
            List<String> listaPermissoes = new ArrayList<>();
            //percorre as permissões passadas, verificanco uma a uma se tem a permissão liberada
            for (String permissao : permissoes) {
                boolean validaPermissao = ContextCompat.checkSelfPermission(activity, permissao) == PackageManager.PERMISSION_GRANTED;

                if (!validaPermissao) {
                    listaPermissoes.add(permissao);
                }
            }

            if (listaPermissoes.isEmpty()) {
                return true;
            }

            //solicita permissoes
            String[] novasPermissoes = new String[listaPermissoes.size()];
            listaPermissoes.toArray(novasPermissoes);
            ActivityCompat.requestPermissions(activity, novasPermissoes, resquestCode);
        }
        return true;
    }
}
