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
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class LocationRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val client: FusedLocationProviderClient
) : LocationRepository {

    @SuppressLint("MissingPermission")
    override fun getCurrentLocation(): Flow<Resource<Location>> {
        return flow {
            if (!context.hasLocationPermission()) {
                emit(Resource.Error("Missing Location Permission"))
                return@flow
            }

            val locationManager =
                context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val isNetworkEnabled =
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            if (!isGpsEnabled && !isNetworkEnabled) {
                emit(Resource.Error("Location not enabled or network"))
                return@flow
            }

            emit(Resource.Loading())

            emit(
                suspendCancellableCoroutine { cont ->
                    val cancellationTokenSource = CancellationTokenSource()

                    client.getCurrentLocation(
                        Priority.PRIORITY_HIGH_ACCURACY,
                        cancellationTokenSource.token
                    ).addOnSuccessListener { location ->
                        if (location != null) {
                            cont.resume(Resource.Success(data = location))
                        } else {
                            cont.resume(Resource.Error(message = "Location is null"))
                            return@addOnSuccessListener
                        }
                    }.addOnFailureListener { exception ->
                        cont.resume(
                            Resource.Error(
                                message = exception.message ?: "Error on failure listener"
                            )
                        )
                        return@addOnFailureListener
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