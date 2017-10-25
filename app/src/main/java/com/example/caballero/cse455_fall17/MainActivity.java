package com.example.caballero.cse455_fall17;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.media.ExifInterface;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    ImageView imgPicture;
    TextView OCRTextView;

    public static final int IMAGE_GALLERY_REQUEST = 2;
    public static final int CAMERA_REQUEST = 1;

    List<String> list = new ArrayList<>();
    final StringBuilder stringBuilder = new StringBuilder();

    Bitmap image;
    String mCurrentPhotoPath;
    TextManager txt;

    //for the drawer added by Carlos
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //for the drawer added by Carlos
        mDrawerLayout=(DrawerLayout) findViewById(R.id.drawer);
        mToggle=new ActionBarDrawerToggle(MainActivity.this,mDrawerLayout,R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        OCRTextView = (TextView) findViewById(R.id.ocrtext);
        imgPicture = (ImageView) findViewById(R.id.imageView);
        FloatingActionButton gallery = (FloatingActionButton) findViewById(R.id.floatingGallery);
        FloatingActionButton camera = (FloatingActionButton) findViewById(R.id.floatingCamera);

        requestWrite();
        populateList();

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callGallery();
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCamera();
            }
        });

        //click OCR text to find text
        OCRTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoFind();
            }
        });

        //click picture to reset text
        imgPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OCRTextView.setText(stringBuilder.toString());
            }
        });
    }
    //for the drawer to pop out added by Carlos
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //ask user permission for external storage
    public void requestWrite(){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);
            }
        }
    }

    //open gallery and wait for result
    public void callGallery() {
        // invoke the image gallery using an implict intent.
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);

        // where do we want to find the data?
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDirectory.getPath();
        // finally, get a URI representation
        Uri data = Uri.parse(pictureDirectoryPath);

        // set the data and type.  Get all image types.
        photoPickerIntent.setDataAndType(data, "image/*");

        // we will invoke this activity, and get something back from it.
        startActivityForResult(photoPickerIntent, IMAGE_GALLERY_REQUEST);
    }

    //open camera and wait
    void startCamera() {
        try {
            dispatchTakePictureIntent();
        } catch (IOException e) {
        }
    }

    //take picture save to path and wait for result
    private void dispatchTakePictureIntent() throws IOException {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                return;
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(MainActivity.this,
                        BuildConfig.APPLICATION_ID + ".provider",
                        createImageFile());
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST);
            }
        }
    }

    //get results, process and display them
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        InputStream inputStream = null;
        if(resultCode != RESULT_CANCELED){
            if (resultCode == RESULT_OK) {
                // if we are here, everything processed successfully.
                if (requestCode == IMAGE_GALLERY_REQUEST) {
                    // if we are here, we are hearing back from the image gallery.

                    // the address of the image on the SD Card.
                    Uri imageUri = data.getData();
                    File imageFile = new File(getRealPathFromURI(imageUri));
                    String path = imageFile.getAbsolutePath();

                    // we are getting an input stream, based on the URI of the image.
                    try {
                        inputStream = getContentResolver().openInputStream(imageUri);

                        // get a bitmap from the stream.
                        image = BitmapFactory.decodeStream(inputStream);


                        rotateImage(imageUri);
                        processImage();

                        // show the image to the user
                        imgPicture.setImageBitmap(image);


                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        // show a message to the user indictating that the image is unavailable.
                        Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();
                    } finally {
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException ignored) {
                            }
                        }

                    }
                }
                if (requestCode == CAMERA_REQUEST) {
                    // Show the thumbnail on ImageView
                    Uri imageUri = Uri.parse(mCurrentPhotoPath);
                    File file = new File(imageUri.getPath());
                    try {
                        InputStream ims = new FileInputStream(file);
                        image = BitmapFactory.decodeStream(ims);

                        rotateImage(imageUri);
                        processImage();


                        imgPicture.setImageBitmap(image);
                    } catch (FileNotFoundException e) {
                        return;
                    }

                    // ScanFile so it will be appeared on Gallery
                    MediaScannerConnection.scanFile(MainActivity.this,
                            new String[]{imageUri.getPath()}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                public void onScanCompleted(String path, Uri uri) {
                                }
                            });
                }
            }
        }
    }

    //get image path
    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    //create path for picture taken
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    //process image to text
    public void processImage(){

        if(image != null){
            TextRecognizer textRecognizer = new TextRecognizer.Builder(this).build();
            if(!textRecognizer.isOperational()) {
                //Log.w(TAG, "Detector dependencies are not yet available.");
            }
            Frame imageFrame = new Frame.Builder()
                    .setBitmap(image)
                    .build();
            final SparseArray<TextBlock> textBlocks = textRecognizer.detect(imageFrame);

            if(textBlocks.size() != 0){
                OCRTextView.post(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < textBlocks.size(); ++i){
                            TextBlock item = textBlocks.valueAt(i);
                            stringBuilder.append(item.getValue());
                            stringBuilder.append("\n");
                        }
                        //String exam = txt.stringList(stringBuilder.toString(),"Exam");
                        OCRTextView.setText(stringBuilder.toString());
                    }
                });
            }
        }
    }

    //rotate image if not 0 degrees
    public void rotateImage(Uri tempuri){

        InputStream in = null;
        ExifInterface exifInterface = null;
        try {
            in = getContentResolver().openInputStream(tempuri);
            exifInterface = new ExifInterface(in);
            // Now you can extract any Exif tag you want
            // Assuming the image is a JPEG or supported raw format
        } catch (IOException e) {
            // Handle any errors
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ignored) {}
            }
        }

        int rotate = 0;
        int orientation = exifInterface.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL);
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                rotate = 90;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                rotate = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                rotate = 270;
                break;
        }

        //Log.v(TAG, "Rotation: " + rotate);

        if (rotate != 0) {

            // Getting width & height of the given image.
            int w = image.getWidth();
            int h = image.getHeight();

            // Setting pre rotate
            Matrix mtx = new Matrix();
            mtx.preRotate(rotate);

            // Rotating Bitmap
            image = Bitmap.createBitmap(image, 0, 0, w, h, mtx, false);
        }

        // Convert to ARGB_8888, required by tess
        image = image.copy(Bitmap.Config.ARGB_8888, true);
        //imgPicture.setImageBitmap(image);

    }

    //find text from list
    public void autoFind(){
        final StringBuilder str = new StringBuilder();
        for(int i = 0; i < list.size(); i++ ){
            String find = list.get(i);
            if(txt.hasString(stringBuilder.toString(),find)){
                str.append(txt.stringList(stringBuilder.toString(),find));
                OCRTextView.setText(str);
            }

        }
    }

    //create list of text
    public void populateList(){
        list.add("Exam");
        list.add("Email");
        list.add("midterm");
        list.add("due");
    }

}
