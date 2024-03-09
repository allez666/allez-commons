package com.allez.lang.util;

import cn.hutool.core.util.StrUtil;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.SecureRandom;
import java.util.Enumeration;

/**
 * @author chenyu
 * @date 2024/3/8 19:04
 * @description
 */
public class SnowFlakeUtils {

    //向左偏移10位
    private static final long NODE_SHIFT = 10L;
    //向左偏移12位
    private static final long SEQUENCE_SHIFT = 12L;
    //最大机器值
    private static final long MAX_NODE = ~(-1L << NODE_SHIFT);
    //最大序列值
    private static final long MAX_SEQUENCE = ~(-1L << SEQUENCE_SHIFT);
    //机器id
    private long node;
    //序列
    private long sequence;
    //最后一次取id的时间戳
    private long referenceTime;

    private static final SnowFlakeUtils INSTANCE = new SnowFlakeUtils();

    //初始化如果没有指定node随机生成一个1-最大机器值之间的值
    private SnowFlakeUtils() {
        try {
            String mac = getLocalMac();
            if (StrUtil.isNotEmpty(mac)) {
                long nodeId = mac.hashCode() % MAX_NODE;
                this.node = nodeId < 1 ? 1 : nodeId;
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.node = new SecureRandom().nextInt((int) MAX_NODE) + 1L;
    }

    private static String getLocalMac() throws SocketException {
        Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
        while (enumeration.hasMoreElements()) {
            byte[] mac = enumeration.nextElement().getHardwareAddress();
            if (mac != null) {
                StringBuffer sb = new StringBuffer("");
                for (int i = 0; i < mac.length; i++) {
                    if (i != 0) {
                        sb.append("-");
                    }
                    //字节转换为整数
                    int temp = mac[i] & 0xff;
                    String str = Integer.toHexString(temp);
                    if (str.length() == 1) {
                        sb.append("0" + str);
                    } else {
                        sb.append(str);
                    }
                }
                return sb.toString().toUpperCase();
            }
        }
        return null;
    }

    //初始化指定每台编号避免不同的机器在同一时间生成一样的id
    public SnowFlakeUtils(long node) {
        if (node >= MAX_NODE || node < 0) {
            throw new IllegalArgumentException(String.format("node can't be greater than %d or less than 0", MAX_NODE));
        }
        this.node = node;
    }

    public static long maxNode() {
        return MAX_NODE;
    }

    //生成下一个id(线程安全)
    public synchronized long next() {
        //获取1970年1月1日0时起的毫秒差
        long currentTime = System.currentTimeMillis();
        //序列当前值
        long counter;
        //当前时间不能小于最后取id时间(毫秒差会随时间增长,说明系统时钟回退过)
        if (currentTime < referenceTime) {
            throw new RuntimeException(String.format("Last referenceTime %s is after reference time %s", referenceTime, currentTime));
        }
        //大与最后时间戳序列归零
        else if (currentTime > referenceTime) {
            this.sequence = (this.sequence > 0 ? 0L : 1L);
        } else {
            //序列小于最大值自增
            if (this.sequence <= SnowFlakeUtils.MAX_SEQUENCE) {
                this.sequence++;
            }
            //序列溢出
            else {
                //阻塞到下一个毫秒,获得新时间戳
                while (currentTime <= referenceTime) {
                    currentTime = System.currentTimeMillis();
                }
                /**
                 * 在新的时间戳获取下一个id
                 * 关于重复阻塞:缩小机器标识值，增大序列最大值
                 */
                next();
            }
        }
        //记录当前值
        counter = this.sequence;
        //记录最后生成id时间戳
        referenceTime = currentTime;

        //移位并通过或运算拼到一起组成64位的ID
        return currentTime << NODE_SHIFT << SEQUENCE_SHIFT | node << SEQUENCE_SHIFT | counter;
    }

    public static long nextId() {
        return INSTANCE.next();
    }

    public static void main(String[] args) throws SocketException {
        System.out.println(nextId());
    }
}
