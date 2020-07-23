package com.github.csandiego.cartrackchallenge.hilt

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.github.csandiego.cartrackchallenge.dao.CredentialDao
import com.github.csandiego.cartrackchallenge.dao.UserDao
import com.github.csandiego.cartrackchallenge.retrofit.RetrofitUserDao
import com.github.csandiego.cartrackchallenge.retrofit.UserService
import com.github.csandiego.cartrackchallenge.room.CartrackChallengeDatabase
import com.github.csandiego.cartrackchallenge.worker.SeedDatabaseWorker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DaoModule {

    @Singleton
    @Provides
    fun database(@ApplicationContext context: Context): CartrackChallengeDatabase =
        Room.databaseBuilder(context, CartrackChallengeDatabase::class.java, "CartrackChallenge")
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
                    WorkManager.getInstance(context).enqueue(request)
                }
            })
            .build()

    @Singleton
    @Provides
    fun credentialDao(database: CartrackChallengeDatabase): CredentialDao = database.credentialDao()

    @Singleton
    @Provides
    fun retrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun userService(retrofit: Retrofit): UserService = retrofit.create(UserService::class.java)

    @Singleton
    @Provides
    fun userDao(service: UserService): UserDao = RetrofitUserDao(service)
}