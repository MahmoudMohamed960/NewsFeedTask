package com.example.newsFeedsApp

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun createArticleApi(
        remoteDataSource: ApiClient,
        @ApplicationContext context: Context
    ): ArticleApi {
        return remoteDataSource.buildApi(ArticleApi::class.java, context)
    }

}