import draw.DrawSystem;
import annotation.CalledByDraw;
import entity.*;
import enums.Direction;
import enums.Role;
import enums.State;
import processing.core.PApplet;
import processing.core.PVector;
import system.Meta;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;

public class Main extends PApplet{
    // 实现识别多个按键:
    LinkedHashSet<Character> pressedKeys = new LinkedHashSet<>();

    Info info;
    Ship enemyShip;
    Ship playerShip;
    LinkedList<Resource> resourceList = new LinkedList<>();
    LinkedList<Bullet> bulletList = new LinkedList<>();

    private State state = State.READY;

    DrawSystem drawSystem;

    public void settings(){
        size((int) Meta.size.x, (int) Meta.size.y);
    }

    public void setup() {
        info = new Info();
        drawSystem = new DrawSystem(this);
        enemyShip = new Ship(Role.COMPUTER);
        playerShip = new Ship(Role.PLAYER);
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
        // 产资源:
        if (frameCount % 10 == 0) {
            Resource resource = new Resource();
            resourceList.add(resource);
        }

        enemyShip.move(playerShip.position);
        enemyShip.updateShootDirection(playerShip.position);

        if (frameCount % 60 == 0) {
            System.out.println("enemy shoot");
            Bullet bullet = enemyShip.shoot(playerShip);
            if (bullet != null) {
                bulletList.add(bullet);
            }
        }

        if (pressedKeys.contains('w') && playerShip.position.y > 0) {
            playerShip.move(Direction.UP);
        }
        if (pressedKeys.contains('s') && playerShip.position.y < height) {
            playerShip.move(Direction.DOWN);
        }
        if (pressedKeys.contains('a') && playerShip.position.x > 0) {
            playerShip.move(Direction.LEFT);
        }
        if (pressedKeys.contains('d') && playerShip.position.x < width) {
            playerShip.move(Direction.RIGHT);
        }

        //遍历所有油滴,检查鼠标操作的飞船是否可以吸收这个油滴
        Iterator<Resource> oilIter = resourceList.iterator();
        while (oilIter.hasNext()) {
            Resource resource = oilIter.next();
            resource.reduceLife();
            if (resource.getRemainLife() <= 0) {
                oilIter.remove();
            }else if(playerShip.checkIfAbsorb(resource)) {
                System.out.println("player absorb");
                playerShip.absorbFuel(resource);
                oilIter.remove();
            } else if (enemyShip.checkIfAbsorb(resource)) {
                System.out.println("enemy absorb");
                enemyShip.absorbFuel(resource);
                oilIter.remove();
            }
        }
        //遍历所有子弹,检查子弹是否超出画面,是否击中敌方飞船
        Iterator<Bullet> BulletIter = bulletList.iterator();
        while(BulletIter.hasNext()){
            Bullet bullet = BulletIter.next();
            bullet.move();
            //飞船是否被击中:
            if (bullet.getRole() == Role.PLAYER && enemyShip.checkIfBeingHit(bullet)) {
                enemyShip.beingHit(bullet);
                BulletIter.remove();
            } else if (bullet.getRole() == Role.COMPUTER && playerShip.checkIfBeingHit(bullet)) {
                playerShip.beingHit(bullet);
                BulletIter.remove();
            } else if (bullet.position.x <= 0 || bullet.position.x >= width || bullet.position.y <= 0 || bullet.position.y >= height) {
                // 超出画面范围
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
            playerShip = new Ship(Role.PLAYER);
            enemyShip = new Ship(Role.COMPUTER);
        } else if (playerShip.dead) {
            state = State.OVER;
            info.resetLevel();
            drawSystem.resetDrawLevelNameScreenCounter();
        }

        //开始绘制画面:
        drawSystem.drawShip(enemyShip);
        drawSystem.drawShip(playerShip);
        drawSystem.drawBullets(bulletList);
        drawSystem.drawResources(resourceList);
        drawSystem.drawGameLayout(info, playerShip, enemyShip);
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
        Bullet bullet = playerShip.shoot(enemyShip);
        if (bullet != null) {
            System.out.println("player shoot");
            // if shit doesn't have enough oil,then the bullet is null
            bulletList.add(bullet);
        }
    }

    public void mouseMoved() {
        playerShip.updateShootDirection(new PVector(mouseX, mouseY));
    }

    public static void main(String... args){
        PApplet.main("Main");
    }
}