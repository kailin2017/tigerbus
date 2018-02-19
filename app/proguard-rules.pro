-keep class com.tigerbus.app.** { *; }
-keep interface com.tigerbus.data.** { *; }

-keep class android.support.** { *; }
-keep interface android.support.** { *; }
-keep class android.arch.** { *; }

-keepnames class okhttp3.** { *; }
-keepnames interface okhttp3.** { *; }
-keepnames class retrofit2.** { *; }
-keepnames interface retrofit2.** { *; }
-keepnames class io.reactivex.** { *; }
-keepnames interface io.reactivex.** { *; }
-keepnames class com.squareup.** { *; }
-keepnames interface com.squareup.** { *; }

-keep class com.jakewharton.rxbinding2.** { *; }
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-dontwarn javax.annotation.**
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement


