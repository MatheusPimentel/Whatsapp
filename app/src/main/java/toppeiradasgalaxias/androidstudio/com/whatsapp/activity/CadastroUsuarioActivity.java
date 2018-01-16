package toppeiradasgalaxias.androidstudio.com.whatsapp.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import toppeiradasgalaxias.androidstudio.com.whatsapp.R;
import toppeiradasgalaxias.androidstudio.com.whatsapp.config.ConfiguracaoFirebase;
import toppeiradasgalaxias.androidstudio.com.whatsapp.helper.Base64Custom;
import toppeiradasgalaxias.androidstudio.com.whatsapp.model.Usuario;

public class CadastroUsuarioActivity extends AppCompatActivity {

    private EditText nome;
    private EditText email;
    private EditText senha;
    private Button cadastrar;
    private FirebaseAuth autenticacao;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        nome = findViewById(R.id.edit_nome);
        email = findViewById(R.id.edit_email);
        senha = findViewById(R.id.edit_senha);
        cadastrar = findViewById(R.id.button_cadastrar);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario = new Usuario();
                usuario.setNome(nome.getText().toString());
                usuario.setEmail(email.getText().toString());
                usuario.setSenha(senha.getText().toString());
                cadastrarUsuario();
            }
        });
    }

    private void cadastrarUsuario() {
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
        .addOnCompleteListener(CadastroUsuarioActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(CadastroUsuarioActivity.this, "Sucesso ao cadastrar usuário", Toast.LENGTH_SHORT).show();
                    FirebaseUser usuarioFirebase = task.getResult().getUser();
                    String identificadorUsuario = Base64Custom.codificarBase64(usuario.getEmail());
                    usuario.setId(identificadorUsuario);
                    usuario.salvar();

                    abrirUsuarioLogado();
                } else {
                    String excessao = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        excessao = "Digite uma senha mais forte, contendo mais caracteres e números";
                        e.printStackTrace();
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        excessao = "O e-mail digitado é inválido, digite um novo e-mail!";
                        e.printStackTrace();
                    } catch (FirebaseAuthUserCollisionException e) {
                        excessao = "Este e-mail já existe, digite um outro e-mail!";
                        e.printStackTrace();
                    } catch (Exception e) {
                        excessao = "Erro ao efetuar o cadastro";
                        e.printStackTrace();
                    }
                    Toast.makeText(CadastroUsuarioActivity.this, "Erro: " + excessao, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void abrirUsuarioLogado() {
        Intent intent = new Intent(CadastroUsuarioActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
