package com.roy.tester.ads;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.NativeAppInstallAd;
import com.google.android.gms.ads.formats.NativeAppInstallAdView;
import com.google.android.gms.ads.formats.NativeContentAd;
import com.google.android.gms.ads.formats.NativeContentAdView;
import com.roy.tester.mytester.R;

import java.util.List;
import java.util.Locale;

/**
 * Created by Administrator on 2018/1/12.
 */

public class AdsActivity extends Activity{
    private static final String ADMOB_AD_UNIT_ID = "ca-app-pub-3940256099942544/2247696110";
    private static final String ADMOB_APP_ID = "ca-app-pub-3940256099942544~3347511713";

    private Button mRefresh;
    private Button mDestroy;
    private Button mShow;
    private CheckBox mRequestAppInstallAds;
    private CheckBox mRequestContentAds;
    private CheckBox mStartVideoAdsMuted;
    private TextView mVideoStatus;

    private NativeAd mNativeAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ads_layout);

        mRefresh = (Button)findViewById(R.id.btn_refresh);
        mDestroy = (Button)findViewById(R.id.btn_destory);
        mShow = (Button)findViewById(R.id.btn_show);
        mRequestAppInstallAds = (CheckBox)findViewById(R.id.cb_appinstall);
        mRequestContentAds = (CheckBox) findViewById(R.id.cb_content);
        mStartVideoAdsMuted = (CheckBox) findViewById(R.id.cb_start_muted);
        mVideoStatus = (TextView)findViewById(R.id.tv_video_status);

        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(this, ADMOB_APP_ID);

        mRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshAd(mRequestAppInstallAds.isChecked(),
                        mRequestContentAds.isChecked());
            }
        });

        mShow.setEnabled(false);
        mShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                showAd();
            }
        });

        mDestroy.setEnabled(false);
        mDestroy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                destory();
            }
        });
    }

    /**
     * Populates a {@link NativeAppInstallAdView} object with data from a given
     * {@link NativeAppInstallAd}.
     *
     * @param nativeAppInstallAd the object containing the ad's assets
     * @param adView             the view to be populated
     */
    private void populateAppInstallAdView(NativeAppInstallAd nativeAppInstallAd,
                                          NativeAppInstallAdView adView) {
        // Get the video controller for the ad. One will always be provided, even if the ad doesn't
        // have a video asset.
        VideoController vc = nativeAppInstallAd.getVideoController();

        // Create a new VideoLifecycleCallbacks object and pass it to the VideoController. The
        // VideoController will call methods on this object when events occur in the video
        // lifecycle.
        vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
            public void onVideoEnd() {
                // Publishers should allow native ads to complete video playback before refreshing
                // or replacing them with another ad in the same UI location.
                mRefresh.setEnabled(true);
                mVideoStatus.setText("Video status: Video playback has ended.");
                super.onVideoEnd();
            }
        });

        adView.setHeadlineView(adView.findViewById(R.id.appinstall_headline));
        adView.setBodyView(adView.findViewById(R.id.appinstall_body));
        adView.setCallToActionView(adView.findViewById(R.id.appinstall_call_to_action));
        adView.setIconView(adView.findViewById(R.id.appinstall_app_icon));
        adView.setPriceView(adView.findViewById(R.id.appinstall_price));
        adView.setStarRatingView(adView.findViewById(R.id.appinstall_stars));
        adView.setStoreView(adView.findViewById(R.id.appinstall_store));

        // Some assets are guaranteed to be in every NativeAppInstallAd.
        ((TextView) adView.getHeadlineView()).setText(nativeAppInstallAd.getHeadline());
        ((TextView) adView.getBodyView()).setText(nativeAppInstallAd.getBody());
        ((Button) adView.getCallToActionView()).setText(nativeAppInstallAd.getCallToAction());
        ((ImageView) adView.getIconView()).setImageDrawable(
                nativeAppInstallAd.getIcon().getDrawable());

        MediaView mediaView = (MediaView)adView.findViewById(R.id.appinstall_media);
        ImageView mainImageView = (ImageView)adView.findViewById(R.id.appinstall_image);

        // Apps can check the VideoController's hasVideoContent property to determine if the
        // NativeAppInstallAd has a video asset.
        if (vc.hasVideoContent()) {
            adView.setMediaView(mediaView);
            mainImageView.setVisibility(View.GONE);
            mVideoStatus.setText(String.format(Locale.getDefault(),
                    "Video status: Ad contains a %.2f:1 video asset.",
                    vc.getAspectRatio()));
        } else {
            adView.setImageView(mainImageView);
            mediaView.setVisibility(View.GONE);

            // At least one image is guaranteed.
            List<NativeAd.Image> images = nativeAppInstallAd.getImages();
            mainImageView.setImageDrawable(images.get(0).getDrawable());

            mRefresh.setEnabled(true);
            mVideoStatus.setText("Video status: Ad does not contain a video asset.");
        }

        // These assets aren't guaranteed to be in every NativeAppInstallAd, so it's important to
        // check before trying to display them.
        if (nativeAppInstallAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAppInstallAd.getPrice());
        }

        if (nativeAppInstallAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAppInstallAd.getStore());
        }

        if (nativeAppInstallAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAppInstallAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        // Assign native ad object to the native view.
        adView.setNativeAd(nativeAppInstallAd);
    }

    /**
     * Populates a {@link com.google.android.gms.ads.formats.NativeContentAdView} object with data from a given
     * {@link NativeContentAd}.
     *
     * @param nativeContentAd the object containing the ad's assets
     * @param adView          the view to be populated
     */
    private void populateContentAdView(NativeContentAd nativeContentAd,
                                       NativeContentAdView adView) {
        mVideoStatus.setText("Video status: Ad does not contain a video asset.");
        mRefresh.setEnabled(true);

        adView.setHeadlineView(adView.findViewById(R.id.contentad_headline));
        adView.setImageView(adView.findViewById(R.id.contentad_image));
        adView.setBodyView(adView.findViewById(R.id.contentad_body));
        adView.setCallToActionView(adView.findViewById(R.id.contentad_call_to_action));
        adView.setLogoView(adView.findViewById(R.id.contentad_logo));
        adView.setAdvertiserView(adView.findViewById(R.id.contentad_advertiser));

        // Some assets are guaranteed to be in every NativeContentAd.
        ((TextView) adView.getHeadlineView()).setText(nativeContentAd.getHeadline());
        ((TextView) adView.getBodyView()).setText(nativeContentAd.getBody());
        ((TextView) adView.getCallToActionView()).setText(nativeContentAd.getCallToAction());
        ((TextView) adView.getAdvertiserView()).setText(nativeContentAd.getAdvertiser());

        List<NativeAd.Image> images = nativeContentAd.getImages();

        if (images.size() > 0) {
            ((ImageView) adView.getImageView()).setImageDrawable(images.get(0).getDrawable());
        }

        // Some aren't guaranteed, however, and should be checked.
        NativeAd.Image logoImage = nativeContentAd.getLogo();

        if (logoImage == null) {
            adView.getLogoView().setVisibility(View.INVISIBLE);
        } else {
            ((ImageView) adView.getLogoView()).setImageDrawable(logoImage.getDrawable());
            adView.getLogoView().setVisibility(View.VISIBLE);
        }

        // Assign native ad object to the native view.
        adView.setNativeAd(nativeContentAd);
    }

    /**
     * Creates a request for a new native ad based on the boolean parameters and calls the
     * corresponding "populate" method when one is successfully returned.
     *
     * @param requestAppInstallAds indicates whether app install ads should be requested
     * @param requestContentAds    indicates whether content ads should be requested
     */
    private void refreshAd(boolean requestAppInstallAds, boolean requestContentAds) {
        if (!requestAppInstallAds && !requestContentAds) {
            Toast.makeText(this, "At least one ad format must be checked to request an ad.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        mRefresh.setEnabled(false);

        AdLoader.Builder builder = new AdLoader.Builder(this, ADMOB_AD_UNIT_ID);

        if (requestAppInstallAds) {
            builder.forAppInstallAd(new NativeAppInstallAd.OnAppInstallAdLoadedListener() {
                @Override
                public void onAppInstallAdLoaded(NativeAppInstallAd ad) {
                    Log.d("Admob", "onAppInstallAdLoaded");
                    mNativeAd = ad;
                    mRefresh.setEnabled(true);
                    mShow.setEnabled(true);
                    mDestroy.setEnabled(true);
                }
            });
        }

        if (requestContentAds) {
            builder.forContentAd(new NativeContentAd.OnContentAdLoadedListener() {
                @Override
                public void onContentAdLoaded(NativeContentAd ad) {
                    Log.d("Admob", "onContentAdLoaded");
                    mNativeAd = ad;
                    mRefresh.setEnabled(true);
                    mShow.setEnabled(true);
                    mDestroy.setEnabled(true);
                }
            });
        }

        VideoOptions videoOptions = new VideoOptions.Builder()
                .setStartMuted(mStartVideoAdsMuted.isChecked())
                .build();

        NativeAdOptions adOptions = new NativeAdOptions.Builder()
                .setVideoOptions(videoOptions)
                .build();

        builder.withNativeAdOptions(adOptions);

        AdLoader adLoader = builder.withAdListener(mAdListener).build();

        adLoader.loadAd(new AdRequest.Builder().build());

        mVideoStatus.setText("");
    }

    AdListener mAdListener = new AdListener() {
        @Override
        public void onAdClosed() {
            Log.d("Admob", "onAdClosed");
        }

        @Override
        public void onAdFailedToLoad(final int errorCode) {
            Log.d("Admob", "onAdFaildLoad");
            mRefresh.setEnabled(true);
            Toast.makeText(AdsActivity.this, "Failed to load native ad: "
                                             + errorCode, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAdLeftApplication() {
            Log.d("Admob", "onAdLeftApplication");
        }

        @Override
        public void onAdLoaded() {
            Log.d("Admob", "onAdLoaded");
        }

        @Override
        public void onAdOpened() {
            Log.d("Admob", "onAdOpened");
        }
    };

    private void showAd(){
        mShow.setEnabled(false);
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.fl_adplaceholder);

        if(mNativeAd instanceof NativeContentAd){
            NativeContentAdView adView = (NativeContentAdView) getLayoutInflater()
                                                                 .inflate(R.layout.ad_content, null);
            populateContentAdView((NativeContentAd)mNativeAd, adView);
            frameLayout.removeAllViews();
            frameLayout.addView(adView);
        }else if(mNativeAd instanceof NativeAppInstallAd){
            NativeAppInstallAdView adView = (NativeAppInstallAdView) getLayoutInflater()
                                                                 .inflate(R.layout.ad_content, null);
            populateAppInstallAdView((NativeAppInstallAd)mNativeAd, adView);
            frameLayout.removeAllViews();
            frameLayout.addView(adView);
        }

    }

    private void destory(){
        if(mNativeAd instanceof NativeAppInstallAd){
            ((NativeAppInstallAd) mNativeAd).destroy();
        }else if(mNativeAd instanceof NativeContentAd){
            ((NativeContentAd) mNativeAd).destroy();
        }
        //if (mNativeAdView instanceof NativeContentAdView || mNativeAdView instanceof NativeAppInstallAdView) {
        //    mNativeAdView.setOnClickListener(null);
        //    mNativeAdView.setOnTouchListener(null);
        //}
    }
}
