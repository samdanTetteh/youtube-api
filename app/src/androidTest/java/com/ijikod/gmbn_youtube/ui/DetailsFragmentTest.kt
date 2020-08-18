package com.ijikod.gmbn_youtube.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.ijikod.gmbn_youtube.VideoFragmentFactory
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class DetailsFragmentTest {

    @Test
    fun is_VideoDurationFormatValid(){
        val videoFactory = VideoFragmentFactory()
        val scenario = launchFragmentInContainer<DetailsFragment>()
        scenario.onFragment {
            Assert.assertEquals(it.durationFormatting("PT30M49S"), "30min:49sec")
        }
    }

}