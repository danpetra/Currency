package com.example.currency.di

import android.content.Context
import com.example.currency.ui.favourites.FavouritesViewModel
import com.example.currency.ui.settings.SettingsViewModel
import com.example.currency.ui.popular.PopularViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [
    DatabaseModule::class,
    NetworkModule::class,
    DataModule::class,
])
@Singleton
interface AppComponent {
    fun injectPopularViewModel(viewModel: PopularViewModel)
    fun injectFavouritesViewModel(viewModel: FavouritesViewModel)
    fun injectSettingsViewModel(viewModel: SettingsViewModel)
    @Component.Builder
    interface Builder{
        @BindsInstance
        fun context(context: Context): Builder
        fun build(): AppComponent
    }
}