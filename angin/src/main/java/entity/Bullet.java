package entity;

import annotation.CalledByDraw;
import enums.Role;
import processing.core.PVector;

/**
 * @Author: huangyiqin
 * @Date: 5/16/2021 8:37 PM
 * @Modified: wangzilinn@gmail.com
 */
public class Bullet {

    public PVector position;
    public PVector size;
    PVector directionVector;

    int distance;

    public Role role;

    /**
     * 这个子弹可以让坏蛋掉多少血
     */
    float damage;

    Bullet(PVector position, PVector directionVector, float damage, Role role) {
        this.directionVector = directionVector.copy();
        this.position = position.copy();
        this.size = new PVector(10, 10);
        this.damage = damage;
        this.role = role;
    }

    @CalledByDraw
    public void move() {
        distance++;
        position.add(directionVector.copy().mult(5));
    }
}
