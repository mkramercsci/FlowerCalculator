package edu.niu.cs.z1860050.flowercalculator;

public class Pair {

    //flowers used in this particular pairing
    Flower parentA;
    Flower parentB;
    Flower child;

    //odds of producing the specified child
    double percentChance;

    //default constructor - this shouldn't get used
    //white rose 0 + white rose 0 = purple rose with 25% chance aka first pair listed in the chart
    public Pair()
    {
        parentA = new Flower(0,0,0);
        parentB = new Flower(0,0,0);
        child = new Flower(0,6,1);
        percentChance = 0.25;
    }

    //set the parents, child, and percent chance
    public Pair(Flower newA, Flower newB, Flower newChild, double newPercent)
    {
        parentA = newA;
        parentB = newB;
        child = newChild;
        percentChance = newPercent;
    }

    public String printPair()
    {
        return parentA.flowerName() + " + " + parentB.flowerName() + " = " + child.flowerName();
    }

    //I don't think this needs getters/setters? pairs don't change after the program launches
}