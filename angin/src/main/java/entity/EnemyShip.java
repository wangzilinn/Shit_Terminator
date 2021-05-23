package entity;

import enums.Direction;
import processing.core.PVector;
import system.MoveSystem;


/**
 * @Author: huangyiqin
 * @Date: 5/16/2021 8:34 PM
 * @Modified: wangzilinn@gmail.com
 */


public class EnemyShip{
    public PVector position;

    final private Engine engine;
    public float fuel = 1000;

    /**
     * 飞船的大小,在该范围内被击中
     */
    public PVector size;

    public int blood = 50;
    public boolean dead = false;

    public EnemyShip() {
        this.position = MoveSystem.randomPosition();
        this.size = new PVector(50, 50);
        this.engine = new Engine(fuel, MoveSystem.randomVelocity(), new PVector(0, 0), true);
        // this.engine = new Engine(fuel, new PVector(0,0), new PVector(0, 0), true);
    }


    public void move(PVector enemyPosition) {
        MoveSystem.collisionModel(engine, position);
        position.add(engine.getVelocity());
        MoveSystem.avoidanceModel(engine, position, enemyPosition);
        position.add(engine.getVelocity());
    }


    /**
     * @return 飞船泄露一滴油
     */
    public Fuel leak(){
        if(dead){
            return null;
        }
        return new Fuel(position, 10);
    }


    /**
     * @param bullet 子弹
     * @return 该子弹是否会击中自己
     */
    public boolean checkIfHit(Bullet bullet) {
        return bullet.position.x + bullet.size.x >= position.x && bullet.position.x <= position.x + size.x
                && bullet.position.y + bullet.size.y >= position.y && bullet.position.y <= position.y + size.y;
    }

    public void hit(Bullet bullet) {
        blood -= bullet.damage;
        if (blood <= 0) {
            dead = true;
        }
    }

    /**
     * 重置飞船的参数,在新的关卡时用到
     */
    public void reset() {
        blood = 50;
        dead = false;
    }
}
