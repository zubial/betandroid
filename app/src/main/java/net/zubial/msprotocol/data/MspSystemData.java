package net.zubial.msprotocol.data;

import net.zubial.msprotocol.enums.MspFlightControllerEnum;
import net.zubial.msprotocol.enums.MspSdcardStateEnum;

import java.io.Serializable;

public class MspSystemData implements Serializable {

    private static final long serialVersionUID = 1L;

    // MSP_API_VERSION
    private Integer mspProtocolVersion;
    private Double boardApiVersion;

    // MSP_FC_VARIANT
    private MspFlightControllerEnum boardFlightControllerIdentifier;

    // MSP_FC_VERSION
    private String boardFlightControllerVersion;

    // MSP_BOARD_INFO
    private String boardIdentifier;
    private Integer boardVersion;
    private Integer boardType;

    // MSP_BUILD_INFO
    private String boardBuildInfo;

    // MSP_NAME
    private String boardName;

    // MSP_STATUS_EX
    private Integer statusCycleTime;
    private Integer statusI2cError;
    private Integer statusCpuload;

    private Integer statusActiveSensors;
    private Boolean statusHaveAccel;
    private Boolean statusHaveGyro;
    private Boolean statusHaveBaro;
    private Boolean statusHaveMag;
    private Boolean statusHaveGps;
    private Boolean statusHaveSonar;

    private Integer statusMode;
    private Integer statusProfile;
    private Integer statusNumProfiles;
    private Integer statusRateProfile;

    // MSP_SDCARD_SUMMARY
    private Boolean sdcardSupported;
    private MspSdcardStateEnum sdcardState;
    private Integer sdcardFsError;
    private Integer sdcardFreeSize;
    private Integer sdcardTotalSize;

    public MspSystemData() {
        // Default Constructor
    }

    public Integer getMspProtocolVersion() {
        return mspProtocolVersion;
    }

    public void setMspProtocolVersion(Integer mspProtocolVersion) {
        this.mspProtocolVersion = mspProtocolVersion;
    }

    public Double getBoardApiVersion() {
        return boardApiVersion;
    }

    public void setBoardApiVersion(Double boardApiVersion) {
        this.boardApiVersion = boardApiVersion;
    }

    public MspFlightControllerEnum getBoardFlightControllerIdentifier() {
        return boardFlightControllerIdentifier;
    }

    public void setBoardFlightControllerIdentifier(MspFlightControllerEnum boardFlightControllerIdentifier) {
        this.boardFlightControllerIdentifier = boardFlightControllerIdentifier;
    }

    public String getBoardFlightControllerVersion() {
        return boardFlightControllerVersion;
    }

    public void setBoardFlightControllerVersion(String boardFlightControllerVersion) {
        this.boardFlightControllerVersion = boardFlightControllerVersion;
    }

    public String getBoardIdentifier() {
        return boardIdentifier;
    }

    public void setBoardIdentifier(String boardIdentifier) {
        this.boardIdentifier = boardIdentifier;
    }

    public Integer getBoardVersion() {
        return boardVersion;
    }

    public void setBoardVersion(Integer boardVersion) {
        this.boardVersion = boardVersion;
    }

    public Integer getBoardType() {
        return boardType;
    }

    public void setBoardType(Integer boardType) {
        this.boardType = boardType;
    }

    public String getBoardBuildInfo() {
        return boardBuildInfo;
    }

    public void setBoardBuildInfo(String boardBuildInfo) {
        this.boardBuildInfo = boardBuildInfo;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public Integer getStatusCycleTime() {
        return statusCycleTime;
    }

    public void setStatusCycleTime(Integer statusCycleTime) {
        this.statusCycleTime = statusCycleTime;
    }

    public Integer getStatusI2cError() {
        return statusI2cError;
    }

    public void setStatusI2cError(Integer statusI2cError) {
        this.statusI2cError = statusI2cError;
    }

    public Integer getStatusCpuload() {
        return statusCpuload;
    }

    public void setStatusCpuload(Integer statusCpuload) {
        this.statusCpuload = statusCpuload;
    }

    public Integer getStatusActiveSensors() {
        return statusActiveSensors;
    }

    public void setStatusActiveSensors(Integer statusActiveSensors) {
        this.statusActiveSensors = statusActiveSensors;
    }

    public Boolean getStatusHaveAccel() {
        return statusHaveAccel;
    }

    public void setStatusHaveAccel(Boolean statusHaveAccel) {
        this.statusHaveAccel = statusHaveAccel;
    }

    public Boolean getStatusHaveGyro() {
        return statusHaveGyro;
    }

    public void setStatusHaveGyro(Boolean statusHaveGyro) {
        this.statusHaveGyro = statusHaveGyro;
    }

    public Boolean getStatusHaveBaro() {
        return statusHaveBaro;
    }

    public void setStatusHaveBaro(Boolean statusHaveBaro) {
        this.statusHaveBaro = statusHaveBaro;
    }

    public Boolean getStatusHaveMag() {
        return statusHaveMag;
    }

    public void setStatusHaveMag(Boolean statusHaveMag) {
        this.statusHaveMag = statusHaveMag;
    }

    public Boolean getStatusHaveGps() {
        return statusHaveGps;
    }

    public void setStatusHaveGps(Boolean statusHaveGps) {
        this.statusHaveGps = statusHaveGps;
    }

    public Boolean getStatusHaveSonar() {
        return statusHaveSonar;
    }

    public void setStatusHaveSonar(Boolean statusHaveSonar) {
        this.statusHaveSonar = statusHaveSonar;
    }

    public Integer getStatusMode() {
        return statusMode;
    }

    public void setStatusMode(Integer statusMode) {
        this.statusMode = statusMode;
    }

    public Integer getStatusProfile() {
        return statusProfile;
    }

    public void setStatusProfile(Integer statusProfile) {
        this.statusProfile = statusProfile;
    }

    public Integer getStatusNumProfiles() {
        return statusNumProfiles;
    }

    public void setStatusNumProfiles(Integer statusNumProfiles) {
        this.statusNumProfiles = statusNumProfiles;
    }

    public Integer getStatusRateProfile() {
        return statusRateProfile;
    }

    public void setStatusRateProfile(Integer statusRateProfile) {
        this.statusRateProfile = statusRateProfile;
    }

    public Boolean getSdcardSupported() {
        return sdcardSupported;
    }

    public void setSdcardSupported(Boolean sdcardSupported) {
        this.sdcardSupported = sdcardSupported;
    }

    public MspSdcardStateEnum getSdcardState() {
        return sdcardState;
    }

    public void setSdcardState(MspSdcardStateEnum sdcardState) {
        this.sdcardState = sdcardState;
    }

    public Integer getSdcardFsError() {
        return sdcardFsError;
    }

    public void setSdcardFsError(Integer sdcardFsError) {
        this.sdcardFsError = sdcardFsError;
    }

    public Integer getSdcardFreeSize() {
        return sdcardFreeSize;
    }

    public void setSdcardFreeSize(Integer sdcardFreeSize) {
        this.sdcardFreeSize = sdcardFreeSize;
    }

    public Integer getSdcardTotalSize() {
        return sdcardTotalSize;
    }

    public void setSdcardTotalSize(Integer sdcardTotalSize) {
        this.sdcardTotalSize = sdcardTotalSize;
    }
}
