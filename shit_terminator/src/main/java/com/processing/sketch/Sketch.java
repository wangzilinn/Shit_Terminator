package com.processing.sketch;

import processing.core.PApplet;

import java.util.Iterator;
import java.util.LinkedList;


/**
 * @Author: huangyiqin
 * @Date: 5/16/2021 8:34 PM
 * @Modified: wangzilinn@gmail.com
 */
public class Sketch extends PApplet {
    /**
     * 游戏总关卡数:2关
     */
    final private int maxLevel = 1;
    Screen screen;
    EnemyShip enemyShip;

    double a = 0;
    int b = 0;
    Ship ship;
    private State state = State.READY;
    /**
     * 游戏当前关卡数:从0开始
     */
    private int level = 0;


    LinkedList<Oil> oilList = new LinkedList<>();
    LinkedList<Bullet> bulletList = new LinkedList<>();

    public void settings() {
        size(1500, 800);
    }

    public void setup() {
        screen = new Screen(this);
        enemyShip = new EnemyShip(this, 0, 0);
        ship = new Ship(this);
    }

    public void draw() {
        background(255);
        switch (state) {
            case READY:
                screen.drawReadyScreen();
                break;
            case PASS:
                screen.drawNextLevelScreen(enemyShip.position);
                break;
            case RUNNING:
                drawGame();
                break;
            case WIN:
                screen.drawWinScreen(enemyShip.position);
                break;
            case OVER:
                screen.drawLoseScreen(enemyShip.position);
                break;
        }
    }

    /**
     * 真正执行游戏部分的逻辑
     */
    @CalledByDraw
    private void drawGame() {
        //检查是否需要显示关卡名字:
        if (screen.checkAndDrawLevelNameScreen(level)) {
            //如果显示了关卡名字,则直接显示下一帧
            return;
        }
        if (a % 30 < 15) {
            enemyShip.moveAndDraw(Direction.DOWN);
        } else {
            enemyShip.moveAndDraw(Direction.UP);
        }
        a += 0.3;

        if (b % 3 == 0) {
            Oil oils = enemyShip.leak();
            if (oils != null) {
                //if ship is destroyed, then the oil is null
                oilList.add(oils);
            }
        }
        b++;

        ship.updateAndDraw();

        //遍历所有油滴,检查鼠标操作的飞船是否可以吸收这个油滴
        Iterator<Oil> oilIter = oilList.iterator();
        while(oilIter.hasNext()){
            Oil oil = oilIter.next();
            oil.updateAndDraw();

            if(ship.checkIfAbsorb(oil)){
                ship.absorbFuel(oil);
                oilIter.remove();
            } else if(oil.position.x >= width){// 超出画面范围
                oilIter.remove();
            }
        }

        //遍历所有子弹,检查子弹是否超出画面,是否击中敌方飞船
        Iterator<Bullet> BulletIter = bulletList.iterator();
        while(BulletIter.hasNext()){
            Bullet bullet = BulletIter.next();
            bullet.updateAndDraw();
            //敌方飞船检查是否被击中:
            if (enemyShip.checkIfHit(bullet)) {
                enemyShip.hit(bullet);
                BulletIter.remove();
            } else if (bullet.position.x <= 0) {// 超出画面范围
                BulletIter.remove();
            }
        }

        // 判断是否游戏已经结束:
        if (enemyShip.dead) {
            System.out.println("enemyShip is dead");
            if (level == maxLevel) {
                state = State.WIN;
                level = 0;
                screen.resetDrawLevelNameScreenCounter();
            } else {
                state = State.PASS;
                level++;
            }
            ship.reset();
            enemyShip.reset();
        } else if(ship.dead) {
            state = State.OVER;
            level = 0;
            screen.resetDrawLevelNameScreenCounter();
        }

        fill(0);
        textSize(12);
        text("Remaining fuel:" + ship.fuel,10, 500);
    }

    public void keyPressed() {
        // 按空格,则将状态变为执行游戏逻辑状态
        if (key == ' ') {
            if (state == State.WIN || state == State.OVER) {
                //当赢了或输了,则点击空格回到准备界面
                state = State.READY;
            } else if (state == State.READY || state == State.PASS) {
                //当在准备界面或者过关了,则开始运行游戏
                state = State.RUNNING;
            }
        }
        int keyNumber = key - 80;
        if (keyNumber == 10 || keyNumber == 42) {
            Bullet bullet = ship.shoot();
            if (bullet != null) {
                // if shit doesn't have enough oil,then the bullet is null
                bulletList.add(bullet);
            }
        }
    }
}
