package cl.uandes.myblogapp.services.location

import android.content.Context
import android.location.Location
import androidx.lifecycle.LiveData
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult

class ForegroundLocationLiveData(context: Context) : LiveData<Location>() {

  private val locationCallback = object : LocationCallback() {
    override fun onLocationResult(locationResult: LocationResult) {
      for (location in locationResult.locations) {
        // set livedata value
        value = location
      }
    }
  }
  private val locationService = ForegroundLocationService(context, locationCallback)

  override fun onActive() {
    super.onActive()
    locationService.startLocationUpdates()
  }

  override fun onInactive() {
    super.onInactive()
    locationService.stopLocationUpdates()
  }
}
