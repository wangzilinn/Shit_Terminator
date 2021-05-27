package entity;

import draw.printer.ShipPrinter;
import enums.Direction;
import enums.ResourceClass;
import enums.Role;
import lombok.Getter;
import processing.core.PVector;
import system.AimingSystem;
import system.Meta;
import system.MoveSystem;

/**
 * @Author: huangyiqin
 * @Date: 5/16/2021 8:37 PM
 * @Modified: wangzilinn@gmail.com
 */

public class Ship {

    final private Gun gun;
    @Getter
    final private Role role;
    @Getter
    private final ShipPrinter printer;
    /**
     * 盛放不同资源的容器
     */
    public ResourceContainer resourceContainer;
    public boolean dead = false;
    /**
     * 死亡时的位置:
     */
    public PVector deadPosition;
    public PVector size;
    public PVector position;
    /**
     * 射击部分:
     */
    public PVector shootDirection;
    PVector lastPosition;
    /**
     * 移动部分:
     */
    private Engine engine;

    public Ship(Role role) {
        this.role = role;
        this.deadPosition = new PVector();
        this.size = new PVector(70, 70);
        this.shootDirection = new PVector(1, 1);

        this.resourceContainer = new ResourceContainer(100, 100, 100);

        this.gun = new Gun(resourceContainer, role);

        switch (this.role) {
            case COMPUTER:
                this.position = MoveSystem.randomPosition();
                this.engine = new Engine(resourceContainer, MoveSystem.randomVelocity(), new PVector(0, 0), true, 5);
                break;
            case PLAYER:
                this.position = new PVector(Meta.screenSize.x / 2, Meta.screenSize.y / 2);
                this.engine = new Engine(resourceContainer, new PVector(0, 0), new PVector(0, 0), false);
                break;
        }

        this.printer = new ShipPrinter();
    }

    /**
     * @param resource 要吸收的油滴
     * @return 是否可以吸收
     */
    public boolean checkIfAbsorb(Resource resource) {
        //注意还要考虑Oil的尺寸
        if (role == Role.COMPUTER) {
            // 降低飞船的吸收半径,不然太难了
            return resource.position.dist(position) < (resource.volume);
        } else {
            return resource.position.dist(position) < (resource.volume * 2 + size.x);
        }
    }

    /**
     * 执行吸收油滴的逻辑
     *
     * @param resource 要吸收的油滴
     */
    public void absorbFuel(Resource resource) {
        if (dead) {
            return;
        }
        printer.startShowingAbsorbResourceEffect(resource.resourceClass);
        resourceContainer.increase(resource.resourceClass, resource.volume);
    }


    /**
     * 键盘移动
     *
     * @param direction 移动方向
     */
    public void move(Direction direction) {
        lastPosition = position;
        engine.setDirection(direction);
        position.add(engine.getVelocity());
        // 减少燃料:
        // float d = position.dist(lastPosition);
        // double r = Math.floor(d / 25);
        // resourceContainer.decrease(ResourceClass.FUEL, (float) r);
        // 判断是否没有燃料了 TODO:这里可以不设置为死掉,而是单纯的停止移动,直到被另一个打死
        if (resourceContainer.empty(ResourceClass.FUEL) && !dead) {
            dead = true;
            deadPosition = position.copy();
        }
    }

    /**
     * 自动移动
     *
     * @param enemyPosition 敌方飞船位置
     */
    public void move(PVector enemyPosition) {
        MoveSystem.collisionModel(engine, position);
        position.add(engine.getVelocity());
        MoveSystem.avoidanceModel(engine, position, enemyPosition);
        position.add(engine.getVelocity());
        MoveSystem.frictionModel(engine);
        position.add(engine.getVelocity());
        // TODO:减少燃料
    }

    /**
     * @param bullet 子弹
     * @return 该子弹是否会击中自己
     */
    public boolean checkIfBeingHit(Bullet bullet) {
        return bullet.position.dist(position) < (size.mag() / 2);
    }

    /**
     * 执行被击中的逻辑
     *
     * @param bullet 被击中时的子弹
     */
    public void beingHit(Bullet bullet) {
        System.out.println(role + " ship was hit, damage is " + bullet.damage);
        resourceContainer.decrease(ResourceClass.SHIELD, bullet.damage);
        // 配置画笔开始显示被击中效果
        printer.startShowingBeingHitEffect();
        if (resourceContainer.empty(ResourceClass.SHIELD)) {
            System.out.println("no shield");
            dead = true;
        }
    }


    public Bullet shoot(Ship enemyShip) {
        Bullet bullet = null;
        switch (role) {
            case PLAYER:
                bullet = gun.shoot(position.copy(), shootDirection.copy());
                break;
            case COMPUTER:
                bullet = AimingSystem.directShootModel(gun, position, enemyShip.position);
                break;
        }
        return bullet;
    }


    public void updateShootDirection(PVector mousePosition) {
        shootDirection = mousePosition.copy().sub(position.copy()).normalize();
    }

    /**
     * 重置飞船的参数,在新的关卡时用到
     */
    public void reset() {
        resourceContainer = new ResourceContainer(100, 100, 100);
        dead = false;
    }

}
