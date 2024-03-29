package plomba.com.br.analisadorprovas;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

public class GaleriaActivity extends AppCompatActivity {

    Helper helper = new Helper();
    final HttpRequestHelper httpRequestHelper = new HttpRequestHelper();
    File[] imageFiles;
    LinearLayout linearLayoutGaleria;
    int screenWidth;
    int screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria);

        try{
            linearLayoutGaleria = (LinearLayout) findViewById(R.id.ll_vertical_galeria);
            getImagesFromDirectory();
            getScreenSizes();

            getDefaultExamImage();

            for(int i = 0; i < imageFiles.length; i ++){

                LinearLayout linhaGaleria = new LinearLayout(this);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        screenHeight/7
                );
                layoutParams.setMargins(10, 10, 10, 10);
                linhaGaleria.setLayoutParams(layoutParams);
                linhaGaleria.setOrientation(LinearLayout.HORIZONTAL);
                linhaGaleria.setPadding(10, 10, 10, 10);
                linhaGaleria.setBackgroundColor(Color.WHITE);

                linhaGaleria.addView(getFileToImageView(imageFiles[i]));
                linhaGaleria.addView(getTextToTextView(imageFiles[i]));

                // Button
                FileInputStream fileInputStream = new FileInputStream(imageFiles[i]);
                long byteLength = imageFiles[i].length();
                byte[] filecontent = new byte[(int) byteLength];
                fileInputStream.read(filecontent, 0, (int) byteLength);
                String imageBase64 = Base64.encodeToString(filecontent, Base64.DEFAULT);
                linhaGaleria.addView(getSendButton(imageBase64));

                linearLayoutGaleria.addView(linhaGaleria);
            }
        }catch(Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    public void getDefaultExamImage(){
        LinearLayout linhaGaleria = new LinearLayout(this);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                screenHeight/7
        );
        layoutParams.setMargins(10, 10, 10, 10);
        linhaGaleria.setLayoutParams(layoutParams);
        linhaGaleria.setOrientation(LinearLayout.HORIZONTAL);
        linhaGaleria.setPadding(10, 10, 10, 10);
        linhaGaleria.setBackgroundColor(Color.WHITE);

        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(
                screenWidth/5,
                LinearLayout.LayoutParams.MATCH_PARENT
        ));
        imageView.setImageResource(R.mipmap.simulado_enem);

        linhaGaleria.addView(imageView);

        TextView textView = new TextView(this);
        textView.setText("Título da prova");
        LinearLayout.LayoutParams layoutParamsTxtView = new LinearLayout.LayoutParams(
                (screenWidth/5)*2,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        Resources r = getResources();
        float pxLeftMargin = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 10, r.getDisplayMetrics()
        );
        layoutParamsTxtView.setMargins(Math.round(pxLeftMargin), 0, 0, 0);
        textView.setLayoutParams(layoutParamsTxtView);

        linhaGaleria.addView(textView);

        //Get image (OMG)
        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.mipmap.simulado_enem);
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        byte[] bitMapData = stream.toByteArray();

        String base64 = Base64.encodeToString(bitMapData, Base64.NO_WRAP);
        linhaGaleria.addView(getSendButton(base64));

        linearLayoutGaleria.addView(linhaGaleria);
    }

    public void getImagesFromDirectory(){
        String imagesPath = helper.isEmulator() ? getFilesDir().toString():
            getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString();

        File directory = new File(imagesPath);
        imageFiles = directory.listFiles();
    }

    public void getScreenSizes(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
    }

    public ImageView getFileToImageView(File image){
        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(
                screenWidth/5,
                LinearLayout.LayoutParams.MATCH_PARENT
        ));
        Bitmap bmp = BitmapFactory.decodeFile(image.getAbsolutePath());
        imageView.setImageBitmap(bmp);

        return imageView;
    }

    public TextView getTextToTextView(File image){
        TextView textView = new TextView(this);
        textView.setText(image.getName());
        LinearLayout.LayoutParams layoutParamsTxtView = new LinearLayout.LayoutParams(
                (screenWidth/5)*2,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        // converting pixels to DIP
        Resources r = getResources();
        float pxLeftMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, r.getDisplayMetrics());

        // set some properties about txtView using the above and insert
        layoutParamsTxtView.setMargins(Math.round(pxLeftMargin), 0, 0, 0);
        textView.setLayoutParams(layoutParamsTxtView);

        return textView;
    }

    public Button getSendButton(final String imgBase64){
        Button btnSend = new Button(this);
        btnSend.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        btnSend.setTextColor(getResources().getColor(R.color.white));
        btnSend.setText("Processar");
        btnSend.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                linearLayoutGaleria.removeAllViews();
                ProgressBar loading = new ProgressBar(getBaseContext());
                linearLayoutGaleria.addView(loading);

                httpRequestHelper.postFoto(new HttpRequestHelper.VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        Intent provaIntent = new Intent(getBaseContext(), ProvaActivity.class);
                        provaIntent.putExtra("json", response.toString());
                        startActivity(provaIntent);
                        //Toast.makeText(GaleriaActivity.this, "Object: " + response.toString(), Toast.LENGTH_LONG).show();
                    }

                    public void onSuccess(JSONArray response){
                        Toast.makeText(GaleriaActivity.this, "Array: " + response.toString(), Toast.LENGTH_LONG).show();
                    }
                }, getBaseContext(), imgBase64);
            }
        });

        return btnSend;
    }
}
