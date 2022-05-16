package edu.niu.cs.z1860050.flowercalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
  TextView mainTV;
  Button button;

  private static final Integer NUM_COLORS = 9;
  private static final Integer NUM_SPECIES = 8;

  //build all the potential color and species values
  ColorData colorsData = new ColorData();
  SpeciesData speciesData = new SpeciesData();

  ArrayList<Flower> flowers = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //connect to the on screen textview
    mainTV = findViewById(R.id.mainTextView);

    //connect to the on screen button
    button = findViewById(R.id.button);

    //insert the values into their containers
    buildAllFlowers();

    mainTV.setText("total = " + flowers.size());
  }

  //method to build all the valid flowers in the game
  public void buildAllFlowers()
  {
    // gold roses not included because they don't involve breeding

    //color order: red white yellow orange pink blue purple black green
    //species order: roses tulips hyacinths lilies cosmos mums windflowers pansies
    boolean isRoses[] = {true, true, true, true, true, true, true, true, false};
    boolean isTulips[] = {true, true, true, true, true, false, true, true, false};
    boolean isHyacinths[] = {true, true, true, true, true, true, true, false, false};
    boolean isLilies[] = {true, true, true, true, true, false, false, true, false};
    boolean isCosmos[] = {true, true, true, true, true, false, false, true, false};
    boolean isMums[] = {true, true, true, false, true, false, true, false, true};
    boolean isWindflowers[] = {true, true, false, true, true, true, true, false, false};
    boolean isPansies[] = {true, true, true, true, false, true, true, false, false};

    boolean exists[][] = new boolean [NUM_SPECIES][NUM_COLORS];
    exists[0] = isRoses;
    exists[1] = isTulips;
    exists[2] = isHyacinths;
    exists[3] = isLilies;
    exists[4] = isCosmos;
    exists[5] = isMums;
    exists[6] = isWindflowers;
    exists[7] = isPansies;

    //traverse the species/color matrix in order and build only the flowers that exist in the game
    for (int currSpecies = 0; currSpecies < NUM_SPECIES; currSpecies++)
    {
      for (int currColor = 0; currColor < NUM_COLORS; currColor++)
      {
        //we found a flower which exists, so add it into the list
        if (exists[currSpecies][currColor])
        {
          Flower flower = new Flower(currSpecies, currColor);
          flowers.add(flower);
        }
      }//end inner for
    }//end outer for
  }//end buildAllFlowers

  public void changeText(View view)
  {
    String showThis = "";

    for (int i = 0; i < flowers.size(); i++)
    {
      //retrieve all roses
      if (flowers.get(i).flowerSpecies() == 0)
      {
        showThis += flowers.get(i).flowerName() + "\n";
      }
    }

    //display the flowers
    mainTV.setText(showThis);
  }
}