package entity;

import enums.Direction;
import lombok.AllArgsConstructor;
import lombok.Data;
import processing.core.PVector;

/**
 * 为entity提供移动能力(组合优于继承)
 * @Author: wangzilinn@gmail.com
 * @Date: 5/23/2021 12:21 PM
 */
@Data
@AllArgsConstructor
public class Engine {
    /**
     * 引擎剩余的燃料
     */
    private float fuel;
    private PVector velocity;
    private PVector acceleration;
    private boolean enableAcceleration;

    public void setDirection(Direction direction) {
        if (enableAcceleration) {
            acceleration = new PVector(0, 0);
            switch (direction) {
                case UP:
                    acceleration.y = -1;
                    break;
                case DOWN:
                    acceleration.y = 1;
                    break;
                case LEFT:
                    acceleration.x = -1;
                    break;
                case RIGHT:
                    acceleration.x = 1;
            }
        }else {
            velocity = new PVector(0, 0);
            switch (direction) {
                case UP:
                    velocity.y = -10;
                    break;
                case DOWN:
                    velocity.y = 10;
                    break;
                case LEFT:
                    velocity.x = -10;
                    break;
                case RIGHT:
                    velocity.x = 10;
            }
        }
    }

    public PVector getVelocity() {
        return velocity.add(acceleration);
    }
}
