package edu.niu.cs.z1860050.flowercalculator;

public class SpeciesData
{
  static public String speciesValues[] =
      {"Roses", "Tulips", "Hyacinths", "Lilies", "Cosmos", "Mums", "Windflowers", "Pansies"};

  //retrieve values from the array
  public String get(int i)
  {
    return speciesValues[i];
  }
}