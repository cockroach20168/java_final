package gui;

import beings.Creature;
import beings.EvilParty;
import beings.JustParty;
import formations.Battlefield;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

import java.io.*;
import java.sql.SQLSyntaxErrorException;
import java.util.Date;
import java.util.Vector;
import java.util.concurrent.Semaphore;

import static java.lang.Math.abs;

public class Battle {
    private boolean isFighting = false;
    private boolean isAllEnd = true;
    private Creature[] creatureList;
    private Vector<MoveRecord> moveRecordList = new Vector<MoveRecord>();
    private Vector<AttackRecord> attackRecordList = new Vector<AttackRecord>();
    private Controller controller;
    private String recordFileName;
    Battle(Controller controller){
        this.controller = controller;
    }
    public boolean getIsAllEnd(){
        return isAllEnd;
    }
    public void setIsAllEnd(boolean flag){
        isAllEnd = flag;
    }
    public boolean getIsFighting(){
        return isFighting;
    }
    public void setIsFighting(boolean flag){
        isFighting = flag;
    }
    public void setCreatureList(Creature[] creatureeList){
        this.creatureList = creatureeList;
    }
    public void createRecordFile(){
        //Date nowDate = new Date();
        //System.out.println(nowDate.toString());
        try {
            int i = 0;
            while(true){
                String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
                path = java.net.URLDecoder.decode(path, "UTF-8");
                String[] pathList = path.split("/");
                String prePath = "/";
                for(int temp = 0; temp < pathList.length-1; temp++){
                    prePath += pathList[temp]+"/";
                }
                System.out.println("path:"+prePath+"record" + i + ".txt");
                File newfile = new File(prePath+"record" + i + ".txt");
                if(newfile.exists()){

                }else{
                    if(newfile.createNewFile()){
                        recordFileName = newfile.getPath();
                        System.out.println(recordFileName+"   create file success");
                        //System.exit(-1);
                        break;
                    }
                    else{
                        System.out.println("create file fail");
                        System.exit(-1);
                    }
                }
                i++;
            }
            //out.write("1 0\r\n");
        }catch (Exception e){
            e.printStackTrace();
            System.exit(-1);
        }
    }
    public void output(Creature creature) {
        if(creature == null){
            return;
        }
        //battle.move(creature, 75*creature.getPositionx(), 75*creature.getPositiony(), 75*creature.getPositionx(), 75*creature.getPositiony());
        System.out.println("image ready to load on x:"+creature.getPositionx()+"y:"+creature.getPositiony());
        creature.getEntity().getImageView().setX(75*creature.getPositionx());
        creature.getEntity().getImageView().setY(75*creature.getPositiony());
        creature.getBloodBar().setX(75*creature.getPositionx());
        creature.getBloodBar().setY(75*creature.getPositiony());
        controller.getBorderPane().getChildren().add(creature.getEntity().getImageView());
        controller.getBorderPane().getChildren().add(creature.getBloodBar());
    }
    public void writeFormationRecord(){
        try{
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(recordFileName, true)));
            for(int i = 0; i < creatureList.length; i++){
                out.write(i + " " + creatureList[i].getPositionx() + " " + creatureList[i].getPositiony()+"\r\n");
            }
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void wirteRecord(int round){
        System.out.println("open file");
        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(recordFileName, true)));
            for(int i = 0; i < moveRecordList.size(); i++) {
                //System.out.println("open file");
                int pos = 0;
                for(int j = 0; j < creatureList.length; j++){
                    if(creatureList[j] == moveRecordList.get(i).being){
                        pos = j;
                        break;
                    }
                }
                System.out.println(round + " " + pos + " "
                        + moveRecordList.get(i).newPosx + " " + moveRecordList.get(i).newPoxy + " "
                        + moveRecordList.get(i).oldPosx + " " + moveRecordList.get(i).newPoxy);
                out.write(round + " " + pos + " "
                        + moveRecordList.get(i).newPosx + " " + moveRecordList.get(i).newPoxy + " "
                        + moveRecordList.get(i).oldPosx + " " + moveRecordList.get(i).newPoxy + "\r\n");
            }
            for(int i = 0; i < attackRecordList.size(); i++){
                int pos1 = 0;
                for(int j = 0; j < creatureList.length; j++){
                    if(creatureList[j] == attackRecordList.get(i).crimimal){
                        pos1 = j;
                        break;
                    }
                }
                int pos2 = 0;
                for(int j = 0; j < creatureList.length; j++){
                    if(creatureList[j] == attackRecordList.get(i).victim){
                        pos2 = j;
                        break;
                    }
                }
                System.out.println(round + " " + pos1 + " "
                        + pos2 + " " + attackRecordList.get(i).damage);
                out.write(round + " " + pos1 + " "
                        + pos2 + " " + attackRecordList.get(i).damage+"\r\n");
            }
            //out.flush();
            out.close();
            //System.exit(-1);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void playAnimation(){
        try{
            Global.animationStart.acquire();
        }catch (Exception e){
            e.printStackTrace();
        }
        final Timeline timeline = new Timeline();
        final Timeline timelineAttack = new Timeline();
        for(int i = 0; i < moveRecordList.size(); i++) {
            int newx = moveRecordList.get(i).newPosx;
            int newy = moveRecordList.get(i).newPoxy;
            int oldx = moveRecordList.get(i).oldPosx;
            int oldy = moveRecordList.get(i).oldPosy;
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1000),
                    new KeyValue(moveRecordList.get(i).being.getEntity().getImageView().xProperty(), oldx * 75),
                    new KeyValue(moveRecordList.get(i).being.getEntity().getImageView().yProperty(), oldy * 75),
                    new KeyValue(moveRecordList.get(i).being.getEntity().getImageView().translateXProperty(), (newx - oldx) * 75),
                    new KeyValue(moveRecordList.get(i).being.getEntity().getImageView().translateYProperty(), (newy - oldy) * 75),
                    new KeyValue(moveRecordList.get(i).being.getBloodBar().xProperty(), oldx * 75),
                    new KeyValue(moveRecordList.get(i).being.getBloodBar().yProperty(), oldy * 75),
                    new KeyValue(moveRecordList.get(i).being.getBloodBar().translateXProperty(), (newx - oldx) * 75),
                    new KeyValue(moveRecordList.get(i).being.getBloodBar().translateYProperty(), (newy - oldy) * 75)));
        }
        for(int i = 0; i < attackRecordList.size(); i++){
            final int index = i;
            timelineAttack.getKeyFrames().add(new KeyFrame(Duration.millis(2000), event -> {
                final Creature victim = attackRecordList.get(index).victim;
                Creature criminal = attackRecordList.get(index).crimimal;

                final Shape magicBall = new Circle(criminal.getPositionx()*75+37, criminal.getPositiony()*75+37, 15 / 2);
                magicBall.setFill(Color.rgb((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
                controller.getBorderPane().getChildren().add(magicBall);
                SequentialTransition sequentialTransition = new SequentialTransition();

                sequentialTransition.getChildren().add(criminal.getMagicBallscaleTransition(magicBall));
                sequentialTransition.getChildren().add(criminal.getMagicBalltranslateTransition(magicBall, victim));
                sequentialTransition.getChildren().add(victim.getVictimBloodAdjustTransition());
                if(victim.getIsDead()) {
                    sequentialTransition.getChildren().add(victim.getVictimRotateTransition());
                    sequentialTransition.getChildren().add(victim.getVictimColorAdjustTransition());
                }
                sequentialTransition.setOnFinished(arg0 -> controller.getBorderPane().getChildren().remove(magicBall));
                sequentialTransition.play();
            }));
        }
        timeline.setOnFinished(event -> timelineAttack.play());
        timeline.setCycleCount(1);
        timeline.setAutoReverse(false);
        timelineAttack.setOnFinished(event -> {
            attackRecordList.clear();
            moveRecordList.clear();
            Global.animationEnd.release();
        });
        timelineAttack.setCycleCount(1);
        timelineAttack.setAutoReverse(false);
        Platform.runLater(() -> timeline.play());
    }
    public void playClearAnimation(){
        final Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(500), event -> {
            controller.getBorderPane().getChildren().clear();
        }));
        timeline.setOnFinished((event) -> {
            isAllEnd = true;
        });
        timeline.play();
    }
    public void attack(Creature creature){
        synchronized (this){
            System.out.println(creature.getname()+"turn");
            if(!creature.getIsDead()){
                int x = creature.getPositionx();
                int y = creature.getPositiony();
                try{
                    int distance = 10000;
                    int victimIndex = -1;
                    for(int i = 0; i < creatureList.length; i++) {
                        if (!creatureList[i].getIsDead() && creature != creatureList[i]
                                && ((creatureList[i] instanceof JustParty && creature instanceof EvilParty) || (creatureList[i] instanceof EvilParty && creature instanceof JustParty))
                                ) {
                            if(distance > abs(creatureList[i].getPositionx() - x) + abs(creatureList[i].getPositiony() - y)){
                                distance = abs(creatureList[i].getPositionx() - x) + abs(creatureList[i].getPositiony() - y);
                                victimIndex = i;
                            }
                        }
                    }
                    if(victimIndex != -1) {
                        if (creature.getAttackStrength() < creatureList[victimIndex].getBlood()) {
                            creatureList[victimIndex].setBlood(creatureList[victimIndex].getBlood() - creature.getAttackStrength());
                            hurt(creature, creatureList[victimIndex], creature.getAttackStrength());
                        } else {
                            creatureList[victimIndex].setIsDead(true);
                            creatureList[victimIndex].setBlood(0);
                            hurt(creature, creatureList[victimIndex], creature.getAttackStrength());
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                creature.getEntity().getMySemaphore().animationEndRelease();

            }
            else{
                System.out.println(creature.getname()+" sorry, I'm dead");
                creature.getEntity().getMySemaphore().animationEndRelease();
            }
        }
        if(isFinished()){
            isFighting = false;
        }
    }
    public void move(final Creature creature, final int newx, final int newy, final int oldx, final int oldy) {
        moveRecordList.add(new MoveRecord(creature, newx, newy, oldx, oldy));
    }
    // 填写attack 记录
    public void hurt(Creature creatureCriminal, Creature creatureVictim, int damage){
        attackRecordList.add(new AttackRecord(creatureCriminal, creatureVictim, damage));
    }
    private boolean isFinished(){
        boolean JustPartyAllDead = true;
        boolean EvilPartyAllDead = true;
        for(int i = 0; i < creatureList.length; i++){
            if(JustPartyAllDead && !creatureList[i].getIsDead() && creatureList[i] instanceof JustParty){
                JustPartyAllDead = false;
            }
            else if(EvilPartyAllDead && !creatureList[i].getIsDead() && creatureList[i] instanceof EvilParty){
                EvilPartyAllDead = false;
            }
        }
        return JustPartyAllDead||EvilPartyAllDead;
    }
}
class MoveRecord{
    MoveRecord(Creature being, int newPosx, int newPosy, int oldPosx, int oldPosy){
        this.being = being;
        this.newPosx = newPosx;
        this.newPoxy = newPosy;
        this.oldPosx = oldPosx;
        this.oldPosy = oldPosy;
    }
    public Creature being;
    public int newPosx;
    public int newPoxy;
    public int oldPosx;
    public int oldPosy;
}
class AttackRecord{
    AttackRecord(Creature criminal, Creature victim, int damage){
        this.crimimal = criminal;
        this.victim = victim;
        this.damage = damage;
    }
    public Creature crimimal;
    public Creature victim;
    int damage;
}