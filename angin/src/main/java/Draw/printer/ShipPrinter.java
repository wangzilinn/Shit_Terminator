package Draw.printer;

import entity.Ship;
import enums.ResourceClass;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @Author: wangzilinn@gmail.com
 * @Date: 5/22/2021 9:43 PM
 */
public class ShipPrinter {

    //被击中后的帧计数
    /**
     * 在Ship中被设为true后开始显示被集中效果，等到帧显示完之后应变为false
     */
    private boolean showBeingHitEffect = false;
    private int beingHitFrameCnt = 0;
    final private int beingHitFrame = 60; //120帧


    /**
     * 外面的环的颜色值
     */
    @Getter
    final private int[] circleColor = new int[]{220, 150, 100, 150, 220};

    public void updateCircleColor() {
        int temp = circleColor[circleColor.length - 1];
        System.arraycopy(circleColor, 0, circleColor, 1, circleColor.length - 1);
        circleColor[0] = temp;
    }

    public boolean checkIfShowBeingHitEffect() {
        return showBeingHitEffect;
    }

    public void increaseBeingHitFrame() {
        if (showBeingHitEffect) {
            beingHitFrameCnt++;
        }
        if (beingHitFrameCnt >= beingHitFrame) {
            showBeingHitEffect = false;
            beingHitFrameCnt = 0;
        }
    }

    /**
     * 调用该方法则指示开始显示被击中效果
     */
    public void startShowingBeingHitEffect() {
        showBeingHitEffect = true;
    }


}
