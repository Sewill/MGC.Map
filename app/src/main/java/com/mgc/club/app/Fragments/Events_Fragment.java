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
import com.mgc.club.app.Activities.Details.Events_Details_Activity;
import com.mgc.club.app.Adapters.Events_Adapter;
import com.mgc.club.app.Application.AppController;
import com.mgc.club.app.Model.Events;
import com.mgc.club.app.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by savva on 07.07.2015.
 */
public class Events_Fragment extends Fragment {
    private static final String url = "http://mgc.club/api/events.json";
    private List<Events> events = new ArrayList<Events>();
    private ListView listView;
    private Events_Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.lists, container, false);


        listView = (ListView) rootView.findViewById(R.id.list);
        adapter = new Events_Adapter(getActivity(), events);
        listView.setAdapter(adapter);

        // Creating volley request obj
        if(url!=null&&!url.equals("null")) {
            JsonArrayRequest movieReq = new JsonArrayRequest(url,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
//                        Log.d(TAG, response.toString());

                            // Parsing json
                            for (int i = 0; i < response.length(); i++) {
                                try {

                                    JSONObject obj = response.getJSONObject(i);
                                    Events event = new Events();
                                    event.setId((Integer) obj.get("id"));
                                    event.setName(obj.getString("name"));
                                    event.setText(obj.getString("text"));

                                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                                    Date date = null;
                                    try {
                                        date = format.parse(obj.getString("start"));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                                    Calendar calendar = new GregorianCalendar();
                                    calendar.setTime(date);
                                    String start ="Начало " + calendar.DAY_OF_MONTH + " " +AppController.getInstance().month[calendar.get(Calendar.MONTH)]+  " "+simpleDateFormat.format(date);

                                    event.setStart(start);
                                    try {
                                        date = format.parse(obj.getString("finish"));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                    String finish = "Конец "+ calendar.DAY_OF_MONTH + " " +AppController.getInstance().month[calendar.get(Calendar.MONTH)]+  " "+simpleDateFormat.format(date);
                                    event.setFinish(finish);
                                    event.setEventcover(obj.getString("eventcover"));

                                    // Genre is json array
//                                JSONArray genreArry = obj.getJSONArray("genre");
//                                ArrayList<String> genre = new ArrayList<String>();
//                                for (int j = 0; j < genreArry.length(); j++) {
//                                    genre.add((String) genreArry.get(j));
//                                }
//                                certificates.setGenre(genre);

                                    // adding movie to movies array
                                    events.add(event);

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
                Intent intent = new Intent(getActivity(), Events_Details_Activity.class);
                intent.putExtra("object", events.get(position));
                startActivity(intent);
            }
        });


        return rootView;
    }
}