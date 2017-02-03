package coding.guillermo.bmiapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity2 extends AppCompatActivity {
    TextView resultText,bmiLabel,underWeightText,normalText,overweightText,obeseText;
    Button saveButton,trackerButton;
    Result result;
    EditText userName;
    DBhandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main2);
        // TextViews
        resultText = (TextView) findViewById(R.id.bmiResult);
        bmiLabel = (TextView) findViewById(R.id.bmiCategory);
        underWeightText = (TextView) findViewById(R.id.underweightText);
        normalText = (TextView) findViewById(R.id.normalText);
        overweightText = (TextView) findViewById(R.id.overweightText);
        obeseText = (TextView) findViewById(R.id.obeseText);
        // Button
        saveButton = (Button) findViewById(R.id.saveButton);
        trackerButton = (Button) findViewById(R.id.trackerButton2);
        // Getting User object from the previous activity
        result = (Result) getIntent().getParcelableExtra("result");
        // Database
        dbHandler = new DBhandler(this);

        // Displaying the arrow in the corresponding place
        ImageView arrow = new ImageView(this);
        arrow.setImageResource(R.drawable.arrow);
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.relativeLayout);

       // Displaying the arrow in the correct place depending on the user's screen specs
        float d = getApplicationContext().getResources().getDisplayMetrics().density;
        int margin = (int) (166 * d);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        arrow.setX(dpToPx(dpValue(result.getBMI())));
        arrow.setY(margin);
        rl.addView(arrow);

        // BMI diplay
        resultText.setText(Double.toString(result.getBMI()));
        bmiLabel.setText(result.getBmiCategory());
        // BMI category bold display
        bmiCategoryBold(result.getBMI());

        // Saving result to SQLite DB
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Displaying the dialog box for name input
                View view = (LayoutInflater.from(MainActivity2.this)).inflate(R.layout.alert_content,null);
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity2.this);
                alertBuilder.setView(view);
                userName = (EditText) view.findViewById(R.id.nameInput);
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                String date = dateFormat.format(new Date());
                result.setDate(date);
                alertBuilder.setCancelable(true).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                result.setName(userName.getText().toString());
                // Saving the result to SQLite database
                dbHandler.addResult(result);
                Toast toast = Toast.makeText(getApplicationContext(),"result saved",Toast.LENGTH_SHORT);
                toast.show();

                    }
                });
                AlertDialog dialog = alertBuilder.create();
                dialog.show();
                Button nButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                nButton.setBackgroundColor(getResources().getColor(R.color.toolBarColor));
                nButton.setTextColor(Color.WHITE);

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
    // Converting from dp to px for arrow display
    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
    // Bold display regarding the bmi result
    public  void bmiCategoryBold(double bmi){
        if(bmi < 18.50){
            underWeightText.setTypeface(null, Typeface.BOLD);
        }
        else if(bmi <= 24.99){
            normalText.setTypeface(null,Typeface.BOLD);
        }
        else if(bmi<=29.99){
            overweightText.setTypeface(null,Typeface.BOLD);
        }
        else{
            obeseText.setTypeface(null,Typeface.BOLD);
        }
    }
    // Arrow position display regarding the bmi result
    public int dpValue(double bmi){
    if(bmi<=13.50) {
        return 0;
    }
    else if(bmi<18.50){
        return 40;
    }
    else if(bmi<24.99) {
        return 133;
    }
    else if(bmi<30){
        return 222;
    }
    else{
        return 311;
    }
    }
}
