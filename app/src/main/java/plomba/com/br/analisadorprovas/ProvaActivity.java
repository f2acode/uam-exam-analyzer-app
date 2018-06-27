package plomba.com.br.analisadorprovas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ProvaActivity extends AppCompatActivity {

    String prova;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prova);

        prova = getIntent().getExtras().get("json").toString();
        TextView errorProva = findViewById(R.id.errorProva);
        errorProva.setText(prova);
    }
}
