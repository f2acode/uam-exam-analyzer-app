package plomba.com.br.analisadorprovas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class EstatisticasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estatisticas);

        TextView tv_fotos_tiradas = (TextView) findViewById(R.id.tv_fotos_tiradas);
        TextView tv_fotos_enviadas = (TextView) findViewById(R.id.tv_fotos_enviadas);

    }
}
