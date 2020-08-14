package com.ijikod.gmbn_youtube

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.ijikod.gmbn_youtube.data.Cache.VideoDatabase
import com.ijikod.gmbn_youtube.data.VideoDetailsRepository
import com.ijikod.gmbn_youtube.data.VideosRepository
import com.ijikod.gmbn_youtube.data.remote.HttpInstance
import com.ijikod.gmbn_youtube.ui.ViewModelFactory


/**
 * Class that handles object creation.
 * Like this, objects can be passed as parameters in the constructors and then replaced for
 * testing, where needed.
 */
object Injection {

    /**
     * Creates an instance of [VideosRepository] based on the [HttpInstance]
     */
    private fun provideVideoRepository(context: Context): VideosRepository {
        return VideosRepository(HttpInstance.create(), VideoDatabase.getInstance(context))
    }


    /**
     * Provides the [ViewModelProvider.Factory] that is then used to get a reference to
     * [ViewModel] objects.
     */
    fun provideViewModelFactory(context: Context): ViewModelProvider.Factory {
        return ViewModelFactory(provideVideoRepository(context))
    }
}
