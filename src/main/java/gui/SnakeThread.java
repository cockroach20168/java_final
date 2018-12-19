package gui;

import beings.Snake;
import formations.Battlefield;

public class SnakeThread extends Snake implements Runnable {
    SnakeThread(Battlefield battlefield, MySemaphore mySemaphore, Battle battle){
        this.battlefield = battlefield;
        this.setMySemaphore(mySemaphore);
        this.battle = battle;
    }
    private boolean moveForward(){
        return battlefield.moveCreature(this.positionx-1, this.positiony, this);
    }
    public void run(){
        battlefield.initLock(this);
        while(battle.getIsFighting()) {
            try{
                mySemaphore.roundStartAcquire();
                if(!moveForward()){
                    //mySemaphore.animationEndRelease();
                }
            }catch (Exception e){
                //do nothing
            }
        }
    }
    private Battlefield battlefield;
    private Battle battle;
}
