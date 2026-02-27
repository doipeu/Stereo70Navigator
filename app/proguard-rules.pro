# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.

# Keep Gson classes
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.google.gson.** { *; }
-keep class com.stereo70.navigator.Coordinate { *; }

# Keep all classes in the app
-keep class com.stereo70.navigator.** { *; }
