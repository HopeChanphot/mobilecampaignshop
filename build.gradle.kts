
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false

    // Use the alias defined in libs.versions.toml
    alias(libs.plugins.kotlin.compose) apply false
}

