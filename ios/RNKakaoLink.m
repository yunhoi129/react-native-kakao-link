
#import "RNKakaoLink.h"
#import <KakaoLink/KakaoLink.h>
#import <KakaoMessageTemplate/KakaoMessageTemplate.h>

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
            contentBuilder.imageURL = [NSURL URLWithString:[params objectForKey:@"imageURL"]];
            contentBuilder.link = [KMTLinkObject linkObjectWithBuilderBlock:^(KMTLinkBuilder * _Nonnull linkBuilder) {
                linkBuilder.mobileWebURL = [NSURL URLWithString:[params objectForKey:@"mobileWebURL"]];
                linkBuilder.webURL = [NSURL URLWithString:[params objectForKey:@"mobileWebURL"]];
                linkBuilder.iosExecutionParams = [params objectForKey:@"iosExecutionParams"];
                linkBuilder.androidExecutionParams = [params objectForKey:@"androidExecutionParams"];
            }];
        }];
        // 버튼
        [feedTemplateBuilder addButton:[KMTButtonObject buttonObjectWithBuilderBlock:^(KMTButtonBuilder * _Nonnull buttonBuilder) {
            buttonBuilder.title = @"앱으로 이동";
            buttonBuilder.link = [KMTLinkObject linkObjectWithBuilderBlock:^(KMTLinkBuilder * _Nonnull linkBuilder) {
                linkBuilder.iosExecutionParams = [params objectForKey:@"iosExecutionParams"];
                linkBuilder.androidExecutionParams = [params objectForKey:@"androidExecutionParams"];
            }];
        }]];
    }];
    
    [[KLKTalkLinkCenter sharedCenter] sendDefaultWithTemplate:template success:^(NSDictionary<NSString *,NSString *> * _Nullable warningMsg, NSDictionary<NSString *,NSString *> * _Nullable argumentMsg) {
        // 성공
        NSDictionary *result = @{@"success" : @"true"};
        resolve(result);
    } failure:^(NSError * _Nonnull error) {
        // 에러
        reject(@"FAIL", @"FAIL", error);
    }];
}

@end

