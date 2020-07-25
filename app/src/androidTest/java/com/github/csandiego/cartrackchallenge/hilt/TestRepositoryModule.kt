package com.github.csandiego.cartrackchallenge.hilt

import com.github.csandiego.cartrackchallenge.repository.CredentialRepository
import com.github.csandiego.cartrackchallenge.repository.TestCredentialRepository
import com.github.csandiego.cartrackchallenge.repository.TestUserRepository
import com.github.csandiego.cartrackchallenge.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
interface TestRepositoryModule {

    @Singleton
    @Binds
    fun credentialRepository(r: TestCredentialRepository): CredentialRepository

    @Singleton
    @Binds
    fun userRepository(r: TestUserRepository): UserRepository
}