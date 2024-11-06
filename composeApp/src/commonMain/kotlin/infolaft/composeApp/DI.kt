package infolaft.composeApp

import infolaft.composeApp.core.delegate.UserViewModelDelegateImpl
import infolaft.composeApp.data.BuildConfig
import infolaft.composeApp.data.InfolaftCache
import infolaft.composeApp.data.InfolaftRepository
import infolaft.composeApp.data.InfolaftService
import infolaft.composeApp.ui.flow.guide.GuideViewModel
import infolaft.composeApp.ui.flow.home.HomeViewModel
import infolaft.composeApp.ui.flow.login.LoginViewModel
import infolaft.composeApp.use_case.ClearSessionUseCase
import infolaft.composeApp.use_case.GetAccountUseCase
import infolaft.composeApp.use_case.GetAreasUseCase
import infolaft.composeApp.use_case.GetUserUseCase
import infolaft.composeApp.use_case.LoadSessionUseCase
import infolaft.composeApp.use_case.LoginUseCase
import infolaft.composeApp.use_case.SaveProgressUseCase
import infolaft.composeApp.use_case.SaveSessionUseCase
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

val appModule = module {
    single { LoginUseCase(get()) }
    single { GetUserUseCase(get()) }
    single { GetAreasUseCase(get()) }
    single { GetAccountUseCase(get()) }
    single { SaveProgressUseCase(get()) }
    single { SaveSessionUseCase(get()) }
    single { LoadSessionUseCase(get()) }
    single { ClearSessionUseCase(get()) }
    /*single<InfolaftDao> {
        val dbBuilder = get<RoomDatabase.Builder<InfolaftDatabase>>()
        dbBuilder.build().infolaftDao()
    }*/
}

val dataModule = module {
    factoryOf(::InfolaftRepository)
    factoryOf(::InfolaftService)
    singleOf(::InfolaftCache)
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
            install(DefaultRequest) {
                url {
                    protocol = URLProtocol.HTTPS
                    host = BuildConfig.HOST
                    //parameters.append("api_key", BuildConfig.APY_KEY)
                }
            }
        }
    }
}

val viewModelModule = module {
    factoryOf(::UserViewModelDelegateImpl)
    viewModelOf(::HomeViewModel)
    viewModelOf(::GuideViewModel)
    viewModelOf(::LoginViewModel)
}

expect val nativeModule: Module

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(appModule, dataModule, viewModelModule, nativeModule)
    }
}