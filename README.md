# java_final
## 效果展示
![Image text](https://github.com/cockroach20168/java_final/blob/master/image/show.gif)

## 说明
### 操作说明
在cmd中执行mvn clean test package,后进入target目录下使用java -jar java_final-1.0-SNAPSHOT.jar命令启动程序。\
程序启动后或回放和游戏结束后用键盘输入 空格 开始游戏，或输入L(按下l键，如中文输入法打开则把中文输入切换为英文)读取记录。
如在回放和游戏过程中按下按键将被忽略。（精彩游戏记录存储于src/record目录下。）\
每次进行游戏都会在jar所在文件夹下生成新的记录文件。
### 文件结构
1.image/:存放效果图片\
2.src/main/java:存放代码\
3.src/main/resource:存放资源文件(包括.fxml和运行时需加载的图片)\
4.src/record:存放我认为精彩的记录\
5.src/test:存放测试模块\
6.pom.xml\
7.README.md
## 项目设计
### 回合战斗的设计
游戏进行是按回合推进的，每个回合中每个生物都拥有一次移动和攻击，在这个回合当中所有生物的动作先后顺序是不固定，所有生物都随时可能被杀死。
### 生物攻击的设计
每个生物会选择距离自己距离最近且未死亡的敌方进行攻击，被攻击的敌方丢失攻击者攻击力对应的生命值或死亡生命值变为0(不出现负值)
### 生物特性
角色|血量|攻击力|攻击效果图
----|----|------|----------
老爷爷|400|40|white fireball
大娃|800|120|red fireball
二娃|800|120|orange fireball
三娃|800|120|yellow fireball
四娃|800|120|green fireball
五娃|800|120|cyan fireball
六娃|800|120|blue fireball
七娃|800|120|purple fireball
蛇精|1000|50|black fireball
蝎子精|1200|80|black fireball
小喽啰|200|50|blackfireball
### 对于同步的设计
由于是回合制游戏，我认为不能通过简单的同时start多个线程，然后让它们停止相同的时间在进行动作，这在长时间的运行中将可能带来问题，
在我的设计中，将回合制看作一个生产者消费者问题，即设计一个RoundTimer释放生物线程所需要的RoundStart Semaphore,同时申请获得AnimationEnd Semaphore,
相应的生物线程申请获取RoundStart Semaphore，在执行完它的动作后释放AnimaitonEnd Semaphore。通过Semaphore确保所有生物线程结束后，才能开始下一轮执行。
### 对battlefield资源竞争设计
简单的实现将所有生物在移动操作时进行互斥将是的线程之间并发度降低，这里采用对每一个Battlefield上的格子添加lock以提高并发度，即每个生物在站上战场后
拥有一个格子的lock，在它进行移动操作是去尝试对它想去的位置上锁，如果成功则移动到该格子，把之前的格子的锁释放
如果失败则保持不动，这种实现可以保证一个生物正在尝试移向下一个位置的时候，不会出现所有其它生物都在等它移完

## 整体运行流程
从Main类的main方法进入\
初始controller, 初始界面添加背景\
由controller创建战场，生物，回合计时器(RoundTimer)，战争控制类(Battle)将生物包装为生物线程\
开始RoundTimer线程，开始所有生物线程\
一方全部死亡时，RoundTimer再经历一回合，使所有生物线程先结束，RoundTimer再结束\
(避免在一回合中某一方的最后一个生物死亡，之前已经行动完的生物进入下一回合的等待，而RoundTimer直接结束
这些生物线程无法结束的情况)
下为回放：\
从Main类的main方法进入\
初始controller, 初始界面添加背景\
开启子线程读取记录，根据记录播放动画
## 实现
### 继承 封装
类图如下：
![Image text](https://github.com/cockroach20168/java_final/blob/master/image/uml.png)
\
1.其中CreatureThread类可以override moveForward()方法，在这里EvilPartyThread和JustPartyThread继承并重写了moveForward以满足生物的要求。
可以通过组合的方式将Creature对象放入CreatureThread及其派生类线程中，然后开始线程。
对于特殊的生物行为需求可以通过从CreatureThread类派生来实现，方便做后续扩展。\
2.Being类依赖于Entity接口，EntityWithSemaphore从Entity派生，增添了多线程需要的信号量。\
![Image text](https://github.com/cockroach20168/java_final/blob/master/image/Battlefield.png)
\
所有阵型实现Formation抽象类，与battlefield无关存储的点仅用于表示相对的站位关系，在抽象类的formationCreature()方法中根据实际的battlefield确定实际放置的位置。\
![Image text](https://github.com/cockroach20168/java_final/blob/master/image/uml.png)
\
1.Controller是god,创造所有葫芦世界中的事物，以及抽象的Battle类(控制战争行为的类)\
2.CreatureThread类的对象和RoundTimer通过MySemaphore同步，完成回合制\
3.Battle类中定义了生物move,attack,存储move attack记录， 播放move attack动画的行为\
### IO与Exception
使用装饰器模式进行IO操作，使用try catch语句对IO异常进行处理
```java
class Battle{
    public void wirteRecord(int round){
        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(recordFileName, true)));
            for(int i = 0; i < moveRecordList.size(); i++) {
                // ...
            }
            for(int i = 0; i < attackRecordList.size(); i++){
                // ...
            }
            out.close();
        }catch (IOException e){
            System.err.println("wirteRecord Error");
            e.printStackTrace();
        }
    }
}
```
### 多线程
CreatureThread和RoundTimer实现Runnable接口，使用时通过new Thread(...).start开始线程\
使用synchronized (this){...}对attack行为进行互斥\
使用了Semaphore和Lock,由于生产者和消费者都需要使用两个Semaphore,封装为MySemaphore
```java
public class MySemaphore {
    MySemaphore(int i, int j){
        semaphoreAnimationEnd = new Semaphore(i);
        semaphoreRoundStart = new Semaphore(j);
    }
    public Semaphore semaphoreAnimationEnd;
    public Semaphore semaphoreRoundStart;
    public void roundStartAcquire() {
    }

    public void animationEndAcquire() {
    }
    public void animationEndRelease(){
    }
    public void roundStartRelease(){
    }
}
```
在多线程中不得不提到javafx在自线程中修改ui显示的问题，由于对javafx的不熟悉，开始时直接在自线程中修改ui显示，
开始时没什么问题，后来修改变多了之后偶尔的会出现空指针报错和不允许在子线程中修改ui的报错，由于这个bug出现频率不是很高，
在调试过程中出现了很大的麻烦，后来在使用platform.runlater{...}将任务交给主线程完成后，多次测试发现bug消失了。
总结原因是javaFx只应该在主线程中修改ui
### 注解
添加注解类,记录作者和最后一次更新时间
```java
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.METHOD, ElementType.TYPE })
public @interface MyAnnotation {
    String Author();
    String Date();
}
```
### 测试
对RoundTimer的逻辑进行测试，测试使用MySemaphore进行同步的正确性
对各种阵型进行测试，确定所有阵型在布阵时不会出现越界等错误
### lamda表达式使用
在播放动画时需要对动画结束进行监听以及构建KeyFrame时需要EventHandler，由于EventHandler仅有一个方法所以使用lamda表达式可以更简练的书写代码
```java
class Battle{
    // ...
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
}
```
## 致谢
感谢这学期java课程两位老师的生动讲解以及助教对pull request的即时处理。
