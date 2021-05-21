package com.processing.sketch;

import processing.core.PVector;

/**
 * @Author: huangyiqin
 * @Date: 5/16/2021 8:37 PM
 * @Modified: wangzilinn@gmail.com
 */


class Ship {
    float fuel = 100;
    boolean dead = false;
    /**
     * 死亡时的位置:
     */
    PVector deadPosition;
    PVector size;

    PVector position;
    PVector lastPosition;


    Ship() {
        position = new PVector(0, 0);
        this.deadPosition = new PVector();
        this.size = new PVector(50, 50);
    }

    /**
     * @param oil 要吸收的油滴
     * @return 是否可以吸收
     */
    boolean checkIfAbsorb(Oil oil) {
        //注意还要考虑Oil的尺寸
        return oil.position.dist(position) < oil.size.mag();
    }

    /**
     * 执行吸收油滴的逻辑
     *
     * @param oil 要吸收的油滴
     */
    void absorbFuel(Oil oil) {
        if (dead) {
            return;
        }
        fuel += oil.volume;
    }

    void moveTo(PVector target) {
        lastPosition = position;
        position = target;
        // 减少燃料:
        double d = position.dist(lastPosition);
        int r = (int) Math.floor(d / 25);
        fuel = fuel - r;
        // 判断是否没有燃料了
        if (fuel <= 0 && !dead) {
            dead = true;
            deadPosition = position.copy();
        }

    }

    Bullet shoot() {
        if (fuel < 10) {
            return null;
        }

        //if we don't have enough oil, then return null directly
        Bullet bullet = new Bullet(position.add(size.copy().div(2)), 10);
        fuel = fuel - 10;
        return bullet;
    }

    /**
     * 重置飞船的参数,在新的关卡时用到
     */
    void reset() {
        fuel = 100;
        dead = false;
    }

}
