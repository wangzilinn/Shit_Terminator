package com.processing.sketch;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: wangzilinn@gmail.com
 * @Date: 5/16/2021 10:05 PM
 * @Modified: wangzilinn@gmail.com
 */
public class Screen {
    private final PApplet sketch;
    PVector centerPosition;
    PVector size;

    String[] levels = new String[]{"Hero", "Salvation"};
    /**
     * 用于记录每一个关卡的名字显示的时间
     */
    HashMap<Integer, Integer> levelNamesCounterMap;
    ParticleGroup ps;

    public Screen(PApplet sketch) {
        this.sketch = sketch;
        System.out.println(sketch.width);
        centerPosition = new PVector((float) sketch.width / 2, (float) sketch.height / 2);
        size = new PVector(sketch.width, sketch.height);
        levelNamesCounterMap = new HashMap<>();
        for (int i = 0; i < levels.length; i++) {
            levelNamesCounterMap.put(i, 40);
        }
        ps = new ParticleGroup(sketch, 6, 2, 200);
    }

    /**
     * 绘制通关页面
     *
     * @param shipPosition 要显示飞船效果的位置
     */
    @CalledByDraw
    public void drawWinScreen(PVector shipPosition) {
        String str = "You Win";
        sketch.fill(0);
        sketch.textSize(60);
        sketch.text(str, getAlignX(str, 60, size.x), centerPosition.y);
        str = "press space to restart";
        sketch.textSize(20);
        sketch.text(str, getAlignX(str, 20, size.x), centerPosition.y + 40);
        ps.addParticle(shipPosition);
        ps.run();
    }

    /**
     * 绘制失败页面
     *
     * @param shipPosition 要显示飞船效果的位置
     */
    @CalledByDraw
    public void drawLoseScreen(PVector shipPosition) {
        String str = "You Lose";
        sketch.fill(0);
        sketch.textSize(60);
        sketch.text(str, getAlignX(str, 60, size.x), centerPosition.y);
        str = "press space to restart";
        sketch.textSize(20);
        sketch.text(str, getAlignX(str, 20, size.x), centerPosition.y + 40);
        ps.addParticle(shipPosition);
        ps.run();
    }

    /**
     * 绘制下一关
     *
     * @param shipPosition 要显示飞船效果的位置
     */
    @CalledByDraw
    public void drawNextLevelScreen(PVector shipPosition) {
        sketch.fill(0);
        sketch.textSize(60);
        String str = "Next Level";
        sketch.text(str, getAlignX(str, 60, size.x), centerPosition.y);
        str = "press space to start";
        sketch.textSize(20);
        sketch.text(str, getAlignX(str, 20, size.x), centerPosition.y + 40);
        ps.addParticle(shipPosition);
        ps.run();
    }

    /**
     * 绘制打开游戏的第一个界面
     */
    @CalledByDraw
    public void drawReadyScreen() {
        sketch.fill(0);
        sketch.textSize(60);
        String str = "Shit Terminator";
        sketch.text(str, getAlignX(str, 60, size.x), centerPosition.y);
        str = "press space to start";
        sketch.textSize(20);
        sketch.text(str, getAlignX(str, 20, size.x), centerPosition.y + 40);
    }

    /**
     * 检查是否需要显示关卡名字, 如果不需要则不显示
     *
     * @param level 关数
     * @return 是否显示了关卡名字
     */
    @CalledByDraw
    public boolean checkAndDrawLevelNameScreen(int level) {
        if (levelNamesCounterMap.get(level) > 0) {
            String str = "Chapter " + (level + 1) + ": " + levels[level];
            sketch.fill(0);
            sketch.textSize(60);
            sketch.text(str, getAlignX(str, 60, size.x), centerPosition.y);
            levelNamesCounterMap.computeIfPresent(level, (key, value) -> --value);
            return true;
        }
        return false;
    }

    /**
     * 重置每一关的名字显示的帧数的计数器
     */
    void resetDrawLevelNameScreenCounter() {
        for (Map.Entry<Integer, Integer> entry : levelNamesCounterMap.entrySet()) {
            entry.setValue(50);
        }
    }

    /**
     * @param str      需要显示的字符
     * @param textSize 文字大小
     * @param width    画面宽度
     * @return 如果要把该字符显示在画面中间, 则其横坐标的值
     */
    private int getAlignX(String str, int textSize, float width) {
        int len = str.length() * textSize / 2;
        return (int) (width / 2 - len / 2);
    }


}
