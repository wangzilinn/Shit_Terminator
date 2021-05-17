package com.processing.sketch;

/**
 * 游戏状态的枚举类型
 *
 * @Author: wangzilinn@gmail.com
 * @Date: 5/17/2021 4:44 PM
 */
public enum State {
    /**
     * 游戏准备页面
     */
    READY,
    /**
     * 游戏正在运行
     */
    RUNNING,
    /**
     * 游戏过关
     */
    PASS,
    /**
     * 游戏通关
     */
    WIN,
    /**
     * 死掉了
     */
    OVER
}
