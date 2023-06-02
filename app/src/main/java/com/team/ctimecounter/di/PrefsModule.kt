package com.team.ctimecounter.di

import android.app.Application
import com.team.ctimecounter.utils.PrefsManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PrefsModule
@Inject
constructor() {
    @Singleton
    @Provides
    fun providePrefsManager(app: Application): PrefsManager {
        return PrefsManager(app = app)
    }
}