package com.allez.application.entity;

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

    private Integer clientType;

    public static ClientInfo of(String version, Integer clientType) {
        return new ClientInfo(version, clientType);
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
        int[] appVersionNumber = convertToNumber(this.version);
        int[] lowestVersionNumber = convertToNumber(versionLimit);

        // 版本格式不一致 放行
        if (appVersionNumber.length != lowestVersionNumber.length) {
            return true;
        }

        for (int i = 0; i < appVersionNumber.length; i++) {
            if (appVersionNumber[i] > lowestVersionNumber[i]) {
                return true;
            } else if (appVersionNumber[i] < lowestVersionNumber[i]) {
                return false;
            }
        }
        return false;
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

    private int[] convertToNumber(String version) {
        String[] split = version.split("\\.");
        int[] result = new int[split.length];

        for (int i = 0; i < split.length; i++) {
            String s = split[i];
            result[i] = Integer.parseInt(s);
        }
        return result;
    }


}
