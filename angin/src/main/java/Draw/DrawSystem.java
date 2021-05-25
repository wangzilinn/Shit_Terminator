package Draw;

import Draw.printer.ShipPrinter;
import annotation.CalledByDraw;
import entity.*;
import enums.ResourceClass;
import enums.Role;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static processing.core.PApplet.cos;
import static processing.core.PApplet.sin;
import static processing.core.PConstants.*;

/**
 * @Author: wangzilinn@gmail.com
 * @Date: 5/16/2021 10:05 PM
 * @Modified: wangzilinn@gmail.com
 */
public class DrawSystem {

    private final PApplet sketch;
    PVector centerPosition;
    PVector size;
    ParticleGroup ps;
    HashMap<Integer, Integer> levelNamesCounterMap;



    public DrawSystem(PApplet sketch) {
        this.sketch = sketch;
        System.out.println(sketch.width);
        centerPosition = new PVector((float) sketch.width / 2, (float) sketch.height / 2);
        size = new PVector(sketch.width, sketch.height);
        ps = new ParticleGroup(sketch, 6, 2, 200);
    }

    /**
     * 绘制通关页面
     *
     * @param shipPosition 要显示飞船效果的位置
     */
    @CalledByDraw
    public void drawWinScreen(PVector shipPosition) {
        String str = "You Win";
        sketch.fill(0);
        sketch.textSize(60);
        sketch.text(str, getAlignX(str, 60, size.x), centerPosition.y);
        str = "press space to restart";
        sketch.textSize(20);
        sketch.text(str, getAlignX(str, 20, size.x), centerPosition.y + 40);
        ps.addParticle(shipPosition);
        ps.run();
    }

    /**
     * 绘制失败页面
     *
     * @param shipPosition 要显示飞船效果的位置
     */
    @CalledByDraw
    public void drawLoseScreen(PVector shipPosition) {
        String str = "You Lose";
        sketch.fill(0);
        sketch.textSize(60);
        sketch.text(str, getAlignX(str, 60, size.x), centerPosition.y);
        str = "press space to restart";
        sketch.textSize(20);
        sketch.text(str, getAlignX(str, 20, size.x), centerPosition.y + 40);
        ps.addParticle(shipPosition);
        ps.run();
    }

    /**
     * 绘制下一关
     *
     * @param shipPosition 要显示飞船效果的位置
     */
    @CalledByDraw
    public void drawNextLevelScreen(PVector shipPosition) {
        sketch.fill(0);
        sketch.textSize(60);
        String str = "Next Level";
        sketch.text(str, getAlignX(str, 60, size.x), centerPosition.y);
        str = "press space to start";
        sketch.textSize(20);
        sketch.text(str, getAlignX(str, 20, size.x), centerPosition.y + 40);
        ps.addParticle(shipPosition);
        ps.run();
    }

    /**
     * 绘制打开游戏的第一个界面
     */
    @CalledByDraw
    public void drawReadyScreen() {
        sketch.fill(0);
        sketch.textSize(60);
        String str = "Shit Terminator";
        sketch.text(str, getAlignX(str, 60, size.x), centerPosition.y);
        str = "press space to start";
        sketch.textSize(20);
        sketch.text(str, getAlignX(str, 20, size.x), centerPosition.y + 40);
    }

    /**
     * 检查是否需要显示关卡名字, 如果不需要则不显示
     *
     * @param info 游戏信息
     * @return 是否显示了关卡名字
     */
    @CalledByDraw
    public boolean checkAndDrawLevelNameScreen(Info info) {
        if (levelNamesCounterMap == null) {
            levelNamesCounterMap = new HashMap<>();
            for (int i = 0; i <= info.getMaxLevel(); i++) {
                levelNamesCounterMap.put(i, 40);
            }
        }
        int currentLevel = info.getCurrentLevel();
        if (levelNamesCounterMap.get(currentLevel) > 0) {
            String str = "Chapter " + (currentLevel + 1) + ": " + info.getCurrentLevelName();
            sketch.fill(0);
            sketch.textSize(60);
            sketch.text(str, getAlignX(str, 60, size.x), centerPosition.y);
            levelNamesCounterMap.computeIfPresent(currentLevel, (key, value) -> --value);
            return true;
        }
        return false;
    }


