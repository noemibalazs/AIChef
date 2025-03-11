package com.noemi.aichef.di

import android.content.Context
import androidx.room.Room
import com.noemi.aichef.observer.AppObserver
import com.noemi.aichef.provider.DispatcherProvider
import com.noemi.aichef.provider.DispatcherSourceProvider
import com.noemi.aichef.repository.RecipeRepository
import com.noemi.aichef.repository.RecipeRepositoryImpl
import com.noemi.aichef.room.RecipeDAO
import com.noemi.aichef.room.RecipeDataBase
import com.noemi.aichef.room.SuggestedDAO
import com.noemi.aichef.service.RecipeService
import com.noemi.aichef.service.RecipeServiceImpl
import com.noemi.aichef.util.Constants.CHEF_DB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AIChefModule {

    @Singleton
    @Provides
    fun provideDispatcher(): DispatcherProvider = DispatcherSourceProvider

    @Singleton
    @Provides
    fun providesScope(provider: DispatcherProvider): CoroutineScope = CoroutineScope(provider.default() + SupervisorJob())

    @Provides
    @Singleton
    fun providesObserver(
        repository: RecipeRepository,
        scope: CoroutineScope
    ): AppObserver = AppObserver(repository, scope)

    @Provides
    @Singleton
    fun providesRecipeDataBase(@ApplicationContext context: Context): RecipeDataBase =
        Room.databaseBuilder(context, RecipeDataBase::class.java, CHEF_DB)
            .fallbackToDestructiveMigration(false)
            .build()

    @Provides
    @Singleton
    fun providesRecipeDAO(dataBase: RecipeDataBase): RecipeDAO = dataBase.provideRecipeDao()

    @Provides
    @Singleton
    fun providesSuggestedRecipeDAO(dataBase: RecipeDataBase): SuggestedDAO = dataBase.provideSuggestedDao()

    @Provides
    @Singleton
    fun providesJson() = Json {
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys = true
        explicitNulls = false
    }

    @Singleton
    @Provides
    fun providesKtorClient(json: Json): HttpClient = HttpClient(Android.create()) {

        install(Logging) {
            level = LogLevel.ALL
        }

        install(ContentNegotiation) {
            json(json)
        }
    }


    @Provides
    @Singleton
    fun providesRecipesService(
        httpClient: HttpClient,
        dispatcherProvider: DispatcherProvider
    ): RecipeService = RecipeServiceImpl(httpClient, dispatcherProvider)

    @Provides
    @Singleton
    fun providesRecipeRepository(
        recipeDAO: RecipeDAO,
        suggestedDAO: SuggestedDAO,
        service: RecipeService, json: Json
    ): RecipeRepository =
        RecipeRepositoryImpl(recipeDAO = recipeDAO, suggestedDAO = suggestedDAO, recipeService = service, json)
}