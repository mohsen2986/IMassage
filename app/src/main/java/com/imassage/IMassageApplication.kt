package com.imassage

import android.app.Application
import android.widget.Toast
import com.facebook.stetho.Stetho
import com.imassage.data.database.sharedPreferences.Preferences
import com.imassage.data.remote.*
import com.imassage.data.remote.api.ApiInterface
import com.imassage.data.remote.api.AuthApiInterface
import com.imassage.data.repository.*
import com.imassage.ui.dialog.account.address.EditAccountAddressDialogViewModelFactory
import com.imassage.ui.dialog.account.editFamily.EditAccountFamilyDialogViewModelFactory
import com.imassage.ui.dialog.account.editGender.EditAccountGenderDialogViewModelFactory
import com.imassage.ui.dialog.account.editImage.EditAccountImageDialogViewModelFactory
import com.imassage.ui.dialog.account.editName.EditAccountNameDialogViewModelFactory
import com.imassage.ui.dialog.resetPassword.ResetPasswordViewModelFactory
import com.imassage.ui.fragment.account.AccountViewModelFactory
import com.imassage.ui.fragment.login.LoginViewModelFactory
import com.imassage.ui.fragment.mainPage.MainPageViewModelFactory
import com.imassage.ui.fragment.paymentGateway.PaymentGatewayViewModelFactory
import com.imassage.ui.fragment.phoneVerification.PhoneVerificationViewModelFactory
import com.imassage.ui.fragment.receipt.ReceiptViewModelFactory
import com.imassage.ui.fragment.registerForm.RegisterFormViewModelFactory
import com.imassage.ui.fragment.reserve.massage.MassageViewModelFactory
import com.imassage.ui.fragment.reserve.packages.PackagesViewModelFactory
import com.imassage.ui.fragment.reserve.question.QuestionViewModelFactory
import com.imassage.ui.fragment.reserve.reserveTime.ReservedTimeViewModelFactory
import com.imassage.ui.fragment.reserveView.ReserveViewViewModelFactory
import com.imassage.ui.fragment.resetPassword.ResetPasswordViewModel
import com.imassage.ui.fragment.signUp.SignUpViewModelFactory
import com.imassage.ui.fragment.signUpForm.SignUpFormViewModel
import com.imassage.ui.fragment.signUpForm.SingUpFormViewModelFactory
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

        // network
        bind<OkHttpClient>() with singleton { Client().invoke()}
        bind<Retrofit>() with singleton { RetrofitBuilder(instance()).invoke() }
        bind<ApiInterface>() with singleton { CreateApiInterface(instance()).invoke() }
        // auth client
        bind() from singleton { TokenInterceptor(instance() , instance() , instance()) }
        bind() from singleton { TokenAuthenticator(instance() , instance())}
        bind<AuthApiInterface>() with singleton { CreateAuthApiInterface(instance() , instance()).invoke() }
        // ViewModel factories
        bind() from provider { SplashScreenViewModelFactory(instance() , instance())}
        bind() from provider { SignUpViewModelFactory() }
        bind() from provider { RegisterFormViewModelFactory(instance()) }
        bind() from provider { LoginViewModelFactory(instance()) }
        bind() from provider { PhoneVerificationViewModelFactory(instance())}
        bind() from provider { MainPageViewModelFactory(instance() , instance() , instance() , instance()) }
        bind() from provider { MassageViewModelFactory(instance() , instance()) }
        bind() from provider { PackagesViewModelFactory(instance() , instance()) }
        bind() from provider { QuestionViewModelFactory(instance()) }
        bind() from provider { ReservedTimeViewModelFactory(instance()) }
        bind() from provider { AccountViewModelFactory(instance()) }
        bind() from provider { ReceiptViewModelFactory(instance() , instance() , instance()) }
        bind() from provider { PaymentGatewayViewModelFactory(instance()) }
        bind() from provider { ReserveViewViewModelFactory(instance()) }
        bind() from provider { com.imassage.ui.fragment.resetPassword.ResetPasswordViewModelFactory(instance())}
        bind() from provider { SingUpFormViewModelFactory(instance()) }
        // DIALOGS
        bind() from provider { EditAccountNameDialogViewModelFactory(instance())}
        bind() from provider { EditAccountFamilyDialogViewModelFactory(instance())}
        bind() from provider { EditAccountImageDialogViewModelFactory(instance())}
        bind() from provider { EditAccountGenderDialogViewModelFactory(instance())}
        bind() from provider { ResetPasswordViewModelFactory(instance())}
        bind() from provider { EditAccountAddressDialogViewModelFactory(instance())}

        // repositories
        bind() from singleton { UserInformationRepository(instance() , instance())}
        bind() from singleton { TokenRepository(instance())}
        bind() from singleton { DataRepository(instance())}
        bind() from singleton { AccountRepository(instance()) }
        bind() from singleton { OrderRepository(instance())}

    }
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)

    }

}
