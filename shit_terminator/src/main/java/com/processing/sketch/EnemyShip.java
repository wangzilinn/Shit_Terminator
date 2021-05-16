package com.processing.sketch;

import processing.core.PApplet;



/**
 * @Author: wangzilinn@gmail.com
 * @Date: 5/16/2021 8:34 PM
 */


class EnemyShip{
    private PApplet sketch;

    int x = 0;
    int y = 0;

    int blood = 50;
    boolean dead = false;

    EnemyShip(PApplet sketch) {
        this.sketch = sketch;
    }

    void moveUp(){
        if(dead){
            return;
        }
        y = y - 10;

        sketch.fill(blood);
        sketch.rect(x,y, 50,50);


    }

    void moveDown(){
        if(dead){
            return;
        }

        y = y + 10;
        sketch.fill(blood);
        sketch.rect(x,y, 50,50);

    }

    Oil leak(){
        if(dead){
            return null;
        }
        Oil oil = new Oil(sketch);
        oil.x = x;
        oil.y = y;
        return oil;
    }

    int explode(){
        sketch.fill(255);
        sketch.rect(x,y, 50, 50);
        //explosion effect
        dead = true;
        return 50;
    }

    int hitted(){
        blood = blood - 10;
        if(blood == 0){
            int explodeScore = explode();
            return explodeScore;
        }
        return 10;
    }
}
