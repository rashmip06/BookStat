package sdlpro.bookstat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ProfilePhoto extends AppCompatActivity {


    private Button image,camera;
    ImageView photo;
    StorageReference mStorage;
    private  static  final  int GALLERY_INTENT=2;
    private static  final int REQUEST_IMAGE=1;
    ProgressDialog mProgress;
    DatabaseReference mRef;
    private String imageFilePath = "";
    private String timeStamp;
    private Uri uri;
    String imageFileName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_photo);


        mProgress=new ProgressDialog(ProfilePhoto.this);

        image=(Button)findViewById(R.id.gallery);
        camera=(Button)findViewById(R.id.cam) ;
        mStorage= FirebaseStorage.getInstance().getReference();

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/~");

                startActivityForResult(intent,GALLERY_INTENT);

            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCameraIntent();

            }
        });


    }

    private void openCameraIntent() {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFile();
            }
            catch (IOException e) {
                e.printStackTrace();
                return;
            }
            Uri photoUri = FileProvider.getUriForFile(ProfilePhoto.this, getPackageName() +".provider", photoFile);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(pictureIntent, REQUEST_IMAGE);
        }
    }
    private File createImageFile() throws IOException{

        timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        imageFilePath = image.getAbsolutePath();

        return image;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mRef=FirebaseDatabase.getInstance().getReference();
        Intent intent=getIntent();

        final User user=(User)intent.getSerializableExtra("users");

        if(requestCode==GALLERY_INTENT && resultCode==RESULT_OK)
        {
            Uri uri=data.getData();
            final StorageReference childRef=mStorage.child("Photos").child(user.getUid()).child(uri.getLastPathSegment());
            childRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(ProfilePhoto.this,"Upload Done",Toast.LENGTH_SHORT).show();
                    StorageReference mStref=FirebaseStorage.getInstance().getReference("Photos");
                    Uri getdownload=taskSnapshot.getDownloadUrl();
                    mRef.child("Users").child(user.uid).child("Image").child("Profile").child("URL").setValue(getdownload.toString());
                    Intent intent1=new Intent(ProfilePhoto.this,Start.class);
                    intent1.putExtra("phone",user.getPhno());
                    startActivity(intent1);

                }
            });

        }

        if(requestCode==REQUEST_IMAGE && resultCode==RESULT_OK)
        {
            File img=new File(imageFilePath);
            uri=Uri.fromFile(img);
            final StorageReference childRef=mStorage.child("Photos").child(user.getUid()).child(uri.getLastPathSegment());
            childRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(ProfilePhoto.this,"Upload Done",Toast.LENGTH_SHORT).show();
                    Uri getdownload=taskSnapshot.getDownloadUrl();
                    mRef.child("Users").child(user.uid).child("Image").child("Profile").child("URL").setValue(getdownload.toString());
                    Intent intent1=new Intent(ProfilePhoto.this,Start.class);
                    intent1.putExtra("phone",user.getPhno());
                    startActivity(intent1);

                }
            });

        }
    }


}
