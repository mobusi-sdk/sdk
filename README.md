SunMediaAds Android SDK
=====
The current version has been tested using API 21, 23, 25 and 26 and require a minSdkVersion >= 17
SunMediaAds supports Banners, Interstitials and Video Rewarded from various leading ad networks, with advanced functionalities like ads auto-fetching and advanced delivery optimization.


## Adding SunMediaAds SDK to your Project

1. Download our SDK, unzip the file and add all files in lib folder to your project's lib folder. 

2. Then add these lines to your build.gradle:

```java
    repositories {
        flatDir {
            dirs 'libs'
        }
    } 
    
    dependecies{
        implementation fileTree(dir: 'libs/libs', include: ['*.jar'])
          implementation 'com.google.android.gms:play-services-base:17.3.0'
  implementation 'com.google.android.gms:play-services-ads:19.2.0'
  implementation 'com.android.support:appcompat-v7:28.0.3'


  implementation(name:'sunmedia-2.12.2', ext: 'aar')
  implementation(name:'libs/smart-core-sdk-7.4.1', ext:'aar')
  implementation(name:'libs/smart-display-sdk-7.4.1', ext:'aar')
  implementation(name:'libs/spotx-sdk-android-4.8.1', ext:'aar')
  implementation(name:'libs/exoplayer-hls-2.10.4', ext:'aar')
  implementation(name:'libs/exoplayer-core-2.10.4', ext:'aar')

  implementation(name:'libs/consent-library-release', ext:'aar')
  implementation(name:'libs/AudienceNetwork', ext:'aar')
  implementation(name:'libs/recyclerview-v7', ext:'aar')
    }
```

**Admob**
Add to your manifest file : 

````java
 <meta-data
        android:name="com.google.android.gms.ads.APPLICATION_ID"
        android:value="YOUR_ADMOB_APPLICATION_ID"/>
````

**We also recommend adding the following lines:**

```java
dependencies {
    implementation 'com.android.support:multidex:1.0.3'
}
```



## Initialize the SDK

Once you have added all the files and the configuration to your build .gradle, it's time to initialize the SDK. 

**Important**: you have to initialize the SDK at the begining of the execution of your app. Make sure you **initiliase it only once**.

### Init the SDK
The SDK Initialisation can be done in two ways:

1. Initialize each Ad Format separately at different points of the game. ***Recommended*** to minimise the number of ads preloaded without showing an impression.

You can bundle them in the same method

```java
SunMediaAds.init(this, APP_HASH, FORMAT.BANNER, FORMAT.INTERSTITIAL);
```

Or each of them separately

```java
SunMediaAds.init(this, APP_HASH, FORMAT.BANNER);

SunMediaAds.init(this, APP_HASH, FORMAT.INTERSTITIAL);

SunMediaAds.init(this, APP_HASH, FORMAT.REWARDED_VIDEO);
```

2. Alternatively, you can initiliase ALL of them at the same time with this method
```java
SunMediaAds.init(this, APP_HASH);
```

The appHash is the hash ID of your app, youre account manaher will provide it.

## Override Activity Lifecycle Methods

It is recommended to pause/resume ads with the onPause/onResume
method:

```java
@Override protected void onPause() {
    super.onPause();
    SunMediaAds.pause();
}

@Override protected void onResume() {
    super.onResume();
    SunMediaAds.resume();
}
```

## Set Listeners

The SDK offers a listener where you can receive the events of the ads. **Important** the method "addListener" add a new listener, so make sure you **do not add the same listener more than once.** 

The SDK will call all the listeners added. You can remove any listener you want to.

```java

 SunMediaAds.removeListener(@NonNull SunMediaAdsListener listener);

 SunMediaAds.clearListener();

 SunMediaAds.addListener(new SunMediaAdsListener() {
      // When the SDK is already initialized, if everything is ok, state will be true.
      @Override public void onInit(boolean state) {

      }
      // Will be called when any ad is loaded, it will tell you the type MediationType.BANNER, MediationType.INSTERSTITIAL and MediationType.REWARDED_VIDEO
      @Override public void onAdLoaded(final MediationType type) {


      }
      // When an ad is closed
      @Override public void onClose(@NonNull MediationType type, @Nullable String adLocation) {

      }
      // When an ad is clicked
      @Override public void onClick(@NonNull MediationType type, @Nullable String adLocation) {

      }
      // When we received an error loading or showing an ad
      @Override public void onError(@NonNull String description, @NonNull MediationType type, @NonNull String adLocation) {

      }
      // When an ad is showed
      @Override public void onShow(@NonNull MediationType type, @NonNull String adLocation, @NonNull Info info) {

      }
      // When you have to give a reward after a rewarded-video
      @Override public void onReward(@NonNull MediationType type, @NonNull String adLocation) {

      }
    });
```
## Showing Ads


Once you have correctly initialize the SDK and set the listeners, then you can show ads. 

>**The mediation SDK auto fetch all ads for you**, when you call the init method also will fecth the first ads, so you only need to call the show methods for the selected ad format.

