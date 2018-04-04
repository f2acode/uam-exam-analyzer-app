package plomba.com.br.analisadorprovas;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class GaleriaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria);

        String path = Environment.getExternalStorageDirectory()
                .toString();

        Log.i("Path", path);
    }
}
