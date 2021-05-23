package entity;

import enums.ResourceClass;
import enums.Role;
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
    private ResourceContainer resourceContainer;
    private Role role;

    /**
     * @param position 枪的当前坐标
     * @param shootDirection 发射方向
     * @return 子弹
     */
    public Bullet shoot(PVector position, PVector shootDirection) {
        if (resourceContainer.get(ResourceClass.AMMO) < 10) {
            return null;
        }
        //if we don't have enough oil, then return null directly
        Bullet bullet = new Bullet(position.copy(), shootDirection, 10, role);
        resourceContainer.decrease(ResourceClass.AMMO, 10);
        return bullet;
    }
}
