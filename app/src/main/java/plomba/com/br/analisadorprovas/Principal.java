package plomba.com.br.analisadorprovas;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Principal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
    }

    static final int REQUEST_TAKE_PHOTO = 1;
    Uri photoURI;

    public void tirarFoto(View view){
        dispararIntentTirarFoto();
    }

    private void dispararIntentTirarFoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // ensures that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // creates the File where the photo should go
            File photoFile = null;
            try{
                photoFile = createImageFile();
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

        ImageView imageView = (ImageView) findViewById(R.id.img_view);

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {

            Uri uri = photoURI;

            Bitmap imageBitmap = BitmapFactory.decodeFile(uri.getPath());
            imageView.setImageBitmap(imageBitmap);
        }
    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException{
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

}
