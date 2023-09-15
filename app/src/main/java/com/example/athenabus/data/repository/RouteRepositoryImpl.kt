package com.example.athenabus.data.repository

import android.util.Log
import com.example.athenabus.common.Resource
import com.example.athenabus.data.mapper.toRoute
import com.example.athenabus.data.remote.OASATelematicsAPI
import com.example.athenabus.domain.model.Route
import com.example.athenabus.domain.repository.RouteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RouteRepositoryImpl @Inject constructor(
    private val api: OASATelematicsAPI
) : RouteRepository {

    override fun getRoutesByID(lineID: String): Flow<Resource<List<Route>>> = flow {
        emit(Resource.Loading())

        try {
            Log.d("text", "Line Id is : $lineID")
            val busRoutes = api.getRoutesForLine(lineCode = lineID).map { it.toRoute() }
            emit(Resource.Loading(data = busRoutes))
            emit(Resource.Success(busRoutes))
        } catch (e: HttpException) {
            emit(
                Resource.Error(message = e.localizedMessage ?: "error")
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(message = "Internet connection error")
            )
        }


    }
}