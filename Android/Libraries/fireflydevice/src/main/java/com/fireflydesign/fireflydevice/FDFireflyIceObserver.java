package com.fireflydesign.fireflydevice;

import java.util.Map;

public interface FDFireflyIceObserver {

    void fireflyIceStatus(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, FDFireflyIceChannel.Status status);
    void fireflyIceDetourError(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, FDDetour detour, FDError error);
    void fireflyIcePing(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, byte[] data);
    void fireflyIceVersion(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, FDFireflyIceVersion version);
    void fireflyIceHardwareVersion(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, FDFireflyIceHardwareVersion version);
    void fireflyIceHardwareId(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, FDFireflyIceHardwareId hardwareId);
    void fireflyIceBootVersion(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, FDFireflyIceVersion bootVersion);
    void fireflyIceDebugLock(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, boolean debugLock);
    void fireflyIceTime(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, double time);
    void fireflyIceRTC(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, Map<String, Object> rtc);
    void fireflyIceHardware(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, Map<String, Object> hardware);
    void fireflyIcePower(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, FDFireflyIcePower power);
    void fireflyIceSite(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, String site);
    void fireflyIceReset(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, FDFireflyIceReset reset);
    void fireflyIceStorage(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, FDFireflyIceStorage storage);
    void fireflyIceMode(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, int mode);
    void fireflyIceTxPower(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, int txPower);
    void fireflyIceRegulator(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, Byte regulator);
    void fireflyIceSensingCount(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, Number sensingCount);
    void fireflyIceIndicate(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, Boolean indicate);
    void fireflyIceRecognition(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, Boolean recognition);
    void fireflyIceLock(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, FDFireflyIceLock lock);
    void fireflyIceLogging(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, FDFireflyIceLogging logging);
    void fireflyIceName(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, String name);
    void fireflyIceDiagnostics(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, FDFireflyIceDiagnostics diagnostics);
    void fireflyIceRetained(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, FDFireflyIceRetained retained);
    void fireflyIceDirectTestModeReport(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, FDFireflyIceDirectTestModeReport directTestModeReport);
    void fireflyIceUpdateVersion(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, FDFireflyIceUpdateVersion version);
    void fireflyIceExternalHash(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, byte[] externalHash);
    void fireflyIcePageData(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, byte[] pageData);
    void fireflyIceSectorHashes(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, FDFireflyIceSectorHash[] sectorHashes);
    void fireflyIceUpdateCommit(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, FDFireflyIceUpdateCommit updateCommit);
    void fireflyIceSensing(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, FDFireflyIceSensing sensing);
    void fireflyIceSync(FDFireflyIce fireflyIce, FDFireflyIceChannel channel, byte[] syncData);

}
