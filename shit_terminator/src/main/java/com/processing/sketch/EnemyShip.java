package com.processing.sketch;

import processing.core.PApplet;
import processing.core.PVector;


/**
 * @Author: huangyiqin
 * @Date: 5/16/2021 8:34 PM
 * @Modified: wangzilinn@gmail.com
 */


class EnemyShip{
    private final PApplet sketch;

    PVector position;

    /**
     * 飞船的大小,在该范围内被击中
     */
    PVector size;

    int blood = 50;
    boolean dead = false;

    EnemyShip(PApplet sketch, float initX, float initY) {
        this.sketch = sketch;
        this.position = new PVector(initX, initY);
        this.size = new PVector(50, 50);
    }

    void moveAndDraw(Direction direction) {
        if (dead) {
            return;
        }
        switch (direction) {
            case UP:
                position.y -= 10;
                break;
            case DOWN:
                position.y += 10;
                break;
            case LEFT:
                position.x -= 10;
                break;
            case RIGHT:
                position.x += 10;
        }

        sketch.fill(blood);
        sketch.rect(position.x, position.y, size.x,size.y);
    }

    /**
     * @return 飞船泄露一滴油
     */
    Oil leak(){
        if(dead){
            return null;
        }
        return new Oil(sketch, position.x, position.y, 10);
    }


    /**
     * @param bullet 子弹
     * @return 该子弹是否会击中自己
     */
    boolean checkIfHit(Bullet bullet) {
        return bullet.position.x + bullet.size.x >= position.x && bullet.position.x <= position.x + size.x
                && bullet.position.y + bullet.size.y >= position.y && bullet.position.y <= position.y + size.y;
    }

    void hit(Bullet bullet) {
        blood -= bullet.damage;
        if (blood <= 0) {
            dead = true;
        }
    }

    /**
     * 重置飞船的参数,在新的关卡时用到
     */
    void reset() {
        blood = 50;
        dead = false;
    }
}
