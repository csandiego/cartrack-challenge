package com.github.csandiego.cartrackchallenge.hilt

import com.github.csandiego.cartrackchallenge.repository.CredentialRepository
import com.github.csandiego.cartrackchallenge.repository.DefaultCredentialRepository
import com.github.csandiego.cartrackchallenge.repository.DefaultUserRepository
import com.github.csandiego.cartrackchallenge.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
interface RepositoryModule {

    @Singleton
    @Binds
    fun credentialRepository(r: DefaultCredentialRepository): CredentialRepository

    @Singleton
    @Binds
    fun userRepository(r: DefaultUserRepository): UserRepository
}