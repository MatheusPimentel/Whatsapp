package toppeiradasgalaxias.androidstudio.com.whatsapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import toppeiradasgalaxias.androidstudio.com.whatsapp.R;
import toppeiradasgalaxias.androidstudio.com.whatsapp.config.ConfiguracaoFirebase;
import toppeiradasgalaxias.androidstudio.com.whatsapp.model.Usuario;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText senha;
    private Button logar;
    private Usuario usuario;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        verificarUsuarioLogado();

        email = findViewById(R.id.edit_email);
        senha = findViewById(R.id.edit_senha);
        logar = findViewById(R.id.button_logar);

        logar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario = new Usuario();
                usuario.setEmail(email.getText().toString());
                usuario.setSenha(senha.getText().toString());

                validarLogin();
            }
        });

    }

    private void verificarUsuarioLogado() {
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.getCurrentUser();

        if (autenticacao.getCurrentUser() != null) {
            abrirTelaPrincipal();
        }
    }

    private void validarLogin() {
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Sucesso ao realizar o Login", Toast.LENGTH_SHORT).show();
                    abrirTelaPrincipal();
                } else {
                    Toast.makeText(LoginActivity.this, "Erro ao realizar o Login", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void abrirTelaPrincipal() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void abrirCadastroUsuario(View v) {
        Intent intent = new Intent(LoginActivity.this, CadastroUsuarioActivity.class);
        startActivity(intent);
    }
}
