package com.Hustar.androidteam.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import android.os.Bundle;
import android.util.Log;

import com.Hustar.androidteam.Model.ProductInfo;
import com.Hustar.androidteam.UploadProductActivity;
import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.Hustar.androidteam.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class BarcodeFragment extends Fragment implements ZXingScannerView.ResultHandler{

    private ZXingScannerView mScannerView;
    Button AFH_Button;
    private List<ProductInfo> productInfoList;
    private ProductInfo productInfo;

    private String barcode;
    private String productimg;
    private String product_name;
    private String caution;
    private String custid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_barcode, container, false);

        SharedPreferences prefs = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        custid = prefs.getString("custid", "none");

        System.out.println("바코드 custid 1 : "+custid);

        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED)
        {
            ActivityCompat.requestPermissions(getActivity(),new String[] {Manifest.permission.CAMERA},100);
        }
        mScannerView = new ZXingScannerView(getContext());

        AFH_Button = view.findViewById(R.id.add_for_hand_button);
        RelativeLayout rl = (RelativeLayout) view.findViewById(R.id.barcode_scan);
        rl.addView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();


        AFH_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UploadProductActivity.class);
                intent.putExtra("custid",custid);

                System.out.println("바코드 버튼 custid : " + custid);

                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void handleResult(Result result) {
        Log.w("handleResult", result.getText());

        barcode = result.getText();

        AssetManager assetManager = getResources().getAssets();

        try {
            InputStream inputStream = assetManager.open("jsons/음료.json");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);

            StringBuffer buffer = new StringBuffer();
            String line = reader.readLine();

            while (line  != null) {
                buffer.append(line+"\n");
                line = reader.readLine();
            }

            String jsonData = buffer.toString();
//                    textView.setText(jsonData);
            JSONArray jsonArray = new JSONArray(jsonData);

            String str="";

            for (int i = 0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String jbarcode = jsonObject.getString("GTIN");

                if (jbarcode.equals(barcode)){
                    productimg = jsonObject.getString("이미지경로 보정 1000 JPG");
                    product_name = jsonObject.getString("상품명(국문)");
                    caution = jsonObject.getString("보관취급주의사항");
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }


        Intent intent = new Intent(getContext(), UploadProductActivity.class);
        intent.putExtra("productimg",productimg);
        intent.putExtra("barcode",barcode);
        intent.putExtra("product_name",product_name);
        intent.putExtra("caution",caution);
        intent.putExtra("custid",custid);

        System.out.println("바코드 custid : "+intent);

        startActivity(intent);
    }
}