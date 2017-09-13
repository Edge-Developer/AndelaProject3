package io.edgedev.andelaproject;

/**
 * Created by OPEYEMI OLORUNLEKE on 9/13/2017.
 */

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by OPEYEMI OLORUNLEKE on 3/5/2017.
 */

public class DownloadAndParseData {
    private Context context;

    public DownloadAndParseData(Context context) {
        this.context = context;
    }

    private void download_profiles(String URL) {
        final List<Developer> developers = new ArrayList<>();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Model model = new Gson().fromJson(response, Model.class);
                Developer developer;
                Item item;

                for (int i = 0; i < model.getItems().size(); i++) {
                    item = model.getItems().get(i);
                    developer = new Developer(
                            item.get_Id(),
                            item.getLogin(),
                            item.getHtmlUrl(),
                            item.getAvatarUrl(),
                            item.getScore()
                    );
                    developers.add(developer);
                }
                Singleton.newInstance().setDevelopers(developers);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        ConnectionManager.getInstance(context).add(mStringRequest);
    }
}