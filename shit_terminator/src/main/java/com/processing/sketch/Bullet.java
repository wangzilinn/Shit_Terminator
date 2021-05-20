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

    int distance;

    /**
     * 这个子弹可以让坏蛋掉多少血
     */
    float damage;

    Bullet(PVector position, float damage) {
        this.position = position.copy();
        this.size = new PVector(10, 10);
        this.damage = damage;
    }

    @CalledByDraw
    void move() {
        distance++;
        position.x -= 5;
    }
}
