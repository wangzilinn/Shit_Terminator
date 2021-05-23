package Draw.printer;

import entity.Ship;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @Author: wangzilinn@gmail.com
 * @Date: 5/22/2021 9:43 PM
 */
@Data
public class ShipPrinter {

    int frameCnt;
    int maxCnt;

    int[] circleColor = new int[]{220, 150, 100, 150, 220};

    public void updateCircleColor() {
        int temp = circleColor[circleColor.length - 1];
        System.arraycopy(circleColor, 0, circleColor, 1, circleColor.length - 1);
        circleColor[0] = temp;
    }

}
