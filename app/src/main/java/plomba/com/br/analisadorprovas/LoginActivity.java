package plomba.com.br.analisadorprovas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void logar(View view){
        etEmail = findViewById(R.id.email);
        etSenha = findViewById(R.id.senha);

        String email = etEmail.getText().toString();
        String senha = etSenha.getText().toString();

        if(!email.contains("augustto.felipe@hotmail.com") || !senha.contains("senha123")){
            Toast.makeText(this, "Email ou senha incorretos", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, Principal.class);
        startActivity(intent);
        finish();
    }

    public void pularLogin(View view){
        Intent intent = new Intent(this, Principal.class);
        startActivity(intent);
        finish();
    }

    public void registrar(View view){
        Intent intent = new Intent(this, RegistroActivity.class);
        startActivity(intent);
    }
}
