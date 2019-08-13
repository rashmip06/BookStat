package sdlpro.bookstat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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



public class SellAct extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private static final int REQUEST_IMAGE = 1;
    private static final int  REQUEST_PERMISSION= 3;
    private Uri uri;
    String text;
    String imageFileName;
    EditText price,desc;
    Button click,gallery,post;
    String curruid;
    private String timeStamp;
    private  static  final  int GALLERY_INTENT=2;
    private String imageFilePath = "";
    Ad ad;
    private String price1,desc1;
    StorageReference mStorage;
    DatabaseReference mRef= FirebaseDatabase.getInstance().getReference();

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        text=adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    Spinner spinner1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);
        }
        Intent intent=getIntent();
        curruid =intent.getStringExtra("uid");
        spinner1=(Spinner)findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(SellAct.this,R.array.Category,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(SellAct.this);

        mStorage= FirebaseStorage.getInstance().getReference();

        price=(EditText)findViewById(R.id.price);
        desc=(EditText)findViewById(R.id.desc);
        click=(Button)findViewById(R.id.click);
        gallery=(Button)findViewById(R.id.gallary);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                price1=price.getText().toString();
                desc1=desc.getText().toString();
                if(price1.isEmpty()||desc1.isEmpty())
                    Toast.makeText(SellAct.this,"Please enter all details",Toast.LENGTH_LONG).show();
                else
                    openCameraIntent();
            }

        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                price1=price.getText().toString();
                desc1=desc.getText().toString();
                if(price1.equals("")||desc1.equals(""))
                    Toast.makeText(SellAct.this,"Please Enter all details",Toast.LENGTH_LONG).show();
                else {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/~");
                    startActivityForResult(intent, GALLERY_INTENT);
                }
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
            Uri photoUri = FileProvider.getUriForFile(SellAct.this, getPackageName() +".provider", photoFile);
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

        price1=price.getText().toString();
        desc1=desc.getText().toString();

        Intent intent = getIntent();

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {

            uri = data.getData();
            StorageReference childRef = mStorage.child("Ads").child(text).child(curruid).child(uri.getLastPathSegment());
            final DatabaseReference myRef= mRef.child("Users");

            childRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloaduri=taskSnapshot.getDownloadUrl();
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                    mRef.child("Ads").child(text).child(timeStamp).child("url").setValue(downloaduri.toString());
                    mRef.child("Ads").child(text).child(timeStamp).child("UD").setValue(curruid);
                    mRef.child("Ads").child(text).child(timeStamp).child("Amount").setValue(price1);
                    mRef.child("Ads").child(text).child(timeStamp).child("Descp").setValue(desc1);
                    mRef.child("Ads").child(text).child(timeStamp).child("AdId").setValue(timeStamp);
                    mRef.child("Users").child(curruid).child("Image").child("Ads").child(timeStamp).setValue(downloaduri.toString());
                    Toast.makeText(SellAct.this, "Ad upload done", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else  if (requestCode == REQUEST_IMAGE && requestCode == REQUEST_IMAGE) {
            //uri=data.getData();
            File img=new File(imageFilePath);
            uri=Uri.fromFile(img);
            StorageReference filepath=mStorage.child("Ads").child(text).child(curruid).child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloaduri=taskSnapshot.getDownloadUrl();
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                    mRef.child("Ads").child(text).child(timeStamp).child("url").setValue(downloaduri.toString());
                    mRef.child("Ads").child(text).child(timeStamp).child("UD").setValue(curruid);
                    mRef.child("Ads").child(text).child(timeStamp).child("Amount").setValue(price1);
                    mRef.child("Ads").child(text).child(timeStamp).child("Descp").setValue(desc1);
                    mRef.child("Ads").child(text).child(timeStamp).child("AdId").setValue(timeStamp);
                    mRef.child("Users").child(curruid).child("Image").child("Ads").child(timeStamp).setValue(downloaduri.toString());

                    Toast.makeText(SellAct.this,"Upload successfull",Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SellAct.this,"upload failed",Toast.LENGTH_LONG).show();
                }
            });

        }
    }

}


