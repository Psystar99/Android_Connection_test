package com.example.test_youtube;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private TextView mTextViewResult;
    ArrayList<String> name_a=new ArrayList<>();
    ArrayList<String> path_a=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //임시로 테스트할 어레이리스트
        name_a.add("tigertest.jpg");
        name_a.add("liontest.jpg");
        path_a.add("/sdcard/Pictures/tigertest.jpg");
        path_a.add("/sdcard/Pictures/liontest.jpg");


        mTextViewResult = findViewById(R.id.text_view_result);
//a. Setup the http request: okhttp3.OkHttpClient

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("text/plain");
        String url="http://192.168.219.102:5000/api/pictures/";

//b. Make the request: okhttp3.Request

        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("picture",name_a.get(0),
                        RequestBody.create(MediaType.parse("application/octet-stream"),
                                new File(path_a.get(0))))
                .addFormDataPart("picture",name_a.get(1),
                        RequestBody.create(MediaType.parse("application/octet-stream"),
                                new File(path_a.get(1))))
                .build();

        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .build();

//c. Read the response: okhttp.Callback[1. failure: onFailure 2. success: onResponse]
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("연결 실패", "error Connect Server error is"+e.toString());
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //System.out.println("response body is"+response.body().string());
                Log.d("연결 성", "error Connect Server error is");

                if (response.isSuccessful()) {
                    // final String myResponse = response.body().string();
                    try {
                        final JSONObject jsonObject = new JSONObject(response.body().string());
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    mTextViewResult.setText(jsonObject.getString("predicted_label"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // Log.d("값 출력", myResponse+"");
                    //MainActivity.this.runOnUiThread(new Runnable() {
                    //    @Override
                    //    public void run() {
                    //        mTextViewResult.setText(myResponse);
                    //    }
                    //});
                }
            }

        });

    }
}