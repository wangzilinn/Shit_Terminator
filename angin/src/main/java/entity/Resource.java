package entity;

import enums.ResourceClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import processing.core.PVector;
import system.MoveSystem;


/**
 * @Author: huangyiqin
 * @Date: 5/16/2021 8:35 PM
 * @Modified: wangzilinn@gmail.com
 */
@Data
@AllArgsConstructor
public class Resource {
    public PVector position;
    /**
     * 这滴油的油量
     */
    float volume;
    ResourceClass resourceClass;
    /**
     * 这滴油的剩余寿命
     */
    int remainLife;

    public Resource() {
        this(MoveSystem.randomPosition(), getRandomVolume(), getRandomClass(), getRandomLife());
    }

    /**
     * 更新位置并画图
     */
    public void reduceLife() {
        if (remainLife > 0) {
            remainLife -= 1;
        }
    }

    private static ResourceClass getRandomClass() {
        double rand = Math.random();
        if (rand > 0.6) {
            return ResourceClass.AMMO;
        } else if (rand > 0.3) {
            return ResourceClass.SHIELD;
        }else {
            return ResourceClass.FUEL;
        }
    }

    private static float getRandomVolume() {
        return (float) (Math.random() * 10);
    }

    private static int getRandomLife() {
        return (int) (Math.random() * 600);
    }
}
