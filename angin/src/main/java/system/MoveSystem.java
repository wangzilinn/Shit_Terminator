package system;

import enums.Direction;
import processing.core.PVector;

/**
 * @Author: wangzilinn@gmail.com
 * @Date: 5/21/2021 9:43 PM
 */
public class MoveSystem {
    public static void move(Direction direction, boolean dead, PVector position) {
        if (dead) {
            return;
        }
        switch (direction) {
            case UP:
                position.y -= 10;
                break;
            case DOWN:
                position.y += 10;
                break;
            case LEFT:
                position.x -= 10;
                break;
            case RIGHT:
                position.x += 10;
        }
    }
}
