package com.example.athenabus.di

import android.app.Application
import androidx.room.Room
import com.example.athenabus.common.Constants
import com.example.athenabus.data.local.TelematicsDatabase
import com.example.athenabus.data.manger.LocalUserMangerImpl
import com.example.athenabus.data.remote.OASATelematicsAPI
import com.example.athenabus.data.repository.BusLineRepositoryImpl
import com.example.athenabus.domain.manger.LocalUserManger
import com.example.athenabus.domain.repository.BusLineRepository
import com.example.athenabus.domain.use_case.app_entry.AppEntryUseCases
import com.example.athenabus.domain.use_case.app_entry.ReadAppEntryUseCase
import com.example.athenabus.domain.use_case.app_entry.SaveAppEntryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
        return BusLineRepositoryImpl(api, db.dao)
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
}