package edu.niu.cs.z1860050.flowercalculator;

public class Flower
{
  //these act as indexes for ColorData and SpeciesData objects
  private int species;
  private int color;
  private int hybridLevel;

  private SpeciesData speciesData = new SpeciesData();
  private ColorData colorData = new ColorData();

  //default constructor
  public Flower()
  {
    color = 0;
    species = 0;
    hybridLevel = 0;
  }

  //constructor with species and color specified as an index to colorData and speciesData
  public Flower(int newSpecies, int newColor, int newHybridLevel)
  {
    species = newSpecies;
    color = newColor;
    hybridLevel = newHybridLevel;
  }

  // returns the index of SpeciesData
  public int flowerSpecies()
  {
    return species;
  }

  // returns the index of ColorData
  public int flowerColor()
  {
    return color;
  }

  // returns the hybrid level
  public int flowerHybridLevel() { return hybridLevel; }

  public void setColor(int newColor)
  {
    color = newColor;
  }

  public void setSpecies(int newSpecies)
  {
    species = newSpecies;
  }

  public void setHybridLevel(int newHybridLevel) {hybridLevel = newHybridLevel; }

  // method to assist with printing
  // ex: "orange hyacinths", "red lilies", "blue roses"
  public String flowerName()
  {
    return colorData.get(this.flowerColor()) + " " + speciesData.get(this.flowerSpecies());
  }

}
