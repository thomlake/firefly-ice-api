//
//  FDFireflyIceTaskSteps.cpp
//  FireflyDevice
//
//  Created by Denis Bohm on 9/14/13.
//  Copyright (c) 2013-2014 Firefly Design LLC / Denis Bohm. All rights reserved.
//

package com.fireflydesign.fireflydevice;

import java.lang.reflect.Method;

import java.util.Map;
import java.util.Random;

public class FDFireflyIceTaskSteps extends FDExecutor.Task implements FDFireflyIceObserver {

    public FDFireflyIce fireflyIce;
    public FDFireflyIceChannel channel;

    public FDFireflyDeviceLog log;

    Random random;
    Method invocation;
    int invocationId;

    public FDFireflyIceTaskSteps(FDFireflyIce fireflyIce, FDFireflyIceChannel channel) {
		this.fireflyIce = fireflyIce;
		this.channel = channel;
		timeout = 15;
		priority = 0;
		isSuspended = false;
		appointment = 0;
        random = new Random();
		invocation = null;
		invocationId = 0;
	}

    public FDFireflyIceTaskSteps() {
        this(null, null);
    }

    void next(Method invocation) {
        //    FDFireflyDeviceLogDebug(@"queing next step %s", NSStringFromSelector(selector));

        fireflyIce.executor.feedWatchdog(this);

        this.invocation = invocation;
        invocationId = random.nextInt();

        //    FDFireflyDeviceLogDebug(@"setup ping 0x%08x %s %s", invocationId, NSStringFromClass([self class]), NSStringFromSelector(_invocation.selector));

        FDBinary binary = new FDBinary();
        binary.putUInt32(invocationId);
        byte[] data = FDBinary.toByteArray(binary.dataValue());
        fireflyIce.coder.sendPing(channel, data);
    }

    public void next(String name) {
        for (Class clazz = getClass(); clazz != null; clazz = clazz.getSuperclass()) {
            try {
                Method method = clazz.getDeclaredMethod(name);
                next(method);
                return;
            } catch (NoSuchMethodException e) {
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException("FDFireflyIceTaskSteps:next no such method " + name);
    }

    public void done() {
        //    FDFireflyDeviceLogDebug(@"task done");
        fireflyIce.executor.complete(this);
    }

    public void executorTaskStarted(FDExecutor executor) {
		//    FDFireflyDeviceLogDebug(@"%s task started", NSStringFromClass([self class]));
		fireflyIce.observable.addObserver(this);
	}

    public void executorTaskSuspended(FDExecutor executor) {
		//    FDFireflyDeviceLogDebug(@"%s task suspended", NSStringFromClass([self class]));
		fireflyIce.observable.removeObserver(this);
	}

    public void executorTaskResumed(FDExecutor executor) {
		//    FDFireflyDeviceLogDebug(@"%s task resumed", NSStringFromClass([self class]));
		fireflyIce.observable.addObserver(this);
	}

    public void executorTaskCompleted(FDExecutor executor) {
		//    FDFireflyDeviceLogDebug(@"%s task completed", NSStringFromClass([self class]));
		fireflyIce.observable.removeObserver(this);
	}

    public void executorTaskFailed(FDExecutor executor, FDError error) {
		//    FDFireflyDeviceLogDebug(@"%s task failed with error %s", NSStringFromClass([self class]), error);
		fireflyIce.observable.removeObserver(this);
	}

    @Override
    public void fireflyIceStatus(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, FDFireflyIceChannel.Status status) {
    }

    @Override
    public void fireflyIceDetourError(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, FDDetour detour, FDError error) {
		fireflyIce.executor.fail(this, error);
	}

    @Override
    public void fireflyIcePing(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, byte[] data) {
		//    FDFireflyDeviceLogDebug(@"ping received");
		FDBinary binary = new FDBinary(FDBinary.toList(data));
		int invocationId = binary.getUInt32();
		if (this.invocationId != invocationId) {
			FDFireflyDeviceLogger.warn(log, "FD010301", "unexpected ping 0x%08x (expected 0x%08x)", invocationId, this.invocationId);
			return;
		}

		if (invocation != null) {
			//        FDFireflyDeviceLogDebug(@"invoking step %s", NSStringFromSelector(_invocation.selector));
            Method invocation = this.invocation;
			this.invocation = null;
            try {
                invocation.invoke(this);
            } catch (Exception e) {
                FDFireflyDeviceLogger.warn(log, "FD010302", "unexpected exception %s", e.toString());
            }
		} else {
			//        FDFireflyDeviceLogDebug(@"all steps completed");
			fireflyIce.executor.complete(this);
		}
	}

    @Override
    public void fireflyIceVersion(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, FDFireflyIceVersion version) {
    }

    @Override
    public void fireflyIceHardwareVersion(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, FDFireflyIceHardwareVersion version) {

    }

    @Override
    public void fireflyIceHardwareId(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, FDFireflyIceHardwareId hardwareId) {
    }

    @Override
    public void fireflyIceBootVersion(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, FDFireflyIceVersion bootVersion) {
    }

    @Override
    public void fireflyIceDebugLock(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, boolean debugLock) {
    }

    @Override
    public void fireflyIceTime(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, double time) {
    }

    @Override
    public void fireflyIceRTC(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, Map<String, Object> rtc) {

    }

    @Override
    public void fireflyIceHardware(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, Map<String, Object> hardware) {

    }

    @Override
    public void fireflyIcePower(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, FDFireflyIcePower power) {
    }

    @Override
    public void fireflyIceSite(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, String site) {
    }

    @Override
    public void fireflyIceReset(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, FDFireflyIceReset reset) {
    }

    @Override
    public void fireflyIceStorage(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, FDFireflyIceStorage storage) {
    }

    @Override
    public void fireflyIceMode(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, int mode) {
    }

    @Override
    public void fireflyIceTxPower(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, int txPower) {
    }

    @Override
    public void fireflyIceRegulator(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, Byte regulator) {

    }

    @Override
    public void fireflyIceSensingCount(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, Number sensingCount) {

    }

    @Override
    public void fireflyIceIndicate(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, Boolean indicate) {

    }

    @Override
    public void fireflyIceRecognition(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, Boolean recognition) {

    }

    @Override
    public void fireflyIceLock(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, FDFireflyIceLock lock) {
    }

    @Override
    public void fireflyIceLogging(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, FDFireflyIceLogging logging) {
    }

    @Override
    public void fireflyIceName(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, String name) {
    }

    @Override
    public void fireflyIceDiagnostics(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, FDFireflyIceDiagnostics diagnostics) {
    }

    @Override
    public void fireflyIceRetained(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, FDFireflyIceRetained retained) {
    }

    @Override
    public void fireflyIceDirectTestModeReport(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, FDFireflyIceDirectTestModeReport directTestModeReport) {
    }

    @Override
    public void fireflyIceUpdateVersion(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, FDFireflyIceUpdateVersion version) {

    }

    @Override
    public void fireflyIceExternalHash(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, byte[] externalHash) {
    }

    @Override
    public void fireflyIcePageData(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, byte[] pageData) {
    }

    @Override
    public void fireflyIceSectorHashes(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, FDFireflyIceSectorHash[] sectorHashes) {
    }

    @Override
    public void fireflyIceUpdateCommit(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, FDFireflyIceUpdateCommit updateCommit) {
    }

    @Override
    public void fireflyIceSensing(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, FDFireflyIceSensing sensing) {
    }

    @Override
    public void fireflyIceSync(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, byte[] syncData) {
    }

}
