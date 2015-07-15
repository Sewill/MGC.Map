package com.mgc.club.app.Activities.Details;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Display;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mgc.club.app.Application.AppController;
import com.mgc.club.app.Model.Places;
import com.mgc.club.app.R;
import org.json.JSONException;
import org.json.JSONObject;

public class Places_Details_Activity extends AppCompatActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    private Places place;
    private TextView note;
    NetworkImageView thumbNail;

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
        int height = point.y / 2;
        ViewGroup.LayoutParams layoutParams = supportMapFragment.getView().getLayoutParams();
        layoutParams.height = height;
        supportMapFragment.getView().setLayoutParams(layoutParams);


        ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);

        ViewGroup.LayoutParams layoutParamsS = scrollView.getLayoutParams();
        layoutParamsS.height = height;
        scrollView.setLayoutParams(layoutParamsS);

        place = (Places) getIntent().getSerializableExtra("object");


        thumbNail = (NetworkImageView) findViewById(R.id.details_places);
        thumbNail.setDefaultImageResId(R.drawable.icon_loading);

        TextView theme = (TextView) findViewById(R.id.details_theme_place);

        theme.setText(place.getName());

        note = (TextView) findViewById(R.id.details_note_place);

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
//                                    Spanned spannedText = Html.fromHtml("<meta charset=\"utf-8\">" + response.getString("desc"));

                                ImageLoader imageLoader = AppController.getInstance().getImageLoader();

                                String desc = response.getString("desc");
                                String info = response.getString("info");
                                String address = response.getString("address");
                                String worktime = response.getString("worktime");
                                String phone_number_link = Html.fromHtml(response.getString("phone_number_link")).toString();
                                String discount_s = Html.fromHtml(response.getString("discount_s")).toString();

                                String url_medium = ((JSONObject) ((JSONObject) ((JSONObject) response.get("placelogo")).get("placelogo")).get("medium")).getString("url");

                                thumbNail.setImageUrl(url_medium, imageLoader);
//                                    certificates.setDesc(spannedText.toString());
                                note.setText(desc + "\n" + info + "\n" + address + "\n" + worktime + "\n" + phone_number_link + "\n" + discount_s);


//                                    setUpMapIfNeeded();
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
//            AppController.getInstance().addToRequestQueue(movieReq);
            queue.add(movieReq);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void setUpMapIfNeeded() {
        // Check if we were successful in obtaining the map.
        if (mMap != null) {
            LatLng latLng = new LatLng(lat, lng);
            mMap.setMyLocationEnabled(true);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));

            mMap.addMarker(new MarkerOptions()
                    .title(place.getName())
                    .snippet("The most populous city in Australia.")
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
