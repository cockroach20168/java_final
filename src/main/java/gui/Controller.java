package gui;

import beings.*;
import formations.*;
import javafx.fxml.FXML;
import javafx.scene.layout.*;

import java.io.*;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import other.CalabashGroup;
import javafx.animation.*;
import javafx.util.Duration;
import java.util.concurrent.Semaphore;

import static java.lang.Math.abs;

public class Controller{
    @FXML
    private BorderPane borderPane;
    private Semaphore animationEnd;
    private Semaphore animationStart;
    private Battle battle;
    //private boolean isEnd = true;
    //@Override
    public void initialize(URL location, ResourceBundle resources){

    }
    public void myInit(){
        animationEnd = new Semaphore(0);
        animationStart = new Semaphore(0);
        Image background = new Image(getClass().getResource("/background.jpg").toString());
        BackgroundSize backgroundSize = new BackgroundSize(75*16, 75*8, true,true,true, false);
        borderPane.setBackground(new Background(new BackgroundImage(background, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, backgroundSize)));
        battle = new Battle(this, animationEnd, animationStart);
    }
    public boolean getIsEnd(){
        return battle.getIsAllEnd();
    }
    public BorderPane getBorderPane(){
        return borderPane;
    }
    public void startGame(){
        battle.setIsFighting(true);
        battle.setIsAllEnd(false);
        //System.out.println("game start");
        //borderPane.getChildren().removeAll();
        MySemaphore[] semaphoresList = new MySemaphore[7+1+1+8];
        //AnimationPlayer animationPlayer = new AnimationPlayer(animationStart, animationEnd);
        //UIOutput temp = new UIOutput(this, animationEnd, animationStart);
        Battlefield battlefield = new Battlefield(battle);

        for(int i = 0; i < semaphoresList.length; i++){
            semaphoresList[i] = new MySemaphore(1,0);
            //System.out.println("create:"+semaphoresList[i].semaphoreAnimationEnd);
        }
        EvilParty[] enemy = new EvilParty[8];
        ScorpionThread scorpion = new ScorpionThread(battlefield, semaphoresList[9], battle);
        enemy[0] = scorpion;
        enemy[0].setImageView(BeingImage.Scorpion);
        Thread scorpionThread = new Thread(scorpion);

        Thread[] littleMonsterThreadList = new Thread[7];
        for(int i = 1; i < 8; i++){
            LittleMonsterThread littleMonster = new LittleMonsterThread(battlefield, semaphoresList[9+i], battle);
            enemy[i] = littleMonster;
            enemy[i].setImageView(BeingImage.LittleMonster);
            littleMonsterThreadList[i-1] = new Thread(littleMonster);
        }
        SnakeThread snake = new SnakeThread(battlefield, semaphoresList[8], battle);
        snake.setImageView(BeingImage.Snake);
        Thread snakeThread = new Thread(snake);

        GrandfatherThread grandfather = new GrandfatherThread(battlefield, semaphoresList[7], battle);
        grandfather.setImageView(BeingImage.Grandfather);
        Thread grandfatherThread = new Thread(grandfather);

        Thread[] calabashThreadList = new Thread[7];
        CalabashThread[] calabashList = new CalabashThread[7];
        for(int i = 0; i < calabashList.length; i++){
            calabashList[i] = new CalabashThread(battlefield, i, semaphoresList[i], battle);
            //System.out.println(semaphoresList[i].semaphoreAnimationEnd);
            calabashThreadList[i] = new Thread(calabashList[i]);
        }
        calabashList[0].setImageView(BeingImage.RedOne);
        calabashList[1].setImageView(BeingImage.OrangeOne);
        calabashList[2].setImageView(BeingImage.YellowOne);
        calabashList[3].setImageView(BeingImage.GreenOne);
        calabashList[4].setImageView(BeingImage.CyanOne);
        calabashList[5].setImageView(BeingImage.BlueOne);
        calabashList[6].setImageView(BeingImage.PurpleOne);
        CalabashGroup calabashGroup = new CalabashGroup(calabashList);

        Creature[] creatureList = new Creature[17];
        creatureList[0] = grandfather;
        creatureList[1] = snake;
        creatureList[2] = scorpion;
        for(int i = 0, start = 3; i < 7; i++){
            creatureList[start+i] = calabashList[i];
        }
        for(int i = 0, start = 10; i < 7; i++){
            creatureList[start+i] = enemy[1+i];
        }
        for(int i = 0; i < creatureList.length; i++){
            System.out.print("init:"+i);
            System.out.println(creatureList[i]);
        }
        battle.createRecordFile();
        battle.getCreatureList(creatureList);
        //battlefield.getBeingList(creatureList);
        randomFomation(battlefield, (Creature[]) calabashGroup.getFormationCreatrue(), grandfather);
        randomFomation(battlefield, (Creature[]) enemy, snake);
        //new CrescentMoon().formationCreatrue(battlefield, (Creature[]) calabashGroup.getFormationCreatrue(), grandfather);
        battlefield.printField();
        battle.writeFormationRecord();
        System.out.println("start game "+Thread.currentThread().getName());

        //Thread animationPlayerThread = new Thread(animationPlayer);
        Thread roundTimerThread = new Thread(new RoundTimer(semaphoresList, battle, true));
        //animationPlayerThread.start();
        roundTimerThread.start();

        for(int i = 0; i < littleMonsterThreadList.length; i++){
            littleMonsterThreadList[i].start();
        }
        scorpionThread.start();
        snakeThread.start();
        grandfatherThread.start();

        for(int i = 0; i < calabashThreadList.length; i++){
            calabashThreadList[i].start();
        }
    }
    public void randomFomation(Battlefield battlefield, Creature[] creatures, Creature leader){
        Random random = new Random();
        int temp = abs(random.nextInt())%8;
        switch (temp){
            case 0:
                new LongSnake().formationCreatrue(battlefield, creatures, leader);
                break;
            case 1:
                new FishSquama().formationCreatrue(battlefield, creatures, leader);
                break;
            case 2:
                new BowandArrow().formationCreatrue(battlefield, creatures, leader);
                break;
            case 3:
                new CraneWing().formationCreatrue(battlefield, creatures, leader);
                break;
            case 4:
                new GeeseFlyShape().formationCreatrue(battlefield, creatures, leader);
                break;
            case 5:
                new SquareCircle().formationCreatrue(battlefield, creatures, leader);
                break;
            case 6:
                new XShape().formationCreatrue(battlefield, creatures, leader);
                break;
            case 7:
                new CrescentMoon().formationCreatrue(battlefield, creatures, leader);
                break;
        }
    }
    public void loadRecord(File recordFile){
        battle.setIsFighting(true);
        battle.setIsAllEnd(false);
        System.out.println("read record and play");
        if (recordFile == null || !recordFile.exists()) {
            System.out.println("read record fail");
            return;
        }
        System.out.println("start init");
        EvilParty[] enemy = new EvilParty[8];
        Scorpion scorpion = new Scorpion();
        enemy[0] = scorpion;
        enemy[0].setImageView(BeingImage.Scorpion);
        for(int i = 1; i < 8; i++){
            LittleMonster littleMonster = new LittleMonster();
            enemy[i] = littleMonster;
            enemy[i].setImageView(BeingImage.LittleMonster);
        }
        Snake snake = new Snake();
        snake.setImageView(BeingImage.Snake);

        Grandfather grandfather = new Grandfather();
        grandfather.setImageView(BeingImage.Grandfather);

        CalabashBrother[] calabashList = new CalabashBrother[7];
        for(int i = 0; i < calabashList.length; i++){
            calabashList[i] = new CalabashBrother(i);
        }
        calabashList[0].setImageView(BeingImage.RedOne);
        calabashList[1].setImageView(BeingImage.OrangeOne);
        calabashList[2].setImageView(BeingImage.YellowOne);
        calabashList[3].setImageView(BeingImage.GreenOne);
        calabashList[4].setImageView(BeingImage.CyanOne);
        calabashList[5].setImageView(BeingImage.BlueOne);
        calabashList[6].setImageView(BeingImage.PurpleOne);
        CalabashGroup calabashGroup = new CalabashGroup(calabashList);
        Creature[] creatureList = new Creature[17];
        creatureList[0] = grandfather;
        creatureList[1] = snake;
        creatureList[2] = scorpion;
        for(int i = 0, start = 3; i < 7; i++){
            creatureList[start+i] = calabashList[i];
        }
        for(int i = 0, start = 10; i < 7; i++){
            creatureList[start+i] = enemy[1+i];
        }
        System.out.println("end init");
        battle.getCreatureList(creatureList);

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(recordFile));
            String string = null;
            while ((string = bufferedReader.readLine()) != null) {
                System.out.println(string);
                String[] record = string.split(" ");
                if (record.length == 3) {
                    int creatureNo = Integer.parseInt(record[0]);
                    int Posx = Integer.parseInt(record[1]);
                    int Posy = Integer.parseInt(record[2]);
                    creatureList[creatureNo].setPosition(Posx, Posy);
                    battle.output(creatureList[creatureNo]);
                }
                else{
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        new Thread(() -> {
            int round = 1;
            try {
                BufferedReader bufferedReader  = new BufferedReader(new FileReader(recordFile));
                String string = null;
                while((string = bufferedReader.readLine()) != null){
                    System.out.println(string);
                    String[] record = string.split(" ");

                    if(record.length == 6){
                        System.out.println("move record");
                        int roundRead = Integer.parseInt(record[0]);
                        int creatureNo = Integer.parseInt(record[1]);
                        int newPosx = Integer.parseInt(record[2]);
                        int newPosy = Integer.parseInt(record[3]);
                        int oldPosx = Integer.parseInt(record[4]);
                        int oldPosy = Integer.parseInt(record[5]);
                        if(roundRead == round){
                            System.out.println("add record");
                            //moveRecordList.add(new MoveRecord(creatureList[creatureNo], newPosx, newPosy, oldPosx, oldPosy));
                            battle.move(creatureList[creatureNo], newPosx, newPosy, oldPosx, oldPosy);
                            creatureList[creatureNo].setPosition(newPosx, newPosy);
                        }
                        else{
                            System.out.println("play animation");
                            round = roundRead;
                            animationStart.release();
                            battle.playAnimation();
                            animationEnd.acquire();
                            try {
                                Thread.sleep(4000);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            battle.move(creatureList[creatureNo], newPosx, newPosy, oldPosx, oldPosy);
                            creatureList[creatureNo].setPosition(newPosx, newPosy);
                        }
                    }else if(record.length == 4){
                        System.out.println("attack record");
                        int roundRead = Integer.parseInt(record[0]);
                        int criminalNo = Integer.parseInt(record[1]);
                        int victimNo = Integer.parseInt(record[2]);
                        int damage = Integer.parseInt(record[3]);

                        if(roundRead == round){
                            Creature criminal = creatureList[criminalNo];
                            Creature victim = creatureList[victimNo];
                            battle.hurt(creatureList[criminalNo], creatureList[victimNo], damage);
                            if(criminal.getAttackStrength() < victim.getBlood()){
                                victim.setBlood(victim.getBlood()-criminal.getAttackStrength());
                            }
                            else{
                                victim.setIsDead(true);
                                victim.setBlood(0);
                            }
                        }
                        else{
                            System.out.println("play animation");
                            round = roundRead;
                            animationStart.release();
                            battle.playAnimation();
                            animationEnd.acquire();
                            try {
                                Thread.sleep(4000);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            Creature criminal = creatureList[criminalNo];
                            Creature victim = creatureList[victimNo];
                            battle.hurt(creatureList[criminalNo], creatureList[victimNo], damage);
                            if(criminal.getAttackStrength() < victim.getBlood()){
                                victim.setBlood(victim.getBlood()-criminal.getAttackStrength());
                            }
                            else{
                                victim.setIsDead(true);
                                victim.setBlood(0);
                            }
                        }
                    }
                    else if(record.length == 3){
                        ;
                    }
                    else{
                        System.out.println("read record fail");
                        return;
                    }
                }
                bufferedReader.close();
                animationStart.release();
                battle.playAnimation();
                animationEnd.acquire();
                try {
                    Thread.sleep(4000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                battle.playClearAnimation();
            }catch (Exception e){

            }
        }).start();
    }
    void testImageViewMove(){
        ImageView temp = new ImageView(BeingImage.PurpleOne.getImage());
        ImageView temp2 = new ImageView(BeingImage.PurpleOne.getImage());
        temp.setFitWidth(74);
        temp.setFitHeight(74);
        temp.setX(74);
        temp.setY(74);
        temp2.setFitWidth(74);
        temp2.setFitHeight(74);
        temp2.setX(150);
        temp2.setY(150);
        borderPane.getChildren().add(temp);
        borderPane.getChildren().add(temp2);
        Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        timeline.setAutoReverse(false);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1000),
                new KeyValue (temp.translateXProperty(), 75)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1000),
                new KeyValue(temp.translateYProperty(), 75)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1000),
                new KeyValue (temp2.translateXProperty(), 75)));
        timeline.play();
    }
}
