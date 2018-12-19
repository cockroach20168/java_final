package gui;

import beings.BeingImage;
import beings.Grandfather;
import formations.Battlefield;
import javafx.animation.PathTransition;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class GrandfatherThread extends Grandfather implements Runnable {
    GrandfatherThread(Battlefield battlefield, MySemaphore mySemaphore, Battle battle){
        this.battlefield = battlefield;
        this.setMySemaphore(mySemaphore);
        this.battle = battle;
    }
    private boolean moveForward(){
        return battlefield.moveCreature(this.positionx+1, this.positiony, this);
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
        mySemaphore.roundStartAcquire();
        mySemaphore.animationEndRelease();
    }
    private Battlefield battlefield;
    private Battle battle;
}
