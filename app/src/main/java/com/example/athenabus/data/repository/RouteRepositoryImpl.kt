package com.example.athenabus.data.repository

import android.util.Log
import com.example.athenabus.common.Resource
import com.example.athenabus.data.local.TelematicsLineDao
import com.example.athenabus.data.mapper.toRoute
import com.example.athenabus.data.remote.OASATelematicsAPI
import com.example.athenabus.domain.model.Route
import com.example.athenabus.domain.model.Stop
import com.example.athenabus.domain.repository.RouteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class RouteRepositoryImpl @Inject constructor(
    private val api: OASATelematicsAPI,
    private val lineDao: TelematicsLineDao
) : RouteRepository {
    override fun getRoutesByID(lineID: String): Flow<Resource<List<Route>>> = flow {
        emit(Resource.Loading())

        try {
            // Step 1: Get the list of LineCodes associated with the given lineID from the database
            val lineCodes = lineDao.getLineCodesFromLineID(lineID)
            Log.d("RouteRepositoryImpl", "lineCodes " + lineCodes.size + " with lineID: " + lineID)
            // Step 2: Fetch routes for each LineCode using API and collect results
            val routes = mutableListOf<Route>()
            for (lineCode in lineCodes) {
                val route = api.getRoutesForLine(lineCode = lineCode).map { it.toRoute() }
                routes.addAll(route)  // .filter { it.RouteType.contains("1") }  filter 1 to not repeat both ways routes f.e. from and to routes
            }
            Log.d("RouteRep", "Routes: " + routes.size)

            emit(Resource.Success(routes))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: "Error"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.message ?: "error"))
        }
    }

    override fun getStopsForRoute(routeCode: String): Flow<Resource<List<Stop>>> {
        TODO("Not yet implemented")
    }
}