    @CalledByDraw
    public void drawShip(Ship ship) {
        ShipPrinter shipPrinter = ship.getPrinter();

        if (ship.dead) {
            sketch.fill(0);
            sketch.rect(ship.deadPosition.x, ship.deadPosition.y, ship.size.x, ship.size.y);
            return;
        }
        //画外面的圈圈:
        for (int i = 0; i < shipPrinter.getCircleColor().length; i++) {
            sketch.noFill();
            sketch.stroke(shipPrinter.getCircleColor()[i]);
            // sketch.ellipseMode(CENTER);
            float radius = ship.size.x + (i + 1) * (1 + i);
            sketch.ellipse(ship.position.x, ship.position.y, radius, radius);
        }
        //更新外面圈圈的颜色
        if (sketch.frameCount % 6 == 0) {
            shipPrinter.updateCircleColor();
        }
        //画飞船本身:
        if (ship.getRole() == Role.PLAYER) {
            sketch.fill(0);
            sketch.ellipse(ship.position.x, ship.position.y, ship.size.x, ship.size.y);
        }else{
            sketch.fill(0);
            polygon(ship.position.x, ship.position.y, ship.size.x / 2,6);
        }

        //画炮塔:
        sketch.pushMatrix();
        sketch.translate(ship.position.x, ship.position.y);
        sketch.rotate(ship.shootDirection.heading() + PI / 2);
        sketch.fill(255);
        // 使用正三角形会看不出来是哪个角发射的子弹，因此使用锐角三角形
        // polygon(0, 0 , (ship.size.y /3 ),3);
        sketch.triangle(0, -ship.size.y / 3, -ship.size.x / 4, ship.size.x / 3, ship.size.x / 4, ship.size.x / 3);
        sketch.popMatrix();
    }

    public void drawBullets(List<Bullet> bulletList) {
        for (Bullet bullet : bulletList) {
            sketch.fill(0);
            sketch.noStroke();
            if (bullet.getRole() == Role.PLAYER) {
                sketch.ellipse(bullet.position.x, bullet.position.y , bullet.size.x, bullet.size.y);
            }else {
                sketch.pushMatrix();
                // 修改射出的正三角形的方向：
                sketch.translate(bullet.position.x, bullet.position.y);
                sketch.rotate(bullet.getDirectionVector().heading());
                polygon(0, 0 , (float) (bullet.size.x/1.5),3);
                sketch.popMatrix();
            }
        }
    }

    public void drawResources(List<Resource> resourceList) {
        for (Resource resource : resourceList) {
            int trans = resource.getRemainLife() * 3;
            switch (resource.getResourceClass()) {
                case AMMO:
                    sketch.fill(255, 0, 0, trans);
                    break;
                case FUEL:
                    sketch.fill(0, 255, 0, trans);
                    break;
                case SHIELD:
                    sketch.fill(0, 0, 255, trans);
            }
            sketch.noStroke();
            sketch.ellipse(resource.position.x, resource.position.y, resource.getVolume() * 2,resource.getVolume() * 2);
        }
    }

    public void drawGameLayout(Info info, Ship playerShip, Ship enemyShip) {
        sketch.fill(0);
        sketch.textSize(12);
        drawResourceContainer(playerShip, new PVector(10, 500));
        drawResourceContainer(enemyShip, new PVector(600, 500));
    }

    private void drawResourceContainer(Ship ship, PVector position) {
        sketch.text("Remaining ammo:" + (int)ship.resourceContainer.get(ResourceClass.AMMO), position.x, position.y);
        sketch.text("Remaining fuel:" + (int)ship.resourceContainer.get(ResourceClass.FUEL), position.x,
                position.y + 20);
        sketch.text("Remaining Shield:" + (int)ship.resourceContainer.get(ResourceClass.SHIELD), position.x,
                position.y + 40);
    }

    /**
     * 重置每一关的名字显示的帧数的计数器
     */
    public void resetDrawLevelNameScreenCounter() {
        for (Map.Entry<Integer, Integer> entry : levelNamesCounterMap.entrySet()) {
            entry.setValue(50);
        }
    }

    /**
     * @param str      需要显示的字符
     * @param textSize 文字大小
     * @param width    画面宽度
     * @return 如果要把该字符显示在画面中间, 则其横坐标的值
     */
    private int getAlignX(String str, int textSize, float width) {
        int len = str.length() * textSize / 2;
        return (int) (width / 2 - len / 2);
    }

    private void polygon(float x, float y, float radius, int npoints) {
        float angle = TWO_PI / npoints;
        sketch.beginShape();
        for (float a = 0; a < TWO_PI; a += angle) {
            float sx = x + cos(a) * radius;
            float sy = y + sin(a) * radius;
            sketch.vertex(sx, sy);
        }
        sketch.endShape(CLOSE);
    }
}
