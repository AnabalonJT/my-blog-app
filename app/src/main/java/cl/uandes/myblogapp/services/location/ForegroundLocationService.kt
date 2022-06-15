package cl.uandes.myblogapp.services.location

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices

class ForegroundLocationService(
  private val context: Context,
  private val locationCallback: LocationCallback
) {

  private var fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

  fun stopLocationUpdates() {
    fusedLocationClient.removeLocationUpdates(locationCallback)
  }

  @SuppressLint("MissingPermission")
  fun startLocationUpdates() {
    askPermissionsIfNotGranted(context)
    fusedLocationClient.requestLocationUpdates(
      locationRequest,
      locationCallback,
      Looper.myLooper()!!
    )
  }

  companion object {
    private const val FOREGROUND_PERMISSIONS_REQUEST_CODE = 34
    val locationRequest: LocationRequest = LocationRequest.create().apply {
      interval = 5000
      smallestDisplacement = 1F
      priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    fun askPermissionsIfNotGranted(context: Context){
      if (!permissionGranted(context)) {
        Toast.makeText(context, "Need location permissions", Toast.LENGTH_LONG).show()
        ActivityCompat.requestPermissions(context as Activity, arrayOf(
          Manifest.permission.ACCESS_FINE_LOCATION,
          Manifest.permission.ACCESS_COARSE_LOCATION
        ), FOREGROUND_PERMISSIONS_REQUEST_CODE)
      }
    }

    private fun permissionGranted(context: Context) : Boolean {
      val coarseLocationPermission = ActivityCompat
        .checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
      val fineLocationPermission = ActivityCompat
        .checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)


      return coarseLocationPermission == PackageManager.PERMISSION_GRANTED &&
          fineLocationPermission == PackageManager.PERMISSION_GRANTED
    }
  }

}
