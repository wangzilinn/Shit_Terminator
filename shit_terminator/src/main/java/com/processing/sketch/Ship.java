package com.processing.sketch;

import processing.core.PApplet;
import processing.core.PVector;

/**
 * @Author: huangyiqin
 * @Date: 5/16/2021 8:37 PM
 * @Modified: wangzilinn@gmail.com
 */


class Ship{
    private final PApplet sketch;
    float fuel = 100;
    boolean dead = false;
    /**
     * 死亡时的位置:
     */
    PVector deadPosition;
    PVector size;


    Ship(PApplet sketch) {
        this.sketch = sketch;
        this.deadPosition = new PVector();
        this.size = new PVector(50, 50);
    }

    boolean checkIfAbsorb(Oil oil) {
        //注意还要考虑Oil的尺寸
        return oil.position.x + oil.size.x  >= sketch.mouseX && oil.position.x <= sketch.mouseX + size.x
                && oil.position.y + oil.size.y >= sketch.mouseY && oil.position.y <= sketch.mouseY + size.y;
    }

    void absorbFuel(Oil oil) {
        if (dead) {
            return;
        }
        fuel += oil.volume;
    }

    void updateAndDraw(){
        if(dead){
            sketch.fill(0);
            sketch.rect(deadPosition.x,deadPosition.y, 50, 50);
            return ;
        }
        sketch.fill(0);
        sketch.rect(sketch.mouseX,sketch.mouseY, 50, 50);
        double d =
                Math.sqrt((sketch.pmouseX - sketch.mouseX)*(sketch.pmouseX - sketch.mouseX)+(sketch.pmouseY - sketch.mouseY)*(sketch.pmouseY - sketch.mouseY));
        int r = (int)Math.floor(d/25);
        fuel = fuel - r;
        if(fuel <= 0 && !dead){
            dead = true;
            deadPosition.x = sketch.mouseX;
            deadPosition.y = sketch.mouseY;
        }
    }

    Bullet shoot(){
        if(fuel < 10){
            return null;
        }
        //if we don't have enough oil, then return null directly
        Bullet bullet = new Bullet(sketch, sketch.mouseX + 25, sketch.mouseY + 25, 10);
        fuel = fuel - 10;
        return bullet;
    }

}
