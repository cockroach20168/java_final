package formations;

import MyAnnotaion.MyAnnotation;

@MyAnnotation(Author = "zmc", Date = "2018/12/15")
public class LongSnake extends Formation {
    public LongSnake(){
        type = FormationType.LONGSNAKE;
        height = 8;
        width = 2;
        cheerPos.set(3,-3);
        posList = new Pos[8];
        for(int i = 0; i < posList.length; i++){
            posList[i] = new Pos();
        }
        posList[0].set(3,-2);
        posList[1].set(2,-2);
        posList[2].set(1,-2);
        posList[3].set(0,-2);
        posList[4].set(4,-2);
        posList[5].set(5,-2);
        posList[6].set(6,-2);
        posList[7].set(7,-2);
    }
}