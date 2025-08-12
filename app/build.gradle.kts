plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")

}

android {
    namespace = "com.example.mytoolboxapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.mytoolboxapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.firestore.ktx)

    implementation("androidx.navigation:navigation-fragment-ktx:2.9.3")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(platform(libs.firebase.bom))
    //las otras implementaciones provocan falla de compilación
    //implementation(platform("com.squareup.okhttp3:okhttp:5.1.0"))
    //implementation(platform("com.squareup.okhttp3:okhttp:6.12.0"))
    //implementation(libs.okhttp)
    //implementation("io.fabric8:kubernetes-httpclient-okhttp:6.12.0")
    implementation("com.squareup.okhttp3:okhttp:4.2.2")
    implementation(libs.json)
    implementation(libs.glide)
    implementation("androidx.compose.material3:material3:1.0.1") // Usa la última versión
    implementation("androidx.compose.ui:ui:1.1.1")
    implementation("androidx.compose.material:material-icons-extended:1.1.1") // Para iconos extendidos
    //implementation("pl.droidsonroids.gif:android-gif-drawable:1.2.28")
    implementation("com.airbnb.android:lottie:6.6.6")
}

