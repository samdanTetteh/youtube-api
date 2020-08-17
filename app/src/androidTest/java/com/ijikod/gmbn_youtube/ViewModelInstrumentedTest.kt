package com.ijikod.gmbn_youtube

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ijikod.gmbn_youtube.TestUtils.Mokito
import com.ijikod.gmbn_youtube.data.modules.Item
import com.ijikod.gmbn_youtube.ui.DetailsFragment
import com.ijikod.gmbn_youtube.vm.VideosListViewModel
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
class ViewModelInstrumentedTest {


    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var vm : VideosListViewModel


    private  val observer: Observer<PagedList<Item>> = Mokito().mock()


    @Before
    fun setUp(){
        vm = VideosListViewModel(Injection.provideVideoRepository(ApplicationProvider.getApplicationContext()))

    }


    @Test
    fun testModelStateChange(){
        vm.videos.observeForever(observer)
        vm.getNewVideos(true)

        Mockito.verify(observer).onChanged(vm.videos.value)
    }


    @Test
    fun testForReturnValues(){
        vm.videos.observeForever(observer)
        vm.getNewVideos(true)

        vm.videos.value?.isNotEmpty()?.let { assert(it) }
    }

}