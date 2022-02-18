package com.Hustar.androidteam.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.Hustar.androidteam.Adapter.ListAdapter;
import com.Hustar.androidteam.Model.ProductInfo;
import com.Hustar.androidteam.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    private RecyclerView recyclerView;
    private FloatingActionButton add_button;
    private ListAdapter listAdapter;
    private List<ProductInfo> productInfoList;
    private String custid;

//    private FirebaseUser firebaseUser;

    ProgressBar progressBar;
    FirebaseStorage firebaseStorage;

    FirebaseUser firebaseUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        View view = inflater.inflate(R.layout.fragment_list, container, false);

//        SharedPreferences prefs = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
//        custid = prefs.getString("custid", "none");

        recyclerView = view.findViewById(R.id.recycler_view_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        productInfoList = new ArrayList<>();
        listAdapter = new ListAdapter(getContext(), productInfoList);
        recyclerView.setAdapter(listAdapter);

        progressBar = view.findViewById(R.id.progress_circular);

        readLists();

        return view;
    }

    private void readLists() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Product");

        SharedPreferences prefs = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        custid = prefs.getString("custid", "none");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productInfoList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ProductInfo PInfo = snapshot.getValue(ProductInfo.class);

                    if (!PInfo.getCustid().equals(custid)) {
                        continue;
                    }
                    productInfoList.add(PInfo);
                }

                listAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

//    @Override
//    public void onItemLongSelected(View v, int position) {
//        ListAdapter.ViewHolder viewHolder = (ListAdapter.ViewHolder)recyclerView.findViewHolderForAdapterPosition(position);
//        Toast.makeText(getActivity(), viewHolder.exp.getText().toString(), Toast.LENGTH_LONG).show();
//    }
//
//    @Override
//    public void onItemSelected(View v, int position) {
//
//    }
}