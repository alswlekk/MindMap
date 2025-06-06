import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.secrets.gradle.plugin)
}

android {
    namespace = "com.example.mindmap"
    compileSdk = 35

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

        // 읽어온 속성에서 NAVER_CLIENT_ID, NAVER_CLIENT_SECRET 가져오기 (없으면 빈 문자열)
        val naverClientId = secretsProps.getProperty("NAVER_CLIENT_ID", "")
        val naverClientSecret = secretsProps.getProperty("NAVER_CLIENT_SECRET", "")

        // AndroidManifest.xml 에서 ${NAVER_CLIENT_ID}, ${NAVER_CLIENT_SECRET} 로 참조할 수 있도록 설정
        manifestPlaceholders["NAVER_CLIENT_ID"] = naverClientId
        manifestPlaceholders["NAVER_CLIENT_SECRET"] = naverClientSecret
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
    buildFeatures {
        compose = true
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
    implementation(libs.androidx.navigation.compose)
    implementation(libs.accompanist.permissions)
    implementation(libs.androidx.material)
    implementation(libs.jsoup)
    implementation("org.jsoup:jsoup:1.13.1")
    val retrofit_version = "2.6.1"
// Retrofit 라이브러리
    implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
// Gson Converter 라이브러리
    implementation("com.squareup.retrofit2:converter-gson:$retrofit_version")
// Scalars Converter 라이브러리
    implementation("com.squareup.retrofit2:converter-scalars:$retrofit_version")
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
