package formations;

import beings.CalabashBrother;
import beings.Grandfather;
import gui.BeingImage;
import gui.EntityWithSemaphore;
import gui.MagicBallImage;
import javafx.scene.image.ImageView;
import org.junit.Test;
import other.CalabashGroup;

import static org.junit.Assert.*;

public class FormationTest {

    @Test
    public void formationCreatrue() {
        Battlefield battlefield = new Battlefield();
        Grandfather grandfather = new Grandfather();

        CalabashBrother[] calabashList = new CalabashBrother[7];
        for(int i = 0; i < calabashList.length; i++){
            calabashList[i] = new CalabashBrother(i);
        }
        CalabashGroup calabashGroup = new CalabashGroup(calabashList);
        new LongSnake().formationCreatrue(battlefield, calabashGroup.getFormationCreatrue(), grandfather);
        new BowandArrow().formationCreatrue(battlefield, calabashGroup.getFormationCreatrue(), grandfather);
        new CraneWing().formationCreatrue(battlefield, calabashGroup.getFormationCreatrue(), grandfather);
        new CrescentMoon().formationCreatrue(battlefield, calabashGroup.getFormationCreatrue(), grandfather);
        new FishSquama().formationCreatrue(battlefield, calabashGroup.getFormationCreatrue(), grandfather);
        new GeeseFlyShape().formationCreatrue(battlefield, calabashGroup.getFormationCreatrue(), grandfather);
        new SquareCircle().formationCreatrue(battlefield, calabashGroup.getFormationCreatrue(), grandfather);
        new XShape().formationCreatrue(battlefield, calabashGroup.getFormationCreatrue(), grandfather);
    }
}