package org.choviwu.example.task;

import org.springframework.stereotype.Component;

/**
 * Created by ChoviWu on 2018/04/12
 * Description:
 */
@Component
public class DayTask {


//    @Scheduled(cron = "0/20 * * * * ?")
    public void task(){
        System.out.println("This is A task 。。。");
    }
}
