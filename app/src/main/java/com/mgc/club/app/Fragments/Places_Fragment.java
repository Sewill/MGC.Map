package com.mgc.club.app.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.mgc.club.app.Activities.Details.Places_Details_Activity;
import com.mgc.club.app.Adapters.Places_Adapter;
import com.mgc.club.app.Application.AppController;
import com.mgc.club.app.Model.Places;
import com.mgc.club.app.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by savva on 07.07.2015.
 */
public class Places_Fragment extends Fragment {
    private static final String url = "http://mgc.club/api/places.json";
    private List<Places> placeses = new ArrayList<Places>();
    private ListView listView;
    private Places_Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.lists, container, false);


        listView = (ListView) rootView.findViewById(R.id.list);
        adapter = new Places_Adapter(getActivity(), placeses);
        listView.setAdapter(adapter);

        // Creating volley request obj
        if (url != null&&!url.equals("null")) {
            JsonArrayRequest movieReq = new JsonArrayRequest(url,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
//                        Log.d(TAG, response.toString());

                            // Parsing json
                            for (int i = 0; i < response.length(); i++) {
                                try {

                                    JSONObject obj = response.getJSONObject(i);
                                    Places places = new Places();
                                    places.setId((Integer) obj.get("id"));
                                    places.setName(obj.getString("name"));
                                    places.setAddress(obj.getString("address"));
                                    places.setLatitude((Double) obj.get("latitude"));
                                    places.setLongitude((Double) obj.get("longitude"));
                                    places.setLogo_url(obj.getString("logo_url"));

                                    // Genre is json array
//                                JSONArray genreArry = obj.getJSONArray("genre");
//                                ArrayList<String> genre = new ArrayList<String>();
//                                for (int j = 0; j < genreArry.length(); j++) {
//                                    genre.add((String) genreArry.get(j));
//                                }
//                                certificates.setGenre(genre);

                                    // adding movie to movies array
                                    placeses.add(places);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            // notifying list adapter about data changes
                            // so that it renders the list view with updated data
                            adapter.notifyDataSetChanged();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
//                VolleyLog.d(TAG, "Error: " + error.getMessage());
                }
            });

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(movieReq);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), Places_Details_Activity.class);
                intent.putExtra("object", placeses.get(position));
                startActivity(intent);
            }
        });


        return rootView;
    }
}
