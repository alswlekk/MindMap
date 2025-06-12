import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.secrets.gradle.plugin)
    alias(libs.plugins.kotlin.ksp)

}

android {
    namespace = "com.example.mindmap"
    compileSdk = 35

    // buildConfig 기능 활성화
    buildFeatures {
        buildConfig = true
    } // buildConfig 기능을 활성화하면, buildConfigField를 사용 가능

    defaultConfig {
        applicationId = "com.example.mindmap"
        minSdk = 31
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val secretsFile = rootProject.file("secrets.properties")
        val secretsProps = Properties()

        if (secretsFile.exists()) {
            // secrets.properties 파일이 존재할 때 로드
            FileInputStream(secretsFile).use { fis ->
                secretsProps.load(fis)
            }
        }
    }

    buildTypes {
        debug {
            buildConfigField("String", "API_KEY", "\"${project.findProperty("API_KEY")}\"")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "API_KEY", "\"${project.findProperty("API_KEY")}\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

configurations.all {
    resolutionStrategy {
        force("com.google.guava:guava:30.1-jre") // guava 최신 버전 강제 적용
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation("com.naver.maps:map-sdk:3.21.0")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation(libs.naver.map.compose)
    implementation("androidx.navigation:navigation-compose:2.7.5")
    implementation(libs.androidx.navigation.compose)
    implementation(libs.accompanist.permissions)
    implementation(libs.androidx.material)
    implementation(libs.jsoup)
    implementation("org.jsoup:jsoup:1.13.1")
    implementation(libs.androidx.compiler)
    val retrofit_version = "2.9.0"
// Retrofit 라이브러리
    implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
// Gson Converter 라이브러리
    implementation("com.squareup.retrofit2:converter-gson:$retrofit_version")
// Scalars Converter 라이브러리
    implementation("com.squareup.retrofit2:converter-scalars:$retrofit_version")
    implementation("com.squareup.retrofit2:converter-simplexml:2.9.0")
    implementation("com.squareup.retrofit2:converter-jaxb:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.compiler)
    ksp(libs.androidx.room.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

}

secrets {
    propertiesFileName = "secrets.properties"
    defaultPropertiesFileName = "local.defaults.properties"
}


