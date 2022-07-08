package edu.niu.cs.z1860050.flowercalculator;

// index into this for strings of colorvals
// colors are sorted by most to least frequently used by species
public class ColorData
{
  static public String colorValues[] =
      {"Red", "White", "Yellow", "Orange", "Pink", "Blue", "Purple", "Black", "Green"};

  //retrieve values from the array
  public String get(int i)
  {
    return colorValues[i];
  }
}
