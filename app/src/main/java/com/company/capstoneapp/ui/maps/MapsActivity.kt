package com.company.capstoneapp.ui.maps

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.company.capstoneapp.R
import com.company.capstoneapp.databinding.ActivityMapsBinding
import com.company.capstoneapp.dataclass.Culinary
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var database: DatabaseReference
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Firebase.database.reference

        val toolbar = binding.toolbar as androidx.appcompat.widget.Toolbar
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar()?.setDisplayShowHomeEnabled(true);
        getSupportActionBar()?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_ios_white_24);

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        // konfigurasi gmaps
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true
        setMapStyle()

        // beri penanda lokasi real terkini pengguna
        markUserLocation()

        // beri penanda lokasi dummy terkini pengguna
        val userLocationDummy = LatLng(-0.4791039926444693, 117.18969923573819)
        mMap.addMarker(MarkerOptions().position(userLocationDummy).title("Lokasi Dummy Anda"))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocationDummy, 18f))

        // dapatkan titik-titik lokasi kuliner di sekitar
        getDataCulinary()
    }

    private fun getDataCulinary() {
        database.child("makanan").limitToFirst(10).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (data in snapshot.children) {
                    val culinaryName = data.child("name").value.toString()
                    val latitude = data.child("latitude").value.toString().toDouble()
                    val longitude = data.child("longitude").value.toString().toDouble()
                    val location = LatLng(latitude, longitude)

                    // beri penanda kuliner di sekitar lokasi pengguna
                    markCulinaryLocation(culinaryName, location)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("LOHE", "WKWKWKW EROR")
            }

        })
    }

    private fun setMapStyle() {
        try {
            val success =
                mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))
            if (!success) {
                Log.e("STYLE_MAPS", "Style parsing failed.")
            }
        } catch (exception: Resources.NotFoundException) {
            Log.e("STYLE_MAPS", "Can't find style. Error: ", exception)
        }
    }

    private fun markUserLocation() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true

            fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
                if (location != null) {
                    lastLocation = location
                    val userLocationReal = LatLng(location.latitude, location.longitude)
                    mMap.addMarker(MarkerOptions().position(userLocationReal).title("Lokasi Real Anda"))
                }
            }
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun markCulinaryLocation(culinaryName: String, location: LatLng) {
        val markerOptions = MarkerOptions()
            .position(location)
            .title(culinaryName)
            .icon(vectorToBitmap(R.drawable.ic_culinary_marker, Color.parseColor("#FBB97F")))

        mMap.addMarker(markerOptions)
    }

    private fun vectorToBitmap(@DrawableRes id: Int, @ColorInt color: Int): BitmapDescriptor {
        val vectorDrawable = ResourcesCompat.getDrawable(resources, id, null)

        if (vectorDrawable == null) {
            Log.e("BitmapHelper", "Resource not found")
            return BitmapDescriptorFactory.defaultMarker()
        }

        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        DrawableCompat.setTint(vectorDrawable, color)
        vectorDrawable.draw(canvas)

        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId === android.R.id.home) {
            finish() // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item)
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                markUserLocation()
            }
        }
}