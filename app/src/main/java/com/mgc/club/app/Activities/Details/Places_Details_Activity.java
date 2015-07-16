package com.mgc.club.app.Activities.Details;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.util.Linkify;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mgc.club.app.Model.Places;
import com.mgc.club.app.R;
import org.json.JSONException;
import org.json.JSONObject;

public class Places_Details_Activity extends AppCompatActivity {

    public static final int TYPE_WEB = 1;
    public static final int TYPE_PHONE = 2;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    private Places place;
    private LinearLayout li_lay;

    private final String part_url = "http://mgc.club/api/places/";
    private final String end_url = ".json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        SupportMapFragment supportMapFragment = null;
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mMap = supportMapFragment.getMap();
        }

        Display display = ((WindowManager)
                getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int height = point.y / 3;
        ViewGroup.LayoutParams layoutParams = supportMapFragment.getView().getLayoutParams();
        layoutParams.height = height;
        supportMapFragment.getView().setLayoutParams(layoutParams);

        li_lay = (LinearLayout) findViewById(R.id.li_lay);

        ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);

        ViewGroup.LayoutParams layoutParamsS = scrollView.getLayoutParams();
        layoutParamsS.height = height;
        scrollView.setLayoutParams(layoutParamsS);

        place = (Places) getIntent().getSerializableExtra("object");

        lat = place.getLatitude();
        lng = place.getLongitude();
        setUpMapIfNeeded();

        try {
            RequestQueue queue = Volley.newRequestQueue(this);
            JsonObjectRequest movieReq = new JsonObjectRequest(Request.Method.GET, part_url + place.getId() + end_url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String desc = response.getString("desc");
                                String info = response.getString("info");
                                String address = response.getString("address");
                                String worktime = response.getString("worktime");
                                String phone_number_link = Html.fromHtml(response.getString("phone_number_link")).toString();
                                String discount_s = Html.fromHtml(response.getString("discount_s")).toString();
                                String ref = response.getString("site");

                                if (info != null && !info.trim().equals("")) {
                                    addingInformation(info, R.drawable.info, 0);
                                }
                                if (address != null && !address.trim().equals("")) {
                                    addingInformation(address, R.drawable.house, 0);
                                }
                                if (worktime != null && !worktime.trim().equals("")) {
                                    addingInformation(worktime, R.drawable.clock, 0);
                                }
                                if (phone_number_link != null && !phone_number_link.trim().equals("")) {
                                    phone_number_link = phone_number_link.replaceAll("\n", "\t");

                                    addingInformation(phone_number_link, R.drawable.phone, TYPE_PHONE);
                                }
                                if (ref != null && !ref.trim().equals("")) {
                                    addingInformation(ref, R.drawable.web, TYPE_WEB);
                                }
                                if (discount_s != null && !discount_s.trim().equals("")) {
                                    addingInformation(discount_s, R.drawable.discount, 0);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //                VolleyLog.d(TAG, "Error: " + error.getMessage());
                }
            });
            queue.add(movieReq);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addingInformation(String s, int res, int type) {
        LinearLayout linearLayout = new LinearLayout(getApplicationContext());
        linearLayout.setGravity(Gravity.CENTER_VERTICAL);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(res);
        imageView.setPadding(10, 0, 0, 0);
        linearLayout.addView(imageView, 30, 30);
        TextView textView = new TextView(this);
        textView.setText(s);
        textView.setTextColor(Color.BLACK);
        textView.setPadding(20, 5, 0, 5);
        switch (type) {
            case TYPE_WEB: {
                Linkify.addLinks(textView, Linkify.WEB_URLS);
                break;
            }
            case TYPE_PHONE: {
                Linkify.addLinks(textView, Linkify.PHONE_NUMBERS);
                break;
            }
        }

        linearLayout.addView(textView);
        li_lay.addView(linearLayout);
    }

    private void setUpMapIfNeeded() {
        // Check if we were successful in obtaining the map.
        if (mMap != null) {
            LatLng latLng = new LatLng(lat, lng);
            mMap.setMyLocationEnabled(true);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));

            mMap.addMarker(new MarkerOptions()
                    .title(place.getName())
//                    .snippet("The most populous city in Australia.")
                    .position(latLng));
        }
    }

    private Double lat = 0.0;
    private Double lng = 0.0;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        actionBar.setDisplayShowCustomEnabled(true);
//        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setTitle("Назад");
//        TextView title =new TextView(getApplicationContext());
//        title.setText("Назад");
//        title.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
//        actionBar.setCustomView(title);

        return true;
    }

}