You have to pass the context of your activity and the ad placement where the ad will be shown "double-coins", "main-menu", "options", etc. It can also be an empty string but we recommend you to always define an ad placement. 

**The ad placements are automatically created on the dashboard and will appear after the first call of that specific ad placement is done.**

Make sure you check that the ad has been correctly loaded by calling the following method, passing the correct mediationType:
```java
SunMediaAds.isAdTypeLoaded(MediationType mediationType);

MediationType.INTERSTITIAL
MediationType.REWARDED_VIDEO
MediationType.BANNER
```
You can also set capping for your ad locations in order to tweak your monetization. You can check if an ad location has reached its capping using this method:
```java
LabCaveMediation.isAdLocationCapped(String adLocation, MediationType mediationType);

```
## Interstitial

```java
if (SunMediaAds.isAdTypeLoaded(MediationType.INTERSTITIAL){
    SunMediaAds.showInterstitial(context, tag);
}
```

## Rewarded Video

```java
if (SunMediaAds.isAdTypeLoaded(MediationType.REWARDED_VIDEO){
    SunMediaAds.showRewardedVideo(context, tag);
}

```

## Banner

The position **TOP** or **BOTTOM** and the size SMART(SCREEN_SIZEx50) or BANNER (320x50) can be set at the beggining of the execution or when you call "showBanner":

```java
if (SunMediaAds.isAdTypeLoaded(MediationType.BANNER))
{
    ...
}

SunMediaAds.showBanner(context, tag, BannerPosition bannerPosition);
SunMediaAds.showBanner(context, tag, BannerSize bannerSize);
SunMediaAds.showBanner(context, tag, BannerPosition bannerPosition,  BannerSize bannerSize);

SunMediaAds.showBanner(labcaveBannerView, tag, BannerPosition bannerPosition);
SunMediaAds.showBanner(labcaveBannerView, tag, BannerSize bannerSize);
SunMediaAds.showBanner(labcaveBannerView, tag, BannerPosition bannerPosition, BannerSize bannerSize);
```
### Verify the integration

In order to check if the SDK is correct, open the test module, you have to call the "Init" method first and wait till the "onInit" listener method is called:

```java
SunMediaAds.initTest(this, "YOUR_API_HASH");
```
>**Make sure you remove this test module on your release build.**

## Sample Activity

You can find here an example of an activity integrating Lab Cave Mediation

[Sample](./app/src/main/java/com/sunmedia/sunmediamediation/android/sample/MainActivity.java)

## Advance integration


### Debugging

You can enable logging to get additional information by using the following method:

```java
SunMediaAds.setLogging(true);
```

### GDPR

>Make sure you set the GDPR user consent before initiliazing the Mediation.

You can set the user consent to the sdk if you manage it. If you don't, the mediation will ask the user for the consent. 

You can use the following methods:

```java
SunMediaAds.setUserConsent(this, false);

SunMediaAds.getUserConsent();
```

### Proguard
If you use proguard add these rules:

```groovy
-keep class com.google.analytics.** { *; }
-keep class com.google.unity.** { *; }
-keep class com.google.android.gms.** { *; }
-keep class com.google.android.ads.** { *; }
-keep public class com.labcave.** { *; }
-keep class com.nerd.** { *; }
#AdColony
-keepclassmembers class * { @android.webkit.JavascriptInterface <methods>; }
-keepclassmembers class com.adcolony.sdk.ADCNative** { *;}

-keep class com.chartboost.** { *; }
-keepattributes SourceFile,LineNumberTable
-keepattributes JavascriptInterface
-keep class android.webkit.JavascriptInterface { *;}
-keep class com.unity3d.ads.** {*;}
-keep class com.facebook.** { *; }

-keepclassmembers class com.ironsource.sdk.controller.IronSourceWebView$JSInterface {
    public *;
}
-keepclassmembers class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}
-keep public class com.google.android.gms.ads.** {
   public *;
}
-keep class com.ironsource.adapters.** { *;
}
-dontwarn com.ironsource.mediationsdk.**
-dontwarn com.ironsource.adapters.**
-dontwarn com.moat.**
-keep class com.moat.** { public protected private *; }

#UnityAds
-keepattributes SourceFile,LineNumberTable
-keepattributes JavascriptInterface
-keep class android.webkit.JavascriptInterface {*;}
-keep class com.unity3d.ads.** {*;}
-keep class com.unity3d.services.** {*;}
-dontwarn com.google.ar.core.**
-dontwarn com.unity3d.services.**

#Vungle
-keep class com.vungle.warren.** { *; }
-dontwarn com.vungle.warren.error.VungleError$ErrorCode
# Moat SDK
-keep class com.moat.** { *; }
-dontwarn com.moat.**
# Okio
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
# Retrofit
-dontwarn okio.**
-dontwarn retrofit2.Platform$Java8
# Gson
-keepattributes Signature
-keepattributes *Annotation*
-dontwarn sun.misc.**
-keep class com.google.gson.examples.android.model.** { *; }
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
# Google Android Advertising ID
-keep class com.google.android.gms.internal.** { *; }
-dontwarn com.google.android.gms.ads.identifier.**
```


