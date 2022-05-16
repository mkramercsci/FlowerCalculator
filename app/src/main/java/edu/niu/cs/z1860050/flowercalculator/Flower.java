package edu.niu.cs.z1860050.flowercalculator;

public class Flower
{
  //these act as indexes for ColorData and SpeciesData objects
  private int species;
  private int color;

  private ColorData colorData = new ColorData();
  private SpeciesData speciesData = new SpeciesData();

  public Flower()
  {
    color = 0;
    species = 0;
  }

  public Flower(int newSpecies, int newColor)
  {
    species = newSpecies;
    color = newColor;
  }

  //returns the index of SpeciesData
  public int flowerSpecies()
  {
    return species;
  }

  //returns the index of ColorData
  public int flowerColor()
  {
    return color;
  }

  public void setColor(int color)
  {
    this.color = color;
  }

  public void setSpecies(int species)
  {
    this.species = species;
  }

  // return color + species to assist with printing
  // ex: "orange hyacinths", "red lilies", "blue roses"
  public String flowerName()
  {
    return colorData.get(this.flowerColor()) + " " + speciesData.get(this.flowerSpecies());
  }

}
