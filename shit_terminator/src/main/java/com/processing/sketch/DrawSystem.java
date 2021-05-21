package com.processing.sketch;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: wangzilinn@gmail.com
 * @Date: 5/16/2021 10:05 PM
 * @Modified: wangzilinn@gmail.com
 */
public class DrawSystem {
    private final PApplet sketch;
    PVector centerPosition;
    PVector size;
    ParticleGroup ps;
    HashMap<Integer, Integer> levelNamesCounterMap;

    public DrawSystem(PApplet sketch) {
        this.sketch = sketch;
        System.out.println(sketch.width);
        centerPosition = new PVector((float) sketch.width / 2, (float) sketch.height / 2);
        size = new PVector(sketch.width, sketch.height);
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
     * @param info 游戏信息
     * @return 是否显示了关卡名字
     */
    @CalledByDraw
    public boolean checkAndDrawLevelNameScreen(Info info) {
        if (levelNamesCounterMap == null) {
            levelNamesCounterMap = new HashMap<>();
            for (int i = 0; i <= info.getMaxLevel(); i++) {
                levelNamesCounterMap.put(i, 40);
            }
        }
        int currentLevel = info.getCurrentLevel();
        if (levelNamesCounterMap.get(currentLevel) > 0) {
            String str = "Chapter " + (currentLevel + 1) + ": " + info.getCurrentLevelName();
            sketch.fill(0);
            sketch.textSize(60);
            sketch.text(str, getAlignX(str, 60, size.x), centerPosition.y);
            levelNamesCounterMap.computeIfPresent(currentLevel, (key, value) -> --value);
            return true;
        }
        return false;
    }

    @CalledByDraw
    public void drawEnemyShip(EnemyShip enemyShip) {
        sketch.fill(enemyShip.blood);
        sketch.rect(enemyShip.position.x, enemyShip.position.y, enemyShip.size.x, enemyShip.size.y);
    }

    @CalledByDraw
    public void drawShip(Ship ship) {
        if (ship.dead) {
            sketch.fill(0);
            sketch.rect(ship.deadPosition.x, ship.deadPosition.y, ship.size.x, ship.size.y);
            return;
        }
        sketch.fill(0);
        sketch.rect(ship.position.x, ship.position.y, ship.size.x, ship.size.y);
    }

    public void drawBullets(List<Bullet> bulletList) {
        for (Bullet bullet : bulletList) {
            sketch.fill(0);
            sketch.ellipse(bullet.position.x, bullet.position.y, bullet.size.x, bullet.size.y);
        }
    }

    public void drawOils(List<Oil> oilList) {
        for (Oil oil : oilList) {
            sketch.fill(200);
            sketch.ellipse(oil.position.x, oil.position.y, oil.size.x, oil.size.y);
        }
    }

    public void drawGameLayout(Info info, Ship ship) {
        sketch.fill(0);
        sketch.textSize(12);
        sketch.text("Remaining fuel:" + ship.fuel, 10, 500);
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
