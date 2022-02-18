package com.Hustar.androidteam;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Hustar.androidteam.Model.ProductInfo;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class UploadProductActivity extends AppCompatActivity {

    Uri productUri;
    String productUrl;
    StorageTask uploadTask;
    StorageReference storageReference;

    String caution;
    String custid;
    String key;

    ProductInfo productInfo;

    ImageView close, product_Image;
    MaterialEditText barcode, product_name, expiration_date;
    TextView save;
    CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_product);

        close = findViewById(R.id.close);
        product_Image = findViewById(R.id.product_Image);
        barcode = findViewById(R.id.barcode);
        product_name = findViewById(R.id.product_name);
        expiration_date = findViewById(R.id.expiration_date);
        save = findViewById(R.id.save);
        calendarView = findViewById(R.id.calenderView);

        DateFormat dateFormat = new SimpleDateFormat("yyyy년MM월dd일");
        Date date = new Date(calendarView.getDate());
        expiration_date.setText(dateFormat.format(date));

        Intent intent = getIntent();
        if(intent != null)
        {
            custid = intent.getStringExtra("custid");
            String JBarcode = intent.getStringExtra("barcode");
            String JProduct_name = intent.getStringExtra("product_name");
            String JCaution = intent.getStringExtra("caution");
            String JProductImg = intent.getStringExtra("productimg");

            System.out.println("custid 1 : "+custid);


            barcode.setText(JBarcode);
            product_name.setText(JProduct_name);
            caution = JCaution;
        }

        storageReference = FirebaseStorage.getInstance().getReference("product");

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String day;
                day = year + "년 " + (month+1) + "월 "  + dayOfMonth + "일";
                expiration_date.setText(day);
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UploadProductActivity.this, MainActivity.class));
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Upload();
            }
        });

        product_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setAspectRatio(1,1)
                        .start(UploadProductActivity.this);
            }
        });
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void Upload() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("저장 중");
        progressDialog.show();

        if (productUri != null) {
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis()
            +"."+getFileExtension(productUri));

            uploadTask = fileReference.putFile(productUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        productUrl = downloadUri.toString();

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Product");

                        String custID = reference.push().getKey();
//                        System.out.println("custid 2 : "+custid);

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("custid", custid);
                        hashMap.put("productimg", productUrl);
                        hashMap.put("barcode",barcode.getText().toString());
                        hashMap.put("product_name",product_name.getText().toString());
                        hashMap.put("expiration_date",expiration_date.getText().toString());
                        hashMap.put("caution",caution);
//                        hashMap.put("key",key);

                        reference.child(custID).setValue(hashMap);

                        progressDialog.dismiss();

                        startActivity(new Intent(UploadProductActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(UploadProductActivity.this, "실패!", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UploadProductActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "이미지가 선택되지 않았습니다.", Toast.LENGTH_SHORT).show();
            progressDialog.cancel();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            productUri = result.getUri();

            product_Image.setImageURI(productUri);
        } else {
            Toast.makeText(this,"Something gone wrong!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(UploadProductActivity.this, MainActivity.class));
            finish();
        }
    }
}