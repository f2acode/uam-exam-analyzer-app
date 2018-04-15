package plomba.com.br.analisadorprovas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class EstatisticasActivity extends AppCompatActivity {

    TextView tv_fotos_tiradas, tv_fotos_enviadas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estatisticas);

        tv_fotos_tiradas = (TextView) findViewById(R.id.tv_fotos_tiradas);
        tv_fotos_enviadas = (TextView) findViewById(R.id.tv_fotos_enviadas);

        HttpRequestHelper httpRequestHelper = new HttpRequestHelper();

        httpRequestHelper.listarEstatisticas(
            new HttpRequestHelper.VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) {}

                public void onSuccess(JSONArray response){
                    //executa a ação aqui com o response obtido
                    Log.i("OI",response.toString());
                    tv_fotos_tiradas.setText(response.toString());
                    tv_fotos_enviadas.setText(response.toString());
                }
            }, this
        );

    }
}
