//
//  FDFireflyIceSimpleTask.m
//  FireflyDevice
//
//  Created by Denis Bohm on 10/17/13.
//  Copyright (c) 2013-2014 Firefly Design LLC / Denis Bohm. All rights reserved.
//

#import <FireflyDevice/FDFireflyIceSimpleTask.h>

@implementation FDFireflyIceSimpleTask

+ (FDFireflyIceSimpleTask *)simpleTask:(FDFireflyIce *)fireflyIce channel:(id<FDFireflyIceChannel>)channel block:(void (^)(void))block
{
    FDFireflyIceSimpleTask *task = [[FDFireflyIceSimpleTask alloc] init];
    task.fireflyIce = fireflyIce;
    task.channel = channel;
    task.block = block;
    return task;
}

- (void)executorTaskStarted:(FDExecutor *)executor
{
    [super executorTaskStarted:executor];
    _block();
    [self next:@selector(complete)];
}

- (void)complete
{
    [self done];
}

@end
