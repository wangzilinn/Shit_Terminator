package draw.printer;

import enums.Color;
import enums.ResourceClass;
import lombok.Getter;

/**
 * @Author: wangzilinn@gmail.com
 * @Date: 5/22/2021 9:43 PM
 */
public class ShipPrinter {

    //被击中后的效果:
    final private int beingHitFrame = 60; //60帧
    /**
     * 外面的环的颜色值
     */
    @Getter
    final private int[] ringColorValue = new int[]{240, 150, 100, 150, 240};
    /**
     * 在Ship中被设为true后开始显示被集中效果，等到帧显示完之后应变为false
     */
    private boolean showBeingHitEffect = false;

    //吸收资源后的效果:
    private int beingHitFrameCnt = 0;
    @Getter
    private Color ringColor;

    /**
     * 平移颜色值
     */
    public void shiftRingColorValue() {
        int temp = ringColorValue[0];
        System.arraycopy(ringColorValue, 1, ringColorValue, 0, ringColorValue.length - 1);
        ringColorValue[ringColorValue.length - 1] = temp;
    }

    public void startShowingAbsorbResourceEffect(ResourceClass resourceClass) {
        switch (resourceClass) {
            case AMMO:
                ringColor = Color.RED;
                break;
            case FUEL:
                ringColor = Color.GREEN;
                break;
            case SHIELD:
                ringColor = Color.BLUE;
                break;
        }
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
