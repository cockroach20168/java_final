package gui;

import MyAnnotaion.MyAnnotation;

@MyAnnotation(Author = "zmc", Date = "2018/12/30")
public class RoundTimer implements Runnable{
    private MySemaphore[] semaphoresList;
    //private Battlefield battlefield;
    //private Semaphore animationEnd;
    //private Semaphore animationStart;
    private boolean isPlay;
    private Battle battle;
    private int readyTofinished;
    RoundTimer(MySemaphore[] semaphoresList, Battle battle, boolean isPlay){
        this.semaphoresList = semaphoresList;
        this.battle = battle;
        this.isPlay = isPlay;
    }
    public void run() {
        int count = 0;
        readyTofinished = 2;
        while (true) {
            if (isPlay && readyTofinished != 0) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // 等待所有生物动作结束
                System.out.println("wait for creature moving attacking");
                for (int i = 0; i < semaphoresList.length; i++) {
                    //System.out.print(i+":");
                    //System.out.println(semaphoresList[i]);
                    semaphoresList[i].animationEndAcquire();
                }

                System.out.println("write record");
                battle.wirteRecord(count);
                //System.out.println("AAAAAAAAAAAAAA start Animation AAAAAAAAAAA");
                Global.animationStart.release();

                System.out.println("play animation");

                battle.playAnimation();

                try {
                    Global.animationEnd.acquire();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    Thread.sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // 该轮动作结束 动画播放结束后 判定战斗结束则退出循环
                if (!battle.getIsFighting() && readyTofinished == 2) {
                    System.out.println("clear animation");
                    battle.playClearAnimation();
                    readyTofinished--;
                }
                else if(readyTofinished == 1){
                    System.out.println("ready to End");
                    readyTofinished--;
                }

                System.out.println("next round");
                // 使所有进程开始下一轮
                for (int i = 0; i < semaphoresList.length; i++) {
                    semaphoresList[i].roundStartRelease();
                }
                count++;

                System.out.println("!!!!!!!!!!!!!!!!RoundTimerEnd!!!!!!!!!!!!!!!");
            }
            else{
                System.out.println("RoundTimer End");
                break;
            }
        }
    }
}
