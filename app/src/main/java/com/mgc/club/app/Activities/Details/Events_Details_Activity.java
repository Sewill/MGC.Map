package com.mgc.club.app.Activities.Details;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.mgc.club.app.Application.AppController;
import com.mgc.club.app.Model.Events;
import com.mgc.club.app.R;

/**
 * Created by savva on 13.07.2015.
 */
public class Events_Details_Activity extends AppCompatActivity {
    private Events event = null;
    private TextView note = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);

        event = (Events) getIntent().getSerializableExtra("object");

        ImageLoader imageLoader = AppController.getInstance().getImageLoader();

        NetworkImageView thumbNail = (NetworkImageView) findViewById(R.id.details_thumbnail);
        thumbNail.setDefaultImageResId(R.drawable.logo_mgc);
        if (event.getEventcover() != null) {
            thumbNail.setImageUrl(event.getEventcover(), imageLoader);
        }

        TextView theme = (TextView) findViewById(R.id.details_theme);

        theme.setText(event.getName() + "\n\n" + event.getStart() + "\n" + event.getFinish());

        note = (TextView) findViewById(R.id.details_note);

        if (event.getText() != null) {
            Spanned spannedText = Html.fromHtml(event.getText());
            note.setText(spannedText.toString());
        }

//        if (certificates.getDesc() == null) {
//
//            try {
//                RequestQueue queue = Volley.newRequestQueue(this);
//                if(certificates.getUrl()!=null) {
//                    JsonObjectRequest movieReq = new JsonObjectRequest(Request.Method.GET, certificates.getUrl(), null,
//                            new Response.Listener<JSONObject>() {
//                                @Override
//                                public void onResponse(JSONObject response) {
//                                    try {
//                                        Spanned spannedText = Html.fromHtml("<meta charset=\"utf-8\">" + response.getString("desc"));
//                                        certificates.setDesc(spannedText.toString());
//                                        note.setText(certificates.getDesc());
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//
//                                }
//                            }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            //                VolleyLog.d(TAG, "Error: " + error.getMessage());
//                        }
//                    });
////            AppController.getInstance().addToRequestQueue(movieReq);
//                    queue.add(movieReq);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else {
//            note.setText(certificates.getDesc());
//        }
    }

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
