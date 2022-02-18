package com.Hustar.androidteam;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Hustar.androidteam.Adapter.ListAdapter;
import com.Hustar.androidteam.Model.ProductInfo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageTask;

import java.util.ArrayList;
import java.util.List;

public class ListDetailActivity extends Activity {

    private ImageView product_img, close;
    private TextView product_name, save, delete;
    private TextView exp;
    private TextView caution;

    public Context context;

    private FirebaseDatabase firebaseDatabase;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;

    private Uri mImgUri;
    private StorageTask uploadTask;
    SharedPreferences sharedPreferences;

    List<ProductInfo> productInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_detail);

        product_img = findViewById(R.id.productimg);
        product_name = findViewById(R.id.mt_product_name);
        exp = findViewById(R.id.mt_expiration_date);
        caution = findViewById(R.id.caution);

        save = findViewById(R.id.save);
        close = findViewById(R.id.close);
        delete = findViewById(R.id.delete);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Product");
//        String custID = reference.push().getKey();

        Intent intent = getIntent();
        if (intent != null) {
            String custid = intent.getStringExtra("custid");
            String pname = intent.getStringExtra("product_name");
            String pexp = intent.getStringExtra("exp");
            String pimg = intent.getStringExtra("productimg");
            String pcaution = intent.getStringExtra("caution");

            System.out.println("custid 1 : " + custid);

            Glide.with(getApplicationContext()).load(pimg);

            product_name.setText("상품명 : " + pname);
            exp.setText("유통기한 : " + pexp + "까지");
            caution.setText("주의사항 : " + pcaution);
        }

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

//        delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Product");
//
//                SharedPreferences prefs = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
//                custid = prefs.getString("custid", "none");
//
//                reference.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        productInfoList.clear();
//                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                            ProductInfo PInfo = snapshot.getValue(ProductInfo.class);
//
//                            if (!PInfo.getCustid().equals(custid)) {
//                                continue;
//                            }
//                            productInfoList.add(PInfo);
//                        }
//
//                        listAdapter.notifyDataSetChanged();
//                        progressBar.setVisibility(View.GONE);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//            }
    }
}
