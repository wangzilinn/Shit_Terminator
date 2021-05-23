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
        return new PVector((float) (Math.random() - 0.5) * 10, (float) (Math.random()  - 0.5)* 10);
    }

    /**
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
     * @param engine .
     * @param currentPosition 当前所在位置
     * @param avoidPosition 要躲避的entity所在位置
     */
    public static void avoidanceModel(Engine engine, PVector currentPosition, PVector avoidPosition) {
        PVector sub = currentPosition.copy().sub(avoidPosition);
        if (sub.mag() < 80) {
            engine.setAcceleration(sub.mult((float) 0.005));
        }
        if (Math.abs(engine.getVelocity().mag()) > 5) {
            PVector acc = engine.getVelocity().copy().normalize().mult(-0.001f);
            engine.setAcceleration(acc);
        }
    }


}
