package com.allez.application.entity;

import com.allez.application.enums.ClientTypeEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.util.Objects;

/**
 * @author chenyu
 * @date 2024/12/18 14:33
 * @description 客户端信息
 */
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@FieldNameConstants
public class ClientInfo {

    private String version;

    /**
     * {@link ClientTypeEnum}
     */
    private Integer clientType;

    private String ip;

    public static ClientInfo of(String version, Integer clientType, String ip) {
        return new ClientInfo(version, clientType, ip);
    }


    /**
     * 版本大于等于  版本限制
     */
    public boolean versionGreaterThanOrEquals(String versionLimit) {
        return versionGreaterThan(versionLimit) || versionEquals(versionLimit);
    }

    /**
     * 版本大于 版本限制
     */
    public boolean versionGreaterThan(String versionLimit) {
        Integer appVersionNumber = versionConvertToNumber(this.version);
        Integer lowestVersionNumber = versionConvertToNumber(versionLimit);
        return appVersionNumber > lowestVersionNumber;
    }

    /**
     * 版本是否等于
     */
    public boolean versionEquals(String versionLimit) {
        return Objects.equals(versionLimit, this.version);
    }

    /**
     * 版本小于等于版本限制
     */
    public boolean versionLessThanOrEquals(String versionLimit) {
        return !versionGreaterThan(versionLimit);
    }

    /**
     * 版本小于版本限制
     */
    public boolean versionLessThan(String versionLimit) {
        return !versionGreaterThanOrEquals(versionLimit);
    }

    private Integer versionConvertToNumber(String version) {
        String replace = version.replaceAll("\\.", "");
        return Integer.parseInt(replace);
    }


}
