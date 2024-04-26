package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dictionaries: List<Map<String, Any>> = listOf(
            mapOf(
                "Hagg - saur Hall" to listOf(
                    "Look at Nice View From Window",
                    "Relax on find the sneeky hidout spot under the stairs",
                    "take datamining (the better class)"
                ),
                "lat" to 47.48277172545428,
                "long" to -94.87309213603913
            ),
            mapOf(
                "Bridgeman Hall" to listOf(
                    "Find lab 214 with dual monitors",
                    "Find 3D printing room",
                    "Find VR room",
                    "Find Elias crying about why his class should be in 214",
                    "Enjoy comfy couches",
                    "Laugh at cs majors"
                ),
                "lat" to 47.483251867230194,
                "long" to -94.87369208839122
            ),
            mapOf(
                "A.C Clark Library" to listOf(
                    "Ask for a job",
                    "Meet the greatest guy on earth T",
                    "Choose 225, 110, and or 319 as one of the study rooms",
                    "Check out a book",
                    "Check out 4th floor"
                ),
                "lat" to 47.483190897082714,
                "long" to -94.8745153312684
            ),

            mapOf(
                "Memorial Hall" to listOf(
                    "Find the Future mcDonald Workers",
                    "find Money",
                    "Take Money",
                    "Buy monitors with taken money"
                ),
                "lat" to 47.48219734690079,
                "long" to -94.87485529811268
            ),

            mapOf(
                "Sattgast Hall" to listOf(
                    "Survive classes",
                    "Throw shade at someone for not showing to class",
                    "Enjoy the beuaty of the world while on a screen"
                ),
                "lat" to 47.48184371199949,
                "long" to -94.87453502279689
            ),

            mapOf(
                "Bangsberg Hall" to listOf(
                    "Find the future Hobbiests",
                    "Attend a concert",
                    "Enjoy the creepy architecture"
                ),
                "lat" to 47.48079747912119,
                "long" to -94.87570838291069
            ),

            mapOf(
                "Bensen Hall" to listOf(
                    "Don't burn down the building",
                    "Meet a nursing major during finals"
                ),
                "lat" to 47.48277172545428,
                "long" to -94.87309213603913
            ),
        )
        val locations: List<Location> = dictionaries.map { map ->
            Location(
                name = map.keys.first(),
                activities = map[map.keys.first()] as List<String>,
                lat = map["lat"] as Double,
                long = map["long"] as Double
            )
        }


        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        println("are you the thing breaking")
        recyclerView.adapter = LocationAdapter(locations)
    }
}