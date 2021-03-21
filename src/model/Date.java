package model;

import java.time.Duration;
import java.util.Calendar;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.input.MouseEvent;

public class Date {

    private Calendar calendar;
    private int[] digitos;

    public Date() {

        calendar = Calendar.getInstance();
        digitos = new int[6];
    }

    public void actualice() {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        digitos[0] = (hour / 10);
        digitos[1] = (hour % 10);
        digitos[2] = (minute / 10);
        digitos[3] = (minute % 10);
        digitos[4] = (second / 10);
        digitos[5] = (second % 10);
    }

    /**public void executeTime(){
        Timeline timeLine = new Timeline();
        Timeline secondTimeline = new Timeline();

        secondTimeline.setCycleCount(Timeline.INDEFINITE);
        Duration duration = new Duration(1000-calendar.get(Calendar.MILLISECOND)%1000);

        KeyFrame keyFrame = new KeyFrame(duration, <MouseEvent> event , KeyValue)

        KeyFrame secondFrame = new KeyFrame
        
        );
    } */

}
