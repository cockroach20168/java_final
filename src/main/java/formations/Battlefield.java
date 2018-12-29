package formations;

import beings.Being;
import beings.Creature;
import beings.EvilParty;
import beings.JustParty;
import gui.Battle;
import gui.Global;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Math.E;
import static java.lang.Math.abs;

public class Battlefield {
    public Battlefield() {
        field = new Being[HEIGHT][WIDTH];
        for (int i = 0; i < HEIGHT; i++){
            for(int j = 0; j < WIDTH; j++){
                field[i][j] = null;
            }
        }
        for(int i = 0; i < WIDTH; i++){
            for(int j = 0; j < HEIGHT; j++){
                lockList[i][j] = new ReentrantLock(true);
            }
        }
    }

    public void printFieldCMD(){
        for (int i = 0; i < HEIGHT; i++){
            for(int j = 0; j < WIDTH; j++){
                if(field[i][j] != null)
                    field[i][j].toldname();
                else{
                    System.out.print("|      ");
                }
            }
            System.out.print("|\n");
        }
        System.out.print('\n');
    }
    public void printField(){
        printFieldCMD();
        for(int i = 0; i < HEIGHT; i++) {
            for(int j = 0; j <WIDTH; j++){
                if(field[i][j] != null){
                    Global.battle.output((Creature)field[i][j]);
                }
                else{
                    //adapter.output(field[i][j]);
                }
            }
        }
    }

    // 此函数用于一开始初始化战场上生物的位置，被FX Thread使用，如果在其中加锁
    // FX Thread 不会释放锁
    public boolean setCreature(int x, int y, Creature creature){
        if(y >= HEIGHT || x >= WIDTH || x < 0 || y < 0)
            return false;
        int oldPositionx = creature.getPositionx();
        int oldPositiony = creature.getPositiony();
        if (field[oldPositiony][oldPositionx] == creature) {
            field[oldPositiony][oldPositionx] = null;
        }
        creature.setPosition(x, y);
        field[y][x] = creature;
        return true;
    }

    // moveCreature 由每个生物进程调用，调用时尝试获取新位置的锁，成功就更换位置，结束后释放原有位置的锁
    public boolean moveCreature(int x, int y, Creature creature){
        boolean flag = true;
        if(creature.getIsDead() || y >= HEIGHT || x >= WIDTH || x < 0 || y < 0)
            flag = false;
        int oldPositionx = creature.getPositionx();
        int oldPositiony = creature.getPositiony();
        //creature.toldname();
        //System.out.println("oldpos:"+lockList[oldPositionx][oldPositiony]);
        //System.out.println("newpos:"+lockList[x][y]);
        // 尝试前进
        if(flag && lockList[x][y].tryLock()) {//成功获取下个位置的锁
            try {
                if (field[y][x] == null) {
                    if (field[oldPositiony][oldPositionx] == creature) {
                        field[oldPositiony][oldPositionx] = null;
                    }
                    field[y][x] = creature;
                    creature.setPosition(x, y);
                    flag = true;
                    Global.battle.move(creature, x, y, oldPositionx, oldPositiony);
                } else {
                    flag = false;
                    System.out.println("NO-WAY 在获取到下个位置的锁后发现该位置有物体");
                    System.exit(-1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lockList[oldPositionx][oldPositiony].unlock();
            }
        }
        else if(flag){
            flag = false;
        }
        return flag;
    }

    // 用于初始化锁的状态，即每个生物应当持有其初始位置的锁
    public void initLock(Creature creature){
        int x = creature.getPositionx();
        int y = creature.getPositiony();
        if(lockList[x][y].tryLock()){
            // 为自己的初始位置上锁
        }
        else{
            System.out.println("NO-WAY 初始战场时，存在生物不能获取一个位置的锁");
        }
    }
    public int getHeight() {
        return HEIGHT;
    }
    public int getWidth() {
        return WIDTH;
    }
    private static final int WIDTH = 16;
    private static final int HEIGHT = 8;
    private Being[][] field;
    private Lock[][] lockList = new Lock[WIDTH][HEIGHT];
    //private Controller controller;
}

