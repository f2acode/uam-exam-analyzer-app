package plomba.com.br.analisadorprovas;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class GaleriaActivity extends AppCompatActivity {

    Helper helper = new Helper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria);

        try{
            String path;
            if(helper.isEmulator())
                path = getFilesDir().toString();
            else
                path = getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString();

            File directory = new File(path);

            LinearLayout llVerticalGaleria = (LinearLayout) findViewById(R.id.ll_vertical_galeria);

            File[] files = directory.listFiles();

            // getting screen sizes
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;

            for(int i = 0; i < files.length; i ++){

                // creating the newLayoutHorizontal for each new line
                LinearLayout llHorizontalGaleria = new LinearLayout(this);
                llHorizontalGaleria.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        height/7
                ));

                llHorizontalGaleria.setOrientation(LinearLayout.HORIZONTAL);
                llHorizontalGaleria.setPadding(3, 3, 3, 3);

                // creating imageView
                ImageView newImgView = new ImageView(this);
                newImgView.setLayoutParams(new LinearLayout.LayoutParams(
                        width/5,
                        LinearLayout.LayoutParams.MATCH_PARENT
                ));

                Bitmap bmp = BitmapFactory.decodeFile(files[i].getAbsolutePath());

                newImgView.setImageBitmap(bmp);
                llHorizontalGaleria.addView(newImgView);

                // creating txtView
                TextView newTxtView = new TextView(this);
                newTxtView.setText(files[i].getName());
                LinearLayout.LayoutParams layoutParamsTxtView = new LinearLayout.LayoutParams(
                        (width/5)*4,
                        LinearLayout.LayoutParams.MATCH_PARENT
                );

                // converting pixels to DIP
                Resources r = getResources();
                float pxLeftMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, r.getDisplayMetrics());
                layoutParamsTxtView.setMargins(Math.round(pxLeftMargin), 0, 0, 0);

                newTxtView.setLayoutParams(layoutParamsTxtView);

                llHorizontalGaleria.addView(newTxtView);

                llVerticalGaleria.addView(llHorizontalGaleria);
            }
        }catch(Exception ex){
            CharSequence text = ex.getMessage();
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(this, text, duration);
            toast.show();
        }

    }
}
