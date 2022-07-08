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

  //method to build all the flowers in the game
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

    //for every valid flower, we move one row down the hybrids array
    //and use that row to set hybrid level when build the flower(s)
    //'gold' and 'blue' refer to star colors from the original chart, not flower color itself
    // 0 = flower from seeds
    // 1 = gold hybrid
    // 2 = blue hybrid  (only roses use this val)
    // 3 = final hybrid
    Integer hybrids[][] = new Integer[][] {
        // ROSES
        new Integer[] {0},    //red
        new Integer[] {0, 1}, //white
        new Integer[] {0},    //yellow
        new Integer[] {1, 2}, //orange
        new Integer[] {3},    //pink
        new Integer[] {3},    //blue
        new Integer[] {1, 2}, //purple
        new Integer[] {3},    //black
        // TULIPS
        new Integer[] {0},    //red
        new Integer[] {0},    //white
        new Integer[] {0, 1}, //yellow
        new Integer[] {1},    //orange
        new Integer[] {3},    //pink
        new Integer[] {3},    //purple
        new Integer[] {3},    //black
        //HYACINTHS
        new Integer[] {0},    //red
        new Integer[] {0},    //white
        new Integer[] {0, 1}, //yellow
        new Integer[] {1},    //orange
        new Integer[] {3},    //pink
        new Integer[] {3},    //blue
        new Integer[] {3},    //purple
        //LILIES - white lily isn't used for any crossbreeding
        new Integer[] {0},    //red
        new Integer[] {0},    //white
        new Integer[] {0},    //yellow
        new Integer[] {3},    //orange
        new Integer[] {3},    //pink
        new Integer[] {3},    //black
        //COSMOS
        new Integer[] {0},    //red
        new Integer[] {0},    //white
        new Integer[] {0},    //yellow
        new Integer[] {1},    //orange
        new Integer[] {3},    //pink
        new Integer[] {3},    //black
        //MUMS
        new Integer[] {0},    //red
        new Integer[] {0},    //white
        new Integer[] {0, 1}, //yellow
        new Integer[] {3},    //pink
        new Integer[] {1},    //purple
        new Integer[] {3},    //green
        //WINDFLOWERS
        new Integer[] {0, 1}, //red
        new Integer[] {0},    //white
        new Integer[] {0},    //orange
        new Integer[] {3},    //pink
        new Integer[] {1},    //blue
        new Integer[] {3},    //purple
        //PANSIES
        new Integer[] {0, 1}, //red
        new Integer[] {0},    //white
        new Integer[] {0},    //yellow
        new Integer[] {3},    //orange
        new Integer[] {1},    //blue
        new Integer[] {3},    //purple
    };

    //keeps track of current row in the hybrid matrix
    int currHybrid = 0;

    //loop through all species
    for (int currSpecies = 0; currSpecies < NUM_SPECIES; currSpecies++)
    {
      //loop through all colors
      for (int currColor = 0; currColor < NUM_COLORS; currColor++)
      {
        //we found a flower which exists
        if (exists[currSpecies][currColor])
        {
          //loop through the current hybrids row and make a flower for each val
          //hybrids[3] = {1, 2} makes two orange roses with hybrid levels 1 and 2
          for (int i = 0; i < hybrids[currHybrid].length; i++)
          {
            //i represents the current integer within the hybrid row
            //hybrids[1][0] = orange rose level 1
            //hybrids[1][1] = orange rose level 2
            Flower flower = new Flower(currSpecies, currColor, hybrids[currHybrid][i]);
            flowers.add(flower);
          }

          //move to the next existing flower in the hybrid matrix
          currHybrid++;
        }//end if exists
      }//end inner for
    }//end outer for

  }//end buildAllFlowers

  // method to build all flower pairs
  public void buildAllPairs()
  {
    Pair a = new Pair();
  }

  //method for testing that prints in the main textview
  //currently connected to the RED button
  public void changeText(View view)
  {
    String showThis = "";

    for (int i = 0; i < flowers.size(); i++)
    {
      //retrieve all flowers of a certain species
      if (flowers.get(i).flowerSpecies() == 7)
      {
        showThis += flowers.get(i).flowerName() + " " + flowers.get(i).flowerHybridLevel() + "\n";
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

    //scan a list of recipes for any occurance of selected flower as parents or child
  }
}// end MainActivity