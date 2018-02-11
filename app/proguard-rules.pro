-keep class android.support.** { *; }
-keep interface android.support.app.** { *; }
-keep class android.arch.** { *; }
-keep class okhttp3.** { *; }
-keep class retrofit2.** { *; }
-keep class io.reactivex.** { *; }
-keep class com.squareup.** { *; }
-keep class com.jakewharton.rxbinding2.** { *; }
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-dontwarn javax.annotation.**
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement


