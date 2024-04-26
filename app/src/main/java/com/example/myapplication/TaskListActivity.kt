package com.example.myapplication

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

class TaskListActivity : AppCompatActivity() , SensorEventListener {
    var testLoc = Location("test")
    private lateinit var sensorManager : SensorManager
    private lateinit var gyroscope: Sensor

    private var client: FusedLocationProviderClient? = null
    private var locationRequest: LocationRequest? = null
    private var locationCallback: LocationCallback? = null

    private lateinit var compass: ImageView
    private var dist: TextView? = null
    private val updateRate: Long = 1000


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) as Sensor
        val tasks = intent.getStringArrayExtra("tasks")?: arrayOf()
        val longitude = intent.getDoubleExtra("long" , 0.0)
        val latitude = intent.getDoubleExtra("lat" , 0.0)
        val listView = findViewById<ListView>(R.id.listView)
        testLoc.longitude = longitude
        testLoc.latitude = latitude
        println(testLoc.longitude)
        println(testLoc.latitude)
        listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, tasks)
        compass = findViewById(R.id.compasspointer)
        dist = findViewById(R.id.distance)
        println("potato")
        if (hasLocationPermission()) {
            findBearing()
            findLocation()
        }
    }
    @SuppressLint("MissingPermission")
    private fun findBearing(){
        client = LocationServices.getFusedLocationProviderClient(this)
        client!!.lastLocation?.addOnSuccessListener(this) {
                location ->
            compass!!.rotation = location.bearingTo(testLoc)
        }
    }
    private fun findLocation() {
        locationRequest =
            LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
                .setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(1000)
                .build()
// Create location callback
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                for (location in locationResult.locations) {
                    dist!!.text = location.distanceTo(testLoc).toString()
                }
            }
        }
        client = LocationServices.getFusedLocationProviderClient(this)
    }
    private fun hasLocationPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_DENIED
        ) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            return false
        }
        return true
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            findLocation()
            findBearing()
        }
    }

    @SuppressLint("MissingPermission")
    override fun onResume() {
        super.onResume()
        findBearing()
        sensorManager.registerListener(this, this.gyroscope ,SensorManager.SENSOR_DELAY_NORMAL/3) //Makes the compass rotate smoothly
        if (hasLocationPermission()) {
            client?.requestLocationUpdates(
                locationRequest!!, locationCallback!!, Looper.getMainLooper())
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager?.unregisterListener(this)
        client?.removeLocationUpdates(locationCallback!!)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        when (event?.sensor?.type) {
            Sensor.TYPE_GYROSCOPE->{
                compass!!.rotation += event.values[2]
            }
            //Have no idea if this actually will work, still gonna try it.
            Sensor.TYPE_ACCELEROMETER->{
                findBearing()
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        null
    }

}
