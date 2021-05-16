package com.processing.sketch;

import processing.core.PApplet;

/**
 * @Author: wangzilinn@gmail.com
 * @Date: 5/16/2021 8:35 PM
 */
class Oil {
    private PApplet sketch;
    int x ;
    int y ;

    Oil(PApplet sketch) {
        this.sketch = sketch;
    }

    void update(){

        x = x + 5;
        sketch.fill(200);
        sketch.ellipse(x,y, 10, 10);

    }

    int delete(){
        sketch.fill(255);
        sketch.ellipse(x,y, 10, 10);
        return 1;
    }
}
