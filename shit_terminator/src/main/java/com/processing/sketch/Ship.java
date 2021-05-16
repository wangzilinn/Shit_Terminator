package com.processing.sketch;

import processing.core.PApplet;

/**
 * @Author: wangzilinn@gmail.com
 * @Date: 5/16/2021 8:37 PM
 */


class Ship{
    private PApplet sketch;
    int score = 0;
    double d;
    int r;
    boolean dead = false;
    int deadx, deady;

    Ship(PApplet sketch) {
        this.sketch = sketch;
    }

    void addScore(int addedScore){
        if(dead){
            return ;
        }
        score = score + addedScore;
    }

    int getScore(){
        return score;
    }

    void mouse(){
        if(dead){
            sketch.fill(0);
            sketch.rect(deadx,deady, 50, 50);
            return ;
        }
        sketch.fill(0);
        sketch.rect(sketch.mouseX,sketch.mouseY, 50, 50);
        d =
                Math.sqrt((sketch.pmouseX - sketch.mouseX)*(sketch.pmouseX - sketch.mouseX)+(sketch.pmouseY - sketch.mouseY)*(sketch.pmouseY - sketch.mouseY));
        r = (int)Math.floor(d/25);
        score = score - r;
        if(score < 0 && !dead){
            dead = true;
            deadx = sketch.mouseX;
            deady = sketch.mouseY;
        }
    }

    Bullet shoot(){
        if(score < 10){
            return null;
        }
        //if we don't have enough oil, then return null directily
        Bullet bullet = new Bullet(sketch);
        bullet.x = sketch.mouseX + 25;
        bullet.y = sketch.mouseY + 25;
        score = score - 10;
        return bullet;
    }

    void lose(){
        if(score <= 0){

        }
    }


}
