package com.pankajkcodes.omdbapisearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View


import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.pankajkcodes.omdbapisearch.databinding.ActivityMainBinding
import org.json.JSONObject
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var requestQueue: RequestQueue? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestQueue = Volley.newRequestQueue(this)
        binding.button.setOnClickListener {
            val data = binding.searchView.text.trim().toString()
            if (data != "") {
                val url = "https://www.omdbapi.com/?apikey=&t=$data"
                val jsonObjectRequest =
                    JsonObjectRequest(
                        Request.Method.GET, url, null,
                        { response: JSONObject ->
                            try {
                                binding.title.text = response.getString("Title")
                                binding.actors.text = response.getString("Actors")
                                binding.runtime.text = response.getString("Runtime")
                                binding.rating.text = response.getString("imdbRating")
                                binding.year.text = response.getString("Year")
                                binding.genre.text = response.getString("Genre")
                                binding.plot.text = response.getString("Plot")
                                binding.language.text = response.getString("Language")
                                Glide.with(this).load(response.getString("Poster"))
                                    .into(binding.image);
                                binding.view.visibility = View.VISIBLE
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    ) { error: VolleyError ->
                        println("Error  " + error.message)
                        Toast.makeText(
                            applicationContext,
                            error.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                requestQueue!!.add(jsonObjectRequest)
            } else {
                Toast.makeText(
                    applicationContext,
                    "Type...",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


    }
}