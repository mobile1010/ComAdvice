package com.nawarin.comadvice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RadioButton radioBeep,radioText;

    private Spinner spinnerSymtom,spinnerBios;

    private Button submit;

    private ArrayList<String> bios = new ArrayList<>();
    private ArrayList<Integer> bios_id = new ArrayList<>();
    private ArrayList<String> bios_detail = new ArrayList<>();

    private ArrayList<String> symtom = new ArrayList<>();
    private ArrayList<Integer> symtom_id = new ArrayList<>();
    private ArrayList<String> symtom_detail = new ArrayList<>();





    LinearLayout linear4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWidget();
        setAction();
    }

    private void initWidget(){
        radioBeep = (RadioButton) findViewById(R.id.radioBeep);
        radioText = (RadioButton) findViewById(R.id.radioText);

        submit = (Button) findViewById(R.id.submit);
        spinnerSymtom = (Spinner) findViewById(R.id.spinnerSymtom);

        spinnerBios = (Spinner) findViewById(R.id.spinnerBios);
        linear4 = (LinearLayout) findViewById(R.id.linear4);

    }

    private void setAction(){
        radioBeep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bios.clear();
                bios_id.clear();
                bios_detail.clear();
                radioBeep.toggle();
                if(radioText.isChecked()){
                    radioText.toggle();
                }
                SqlConnection sqlconection = new SqlConnection("GetSymtom");

                sqlconection.setSqlListener(new SqlconInterface() {
                    @Override
                    public void onFinishTask(String taskID, String result) {

                        try {
                            JSONArray jsonArray = new JSONArray(result);
                            if (jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    bios.add(jsonObject.getString("Bios_name"));
                                    bios_id.add(Integer.parseInt(jsonObject.getString("Bios_id")));

                                }

                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_dropdown_item_1line,bios);

                                spinnerBios.setAdapter(adapter);

                                linear4.setVisibility(View.VISIBLE);

                            }

                        } catch (Exception e) {
                            Log.d("BloodAPP", "JSON to Array  ========> " + e);
                            Toast.makeText(getApplicationContext(), "Error loading data", Toast.LENGTH_SHORT).show();

                            linear4.setVisibility(View.GONE);
                        }

                    }
                });

                String sql = "SELECT * FROM tb_type_bios";

                sqlconection.execute(sql,SqlConnection.SELECT);
            }
        });

        radioText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                symtom.clear();
                symtom_id.clear();
                symtom_detail.clear();
                linear4.setVisibility(View.GONE);
                radioText.toggle();
                if(radioBeep.isChecked()){
                    radioBeep.toggle();
                }
                SqlConnection sqlconection = new SqlConnection("GetSymtomText");

                sqlconection.setSqlListener(new SqlconInterface() {
                    @Override
                    public void onFinishTask(String taskID, String result) {

                        try {
                            JSONArray jsonArray = new JSONArray(result);
                            if (jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    symtom.add(jsonObject.getString("Syt_name"));
                                    symtom_id.add(Integer.parseInt(jsonObject.getString("Syt_id")));
                                    symtom_detail.add(jsonObject.getString("Syt_detail"));

                                }

                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_dropdown_item_1line,symtom);

                                spinnerSymtom.setAdapter(adapter);



                            }

                        } catch (Exception e) {
                            Log.d("BloodAPP", "JSON to Array  ========> " + e);
                            Toast.makeText(getApplicationContext(), "Error loading data", Toast.LENGTH_SHORT).show();

                        }

                    }
                });

                String sql = "SELECT * FROM tb_sympotom_text";

                sqlconection.execute(sql,SqlConnection.SELECT);
            }
        });



        spinnerBios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                symtom.clear();
                symtom_id.clear();
                symtom_detail.clear();
                SqlConnection sqlconection = new SqlConnection("GetSymtomText");

                sqlconection.setSqlListener(new SqlconInterface() {
                    @Override
                    public void onFinishTask(String taskID, String result) {

                        try {
                            JSONArray jsonArray = new JSONArray(result);
                            if (jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    symtom.add(jsonObject.getString("Sym_name"));
                                    symtom_id.add(Integer.parseInt(jsonObject.getString("Sym_id")));
                                    symtom_detail.add(jsonObject.getString("Sym_detail"));

                                }

                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_dropdown_item_1line,symtom);

                                spinnerSymtom.setAdapter(adapter);

                                linear4.setVisibility(View.VISIBLE);

                            }

                        } catch (Exception e) {
                            Log.d("BloodAPP", "JSON to Array  ========> " + e);
                            Toast.makeText(getApplicationContext(), "Error loading data", Toast.LENGTH_SHORT).show();

                            linear4.setVisibility(View.GONE);
                        }

                    }
                });

                String sql = "SELECT * FROM tb_sympotom_bios WHERE Bios_id = "+(position+1);

                sqlconection.execute(sql,SqlConnection.SELECT);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radioBeep.isChecked() || radioText.isChecked()){
                    String sql = "";
                    if(radioBeep.isChecked()){
                        sql = sql+"select Sug_detail as 'detail', Sug_img as 'img' from tb_suggestion_bios where Sym_id = ";

                    }else if(radioText.isChecked()){

                        sql = sql+"select Sugt_detail as 'detail', Sugt_img as 'img' from tb_suggestion_text where Syt_id = ";

                    }

                    sql = sql+symtom_id.get(spinnerSymtom.getSelectedItemPosition());

                    Intent intent = new Intent(MainActivity.this,ShowActivity.class);

                    intent.putExtra("sql",sql);
                    intent.putExtra("symtom",symtom_detail.get(spinnerSymtom.getSelectedItemPosition()));

                    startActivity(intent);


                }else {
                    Toast.makeText(getApplicationContext(),"กรุณาเลือกกลุ่มอาการ",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

}
