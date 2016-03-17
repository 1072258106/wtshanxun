# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in d:\Program Files\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For ic_head_more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}Warning:org.androidannotations.api.rest.RestClientHeaders: can't find referenced class org.springframework.http.HttpAuthentication
-dontwarn net.youmi.android.**
-keep class net.youmi.android.** { *;}

-dontwarn org.apache.http.**
-keep class org.apache.http.** { *;}

-dontwarn org.springframework.**
-keep class org.springframework.** { *;}

-dontwarn com.tencent.android.tpush.**
-keep class com.tencent.android.tpush.** { *;}

-dontwarn com.pgyersdk.**
-keep class com.pgyersdk.** { *; }
-keepclassmembers class ** {
    public void onEvent*(**);
    void onEvent*(**);
}
