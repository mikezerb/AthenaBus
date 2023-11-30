package com.example.athenabus.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.athenabus.common.Constants
import com.example.athenabus.data.local.TelematicsDatabase
import com.example.athenabus.data.location.DefaultLocationTracker
import com.example.athenabus.data.manger.LocalUserMangerImpl
import com.example.athenabus.data.remote.OASATelematicsAPI
import com.example.athenabus.data.repository.BusLineRepositoryImpl
import com.example.athenabus.data.repository.LocationRepositoryImpl
import com.example.athenabus.data.repository.RouteRepositoryImpl
import com.example.athenabus.domain.location.LocationTracker
import com.example.athenabus.domain.manger.LocalUserManger
import com.example.athenabus.domain.repository.BusLineRepository
import com.example.athenabus.domain.repository.LocationRepository
import com.example.athenabus.domain.repository.RouteRepository
import com.example.athenabus.domain.use_case.app_entry.AppEntryUseCases
import com.example.athenabus.domain.use_case.app_entry.ReadAppEntryUseCase
import com.example.athenabus.domain.use_case.app_entry.SaveAppEntryUseCase
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOASATelematicsApi(): OASATelematicsAPI {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OASATelematicsAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideLineRepository(
        api: OASATelematicsAPI,
        db: TelematicsDatabase
    ): BusLineRepository {
        return BusLineRepositoryImpl(api, db.linesDao, db.favoritesDao)
    }

    @Provides
    @Singleton
    fun provideRouteRepository(
        api: OASATelematicsAPI,
        db: TelematicsDatabase
    ): RouteRepository {
        return RouteRepositoryImpl(api, db.linesDao)
    }

    @Provides
    @Singleton
    fun provideTelematicsDatabase(app: Application): TelematicsDatabase {
        return Room.databaseBuilder(
            app,
            TelematicsDatabase::class.java,
            "telematics.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideLocalUserManger(app: Application): LocalUserManger = LocalUserMangerImpl(app)

    @Provides
    @Singleton
    fun provideAppEntryUseCases(localUserManger: LocalUserManger) = AppEntryUseCases(
        readAppEntryUseCase = ReadAppEntryUseCase(localUserManger),
        saveAppEntryUseCase = SaveAppEntryUseCase(localUserManger)
    )

    @Provides
    fun provideDataStoreUtil(@ApplicationContext context: Context): DataStoreUtil =
        DataStoreUtil(context)

    @Provides
    @Singleton
    fun providesLocationRepository(
        @ApplicationContext context: Context,
        client: FusedLocationProviderClient
    ): LocationRepository = LocationRepositoryImpl(context, client)

    @Provides
    @Singleton
    fun providesFusedLocationProviderClient(
        application: Application
    ): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(application)


    @Provides
    @Singleton
    fun providesLocationTracker(
        fusedLocationProviderClient: FusedLocationProviderClient,
        application: Application
    ): LocationTracker = DefaultLocationTracker(
        fusedLocationProviderClient = fusedLocationProviderClient,
        application = application
    )
}