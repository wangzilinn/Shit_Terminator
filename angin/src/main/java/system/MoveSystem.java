package system;

import entity.Engine;
import processing.core.PVector;

/**
 * @Author: wangzilinn@gmail.com
 * @Date: 5/23/2021 12:58 PM
 */
public class MoveSystem {
    public static PVector randomPosition() {
        float x = Meta.size.x;
        float y = Meta.size.y;

        return new PVector((float) Math.random() * x, (float) Math.random() * y);
    }

    public static PVector randomVelocity() {
        return new PVector((float) (Math.random() - 0.5) * 5, (float) (Math.random()  - 0.5)* 5);
    }

    /**
     * 完全碰撞模型
     * @param engine .
     * @param position 当前entity所在位置
     */
    public static void collisionModel(Engine engine, PVector position) {
        if (position.x > Meta.size.x || position.x < 0) {
            PVector velocity = engine.getVelocity();
            velocity.x = -velocity.x;
        }
        if (position.y > Meta.size.y || position.y < 0) {
            PVector velocity = engine.getVelocity();
            velocity.y = -velocity.y;
        }
    }

    /**
     * 避免碰撞模型
     * @param engine .
     * @param currentPosition 当前所在位置
     * @param avoidPosition 要躲避的entity所在位置
     */
    public static void avoidanceModel(Engine engine, PVector currentPosition, PVector avoidPosition) {
        float dist = currentPosition.dist(avoidPosition);
        PVector sub = currentPosition.copy().sub(avoidPosition).normalize();
        PVector direction = sub.copy().normalize();
        PVector acc = direction.mult(1 / dist * 10);
        if (dist < 300) {
            engine.setAcceleration(acc);
        }
    }

    /**
     * 滑动摩擦模型
     * @param engine .
     */
    public static void frictionModel(Engine engine) {
        PVector accDirection = engine.getVelocity().copy().normalize();
        PVector friction = accDirection.mult(-0.001f);
        PVector acc = engine.getAcceleration().copy();
        if (Math.abs(engine.getVelocity().x) > 3) {
            acc.x += friction.x;
        }else {
            acc.x = 0;
        }
        if (Math.abs(engine.getVelocity().y) > 3) {
            acc.y += friction.y;
        }else {
            acc.y = 0;
        }
        engine.setAcceleration(acc);
    }


}
