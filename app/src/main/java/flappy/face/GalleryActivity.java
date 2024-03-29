package flappy.face;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {

    int SELECT_PICTURE = 200;

    Button gallery, takephoto, valid;
    ImageView selectedImage;
    Bitmap bitmap=null;

    ActivityResultLauncher activityResultLauncher;
    @Override
    //Show the activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    bitmap = (Bitmap) result.getData().getExtras().get("data");
                    selectedImage.setImageBitmap(bitmap);
                }
            }
        });
        valid = findViewById(R.id.valid);
        gallery = findViewById(R.id.button_gallery);
        takephoto = findViewById(R.id.button_camera);
        selectedImage = findViewById(R.id.selected_image);

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageSelector();
            }
        });

        takephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhoto();
            }
        });

        valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validPhoto();
            }
        });
    }

    private void validPhoto() {
        if(bitmap!=null) {
            ArrayList<Bitmap> arrBms = new ArrayList<>();
            arrBms.add(bitmap);
            arrBms.add(bitmap);
            Intent intent = new Intent();
            intent.putExtra("DATA", arrBms);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    //Open camera
    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            activityResultLauncher.launch(intent);
        }
        else {
            Toast.makeText(GalleryActivity.this, "There is no app that support this action",
                    Toast.LENGTH_SHORT).show();
        }
    }

    //Open gallery
    private void imageSelector() {
        Intent selector = new Intent(Intent.ACTION_GET_CONTENT);
        selector.setType("image/*");
        startActivityForResult(Intent.createChooser(selector, "Select Picture"), SELECT_PICTURE);
    }

    //Show selected image or taken photo
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    selectedImage.setImageURI(selectedImageUri);
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}