package com.example.androidweek18;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class objectFragment extends Fragment {

    public static final String ARG_OBJECT = "object";
    private String searchKey = "Global";

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle savedInstanceState) {
        return layoutInflater.inflate(R.layout.object_layout, parent, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.i("fragmentapp","ObjectFragment Created!");

        // We get the page Number from CollectionPagerAdapter.
        final Bundle args = getArguments();
        TextView textView = view.findViewById(R.id.objectTextField);
        textView.setText(Integer.toString(args.getInt(ARG_OBJECT)));

        final ListView listView = view.findViewById(R.id.fragmentListView);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url ="https://api.covid19api.com/summary";

        // Request a string response from the provided URL.
        // I think you can use JSONRequest, but this works fine here.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("fragmentapp","Calling API");
                        try {
                            // Here we get some JSON data from the url.
                            // We search for a keyword and put that data in a String array and split it.
                            // We put it in the list

                            // We could make a switch case here to specify what data goes in what page view.
                            // Right now it just the first page and the rest show NOPE.

                            JSONObject infodata = new JSONObject(response);
                            if (infodata.has(searchKey)){
                                if (args.getInt("OBJECT")==1) {
                                    String searchedData = infodata.optString(searchKey);
                                    String[] data = searchedData.split(",");
                                    Log.i("fragmentapp", searchedData);
                                    ListAdapter adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, data);
                                    listView.setAdapter(adapter);
                                }else {
                                    String[] data = {"NOPE"};
                                    ListAdapter adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, data);
                                    listView.setAdapter(adapter);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("fragmentapp","API called failed !");
            }
        });
        queue.add(stringRequest);

    }
}
