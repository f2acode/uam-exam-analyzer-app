package plomba.com.br.analisadorprovas;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by augus on 4/14/2018.
 */

public class HttpRequestHelper {

    public interface VolleyCallback{
        //void onSuccess(String result);
        void onSuccess(JSONObject result);
        void onSuccess(JSONArray result);
    }

    public void listarEstatisticas(final VolleyCallback callback, Context context){

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        String url ="https://habitapi.azurewebsites.net/api/habit";

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray jsonArray){
                        callback.onSuccess(jsonArray);
                    }
                },
                new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Log.i("ERROR", error.getStackTrace().toString());
                    //TODO: handle failure
                }
            }
        );

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }

    public void postFoto(final VolleyCallback callback, Context context){

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        String url ="http://localhost:49732/api/images";

        Map<String, String> params = new HashMap();
        params.put("description", "habit");
        params.put("date", "date");

        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //TODO: handle success
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                //TODO: handle failure
            }
        });

        Volley.newRequestQueue(context).add(jsonRequest);
    }
}
