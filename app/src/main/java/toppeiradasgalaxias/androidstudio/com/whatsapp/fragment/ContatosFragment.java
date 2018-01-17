package toppeiradasgalaxias.androidstudio.com.whatsapp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import toppeiradasgalaxias.androidstudio.com.whatsapp.R;
import toppeiradasgalaxias.androidstudio.com.whatsapp.config.ConfiguracaoFirebase;
import toppeiradasgalaxias.androidstudio.com.whatsapp.helper.Preferencias;
import toppeiradasgalaxias.androidstudio.com.whatsapp.model.Contato;

public class ContatosFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter adapter;
    private ArrayList<String> contatos;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListenerContatos;

    public ContatosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListenerContatos);
        Log.i("ValueEventListener", "onStop");
    }

    @Override
    public void onStart() {
        super.onStart();
        firebase.addValueEventListener(valueEventListenerContatos);
        Log.i("ValueEventListener", "onStart");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contatos = new ArrayList<>();
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_contatos, container, false);

        listView = view.findViewById(R.id.lv_contatos);
        adapter = new ArrayAdapter(
                getActivity(),
                R.layout.lista_contato,
                contatos
        );
        listView.setAdapter(adapter);

        //recuperar contatos do firebase
        Preferencias preferencias = new Preferencias(getActivity());
        String identificadorUsuarioLogado = preferencias.getIdentificador();
        firebase = ConfiguracaoFirebase.getFirebase()
                .child("contatos")
                .child(identificadorUsuarioLogado);

        //listener para recuperar contatos
        valueEventListenerContatos = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //limpa lista
                contatos.clear();

                //listar contatos
                for (DataSnapshot dados: dataSnapshot.getChildren()) {
                    Contato contato = dados.getValue(Contato.class);
                    contatos.add(contato.getNome());
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        return view;
    }
}
