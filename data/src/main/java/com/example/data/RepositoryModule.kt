package com.example.data

import com.example.domain.AuthorsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindRepository(
        repository: AuthorsRepositoryImpl
    ): AuthorsRepository
}