package gui;

import MyAnnotaion.MyAnnotation;
import beings.Creature;
import formations.Battlefield;
@MyAnnotation(Author = "zmc", Date = "2018/12/29")
public class CreatureThread implements Runnable{

    CreatureThread(Creature creature, Battlefield battlefield, MySemaphore mySemaphore){
        this.creature = creature;
        this.battlefield = battlefield;
        creature.getEntity().setMySemaphore(mySemaphore);
    }
    protected Creature getCreature(){
        return creature;
    }
    void start(){
        new Thread(this).start();
    }
    public boolean moveForward(Battlefield battlefield, Creature creature){
        return battlefield.moveCreature(creature.getPositionx()+1, creature.getPositiony(), creature);
    };
    public void run(){
        battlefield.initLock(creature);
        while(Global.battle.getIsFighting()) {
            creature.getEntity().getMySemaphore().roundStartAcquire();
            if(!moveForward(battlefield, creature)){// 移动到边界或遇到其它东西无法继续移动
                // mySemaphore.animationEndRelease();
            }
            Global.battle.attack(creature);
        }
        creature.getEntity().getMySemaphore().roundStartAcquire();
        creature.getEntity().getMySemaphore().animationEndRelease();
        System.out.println(creature.getname()+"Thread End");
    }
    private Creature creature;
    private Battlefield battlefield;
}
@MyAnnotation(Author = "zmc", Date = "2018/12/29")
class JustPartyThread extends CreatureThread{
    public JustPartyThread(Creature creature, Battlefield battlefield, MySemaphore mySemaphore){
        super(creature, battlefield, mySemaphore);
    }
    @Override
    public boolean moveForward(Battlefield battlefield, Creature creature) {
        return battlefield.moveCreature(creature.getPositionx()+1, creature.getPositiony(), creature);
    }
}
@MyAnnotation(Author = "zmc", Date = "2018/12/29")
class EvilPartyThread extends CreatureThread{
    public EvilPartyThread(Creature creature, Battlefield battlefield, MySemaphore mySemaphore){
        super(creature, battlefield, mySemaphore);
    }
    @Override
    public boolean moveForward(Battlefield battlefield, Creature creature) {
        return battlefield.moveCreature(creature.getPositionx()-1, creature.getPositiony(), creature);
    }
}
