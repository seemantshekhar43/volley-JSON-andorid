package comandydevo.wixsite.seemantshekhar43.volleyjson;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.android.volley.Request.Method.GET;

public class MainActivity extends AppCompatActivity {

    /* paste the following in gradle(Module:app) in dependencies
    "compile 'com.android.volley:volley:1.0.0'"
*/

/* paste the following in AndroidManifest.xml
    <uses-permission android:name="android.permission.INTERNET"/>
 */
/* create a new file in java
        MySingleton
 */

    String myUrl = "http://samples.openweathermap.org/data/2.5/weather?zip=94040,us&appid=b1b15e88fa797225412429c1c50c122a1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // introducing our button
        Button button = (Button) findViewById(R.id.button);

        // on click listener
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //making json object request
                final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, myUrl, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                // lets watch what we have requested
                                Log.i("JSON","JSON object is: " + response);

                                // lets dig out some data from json object
                                try {
                                    String coor = response.getString("coord");

                                    // but coor is again a json object so dig out more. we define new jsonobject
                                   JSONObject co = new JSONObject(coor);
                                   String lon = co.getString("lon");
                                   String lat = co.getString("lat");

                                   // lets watch the output
                                    Log.i("Lon","Lon " + lon);
                                    Log.i("Lat","Lat " + lat);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                // lets dig out another data from json
                                try {
                                    String weather = response.getString("weather");

                                    //lets watch the data
                                    Log.i("weather","weather" + weather);

                                    // getting under weather array
                                    JSONArray array = new JSONArray(weather);

                                    // use loop to extract the elements of the array
                                    for (int i = 0; i<array.length(); i++){
                                        JSONObject parObject = array.getJSONObject(i);

                                        //lets watch the data
                                        Log.i("id","id " + parObject.getString("id"));
                                        Log.i("main","main " + parObject.getString("main"));

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                // lets watch the error if any
                                Log.i("Error","Something went wrong:" + error);
                            }
                        });
                MySingelton.getInstance(MainActivity.this).addToRequestQue(jsonObjectRequest);
            }
        });
    }
}
