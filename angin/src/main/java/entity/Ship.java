package entity;

import Draw.printer.ShipPrinter;
import enums.Direction;
import enums.FuelClass;
import lombok.Getter;
import processing.core.PVector;

import java.util.HashMap;

/**
 * @Author: huangyiqin
 * @Date: 5/16/2021 8:37 PM
 * @Modified: wangzilinn@gmail.com
 */


public class Ship {

    /**
     * 盛放不同燃料的容器
     */
    HashMap<FuelClass, Float> fuelTankMap;

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
     * 射击部分:
     */
    public PVector shootDirection;
    final private Gun gun;
    /**
     * 移动部分:
     */
    float step;
    final private Engine engine;

    @Getter
    private final ShipPrinter printer;



    public Ship() {
        this.position = new PVector(0, 0);
        this.deadPosition = new PVector();
        this.size = new PVector(50, 50);
        this.shootDirection = new PVector(1, 1);
        this.fuelTankMap = new HashMap<>();
        this.printer = new ShipPrinter();
        this.gun = new Gun(fuel);
        this.engine = new Engine(fuel, new PVector(10, 10), new PVector(0, 0), false);
    }

    /**
     * @param fuel 要吸收的油滴
     * @return 是否可以吸收
     */
    public boolean checkIfAbsorb(Fuel fuel) {
        //注意还要考虑Oil的尺寸
        return fuel.position.dist(position) < fuel.size.mag();
    }

    /**
     * 执行吸收油滴的逻辑
     *
     * @param fuel 要吸收的油滴
     */
    public void absorbFuel(Fuel fuel) {
        if (dead) {
            return;
        }
        fuelTankMap.put(fuel.getFuelClass(), fuel.volume);
        this.fuel += fuel.volume;
    }


    public void move(Direction direction) {
        lastPosition = position;
        engine.setDirection(direction);
        position.add(engine.getVelocity());
        // 减少燃料:
        double d = position.dist(lastPosition);
        int r = (int) Math.floor(d / 25);
        fuel = fuel - r;
        engine.setFuel(fuel);
        // 判断是否没有燃料了
        if (fuel <= 0 && !dead) {
            dead = true;
            deadPosition = position.copy();
        }
    }


    public Bullet shoot() {
        gun.setRemainingEnergy(fuel);
        Bullet bullet  = gun.shoot(position.copy(), shootDirection.copy());
        fuel = gun.getRemainingEnergy();
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
