package com.processing.sketch;

import processing.core.PApplet;
import processing.core.PVector;

import javax.swing.text.Position;

/**
 * @Author: huangyiqin
 * @Date: 5/16/2021 8:35 PM
 * @Modified: wangzilinn@gmail.com
 */
class Oil {
    private final PApplet sketch;
    PVector position;
    PVector size;
    /**
     * 这滴油的油量
     */
    float volume;

    public Oil(PApplet sketch, float x, float y, int volume) {
        this.sketch = sketch;
        this.volume = volume;
        position = new PVector(x, y);
        size = new PVector(10, 10);
    }

    /**
     * 更新位置并画图
     */
    void updateAndDraw(){
        position.x += 5;
        sketch.fill(200);
        sketch.ellipse(position.x, position.y, size.x, size.y);
    }
}
