package com.nawarin.comadvice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

public class ShowActivity extends AppCompatActivity {

    ImageView resultPic;

    TextView txtSymtom,txtSug;

    String sql,symtom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        initWidget();
        setResult();

    }

    private void initWidget(){
        resultPic = (ImageView) findViewById(R.id.resultPic);
        txtSymtom = (TextView) findViewById(R.id.txtSymtom);
        txtSug = (TextView) findViewById(R.id.txtSug);
        Intent gotIn = getIntent();
        sql = gotIn.getStringExtra("sql");
        symtom = gotIn.getStringExtra("symtom");
        Log.d("BloodAPP", "Got intent  ========> " + sql +" === "+symtom);
    }

    private void setResult(){

        txtSymtom.setText(symtom);
        SqlConnection sqlconection = new SqlConnection("GetSymtomText");

        sqlconection.setSqlListener(new SqlconInterface() {
            @Override
            public void onFinishTask(String taskID, String result) {

                try {
                    JSONArray jsonArray = new JSONArray(result);
                    if (jsonArray.length() > 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Server server  = new Server();

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            txtSug.setText(jsonObject.getString("detail"));
                            Picasso.with(getApplicationContext()).load(server.address+jsonObject.getString("img")).into(resultPic);



                        }


                    }

                } catch (Exception e) {
                    Log.d("BloodAPP", "JSON to Array  ========> " + e);
                    Toast.makeText(getApplicationContext(), "Error loading data", Toast.LENGTH_SHORT).show();

                }

            }
        });



        sqlconection.execute(sql,SqlConnection.SELECT);
    }
}
