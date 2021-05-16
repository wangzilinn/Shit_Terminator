package com.processing.sketch;

import processing.core.PApplet;
import processing.core.PVector;

/**
 * @Author: wangzilinn@gmail.com
 * @Date: 5/16/2021 10:05 PM
 */
public class Screen {
    private final PApplet sketch;
    PVector position;

    public Screen(PApplet sketch) {
        this.sketch = sketch;
        position = new PVector((float) sketch.width / 2 - 30, (float) sketch.height / 2 - 30);
    }

    public void drawWinScreen() {
        sketch.fill(0);
        sketch.textSize(60);
        sketch.text("You Win", position.x, position.y);
    }

    public void drawLoseScreen() {
        sketch.fill(0);
        sketch.textSize(60);
        sketch.text("You Lose",  position.x, position.y);
    }
}
