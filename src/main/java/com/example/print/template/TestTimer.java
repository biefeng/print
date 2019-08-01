package com.example.print.template;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/*
 *@Author BieFeNg
 *@Date 2019/7/29 17:19
 *@DESC
 */
public class TestTimer {
    static Task task;
    static Timer t;
    public static void main(String[] args) {

        TestTimer testMain = new TestTimer();
        /*TaskManager manager = testMain.new TaskManager();
        Timer t = new Timer();
        t.schedule(manager, new Date());*/
        t = new Timer();
        task = testMain.new Task(t);
        t.schedule(task, 0, 3000);

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(9);
                task.cancel();
                if (t.purge()==1){
                    System.out.println("存货任务数为0");
                    t.cancel();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    class TaskManager extends TimerTask {

        @Override
        public void run() {
            Timer t = new Timer();
            Task task = new Task(t);
            LocalDateTime localDateTime = LocalDateTime.now();

            Date now = new Date();
            t.schedule(task, 100, 3000);
        }
    }

    class Task extends TimerTask {

        private Timer timer;
        private LocalTime startLocal;
        private LocalTime endLocal;

        public Task(Timer timer) {
            this.startLocal = LocalTime.parse("18:51");
            this.endLocal = LocalTime.parse("23:55:00");
            this.timer = timer;
        }

        @Override
        public void run() {
            try {
                if (LocalTime.now().compareTo(endLocal) < 0) {
                    throw new RuntimeException("卧槽");
                } else {
                    cancel();
                }
            } catch (Exception e) {
                e.printStackTrace();
             //   callback();
            }
        }


        public void callback() {
            task = new Task(timer);
            timer.schedule(task, 3000, 3000);
        }
    }
}
