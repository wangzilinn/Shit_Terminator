import Draw.DrawSystem;
import annotation.CalledByDraw;
import entity.*;
import enums.Direction;
import enums.State;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;

public class Main extends PApplet{
    // 实现识别多个按键:
    LinkedHashSet<Character> pressedKeys = new LinkedHashSet<>();

    Info info;
    EnemyShip enemyShip;
    Ship ship;
    LinkedList<Fuel> fuelList = new LinkedList<>();
    LinkedList<Bullet> bulletList = new LinkedList<>();

    double a = 0;
    int b = 0;

    private State state = State.READY;

    DrawSystem drawSystem;

    public void settings(){
        size((int) Meta.size.x, (int) Meta.size.y);
    }

    public void setup() {
        info = new Info();
        drawSystem = new DrawSystem(this);
        enemyShip = new EnemyShip();
        ship = new Ship();
    }

    public void draw() {

        background(255);
        switch (state) {
            case READY:
                drawSystem.drawReadyScreen();
                break;
            case PASS:
                drawSystem.drawNextLevelScreen(enemyShip.position);
                break;
            case RUNNING:
                drawGame();
                break;
            case WIN:
                drawSystem.drawWinScreen(enemyShip.position);
                break;
            case OVER:
                drawSystem.drawLoseScreen(enemyShip.position);
                break;
        }
    }

    /**
     * 真正执行游戏部分的逻辑
     */
    @CalledByDraw
    private void drawGame() {
        //检查是否需要显示关卡名字:
        if (drawSystem.checkAndDrawLevelNameScreen(info)) {
            //如果显示了关卡名字,则直接显示下一帧
            return;
        }
        // 执行游戏逻辑:
        if (a % 30 < 15) {
            enemyShip.move(Direction.DOWN);
        } else {
            enemyShip.move(Direction.UP);
        }
        a += 0.3;
        if (b % 3 == 0) {
            Fuel oils = enemyShip.leak();
            if (oils != null) {
                //if ship is destroyed, then the oil is null
                fuelList.add(oils);
            }
        }
        b++;

        if (pressedKeys.contains('w')) {
            ship.move(Direction.UP);
        }
        if (pressedKeys.contains('s')) {
            ship.move(Direction.DOWN);
        }
        if (pressedKeys.contains('a')) {
            ship.move(Direction.LEFT);
        }
        if (pressedKeys.contains('d')) {
            ship.move(Direction.RIGHT);
        }

        //遍历所有油滴,检查鼠标操作的飞船是否可以吸收这个油滴
        Iterator<Fuel> oilIter = fuelList.iterator();
        while (oilIter.hasNext()) {
            Fuel fuel = oilIter.next();
            fuel.move();
            if (ship.checkIfAbsorb(fuel)) {
                ship.absorbFuel(fuel);
                oilIter.remove();
            } else if (fuel.position.x >= width) {// 超出画面范围
                oilIter.remove();
            }
        }
        //遍历所有子弹,检查子弹是否超出画面,是否击中敌方飞船
        Iterator<Bullet> BulletIter = bulletList.iterator();
        while(BulletIter.hasNext()){
            Bullet bullet = BulletIter.next();
            bullet.move();
            //敌方飞船检查是否被击中:
            if (enemyShip.checkIfHit(bullet)) {
                enemyShip.hit(bullet);
                BulletIter.remove();
            } else if (bullet.position.x <= 0) {// 超出画面范围
                BulletIter.remove();
            }
        }

        // 判断是否游戏状态是否已经改变:
        if (enemyShip.dead) {
            System.out.println("enemyShip is dead");
            if (info.isMaxLevel()) {
                state = State.WIN;
                info.resetLevel();
                drawSystem.resetDrawLevelNameScreenCounter();
            } else {
                state = State.PASS;
                info.upgradeLevel();
            }
            ship.reset();
            enemyShip.reset();
        } else if (ship.dead) {
            state = State.OVER;
            info.resetLevel();
            drawSystem.resetDrawLevelNameScreenCounter();
        }

        //开始绘制画面:
        drawSystem.drawEnemyShip(enemyShip);
        drawSystem.drawShip(ship);
        drawSystem.drawBullets(bulletList);
        drawSystem.drawOils(fuelList);
        drawSystem.drawGameLayout(info, ship);
    }

    public void keyPressed() {
        // 按空格,则将状态变为执行游戏逻辑状态
        if (key == ' ') {
            if (state == State.WIN || state == State.OVER) {
                //当赢了或输了,则点击空格回到准备界面
                state = State.READY;
            } else if (state == State.READY || state == State.PASS) {
                //当在准备界面或者过关了,则开始运行游戏
                state = State.RUNNING;
            }
        }

        pressedKeys.add(key);
    }

    public void keyReleased() {
        pressedKeys.remove(key);
    }

    public void mousePressed() {
        Bullet bullet = ship.shoot();
        if (bullet != null) {
            // if shit doesn't have enough oil,then the bullet is null
            bulletList.add(bullet);
        }
    }

    public void mouseMoved() {
        ship.updateShootDirection(new PVector(mouseX, mouseY));
    }

    public static void main(String... args){
        PApplet.main("Main");
    }
}
