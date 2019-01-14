package com.example.gfx.encontreocep.Service;

import android.os.AsyncTask;
import android.util.Log;

import com.example.gfx.encontreocep.Response.ViaCepResponse;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Service extends AsyncTask<Void, Void, Void> {
    private OnReturnServicePrimary mListener;
    private boolean sucess = false;
    private Object result;
    private String error;
    private String cep;

    public Service(String cep, OnReturnServicePrimary mListener){
        this.cep = cep;
        this.mListener = mListener;
    }

    @Override
    protected void onPostExecute(Void avoid) {
        if (mListener != null) {
            if (sucess) mListener.onCompletion(result);
            else mListener.onError(error);
        }
    }

    public interface OnReturnServicePrimary {
        void onCompletion(Object response);

        void onError(String error);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        service();
        return null;
    }

    private Object service(){
        try{
            String url = "https://viacep.com.br/ws/"+ cep +"/json";
            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(3, TimeUnit.MINUTES)
                    .connectTimeout(3, TimeUnit.MINUTES)
                    .build();

            Request request = new Request.Builder()
            .header("Content-type:","application/josn;charset=utf-8")
            .url(url)
            .build();

            Response response = client.newCall(request).execute();
            if(response.isSuccessful()){
                String jsonResponse = response.body().string();
                response.close();
                result = new Gson().fromJson(jsonResponse, ViaCepResponse.class);
                sucess = true;
            }else{
                error = String.valueOf(response.code());
            }
        }catch (Exception e){
            error = e.getMessage();
            Log.e("Exception", error);
        }

        return null;
    }
}
