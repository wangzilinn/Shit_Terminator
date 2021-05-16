package com.processing.sketch;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.Iterator;
import java.util.LinkedList;


/**
 * @Author: huangyiqin
 * @Date: 5/16/2021 8:34 PM
 * @Modified: wangzilinn@gmail.com
 */
public class Sketch extends PApplet {
    double a = 0;
    int b = 0;

    Screen screen = new Screen(this);

    EnemyShip enemyShip = new EnemyShip(this, 0, 0);
    Ship ship = new Ship(this);
    LinkedList<Oil> oilList = new LinkedList<>();
    LinkedList<Bullet> bulletList = new LinkedList<>();
    ParticleSystem ps;

    public void settings() {
        size(1500,800);
    }

    public void setup() {
        ps = new ParticleSystem(this, new PVector((float) width/2, 50));
    }

    public void draw() {
        background(255);

        if (a % 30 < 15){
            enemyShip.moveAndDraw(Direction.DOWN);
        }else{
            enemyShip.moveAndDraw(Direction.UP);
        }
        a += 0.3;

        if(b % 3 == 0){
            Oil oils = enemyShip.leak();
            if (oils != null){
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
        if (enemyShip.dead){
            ps.addParticle(enemyShip.position);
            ps.run();
            screen.drawWinScreen();
        } else if(ship.dead){
            screen.drawLoseScreen();
        }

        fill(0);
        textSize(12);
        text("Remaining fuel:" + ship.fuel,10, 500);
    }

    public void keyPressed(){
        int keyNumber = key - 80;
        if(keyNumber == 10 || keyNumber == 42){
            Bullet bullet = ship.shoot();
            if (bullet != null){
                // if shit doesn't have enough oil,then the bullet is null
                bulletList.add(bullet);
            }
        }
    }
}
