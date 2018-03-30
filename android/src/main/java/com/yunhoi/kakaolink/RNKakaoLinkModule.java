
package com.yunhoi.kakaolink;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.Map;

public class RNKakaoLinkModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  public RNKakaoLinkModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @ReactMethod
  public void link(Map<String, Object> params, Promise promise) {

      FeedTemplate params = FeedTemplate
              .newBuilder(ContentObject.newBuilder("디저트 사진",
                      "http://mud-kage.kakao.co.kr/dn/NTmhS/btqfEUdFAUf/FjKzkZsnoeE4o19klTOVI1/openlink_640x640s.jpg",
                      LinkObject.newBuilder().setWebUrl("https://developers.kakao.com")
                              .setMobileWebUrl("https://developers.kakao.com").build())
                      .setDescrption("아메리카노, 빵, 케익")
                      .build())
              .setSocial(SocialObject.newBuilder().setLikeCount(10).setCommentCount(20)
                      .setSharedCount(30).setViewCount(40).build())
              .addButton(new ButtonObject("웹에서 보기", LinkObject.newBuilder().setWebUrl("'https://developers.kakao.com").setMobileWebUrl("'https://developers.kakao.com").build()))
              .addButton(new ButtonObject("앱에서 보기", LinkObject.newBuilder()
                      .setWebUrl("'https://developers.kakao.com")
                      .setMobileWebUrl("'https://developers.kakao.com")
                      .setAndroidExecutionParams("key1=value1")
                      .setIosExecutionParams("key1=value1")
                      .build()))
              .build();

      KakaoLinkService.getInstance().sendDefault(this, params, new ResponseCallback<KakaoLinkResponse>() {
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
}