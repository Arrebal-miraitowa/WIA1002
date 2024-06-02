package com.weijie.ui.FXTool;

import com.weijie.ui.controls.WJStage;
import com.weijie.ui.page.WJLogin;
import com.weijie.ui.page.WJMid;
import javafx.animation.*;
import javafx.beans.value.WritableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class AnimationUtils {

    /**
     * 并行动画
     * <br/>
     * 注意：target，startValue，endValue 长度必须一致
     *
     * @param duration        执行时间，默认从0开始
     * @param targets
     * @param startValue      开始值
     * @param endValue        结束值
     * @param finishedHandler 动画结束事件
     * @param <T>
     * @return
     */
    public static <T> Timeline parallel(Duration duration,
                                        WritableValue<T>[] targets,
                                        T[] startValue,
                                        T[] endValue,
                                        EventHandler<ActionEvent> finishedHandler) {
        return parallel(duration, targets, startValue, endValue, finishedHandler, false, Duration.ZERO);
    }

    /**
     * 并行动画
     * <br/>
     * 注意：target，startValue，endValue 长度必须一致
     *
     * @param duration        执行时间，默认从0开始
     * @param targets
     * @param startValue      开始值
     * @param endValue        结束值
     * @param finishedHandler 动画结束事件
     * @param autoReverse     自动倒带
     * @param <T>
     * @return
     */
    public static <T> Timeline parallel(Duration duration,
                                        WritableValue<T>[] targets,
                                        T[] startValue,
                                        T[] endValue,
                                        EventHandler<ActionEvent> finishedHandler,
                                        boolean autoReverse) {
        return parallel(duration, targets, startValue, endValue, finishedHandler, autoReverse, Duration.ZERO);
    }

    /**
     * 并行动画
     * <br/>
     * 注意：target，startValue，endValue 长度必须一致
     *
     * @param duration        执行时间，默认从0开始
     * @param targets
     * @param startValue      开始值
     * @param endValue        结束值
     * @param finishedHandler 动画结束事件
     * @param autoReverse     自动倒带
     * @param pause           动画完成之前的暂停时间*2
     * @param <T>
     * @return
     */
    public static <T> Timeline parallel(Duration duration,
                                        WritableValue<T>[] targets,
                                        T[] startValue,
                                        T[] endValue,
                                        EventHandler<ActionEvent> finishedHandler,
                                        boolean autoReverse,
                                        Duration pause) {

        final Timeline timeline = new Timeline();
        List<KeyValue> startKeyValues = new ArrayList<>();
        List<KeyValue> endKeyValues = new ArrayList<>();
        for (int i = 0; i < targets.length; i++) {
            WritableValue<T> writableValue = targets[i];
            startKeyValues.add(new KeyValue(writableValue, startValue[i], Interpolator.LINEAR));
            endKeyValues.add(new KeyValue(writableValue, endValue[i], Interpolator.LINEAR));
        }
        KeyFrame startKeyFrame = new KeyFrame(Duration.ZERO, startKeyValues.toArray(new KeyValue[0]));
        KeyFrame endKeyFrame = new KeyFrame(duration, endKeyValues.toArray(new KeyValue[0]));
        timeline.getKeyFrames().setAll(startKeyFrame, endKeyFrame);
        if (pause.toMillis() != 0) {
            timeline.getKeyFrames().add(new KeyFrame(duration.add(pause))); // 暂停动画
        }
        timeline.setCycleCount(autoReverse ? 2 : 1);
        timeline.setAutoReverse(autoReverse);
        timeline.setOnFinished(finishedHandler);
        return timeline;
    }

    //*****************************************MyAnimation*****************************************\\

    public static SequentialTransition silkyPopupAnimation(WJStage parent, WJLogin example, WJMid wjMid, double insets, Duration time) {
        WJMid signUpMid = example.getSignUpMid();
        signUpMid.toFront();
        signUpMid.setVisible(true);
        double insetsPro = parent.getInsetsPro().getBottom();
        double sW = signUpMid.getMaxWidth(), sH = signUpMid.getMaxHeight();
        double pW = parent.getWidth(), pH = parent.getHeight();
        double scaleMaxW = (pW - insets + 15) / sW, scaleMaxH = (pH - insets + 15) / sH;
        ScaleTransition st1 = new ScaleTransition(time, signUpMid);
        st1.setToX(scaleMaxW);
        st1.setToY(scaleMaxH);
        st1.setInterpolator(Interpolator.EASE_OUT);
        ScaleTransition st2 = new ScaleTransition(time, signUpMid);
        st2.setToX((pW - insets) / sW);
        st2.setToY((pH - insets) / sH);
        st2.setInterpolator(Interpolator.EASE_IN);
        TranslateTransition tt = new TranslateTransition(time, signUpMid);
        double finalX = (pW - sW) / 2 - (signUpMid.getLayoutX() + insetsPro);
        double finalY = (pH - sH) / 2 - (signUpMid.getLayoutY() + insetsPro);
        tt.setByX(finalX - 1);
        tt.setByY(-finalY - 3);
        tt.setInterpolator(Interpolator.EASE_OUT);
        ParallelTransition pt1 = new ParallelTransition(st1, tt);
        pt1.setOnFinished(e -> {
            example.getSignInBox().setVisible(false);
            example.getSignUpBox().setVisible(false);
            wjMid.setVisible(true);
        });
        ParallelTransition pt2 = new ParallelTransition(st2, fade(time, wjMid, false));
        pt2.setOnFinished(e -> example.getChildren().remove(signUpMid));
        return new SequentialTransition(fade(Duration.millis(600), signUpMid, false), pt1, pt2);
    }

    public static FadeTransition fade(Duration time, Node node, boolean toTransparent) {
        FadeTransition ft = new FadeTransition(time, node);
        ft.setFromValue(toTransparent ? 1 : 0);
        ft.setToValue(toTransparent ? 0 : 1);
        return ft;
    }

    public static FadeTransition fade(Duration time, Node node, boolean toTransparent, EventHandler<ActionEvent> eventHandler) {
        FadeTransition ft = fade(time, node, toTransparent);
        ft.setOnFinished(eventHandler);
        return ft;
    }
}
