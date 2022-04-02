plugins {
    id(Android.libraryPlugin)
    id(Kotlin.androidPlugin)
}

android {
    compileSdk = Application.targetSdk

    defaultConfig {
        minSdk = Application.minSdk
        targetSdk = Application.targetSdk

        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    implementation(Koin.core)

    implementation(platform(OkHttp.bomDependency))
    implementation(OkHttp.okhttp)
    implementation(OkHttp.loggingInterceptor)

    implementation(Timber.dependency)

    implementation(Gson.dependency)

    implementation(Retrofit.dependency)
    implementation(Retrofit.gsonConverter)

    implementation(project(":errors"))
}
