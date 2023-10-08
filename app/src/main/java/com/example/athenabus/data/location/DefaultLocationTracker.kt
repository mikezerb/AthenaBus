package com.example.athenabus.data.location

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.example.athenabus.domain.location.LocationTracker
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine

class DefaultLocationTracker(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val application: Application,
) : LocationTracker {

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getCurrentLocation(): Location? {
        val hasAccessFineLocationPermission = ContextCompat.checkSelfPermission(
            application,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val hasAccessCoarseLocationPermission = ContextCompat.checkSelfPermission(
            application,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val locationManager = application.getSystemService(
            Context.LOCATION_SERVICE
        ) as LocationManager

        val isGpsEnabled = locationManager
            .isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        if (!isGpsEnabled && !(hasAccessCoarseLocationPermission || hasAccessFineLocationPermission)) {
            return null
        }

        return suspendCancellableCoroutine { cont ->
            val cancellationTokenSource = CancellationTokenSource()
            fusedLocationProviderClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                cancellationTokenSource.token
            ).addOnSuccessListener { location ->
                if(location != null){
                    cont.resume(location) {} // Resume coroutine with location result
                }else {
                    cont.resume(null) {} // Resume coroutine with null location result
                }
            }.addOnCompleteListener {
                cont.cancel() // Cancel the coroutine
            }.addOnFailureListener {
                cont.resume(null) {} // Resume coroutine with null location result
            }.addOnCanceledListener {
                cont.cancel() // Cancel the coroutine
            }
        }
    }
}