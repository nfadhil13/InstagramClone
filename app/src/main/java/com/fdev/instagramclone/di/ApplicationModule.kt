package com.fdev.instagramclone.di


import android.content.Context
import androidx.room.Room
import com.fdev.instagramclone.framework.datasource.cache.database.Database
import com.fdev.instagramclone.framework.datasource.cache.database.UserDao
import com.fdev.instagramclone.util.SessionManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ApplicationModule {

    @Provides
    fun provideFirestore() : FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    fun provideFirebaseAuth() : FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Singleton
    @Provides
    fun provideSessionManager(firebaseauth : FirebaseAuth) : SessionManager {
        return SessionManager(firebaseauth)
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context : Context) : Database {
        return Room
                .databaseBuilder(
                        context,
                        Database::class.java,
                        Database.DATABASE_NAME
                )
                .build()
    }


    @Provides
    fun provideUserDao(database: Database) : UserDao{
        return database.userDao()
    }

}