package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import processing.core.PVector;

/**
 * 为entity提供射击能力
 * @Author: wangzilinn@gmail.com
 * @Date: 5/23/2021 12:24 PM
 */
@Data
@AllArgsConstructor
public class Gun {

    /**
     * 枪的剩余子弹数
     */
    private float remainingEnergy;

    public Bullet shoot(PVector position, PVector shootDirection) {
        if (remainingEnergy < 10) {
            return null;
        }
        //if we don't have enough oil, then return null directly
        Bullet bullet = new Bullet(position.copy(), shootDirection, 10);
        remainingEnergy -= 10;
        return bullet;
    }
}
