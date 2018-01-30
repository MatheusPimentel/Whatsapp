package toppeiradasgalaxias.androidstudio.com.whatsapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import toppeiradasgalaxias.androidstudio.com.whatsapp.R;
import toppeiradasgalaxias.androidstudio.com.whatsapp.helper.Preferencias;
import toppeiradasgalaxias.androidstudio.com.whatsapp.model.Mensagem;

public class MensagemAdapter extends ArrayAdapter<Mensagem> {

    private Context context;
    private ArrayList<Mensagem> mensagens;

    public MensagemAdapter(@NonNull Context context, @NonNull ArrayList<Mensagem> objects) {
        super(context, 0, objects);
        this.context = context;
        this.mensagens = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

//        if (mensagens == null) {
//            return super.getView(position, convertView, parent);
//        }
        View view = null;

        if (mensagens != null) {

            //recupera dados do usuario remetente
            Preferencias preferencias = new Preferencias(context);
            String idUsuarioRemetente = preferencias.getIdentificador();

            //inicializa o objeto para montagem da view
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            //recupera mensagem
            Mensagem mensagem = mensagens.get(position);

            //monta a view a partir de um xml
            if (idUsuarioRemetente.equals(mensagem.getIdUsuario())) {
                view = inflater.inflate(R.layout.item_mensagem_direira, parent, false);
            } else {
                view = inflater.inflate(R.layout.item_mensagem_esquerda, parent, false);
            }

            //recupera elemento para exibição
            TextView textoMensagem = (TextView) view.findViewById(R.id.tv_mensagem);
            textoMensagem.setText(mensagem.getMensagem());
        }
        return view;
    }
}
