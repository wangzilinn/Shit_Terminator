package entity;

import enums.Direction;
import processing.core.PVector;

import System.*;

/**
 * @Author: huangyiqin
 * @Date: 5/16/2021 8:37 PM
 * @Modified: wangzilinn@gmail.com
 */


public class Ship {
    public float fuel = 100;
    public boolean dead = false;
    /**
     * 死亡时的位置:
     */
    public PVector deadPosition;
    public PVector size;

    public PVector position;
    PVector lastPosition;

    /**
     * 射击时鼠标的方向
     */
    public PVector shootDirection;
    /**
     * 每移动一次移动的距离
     */
    float step;


    public Ship() {
        position = new PVector(0, 0);
        this.deadPosition = new PVector();
        this.size = new PVector(50, 50);
        this.shootDirection = new PVector(1, 1);
    }

    /**
     * @param oil 要吸收的油滴
     * @return 是否可以吸收
     */
    public boolean checkIfAbsorb(Oil oil) {
        //注意还要考虑Oil的尺寸
        return oil.position.dist(position) < oil.size.mag();
    }

    /**
     * 执行吸收油滴的逻辑
     *
     * @param oil 要吸收的油滴
     */
    public void absorbFuel(Oil oil) {
        if (dead) {
            return;
        }
        fuel += oil.volume;
    }


    public void move(Direction direction) {
        lastPosition = position;
        MoveSystem.move(direction, dead, position);
        // 减少燃料:
        double d = position.dist(lastPosition);
        int r = (int) Math.floor(d / 25);
        fuel = fuel - r;
        // 判断是否没有燃料了
        if (fuel <= 0 && !dead) {
            dead = true;
            deadPosition = position.copy();
        }
    }


    public Bullet shoot() {
        if (fuel < 10) {
            return null;
        }
        //if we don't have enough oil, then return null directly
        Bullet bullet = new Bullet(position.copy(), shootDirection, 10);
        fuel = fuel - 10;
        return bullet;
    }

    public void updateShootDirection(PVector mousePosition) {
        shootDirection = mousePosition.copy().sub(position.copy()).normalize();
    }

    /**
     * 重置飞船的参数,在新的关卡时用到
     */
    public void reset() {
        fuel = 100;
        dead = false;
    }

}
