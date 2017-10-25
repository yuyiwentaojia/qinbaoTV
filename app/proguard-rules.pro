# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontoptimize
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-keepattributes *Annotation*
-keepattributes EnclosingMethod
-keep public class * extends android.app.Fragment
    -keep public class * extends android.app.Activity
    -keep public class * extends android.app.Application
    -keep public class * extends android.app.Service
    -keep public class * extends android.content.BroadcastReceiver
    -keep public class * extends android.content.ContentProvider
    -keep public class * extends android.app.backup.BackupAgentHelper
    -keep public class * extends android.preference.Preference
    -keep public class com.android.vending.licensing.ILicensingService
    -keep public class * extends android.support.v4.app.Fragment
    -ignorewarning

#-libraryjars libs/bugly_crash_release.jar
#-libraryjars libs/umeng-analytics-v6.0.1.jar
#-libraryjars libs/videocache-1.0.0.jar

-dontwarn android.support.**

-keep class android.** {*;}
-dontwarn android.**

-dontwarn com.ihome.android.market2.aidl.**
-keep class com.ihome.android.market2.aidl.* {*;}

-dontwarn com.squareup.picasso.**
-keep class com.squareup.picasso.* {*;}

-dontwarn com.iqinbao.player.**
-keep class com.iqinbao.player.**{*;}

-dontwarn tv.danmaku.ijk.media.player.**
-keep class tv.danmaku.ijk.media.player.**{*;}

-keep class com.squareup.wire.* {
        public <fields>;
        public <methods>;
}

-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}

-dontwarn com.iqinbao.android.videocache.**
-keep class com.iqinbao.android.videocache.** {*;}

-keepclassmembers class * {
    public <init> (org.json.JSONObject);
}

-keep public class com.iqinbao.android.songstv.R$*{
    public static final int *;
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-dontwarn com.google.gson.**
-keep class com.google.gson.* {*;}

-keep public class com.google.gson.**
-keep public class com.google.gson.** {public private protected *;}

-keepattributes Signature

-keepattributes *Annotation*
-keep class sun.misc.Unsafe { *; }

-keep class com.iqinbao.android.songstv.beanstv.** { *; }

-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keep public class * implements java.io.Serializable {*;}

-assumenosideeffects class android.util.Log {
           public static boolean isLoggable(java.lang.String, int);
           public static int v(...);
           public static int i(...);
           public static int w(...);
           public static int d(...);
           public static int e(...);
       }