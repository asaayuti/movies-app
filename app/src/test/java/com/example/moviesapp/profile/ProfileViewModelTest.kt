package com.example.moviesapp.profile

import org.junit.Assert.assertNotNull
import org.junit.Test

class ProfileViewModelTest {

    @Test
    fun testProfileViewModelCreation() {
        val viewModel = ProfileViewModel()
        assertNotNull(viewModel)
    }
}
