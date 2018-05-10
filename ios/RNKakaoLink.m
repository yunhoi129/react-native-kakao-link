
#import "RNKakaoLink.h"
#import <KakaoLink/KakaoLink.h>
#import <KakaoMessageTemplate/KakaoMessageTemplate.h>
#import <KakaoNavi/KakaoNavi.h>

@implementation RNKakaoLink

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}
RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(link: (NSDictionary *)params
                  resolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)
{
    KMTTemplate *template = [KMTFeedTemplate feedTemplateWithBuilderBlock:^(KMTFeedTemplateBuilder * _Nonnull feedTemplateBuilder) {
        
        // 콘텐츠
        feedTemplateBuilder.content = [KMTContentObject contentObjectWithBuilderBlock:^(KMTContentBuilder * _Nonnull contentBuilder) {
            contentBuilder.title = [params objectForKey:@"title"];
            contentBuilder.desc = [params objectForKey:@"desc"];
            contentBuilder.imageURL = [NSURL URLWithString:[params objectForKey:@"imageUrl"]];
            contentBuilder.link = [KMTLinkObject linkObjectWithBuilderBlock:^(KMTLinkBuilder * _Nonnull linkBuilder) {
                linkBuilder.iosExecutionParams = [params objectForKey:@"iosExecutionParams"];
                linkBuilder.androidExecutionParams = [params objectForKey:@"androidExecutionParams"];
            }];
        }];
        
        [feedTemplateBuilder addButton:[KMTButtonObject buttonObjectWithBuilderBlock:^(KMTButtonBuilder * _Nonnull buttonBuilder) {
            buttonBuilder.title = @"앱으로 이동";
            buttonBuilder.link = [KMTLinkObject linkObjectWithBuilderBlock:^(KMTLinkBuilder * _Nonnull linkBuilder) {
                linkBuilder.iosExecutionParams = [params objectForKey:@"iosExecutionParams"];
                linkBuilder.androidExecutionParams = [params objectForKey:@"androidExecutionParams"];
            }];
        }]];
    }];
    
    [[KLKTalkLinkCenter sharedCenter] sendDefaultWithTemplate:template success:^(NSDictionary<NSString *,NSString *> * _Nullable warningMsg, NSDictionary<NSString *,NSString *> * _Nullable argumentMsg) {
    } failure:^(NSError * _Nonnull error) {
    }];
}

RCT_EXPORT_METHOD(navi: (NSDictionary *) naviParams)
{
    //name: _.get(data, 'name', ''),
    //lat: lat,
    //lng: lng
    NSLog(@"%@", naviParams);
    // 목적지 생성
    KNVLocation *destination = [KNVLocation locationWithName:[naviParams objectForKey:@"name"]
                                                           x:[NSNumber numberWithDouble:[[naviParams objectForKey:@"lng"] doubleValue]]
                                                           y:[NSNumber numberWithDouble:[[naviParams objectForKey:@"lat"] doubleValue]]];
    
    // WGS84 좌표타입 옵션 설정
    KNVOptions *options = [KNVOptions options];
    options.coordType = KNVCoordTypeWGS84;
    
    // 목적지 공유 실행
    KNVParams *params = [KNVParams paramWithDestination:destination options:options];
    [[KNVNaviLauncher sharedLauncher] shareDestinationWithParams:params completion:nil];
}

@end

