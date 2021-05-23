package entity;

import enums.FuelClass;
import lombok.Data;
import processing.core.PVector;


/**
 * @Author: huangyiqin
 * @Date: 5/16/2021 8:35 PM
 * @Modified: wangzilinn@gmail.com
 */
@Data
public class Fuel {
    public PVector position;
    public PVector size;
    /**
     * 这滴油的油量
     */
    float volume;

    FuelClass fuelClass;

    public Fuel(PVector position, int volume, FuelClass fuelClass) {
        this.volume = volume;
        this.position = position.copy();
        this.size = new PVector(10, 10);
        this.fuelClass = fuelClass;
    }

    public Fuel(PVector position, int volume) {
        this(position, volume, getRandomColor());
    }


    /**
     * 更新位置并画图
     */
    public void move() {
        position.x += 5;
    }

    private static FuelClass getRandomColor() {
        double rand = Math.random();
        if (rand > 0.6) {
            return FuelClass.RED;
        } else if (rand > 0.3) {
            return FuelClass.BLUE;
        }else {
            return FuelClass.GREEN;
        }
    }
}
