package com.sunmedia.sunmediamediation.android.sample;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.labcave.mediationlayer.LabCaveMediationBannerView;
import com.labcave.mediationlayer.MediationType;
import com.labcave.mediationlayer.mediationadapters.models.Info;
import com.sunmedia.sdk.SunMediaAds;
import com.sunmedia.sdk.SunMediaAdsListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
  private static final String APP_ID = "YOUR_APP_HASH";

  private LabCaveMediationBannerView bannerView;
  private final Handler UIHandler = new Handler(Looper.getMainLooper());

  private Button showBanner;
  private Button showInterstitial;
  private Button showRewarded;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    bannerView = findViewById(R.id.banner);
    showBanner = findViewById(R.id.showBanner);
    showBanner.setOnClickListener(this);

    showInterstitial = findViewById(R.id.showInterstitial);
    showInterstitial.setOnClickListener(this);

    showRewarded = findViewById(R.id.showRewarded);
    showRewarded.setOnClickListener(this);

    Button showTest = findViewById(R.id.showTest);
    showTest.setOnClickListener(this);

    initSunMediaMediationLayer();
  }

  private void initSunMediaMediationLayer() {
    SunMediaAds.setLogging(true);
    SunMediaAds.setListener(new SunMediaAdsListener() {
      @Override
      public void onInit(boolean state) {
      }

      @Override
      public void onAdLoaded(final MediationType type) {
        UIHandler.post(new Runnable() {
          @Override public void run() {
            switch (type) {
              case BANNER:
                showBanner.setVisibility(View.VISIBLE);
                break;

                case INTERSTITIAL:
                  showInterstitial.setVisibility(View.VISIBLE);
                  break;

                  case REWARDED_VIDEO:
                    showRewarded.setVisibility(View.VISIBLE);
                    break;
            }
          }
        });
      }

      @Override
      public void onError(@NonNull String description, @NonNull MediationType type, @NonNull String adLocation) {
      }

      @Override
      public void onClose(@NonNull MediationType type, @Nullable String adLocation) {
      }

      @Override
      public void onClick(@NonNull MediationType type, @Nullable String adLocation) {
      }

      @Override
      public void onShow(@NonNull MediationType type, @NonNull String adLocation, @NonNull Info info) {
      }

      @Override
      public void onReward(@NonNull MediationType type, @NonNull String adLocation) {
      }
    });

            SunMediaAds.init(this, APP_ID);
  }

  @Override protected void onPause() {
    super.onPause();
    SunMediaAds.pause();
  }

  @Override protected void onResume() {
    super.onResume();
    SunMediaAds.resume();
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.showTest:
        SunMediaAds.initTest(this, APP_ID);
        break;

      case R.id.showBanner:
        SunMediaAds.showBanner(bannerView, "banner");
        break;

      case R.id.showInterstitial:
        SunMediaAds.showInterstitial(MainActivity.this, "inter");
        break;

      case R.id.showRewarded:
        SunMediaAds.showRewardedVideo(MainActivity.this, "rewarded");
        break;
    }
  }
}
