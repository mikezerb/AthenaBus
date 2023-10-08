package com.example.athenabus.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import com.example.athenabus.common.Resource
import com.example.athenabus.domain.location.hasLocationPermission
import com.example.athenabus.domain.repository.LocationRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class LocationRepositoryImpl @Inject constructor(
    private val context: Context,
    private val client: FusedLocationProviderClient
) : LocationRepository {

    @SuppressLint("MissingPermission")
    override suspend fun getLocationUpdates(interval: Long): Flow<Resource<Location>> {
        return callbackFlow {
            if (!context.hasLocationPermission()) {
                throw LocationRepository.LocationException("Missing Location permissions")
            }

            val locationManager =
                context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val isNetworkEnabled =
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            if (!isGpsEnabled && !isNetworkEnabled) {
                send(Resource.Error("Missing Location Permission"))
                return@callbackFlow
            }

            send(Resource.Loading())

            send(
                suspendCancellableCoroutine { cont ->
                    val cancellationTokenSource = CancellationTokenSource()

                    client.getCurrentLocation(
                        Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                        cancellationTokenSource.token
                    ).addOnSuccessListener { location ->
                        if (location != null) {
                            cont.resume(Resource.Success(data = location))
                        } else {
                            cont.resume(Resource.Error(message = "Location is null"))
                        }
                    }.addOnFailureListener { exception ->
                        cont.resume(
                            Resource.Error(
                                message = exception.message ?: "Error on failure listener"
                            )
                        )
                    }.addOnCompleteListener {
                        cont.cancel()
                    }.addOnCanceledListener {
                        cont.cancel()
                    }
                    cont.invokeOnCancellation {
                        cont.cancel()
                    }
                }
            )
        }
    }
}