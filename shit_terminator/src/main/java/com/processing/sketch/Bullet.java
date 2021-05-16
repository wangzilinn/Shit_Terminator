package com.processing.sketch;
import processing.core.PApplet;

/**
 * @Author: huangyiqin
 */
class Bullet{
    private PApplet sketch;

    int x ;
    int y ;

    Bullet(PApplet sketch) {
        this.sketch = sketch;
    }

    void update(){

        x = x - 5;
        sketch.fill(0);
        sketch.ellipse(x,y, 10, 10);

    }

    int delete(){
        sketch.fill(255);
        sketch.ellipse(x,y, 10, 10);
        return 0;
    }
}
