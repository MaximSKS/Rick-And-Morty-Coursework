package com.example.rickandmortycoursework.di

import android.app.Application
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.example.rickandmortycoursework.data.local.RickAndMortyDatabase
import com.example.rickandmortycoursework.data.remote.RickyAndMortyApi
import com.example.rickandmortycoursework.data.repository.RickAndMortyImpl
import com.example.rickandmortycoursework.domain.repository.RickAndMortyRepository
import com.example.rickandmortycoursework.util.Constant.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRickyAndMortyApi(): RickyAndMortyApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(RickyAndMortyApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRickAndMortyRepository(
        api: RickyAndMortyApi,
        db: RickAndMortyDatabase
    ): RickAndMortyRepository {
        return RickAndMortyImpl(api, db.rickMortyDao)
    }

    @Provides
    @Singleton
    fun provideRickAndMortyDatabase(app: Application): RickAndMortyDatabase {
        return Room.databaseBuilder(app, RickAndMortyDatabase::class.java, "FavoriteDatabase")
            .build()
    }

}

