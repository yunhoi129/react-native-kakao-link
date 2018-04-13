
package com.yunhoi.kakaolink;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.kakao.kakaolink.v2.KakaoLinkResponse;
import com.kakao.kakaolink.v2.KakaoLinkService;
import com.kakao.kakaonavi.KakaoNaviParams;
import com.kakao.kakaonavi.KakaoNaviService;
import com.kakao.kakaonavi.Location;
import com.kakao.kakaonavi.NaviOptions;
import com.kakao.kakaonavi.options.CoordType;
import com.kakao.kakaonavi.options.RpOption;
import com.kakao.kakaonavi.options.VehicleType;
import com.kakao.message.template.ButtonObject;
import com.kakao.message.template.ContentObject;
import com.kakao.message.template.FeedTemplate;
import com.kakao.message.template.LinkObject;
import com.kakao.network.ErrorResult;
import com.kakao.network.callback.ResponseCallback;
import com.kakao.util.helper.log.Logger;

public class RNKakaoLinkModule extends ReactContextBaseJavaModule implements ActivityEventListener{

    private final ReactApplicationContext reactContext;

    public RNKakaoLinkModule(ReactApplicationContext reactContext) {
        super(reactContext);
        reactContext.addActivityEventListener(this);
        this.reactContext = reactContext;
    }

    private String getString(ReadableMap map, String key) {

        if(map.hasKey(key)) {
            Log.d("JIJUYEO", map.getString(key));
            return map.getString(key);
        }
        return null;
    }

    @ReactMethod
    public void navi(ReadableMap map) {
        String name = getString(map, "name");
        double lat = map.getDouble("lat");
        double lng = map.getDouble("lng");
        Location destination = Location.newBuilder(name, lng, lat).build();
        NaviOptions options = NaviOptions.newBuilder().setCoordType(CoordType.WGS84).setVehicleType(VehicleType.FIRST).setRpOption(RpOption.SHORTEST).build();

// 경유지를 1개 포함하는 KakaoNaviParams.Builder 객체
        KakaoNaviParams.Builder builder = KakaoNaviParams.newBuilder(destination).setNaviOptions(options);
        KakaoNaviService.getInstance().navigate(reactContext.getCurrentActivity(), builder.build());
    }

    @ReactMethod
    public void link(ReadableMap params) {

        Log.d("JIJUYEOKAKAO", params.toString());

        FeedTemplate feedParams = FeedTemplate
                .newBuilder(
                        ContentObject.newBuilder(
                                getString(params, "title"),
                                getString(params, "imageUrl"),
                                LinkObject.newBuilder()
                                        .setIosExecutionParams(getString(params, "iosExecutionParams"))
                                        .setAndroidExecutionParams(getString(params, "androidExecutionParams"))
                                        .build())
                                .setDescrption(getString(params, "summary"))
                                .build())
                .addButton(new ButtonObject("앱에서 보기", LinkObject.newBuilder()
                        .setIosExecutionParams(getString(params, "iosExecutionParams"))
                        .setAndroidExecutionParams(getString(params, "androidExecutionParams"))
                        .build()))
                .build();

        KakaoLinkService.getInstance().sendDefault(getCurrentActivity(), feedParams, new ResponseCallback<KakaoLinkResponse>() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                Logger.e(errorResult.toString());
            }

            @Override
            public void onSuccess(KakaoLinkResponse result) {

            }
        });



    }

    @Override
    public String getName() {
        return "RNKakaoLink";
    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onNewIntent(Intent intent) {
        Log.d("jijuyeo", "onNewIntent - Module Listener");
        if(intent.getData() != null) {
            Log.d("jijuyeo", intent.getData().toString());
        }
    }
}