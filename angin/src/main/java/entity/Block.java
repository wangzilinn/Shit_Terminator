package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import processing.core.PVector;

/**
 * 障碍物 第二关出现
 * @Author: wangzilinn@gmail.com
 * @Date: 5/25/2021 4:56 PM
 */
@Data
@AllArgsConstructor
public class Block {
    PVector topLeftCoordinate;
    PVector bottomRightCoordinate;
    float width = 50;
    float height = 50;

    int remainLife;

    public Block(PVector screenSize, PVector[] avoidPosition) {
        for (PVector pVector : avoidPosition) {
            topLeftCoordinate = new PVector((float) Math.random() * screenSize.x, (float) Math.random() * screenSize.x);
            bottomRightCoordinate = topLeftCoordinate.copy().add(new PVector(width, height));
            // 如果与避免的位置距离过近,则重新生成
            while (topLeftCoordinate.dist(pVector) < 100 || bottomRightCoordinate.dist(pVector) < 100) {
                topLeftCoordinate = new PVector((float) Math.random() * screenSize.x, (float) Math.random() * screenSize.x);
                bottomRightCoordinate = topLeftCoordinate.copy().add(new PVector(width, height));
            }
        }
        remainLife = getRandomLife();
    }

    public void reduceLife() {
        if (remainLife > 0) {
            remainLife -= 1;
        }
    }

    public boolean positionIsInBlock(PVector position) {
        return position.x >= topLeftCoordinate.x
                && position.x <= bottomRightCoordinate.x
                && position.y >= topLeftCoordinate.y
                && position.y <= bottomRightCoordinate.y;
    }

    private static int getRandomLife() {
        return (int) (Math.random() * 600);
    }
}
