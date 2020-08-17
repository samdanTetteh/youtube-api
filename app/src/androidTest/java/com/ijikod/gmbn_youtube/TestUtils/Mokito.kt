package com.ijikod.gmbn_youtube.TestUtils

import org.mockito.Mockito

class Mokito {
    inline fun <reified T> mock(): T = Mockito.mock(T::class.java)
}