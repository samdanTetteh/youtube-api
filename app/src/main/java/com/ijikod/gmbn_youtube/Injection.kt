package com.ijikod.gmbn_youtube

import androidx.lifecycle.ViewModelProvider
import com.ijikod.gmbn_youtube.repository.VideosRepository
import com.ijikod.gmbn_youtube.repository.remote.HttpInstance
import com.ijikod.gmbn_youtube.repository.remote.VideosApiService
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
    private fun provideVideoRepository(): VideosRepository {
        return VideosRepository(HttpInstance.create())
    }

    /**
     * Provides the [ViewModelProvider.Factory] that is then used to get a reference to
     * [ViewModel] objects.
     */
    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return ViewModelFactory(provideVideoRepository())
    }
}
