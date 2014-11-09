package ffuxaq.gmail.com.app4;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.transform.Result;

/**
 * Created by ffuxaq on 09/11/2014.
 */
public class ForecastFragment extends Fragment {

    public ForecastFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {



        String[] my_list = {
                "Today --asdsadsd-",
                "Tomorrow -sdsa",
                "Wednasdey -dafdsfd",
                "Thursday -wadasda",
                "Friday --sadasdasd",
                "Saturday -sdsadsadsad",
                "Sunday - sdsadasd"


        };
        List<String> entriesForecast = new ArrayList<String>(Arrays.asList(my_list));

        ArrayAdapter<String> aa = new ArrayAdapter<String>(
                //the current context ...this fragment's parent activity
                getActivity(),
                //Id of list item layout
                R.layout.list_item_forecast,
                //ID of the textview to populate
                R.id.list_item_forecast_textview,
                //Forecast data
                entriesForecast);



        View rootView = inflater.inflate(R.layout.fragment_main,container);

        ListView lw = (ListView)rootView.findViewById(R.id.listview_forecast);
        lw.setAdapter(aa);

        return rootView;
    }

    public class FetchWeatherTask extends AsyncTask<URL,Integer,String>{


       @Override
        protected String doInBackground(URL... urls){
            // These two need to be declared outside the try/catch
// so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
// Will contain the raw JSON response as a string.
            String forecastJsonStr = null;
            try {
// Construct the URL for the OpenWeatherMap query
// Possible parameters are available at OWM's forecast API page, at
// http://openweathermap.org/API#forecast
                URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7");
// Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                //urlConnection.connect(); open connection
                // calls connect() con il metodo getInputStream
// Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
// Nothing to do.
                    forecastJsonStr = null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
// Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
// But it does make debugging a *lot* easier if you print out the completed
// buffer for debugging.
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
// Stream was empty. No point in parsing.
                    forecastJsonStr = null;
                }
                forecastJsonStr = buffer.toString();
            }
            catch(MalformedURLException mue){
                Log.e("PlaceholderFragment", "Error ",mue);
            }
            catch (IOException ioe) {
                Log.e("PlaceholderFragment", "Error ", ioe);
// If the code didn't successfully get the weather data, there's no point in attempting
// to parse it.
                forecastJsonStr = null;
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }

            return forecastJsonStr;
        }


    }
}
