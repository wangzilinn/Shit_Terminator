package system;

import entity.Bullet;
import entity.Gun;
import processing.core.PVector;

/**
 * @Author: wangzilinn@gmail.com
 * @Date: 5/23/2021 9:02 PM
 */
public class AimingSystem {
    public static Bullet directShootModel(Gun gun, PVector currentPosition, PVector enemyPosition) {
        PVector direction = enemyPosition.copy().sub(currentPosition).normalize(null);
        return gun.shoot(currentPosition, direction);
    }
}
