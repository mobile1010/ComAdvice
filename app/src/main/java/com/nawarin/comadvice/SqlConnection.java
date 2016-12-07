package com.nawarin.comadvice;

import android.os.AsyncTask;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by Narrin on 4/12/2559.
 */
public class SqlConnection extends AsyncTask<String,Void,String> {

    private SqlconInterface sqlListener;


    private String taskID = null;



    public static final String SELECT = "select.php",UPDATE = "update.php",DELETE = "",INSERT = "insert.php";


    public SqlConnection(String taskID){
        this.taskID = taskID;
    }


    @Override
    protected String doInBackground(String... params) {
        Server server = new Server();

        OkHttpClient httpClient = new OkHttpClient();

        String url = server.address+params[1];

        RequestBody body = new FormEncodingBuilder().add("sql",params[0]).build();




        try {
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Response response = httpClient.newCall(request).execute();
            if(response.isSuccessful()){
                return response.body().string();
            }else {
                return "false";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "false";
        }


    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        sqlListener.onFinishTask(taskID,s);
    }

    public void setSqlListener(SqlconInterface sqlListener){
        this.sqlListener = sqlListener;
    }


}
