package plomba.com.br.analisadorprovas;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProvaActivity extends AppCompatActivity {

    LinearLayout linLayoutVProva, linLayoutHProva, linLayoutVQuestoes;
    JSONObject provas;
    TextView questao, opcaoEscolhida, opcaoCerta;
    int screenWidth, screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prova);

        linLayoutVProva = findViewById(R.id.prova);
        linLayoutVQuestoes = new LinearLayout(this);
        linLayoutVQuestoes.setOrientation(LinearLayout.VERTICAL);

        try{
            provas = new JSONObject(getIntent().getStringExtra("json"));
            JSONArray feedback = provas.getJSONArray("Feedback");

            TextView errorProva = findViewById(R.id.errorProva);
            linLayoutVProva.removeView(errorProva);

            linLayoutHProva = new LinearLayout(this);
            linLayoutHProva.setOrientation(LinearLayout.HORIZONTAL);

            questao = new TextView(this);
            questao.setText("Questão");
            questao.setTextSize(20);
            questao.setGravity(Gravity.CENTER);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    0, LinearLayout.LayoutParams.WRAP_CONTENT,1f
            );

            questao.setLayoutParams(params);
            linLayoutHProva.addView(questao);

            opcaoCerta = new TextView(this);
            opcaoCerta.setText("Correta");
            opcaoCerta.setTextSize(20);
            opcaoCerta.setGravity(Gravity.CENTER);
            opcaoCerta.setLayoutParams(params);
            linLayoutHProva.addView(opcaoCerta);

            opcaoEscolhida = new TextView(this);
            opcaoEscolhida.setText("Assinalada");
            opcaoEscolhida.setGravity(Gravity.CENTER);
            opcaoEscolhida.setTextSize(20);
            opcaoEscolhida.setLayoutParams(params);
            linLayoutHProva.addView(opcaoEscolhida);

            linLayoutVProva.addView(linLayoutHProva);

            ScrollView scrollView = new ScrollView(this);

            for(int i = 0 ; i < feedback.length() ; i++){

                linLayoutHProva = new LinearLayout(this);
                linLayoutHProva.setOrientation(LinearLayout.HORIZONTAL);

                questao = new TextView(this);
                questao.setText(feedback.getJSONObject(i).getString("Question"));
                questao.setTextSize(20);
                questao.setGravity(Gravity.CENTER);

                params = new LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.WRAP_CONTENT,1f
                );

                questao.setLayoutParams(params);
                linLayoutHProva.addView(questao);

                opcaoCerta = new TextView(this);
                opcaoCerta.setText("A");
                opcaoCerta.setTextSize(20);
                opcaoCerta.setGravity(Gravity.CENTER);
                opcaoCerta.setLayoutParams(params);
                linLayoutHProva.addView(opcaoCerta);

                opcaoEscolhida = new TextView(this);
                opcaoEscolhida.setText(feedback.getJSONObject(i).getString("Answer"));
                opcaoEscolhida.setGravity(Gravity.CENTER);
                opcaoEscolhida.setTextSize(20);
                opcaoEscolhida.setLayoutParams(params);
                linLayoutHProva.addView(opcaoEscolhida);

                linLayoutVQuestoes.addView(linLayoutHProva);
            }

            scrollView.addView(linLayoutVQuestoes);
            linLayoutVProva.addView(scrollView);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getScreenSizes(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
    }
}
