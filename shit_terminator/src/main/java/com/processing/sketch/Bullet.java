package com.processing.sketch;

import processing.core.PVector;

/**
 * @Author: huangyiqin
 * @Date: 5/16/2021 8:37 PM
 * @Modified: wangzilinn@gmail.com
 */
class Bullet {

    PVector position;
    PVector size;
    PVector directionVector;

    int distance;

    /**
     * 这个子弹可以让坏蛋掉多少血
     */
    float damage;

    Bullet(PVector position, PVector directionVector, float damage) {
        this.directionVector = directionVector;
        this.position = position.copy();
        this.size = new PVector(10, 10);
        this.damage = damage;
    }

    @CalledByDraw
    void move() {
        distance++;
        position.add(directionVector.copy().mult(5));
    }
}
