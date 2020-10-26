package com.imassage

import android.app.Application
import com.facebook.stetho.Stetho
import com.imassage.data.database.sharedPreferences.Preferences
import com.imassage.data.remote.*
import com.imassage.data.remote.api.ApiInterface
import com.imassage.data.remote.api.AuthApiInterface
import com.imassage.data.repository.TokenRepository
import com.imassage.data.repository.UserInformationRepository
import com.imassage.ui.fragment.login.LoginViewModelFactory
import com.imassage.ui.fragment.phoneVerification.PhoneVerificationViewModelFactory
import com.imassage.ui.fragment.registerForm.RegisterFormViewModelFactory
import com.imassage.ui.fragment.signUp.SignUpViewModelFactory
import com.imassage.ui.fragment.splashScreen.SplashScreenViewModelFactory
import okhttp3.OkHttpClient
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import retrofit2.Retrofit

class IMassageApplication(
): Application() , KodeinAware{
    override val kodein: Kodein = Kodein.lazy {
        import(androidModule(this@IMassageApplication))

        bind() from singleton { Preferences(applicationContext)}
        bind() from singleton { TokenRepository(instance())}
        // network
        bind<OkHttpClient>() with singleton { Client().invoke()}
        bind<Retrofit>() with singleton { RetrofitBuilder(instance()).invoke() }
        bind<ApiInterface>() with singleton { CreateApiInterface(instance()).invoke() }
        // auth client
        bind() from singleton { TokenInterceptor(instance() , instance() , instance()) }
        bind() from singleton { TokenAuthenticator(instance() , instance())}
        bind<AuthApiInterface>() with singleton { CreateAuthApiInterface(instance() , instance()).invoke() }

        // ViewModel factories
        bind() from provider { SplashScreenViewModelFactory(instance())}
        bind() from provider { SignUpViewModelFactory() }
        bind() from provider { RegisterFormViewModelFactory(instance()) }
        bind() from provider { LoginViewModelFactory(instance()) }
        bind() from provider { PhoneVerificationViewModelFactory(instance())}

        // repositories
        bind() from singleton { UserInformationRepository(instance() , instance())}


    }
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)

    }

}
