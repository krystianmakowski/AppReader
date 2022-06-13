package com.makowski.appreader.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.makowski.appreader.network.BooksApi
import com.makowski.appreader.repository.BookRepository
import com.makowski.appreader.repository.FireRepository
import com.makowski.appreader.utils.Constants
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

//    @Singleton
//    @Provides
//    fun provideBookRepository(api: BooksApi) = BookRepository(api)
    @Singleton
    @Provides
    fun provideFireBookRepository() = FireRepository(queryBook = FirebaseFirestore.getInstance()
        .collection("books"))

    @Singleton
    @Provides
    fun provideBookApi():BooksApi{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BooksApi::class.java)
    }
}