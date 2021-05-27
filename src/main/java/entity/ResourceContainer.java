package entity;

import enums.ResourceClass;
import lombok.Data;

import java.util.HashMap;

/**
 * @Author: wangzilinn@gmail.com
 * @Date: 5/23/2021 7:54 PM
 */
@Data
public class ResourceContainer {

    HashMap<ResourceClass, Float> resourceContainerMap = new HashMap<>();

    public ResourceContainer(float ammo, float fuel, float shield) {
        resourceContainerMap.put(ResourceClass.AMMO, ammo);
        resourceContainerMap.put(ResourceClass.FUEL, fuel);
        resourceContainerMap.put(ResourceClass.SHIELD, shield);
    }

    public void increase(ResourceClass resourceClass, float amount) {
        resourceContainerMap.compute(resourceClass, (key, value)->{
            if (value == null) {
                value = 0f;
            }
            value += amount;
            return value;
        });
    }

    public void decrease(ResourceClass resourceClass, float amount) {
        increase(resourceClass, -amount);
    }

    public float get(ResourceClass resourceClass) {
        return resourceContainerMap.get(resourceClass);
    }

    public boolean empty(ResourceClass resourceClass) {
        return resourceContainerMap.get(resourceClass) < 0;
    }
}
