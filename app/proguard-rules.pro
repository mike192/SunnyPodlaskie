-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# Retrofit
-dontwarn retrofit.**
-keep class retrofit.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-keepattributes *Annotation*

-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}

# OkHttp
-keepattributes Signature
-keepattributes *Annotation*
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**
-dontnote okhttp3.**

# Okio
-dontwarn java.nio.file.*
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

# Gson
-keep class com.google.gson.stream.** { *; }

# AndroidX
-dontwarn com.google.android.material.**
-keep class com.google.android.material.** { *; }

-dontwarn androidx.**
-keep class androidx.** { *; }
-keep interface androidx.** { *; }

# JNI
-keepclassmembers class pl.mosenko.sunnypodlaskie.util.WeatherApiKeyProvider {
    native <methods>;
}

# DTOs
-keep class pl.mosenko.sunnypodlaskie.api.dto.** { <fields>; }
