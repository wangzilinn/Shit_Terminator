package com.processing.sketch;

import processing.core.PVector;

/**
 * @Author: huangyiqin
 * @Date: 5/16/2021 8:35 PM
 * @Modified: wangzilinn@gmail.com
 */
class Oil {
    PVector position;
    PVector size;
    /**
     * 这滴油的油量
     */
    float volume;

    public Oil(PVector position, int volume) {
        this.volume = volume;
        this.position = position.copy();
        this.size = new PVector(10, 10);
    }

    /**
     * 更新位置并画图
     */
    void move() {
        position.x += 5;
    }
}
