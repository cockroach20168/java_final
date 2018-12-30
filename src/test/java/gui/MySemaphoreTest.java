package gui;

import org.junit.Test;

import java.util.concurrent.Semaphore;

import static org.junit.Assert.*;

public class MySemaphoreTest {

    MySemaphore mySemaphore;
    @Test
    public void SemaphoreTest(){
        int count = 0;
        MySemaphore[] semaphoresList = new MySemaphore[17];
        for(int i = 0; i < semaphoresList.length; i++){
            semaphoresList[i] = new MySemaphore(1,0);
        }
        while (true) {
            if (count <= 10) {
                for (int i = 0; i < semaphoresList.length; i++) {
                    //System.out.print(i+":");
                    //System.out.println(semaphoresList[i]);
                    semaphoresList[i].animationEndAcquire();
                }

                Global.animationStart.release();

                System.out.println("play animation");

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Global.animationStart.acquire();
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                        Global.animationEnd.release();
                    }
                }).start();

                try {
                    Global.animationEnd.acquire();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < semaphoresList.length; i++) {
                    semaphoresList[i].roundStartRelease();
                }
                // 模拟生物线程
                for(int i = 0; i < semaphoresList.length; i++){
                    final int index = i;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            semaphoresList[index].roundStartAcquire();
                            semaphoresList[index].animationEndRelease();
                        }
                    }).start();
                }
                count++;
            }
            else{
                System.out.println("RoundTimer End");
                break;
            }
        }
    }
}