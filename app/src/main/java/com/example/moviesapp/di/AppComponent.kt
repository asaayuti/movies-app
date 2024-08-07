package com.example.moviesapp.di

import com.example.moviesapp.core.di.CoreComponent
import com.example.moviesapp.detail.DetailActivity
import com.example.moviesapp.favorite.FavoriteFragment
import com.example.moviesapp.home.HomeFragment
import dagger.Component

@AppScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [AppModule::class]
)
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): AppComponent
    }

    fun inject(fragment: HomeFragment)
    fun inject(fragment: FavoriteFragment)
    fun inject(activity: DetailActivity)

}