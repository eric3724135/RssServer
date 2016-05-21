package com.xerry.schedule;

import com.xerry.parser.PTTParser;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Timer;

/**
 * Created by wwp on 2016/5/21.
 */
public class TimerInitial {


    private static TimerInitial ourInstance = new TimerInitial();

    public static TimerInitial getInstance() {
        return ourInstance;
    }


    private TimerInitial() {
        new Timer().schedule(new PttHandler(), 5000, 5000);
    }


}
