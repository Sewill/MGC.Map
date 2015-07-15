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
import com.mgc.club.app.Activities.Details.Certificates_Details_Activity;
import com.mgc.club.app.Adapters.Certificates_Adapter;
import com.mgc.club.app.Application.AppController;
import com.mgc.club.app.Model.Certificates;
import com.mgc.club.app.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by savva on 06.07.2015.
 */
public class Certificates_Fragment extends Fragment {
    private static final String url = "http://mgc.club/api/certificates.json";
    private List<Certificates> certificatesList = new ArrayList<Certificates>();
    private ListView listView;
    private Certificates_Adapter adapter;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.lists, container, false);

        listView = (ListView) rootView.findViewById(R.id.list);
        adapter = new Certificates_Adapter(getActivity(), certificatesList);
        listView.setAdapter(adapter);
        // Creating volley request obj
        try {
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
                                        Certificates certificates = new Certificates();
                                        certificates.setId((Integer) obj.get("id"));
                                        certificates.setName(obj.getString("name"));
                                        certificates.setBonus_price((Integer) obj.get("bonus_price"));
                                        certificates.setCover_url(obj.getString("cover_url"));
                                        certificates.setUrl(obj.getString("url"));

                                        // Genre is json array
                                        //                                JSONArray genreArry = obj.getJSONArray("genre");
                                        //                                ArrayList<String> genre = new ArrayList<String>();
                                        //                                for (int j = 0; j < genreArry.length(); j++) {
                                        //                                    genre.add((String) genreArry.get(j));
                                        //                                }
                                        //                                certificates.setGenre(genre);

                                        // adding movie to movies array
                                        certificatesList.add(certificates);

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
        } catch (Exception e) {
            e.printStackTrace();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), Certificates_Details_Activity.class);
                intent.putExtra("object", certificatesList.get(position));
                startActivity(intent);
            }
        });


        return rootView;
    }
}
