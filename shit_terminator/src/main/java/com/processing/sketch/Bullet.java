package com.processing.sketch;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * @Author: huangyiqin
 */
class Bullet{
    private final PApplet sketch;

    PVector position;
    PVector size;

    /**
     * 这个子弹可以让坏蛋掉多少血
     */
    float damage;

    Bullet(PApplet sketch, float x, float y, float damage) {
        this.sketch = sketch;
        this.position = new PVector(x, y);
        this.size = new PVector(10, 10);
        this.damage = damage;
    }

    void updateAndDraw(){
        position.x -= 5;
        sketch.fill(0);
        sketch.ellipse(position.x,position.y, size.x, size.y);

    }
}
