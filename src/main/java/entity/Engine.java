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
public class Engine {
    /**
     * 引擎剩余的燃料
     */
    private ResourceContainer resourceContainer;
    private PVector velocity;
    private PVector acceleration;
    private boolean enableAcceleration;

    private boolean enableVelocityLimiter;
    private float maxVelocity;

    public Engine(ResourceContainer resourceContainer, PVector velocity, PVector acceleration,
                  boolean enableAcceleration) {
        this(resourceContainer, velocity, acceleration, enableAcceleration, false, 0);
    }

    public Engine(ResourceContainer resourceContainer, PVector velocity, PVector acceleration,
                  boolean enableAcceleration, float maxVelocity) {
        this(resourceContainer, velocity, acceleration, enableAcceleration, true, maxVelocity);
    }

    private Engine(ResourceContainer resourceContainer, PVector velocity, PVector acceleration, boolean enableAcceleration,
             boolean enableVelocityLimiter, float maxVelocity) {
        this.resourceContainer = resourceContainer;
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.enableAcceleration = enableAcceleration;
        this.enableVelocityLimiter = enableVelocityLimiter;
        this.maxVelocity = maxVelocity;
    }


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
        accLimiter(0.03f);
        velocity.add(acceleration);
        if (enableVelocityLimiter) {
            velocityLimiter();
        }
        return velocity;
    }

    private void accLimiter(float limit) {
        if (acceleration.x > limit) {
            acceleration.x = limit;
        }
        if (acceleration.x < -limit) {
            acceleration.x = -limit;
        }
        if (acceleration.y > limit) {
            acceleration.y = limit;
        }
        if (acceleration.y < -limit) {
            acceleration.y = -limit;
        }
    }

    private void velocityLimiter() {
        if (velocity.mag() > maxVelocity) {
            velocity = velocity.normalize(null).mult(maxVelocity);
        }
    }

    public void setVelocityLimiter(float limiter) {
        maxVelocity = limiter;
        enableVelocityLimiter = true;
    }


}
