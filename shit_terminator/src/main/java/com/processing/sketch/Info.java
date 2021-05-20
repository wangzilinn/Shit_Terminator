package com.processing.sketch;

/**
 * 游戏的所有信息
 *
 * @Author: wangzilinn@gmail.com
 * @Date: 5/20/2021 11:14 AM
 */
public class Info {
    final private int maxLevel = 1;
    String[] levels = new String[]{"Hero", "Salvation"};
    private int currentLevel = 0;

    /**
     * 用于记录每一个关卡的名字显示的时间
     */


    public int getMaxLevel() {
        return maxLevel;
    }

    public void resetLevel() {
        this.currentLevel = 0;
    }

    public void upgradeLevel() {
        this.currentLevel++;
    }

    public boolean isMaxLevel() {
        return currentLevel == maxLevel;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public String getCurrentLevelName() {
        return levels[currentLevel];
    }
}
