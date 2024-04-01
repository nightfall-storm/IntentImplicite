package com.example.intent_implicite;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1;

    private ImageView img; // Declare the ImageView variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = findViewById(R.id.img); // Initialize the ImageView
    }

    public void connexion_click(View view) {
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void img_click(View view) {

        Intent intent;
        switch (view.getId()) {
            case R.id.btn_galerie:
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 2);
                break;
            case R.id.btn_camera:
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted, proceed with taking a picture
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePictureIntent, 1);
                } else {
                    // Permission not granted, request it
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
                    // You can optionally add a Toast message here indicating that the permission is requested
                    // Toast.makeText(this, "Requesting camera permission...", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btn_video:
                // Example: Launch a video-related activity
                Intent videoIntent = new Intent(MainActivity.this, VideoActivity.class);
                startActivity(videoIntent);
                break;

            case R.id.btn_telephone:
                // Example: Launch a telephone-related activity
                Toast.makeText(this, "Making a phone call", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_appel:
                Intent telephoneIntent = new Intent(Intent.ACTION_DIAL);
                telephoneIntent.setData(Uri.parse("tel:" + "123456789")); // Replace with the desired phone number
                startActivity(telephoneIntent);
                break;

            case R.id.btn_sms:
                // Example: Launch an activity related to sending SMS
                Intent smsActivityIntent = new Intent(MainActivity.this, SmsActivity.class);
                startActivity(smsActivityIntent);
                break;

            case R.id.btn_google:
                // Example: Launch an activity related to Google
                Intent googleIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"));
                startActivity(googleIntent);
                break;

            case R.id.btn_facebook:
                // Example: Launch an activity related to Facebook
                Intent facebookIntent = new Intent(Intent.ACTION_SEND);
                facebookIntent.setType("text/plain");
                facebookIntent.setPackage("com.facebook");
                facebookIntent.putExtra(Intent.EXTRA_TEXT, "Hello from your app!"); // Replace with the desired message
                try {
                    startActivity(facebookIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    // Handle exception if fb is not installed
                    Toast.makeText(this, "Facebook not installed", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btn_whatsapp:
                // Example: Launch an activity related to WhatsApp
                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Hello from your app!"); // Replace with the desired message
                try {
                    startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    // Handle exception if WhatsApp is not installed
                    Toast.makeText(this, "WhatsApp not installed", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImageView img = findViewById(R.id.img);
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == 1 && resultCode == RESULT_OK) {

            Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");

            img.setImageBitmap(imageBitmap);
        }
        else if (requestCode == 2 && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            img.setImageURI(selectedImage);

            /*File file = new File(
                    getExternalCacheDir(Environment.DIRECTORY_PICTURES),"img1_jpg"
            ); try {
                OutputStream outputStream = new FileOutputStream(file);
                InputStream inputStream = new FileInputStream(Selected)*/
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            // Check if the permission was granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with taking a picture
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePictureIntent, 1);
            } else {
                Toast.makeText(this,
                                " Permission Denied",
                                Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
}