package coding.guillermo.bmiapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    TextView weightMeasure,feetText,inchesText,centText;
    EditText feetInput, inchesInput,weightInput,centInput;
    Button metricButton, standardButton, calculateButton,trackerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // TextViews
        weightMeasure = (TextView) findViewById(R.id.weightMeasure);
        feetText = (TextView) findViewById(R.id.feetText);
        inchesText = (TextView) findViewById(R.id.inchesText);
        centText = (TextView) findViewById(R.id.centText);
        // Buttons
        metricButton = (Button) findViewById(R.id.metricButton);
        standardButton = (Button) findViewById(R.id.standardButton);
        calculateButton = (Button) findViewById(R.id.calculateButton);
        trackerButton = (Button) findViewById(R.id.trackerButton);
        // User inputs
        weightInput = (EditText) findViewById(R.id.weightInput);
        feetInput = (EditText) findViewById(R.id.feetInput);
        inchesInput = (EditText) findViewById(R.id.inchesInput);
        centInput = (EditText) findViewById(R.id.centInput);
        // Starting visibilities
        centInput.setVisibility(View.GONE);
        centText.setVisibility(View.GONE);
        standardButton.setVisibility(View.GONE);

        // Changing the layout from standard to metric measurements
        metricButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            metricButton.setVisibility(View.GONE);
            standardButton.setVisibility(View.VISIBLE);
            weightMeasure.setText("kg");
            weightInput.setText("");
            centInput.setVisibility(View.VISIBLE);
            centText.setVisibility(View.VISIBLE);
            feetInput.setText("");
            feetInput.setVisibility(View.GONE);
            feetText.setVisibility(View.GONE);
            inchesInput.setText("");
            inchesInput.setVisibility(View.GONE);
            inchesText.setVisibility(View.GONE);
            }
        });

        // Changing the layout from metric to standard measurements
        standardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                standardButton.setVisibility(View.GONE);
                metricButton.setVisibility(View.VISIBLE);
                weightMeasure.setText("lb");
                weightInput.setText("");
                centInput.setText("");
                centInput.setVisibility(View.GONE);
                centText.setVisibility(View.GONE);
                feetInput.setVisibility(View.VISIBLE);
                feetText.setVisibility(View.VISIBLE);
                inchesInput.setVisibility(View.VISIBLE);
                inchesText.setVisibility(View.VISIBLE);
            }
        });

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
         // Checking if there is a value in the corresponding EditText
         if(weightInput.getText().toString().length()!=0 && feetInput.getText().toString().length()!=0 && inchesInput.getText().toString().length()!=0
             ||weightInput.getText().toString().length()!=0 && centInput.getText().toString().length()!=0) {
             // Converting the measurements depending on the measurement inputs
             if (weightMeasure.getText().toString().equals("lb")) {
                 // Converting from feet and inches to just inches for calculation
                 double inches = (Double.parseDouble(feetInput.getText().toString()) * 12) + Double.parseDouble(inchesInput.getText().toString());
                 // Creating a Result object with the given inputs
                 Result result = new Result("", Double.parseDouble(weightInput.getText().toString()), inches, weightMeasure.getText().toString(), "");
                 // Starting the next activity
                 Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                 intent.putExtra("result", result);
                 startActivity(intent);
             } else if (weightMeasure.getText().toString().equals("kg")) {
                 // Converting from cm to inches
                 double inches = Double.parseDouble(centInput.getText().toString()) * .39370;
                 // Creating Result object with the give inputs
                 Result result = new Result("", Double.parseDouble(weightInput.getText().toString()), inches, weightMeasure.getText().toString(), "");
                 // Starting the next activity
                 Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                 intent.putExtra("result", result);
                 startActivity(intent);
             }
         }else{
             // Warning the user that an EditText has been left blank and cannot proceed
             Toast.makeText(getApplicationContext(),"Missing input",Toast.LENGTH_SHORT).show();
         }
            }
        });

        // Starting activity to display the saved BMI results from the SQLite database
        trackerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity3.class);
                startActivity(intent);
            }
        });

    }

}
