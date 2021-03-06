//
//  FDFireflyIceChannelMock.h
//  FireflyDevice
//
//  Created by Denis Bohm on 2/22/14.
//  Copyright (c) 2014 Firefly Design LLC / Denis Bohm. All rights reserved.
//

#import <FireflyDevice/FDFireflyIceChannel.h>
#import <FireflyDevice/FDFireflyIceDeviceMock.h>

@interface FDFireflyIceChannelMock : NSObject <FDFireflyIceChannel>

@property id<FDFireflyIceChannelDelegate> delegate;

@property FDFireflyIceDeviceMock *device;

@end
