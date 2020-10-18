package com.imassage

import android.app.Application
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidModule

class IMassageApplication(
): Application() , KodeinAware{
    override val kodein: Kodein = Kodein.lazy {
        import(androidModule(this@IMassageApplication))

    }
}
