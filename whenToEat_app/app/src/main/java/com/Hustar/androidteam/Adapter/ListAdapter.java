package com.Hustar.androidteam.Adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.Hustar.androidteam.Fragment.ListFragment;
import com.Hustar.androidteam.ListDetailActivity;
import com.Hustar.androidteam.Model.ProductInfo;
import com.Hustar.androidteam.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

//    public interface OnListItemLongSelectedInterface {
//        void onItemLongSelected(View v, int position);
//    }
//
//    public interface OnListItemSelectedInterface {
//        void onItemSelected(View v, int position);
//    }

    public Context context;
    public List<ProductInfo> products;
    public String custid;

    private FirebaseUser firebaseUser;

//    private OnListItemSelectedInterface mListener;
//    private OnListItemLongSelectedInterface mLongListener;

    FirebaseDatabase firebaseDatabase;

    public ListAdapter(Context context, List<ProductInfo> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        ProductInfo productInfo = products.get(i);

        SharedPreferences prefs = context.getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        custid = prefs.getString("custid", "none");

        Glide.with(context).load(productInfo.getProductimg())
                .apply(new RequestOptions().placeholder(R.drawable.placeholder))
                .into(viewHolder.imageView);

        viewHolder.P_name.setText("상품명 : "+productInfo.getProduct_name());
        viewHolder.exp.setText("유통기한 : "+productInfo.getExpiration_date());


        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Product").child("");
//                System.out.println("adapter 키 값 : "+reference.getKey());

                custid = prefs.getString("custid", "none");

                Intent intent = new Intent(v.getContext(), ListDetailActivity.class);
                intent.putExtra("custid",productInfo.getCustid());
                intent.putExtra("product_name",productInfo.getProduct_name());
                intent.putExtra("exp",productInfo.getExpiration_date());
                intent.putExtra("caution",productInfo.getCaution());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageView;
        public TextView P_name, exp, caution;
        public MaterialEditText mt_productname, mt_exp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            P_name = itemView.findViewById(R.id.P_name);
            exp = itemView.findViewById(R.id.exp);
            caution = itemView.findViewById(R.id.caution);
            mt_productname = itemView.findViewById(R.id.mt_product_name);
            mt_exp = itemView.findViewById(R.id.mt_expiration_date);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.d("test", "position = "+ getAdapterPosition());
//
//                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Product");
//
////                    String key = reference.push().getKey();
//
//                    firebaseDatabase.getReference().child("Product").child(reference.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            Toast.makeText(context, "삭제 성공", Toast.LENGTH_SHORT).show();
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            System.out.println("error: " + e.getMessage());
//                            Toast.makeText(context, "삭제 실패", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//            });
        }
    }
}
