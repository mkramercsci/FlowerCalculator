package edu.niu.cs.z1860050.flowercalculator;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
  TextView mainTV;
  Button button, redBtn;
  Spinner speciesSpin;

  private static final Integer NUM_COLORS = 9;
  private static final Integer NUM_SPECIES = 8;

  //build all the potential color and species values
  ColorData colorsData = new ColorData();
  SpeciesData speciesData = new SpeciesData();

  //holds all flowers that exist in the game
  ArrayList<Flower> flowers = new ArrayList<>();

  @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //connect to the on screen textview
    mainTV = findViewById(R.id.mainTextView);

    //connect to the on screen button
    redBtn = findViewById(R.id.redButton);
    redBtn.setText("Red");

    //connect to the on screen spinner
    speciesSpin = findViewById(R.id.speciesSpinner);

    //attach the adapter to the spinner
    ArrayAdapter<String> javaAdapter = new ArrayAdapter<>(this,
        R.layout.spinner_view, SpeciesData.speciesValues);

    //connect the adapter and spinner
    speciesSpin.setAdapter(javaAdapter);

    //set up the listener
    speciesSpin.setOnItemSelectedListener(spinnerListener);

    //make all the existing flower objects
    buildAllFlowers();

    //test that all the flowers were created appropriately
    mainTV.setText("total = " + flowers.size());

  }

  //build the listener which detects the user's spinner selection
  private AdapterView.OnItemSelectedListener spinnerListener = new AdapterView.OnItemSelectedListener()
  {
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
    {
      String selection;

      //get the selection from the spinner
      selection = adapterView.getItemAtPosition(position).toString();

      //selection contains a string of the species selected by the user. pass it somewhere
      //doSomething(selection);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView)
    { }
  };//end OnItemSelectedListener

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

    //does this species/color combo exist?
    boolean exists[][] = new boolean [NUM_SPECIES][NUM_COLORS];
    exists[0] = isRoses;
    exists[1] = isTulips;
    exists[2] = isHyacinths;
    exists[3] = isLilies;
    exists[4] = isCosmos;
    exists[5] = isMums;
    exists[6] = isWindflowers;
    exists[7] = isPansies;

    // hybrid matrix for roses
    Integer hybridRoses[][] = new Integer[][] {
        new Integer[] {0},    //red
        new Integer[] {0, 1}, //white
        new Integer[] {0},    //yellow
        new Integer[] {1, 2}, //orange
        new Integer[] {3},    //pink
        new Integer[] {3},    //blue
        new Integer[] {1, 2}, //purple
        new Integer[] {3},    //black
        new Integer[] {null}  //green
    };

    //traverse the species/color matrix and build only the flowers that exist in the game
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

  //test function to prove all the flowers built successfully
  //currently not connected to any button
  public void changeText(View view)
  {
    String showThis = "";

    for (int i = 0; i < flowers.size(); i++)
    {
      //retrieve all tulips
      if (flowers.get(i).flowerSpecies() == 1)
      {
        showThis += flowers.get(i).flowerName() + "\n";
      }
    }

    //display the flowers
    mainTV.setText(showThis);
  }//end changeText

  //method to update the screen when a color button is clicked
  public void onClickColor(View view)
  {
    //we want to update two scrollviews:
      //list of ways to obtain selected flower
      //list of recipes involving selected flower

    //find out which flower we want
      //make a determineSelected method?

    //check the list of recipes where result = currentFlower
      //use a boolean flag to check seedbag colors, hybrids may have multiple recipes

    //check the list of recipes where parent A or B = currentFlower
      //indicate hybrid level with a number 0-3
      // 3 = seedbag
      // 2 = blue hybrid
      // 1 = yellow hybrid
      // 0 = final hybrid

  }

}// end MainActivity