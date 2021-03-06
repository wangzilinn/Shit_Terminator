package entity;

import enums.Role;
import lombok.Data;
import lombok.Getter;
import processing.core.PVector;

/**
 * @Author: huangyiqin
 * @Date: 5/16/2021 8:37 PM
 * @Modified: wangzilinn@gmail.com
 */
@Data
public class Bullet {

    @Getter
    final private Role role;
    public PVector position;
    public PVector size;
    PVector directionVector;
    int distance;
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


    public void move() {
        distance++;
        position.add(directionVector.copy().mult(5));
    }
}
