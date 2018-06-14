package creatures;

import huglife.*;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class Clorus extends Creature {

    public Clorus(double e){
        super("clorus");
        energy = e;
    }

    public Color color(){
        return new Color(34, 0, 231);
    }

    public void move(){
        energy -= 0.03;
    }

    public void attack(Creature c){
        energy += c.energy();
    }

    public Clorus replicate(){
        this.energy /= 2;
        return new Clorus(energy);
    }

    public void stay(){
        energy -= 0.01;
    }

    public Action chooseAction(Map<Direction, Occupant> neighbors){
        List<Direction> empties = getNeighborsOfType(neighbors, "empty");
        List<Direction> plips = getNeighborsOfType(neighbors, "plip");
        if (empties.isEmpty())
            return new Action(Action.ActionType.STAY);
        else if (!plips.isEmpty()){
            Direction moveDir = HugLifeUtils.randomEntry(plips);
            return new Action(Action.ActionType.ATTACK, moveDir);
        }
        else if (energy >= 1){
            Direction moveDir = HugLifeUtils.randomEntry(empties);
            return new Action(Action.ActionType.REPLICATE, moveDir);
        }
        Direction moveDir = HugLifeUtils.randomEntry(empties);
        return new Action(Action.ActionType.MOVE, moveDir);
    }
}
