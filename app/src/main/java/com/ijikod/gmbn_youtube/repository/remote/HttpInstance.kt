package com.ijikod.gmbn_youtube.repository.remote

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


const val CHANNEL_ID = "UC_A--fhX5gea0i4UtpD99Gg"
const val API_KEY = "AIzaSyAIFsPu_nHRo8yNxOt8QU8R3szAvPdUqbg"
class HttpInstance {


    /**
     * Retrofit singleton instance using [MoshiConverterFactory]
     * **/
    companion object{
        private const val BASE_URL = "https://www.googleapis.com/youtube/v3/"

        /**
         *Initialise retrofit instance to be used for http requests
         * **/
        fun create() : VideosApiService{
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(VideosApiService::class.java)
        }
    }
}