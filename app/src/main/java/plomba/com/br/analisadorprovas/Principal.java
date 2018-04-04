package plomba.com.br.analisadorprovas;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Principal extends AppCompatActivity {

    static final int REQUEST_TAKE_PHOTO = 1;
    Uri photoURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
    }

    public void dispararIntentTirarFoto(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // ensures that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // creates the File where the photo should go
            File photoFile = null;
            try{
                photoFile = criarArquivoImagem();
            }catch(IOException ex){
                // error occurred while creating the file
                ex.printStackTrace();
            }
            // continues only if the file was successfully created
            if(photoFile != null){
                // Here we don't receive data on onActivityResult - because of the EXTRA_OUTPUT
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));

                photoURI = Uri.fromFile(photoFile);
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {

            //ImageView imageView = (ImageView) findViewById(R.id.img_view);

            //Uri uri = photoURI;
            //Bitmap imageBitmap = BitmapFactory.decodeFile(uri.getPath());
            //imageView.setImageBitmap(imageBitmap);
        }
    }

    private File criarArquivoImagem() throws IOException{

        String mCurrentPhotoPath;

        // create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName, /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
        );

        // save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public void abrirGaleria(View view){
        Intent intent = new Intent(this, GaleriaActivity.class);
        startActivity(intent);
    }

    public void abrirEstatisticas(View view){
        Intent intent = new Intent(this, EstatisticasActivity.class);
        startActivity(intent);
    }
}
