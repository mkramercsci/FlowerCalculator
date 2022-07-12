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

import java.sql.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
  private static final int NUM_COLORS = 9;
  private static final int NUM_SPECIES = 8;

  //on screen items to be connected
  TextView titleTV, mainTV;
  Button redBtn;
  Spinner speciesSpin;

  //the app starts with Red Roses. these index ColorData and SpeciesData
  private int selectedSpecies = 0;
  private int selectedColor = 0;

  //build all the color and species values
  SpeciesData speciesData = new SpeciesData();
  ColorData colorsData = new ColorData();

  //store all flowers that exist in the game
  ArrayList<Flower> flowers = new ArrayList<>();

  //store all flower parents + child + percent chance of child
  ArrayList<Pair> pairs = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //connect to the on screen textview
    titleTV = findViewById(R.id.titleTextView);
    mainTV = findViewById(R.id.mainTextView);

    //connect to the on screen button
    redBtn = findViewById(R.id.redButton);
    redBtn.setText("");

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
    buildAllPairs();

    //test that all the flowers were created appropriately
    mainTV.setText("total = " + flowers.size());

  }

  //build the listener which detects the user's spinner selection
  private AdapterView.OnItemSelectedListener spinnerListener = new AdapterView.OnItemSelectedListener()
  {
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
    {
      //update species to the newly selected value
      selectedSpecies = position;

      //update the title to reflect the selection
      updateTitle(selectedColor, position);

      //do something with position to display the pairs
      //updatePairs(position);
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
    // ROSES
    Pair rose0 = new Pair(flowers.get(1), flowers.get(1), flowers.get(8), 0.25);      //white 0 + white 0 = purple 1
    Pair rose1 = new Pair(flowers.get(1), flowers.get(3), flowers.get(2), 0.5);       //white 0 + yellow 0 = white 1
    Pair rose2 = new Pair(flowers.get(3), flowers.get(0), flowers.get(4), 0.5);       //yellow 0 + red 0 = orange 1
    Pair rose3 = new Pair(flowers.get(0), flowers.get(0), flowers.get(10), 0.25);     //red 0 + red 0 = black 3
    Pair rose4 = new Pair(flowers.get(0), flowers.get(0), flowers.get(6), 0.25);      //red 0 + red 0 = pink 1
    Pair rose5 = new Pair(flowers.get(8), flowers.get(2), flowers.get(9), 0.125);     //purple 1 + white 1 = purple 2
    Pair rose6 = new Pair(flowers.get(9), flowers.get(4), flowers.get(5), 0.125);     //purple 2 + orange 1 = orange 2
    Pair rose7 = new Pair(flowers.get(5), flowers.get(5), flowers.get(7), 0.0625);    //orange 2 + orange 2 = blue 3

    //add all the roses into the list of pairs
    pairs.add(rose0);
    pairs.add(rose1);
    pairs.add(rose2);
    pairs.add(rose3);
    pairs.add(rose4);
    pairs.add(rose5);
    pairs.add(rose6);
    pairs.add(rose7);

    // TULIPS
    Pair tulip8 = new Pair(flowers.get(11), flowers.get(13), flowers.get(15), 0.5);      //red 0 + yellow 0 = orange 1
    Pair tulip9 = new Pair(flowers.get(11), flowers.get(13), flowers.get(14), 0.5);      //red 0 + yellow 0 = yellow 1
    Pair tulip10 = new Pair(flowers.get(11), flowers.get(11), flowers.get(18), 0.125);   //red 0 + red 0 = black 3
    Pair tulip11 = new Pair(flowers.get(12), flowers.get(11), flowers.get(16), 0.5);     //white 0 + red 0 = pink 3
    Pair tulip12 = new Pair(flowers.get(15), flowers.get(14), flowers.get(17), 0.0625);  //orange 1 + yellow 1 = purple 3

    //add all the tulips to the list of pairs
    pairs.add(tulip8);
    pairs.add(tulip9);
    pairs.add(tulip10);
    pairs.add(tulip11);
    pairs.add(tulip12);

    // HYACINTH


  }

  //update the title with currently selected values
  public void updateTitle(int color, int species)
  {
    String newTitle = colorsData.get(color) + " " + speciesData.get(species);
    titleTV.setText(newTitle);
  }

  //method for testing that prints in mainTV
  //connected to the RED button
  public void changeText(View view)
  {
    String showThis = "";

    //print all the newly built pairs
/*    for (int i = 8; i <= 12; i++)
    {
      showThis += pairs.get(i).printPair() + "\n";
    }
*/
    //roses = 8
    //tulips = 7

    for(int i = 13; i <= 23; i++)
    {
      showThis += i + ": " + flowers.get(i).flowerName() + "\n";
    }

    mainTV.setText(showThis);

  }//end changeText

  //method to update the screen when a color button is clicked
  public void onClickColor(View view)
  {
    //we want to update two scrollviews:
      //list of pairs where child is the selected flower
      //list of pairs where one parent is the selected flower

    //scan a list of pairs for any occurance of selected flower as parents or child
  }

}// end MainActivity