package coding.guillermo.bmiapp;

import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity3 extends AppCompatActivity {
    ArrayList<Result> bmiResults = new ArrayList<>();
    ListView listView;
    Result_ListAdapter adapter;
    Result result;
    DBhandler dbHandler;
    TextView resultHeading,bmiResult,bmiCategory,underweightText,normalText,overweightText,obeseText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewcontents_layout);
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View bmiInfo = (LayoutInflater.from(MainActivity3.this)).inflate(R.layout.bmi_info,null);
                resultHeading = (TextView) bmiInfo.findViewById(R.id.resultHeading);
                bmiResult = (TextView) bmiInfo.findViewById(R.id.bmiResult);
                bmiCategory = (TextView) bmiInfo.findViewById(R.id.bmiCategory);
                underweightText = (TextView) bmiInfo.findViewById(R.id.underweightText);
                normalText = (TextView) bmiInfo.findViewById(R.id.normalText);
                overweightText = (TextView) bmiInfo.findViewById(R.id.overweightText);
                obeseText = (TextView) bmiInfo.findViewById(R.id.obeseText);
                Result resultInfo = bmiResults.get(position);
                resultHeading.setText(resultInfo.getName()+"'s BMI result");
                bmiResult.setText(Double.toString(resultInfo.getBMI()));
                bmiCategory.setText(resultInfo.getBmiCategory());
                // Displaying the arrow in the corresponding place
                ImageView arrow = new ImageView(getApplicationContext());
                arrow.setImageResource(R.drawable.arrow);
                RelativeLayout rl = (RelativeLayout) bmiInfo.findViewById(R.id.relativeLayout);
                // displaying the arrow in the correct place depending on the user's screen specs
                float d = getApplicationContext().getResources().getDisplayMetrics().density;
                int yValue = (int) (159 * d);
                DisplayMetrics metrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metrics);
                arrow.setX(dpToPx(dpValue(resultInfo.getBMI())));
                arrow.setY(yValue);
                rl.addView(arrow);

                bmiCategoryBold(resultInfo.getBMI());
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity3.this);
                alertBuilder.setView(bmiInfo);

                AlertDialog dialog = alertBuilder.create();
                dialog.show();
            }
        });

        // Database adapter
        dbHandler = new DBhandler(this);
        Cursor data = dbHandler.getListContents();
        // Obtaining the saved bmi results from the SQLite database
        while(data.moveToNext()){
            result = new Result(data.getString(1),data.getDouble(2),data.getDouble(3),data.getString(4),data.getString(5));
            result.setId(data.getInt(0));
            bmiResults.add(result);
        }
        // Reversing the ArrayList for display
        Collections.reverse(bmiResults);
        adapter = new Result_ListAdapter(this,R.layout.list_adapter_view,bmiResults);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(final ContextMenu menu, final View v, final ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu,v,menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_context,menu);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Result result = bmiResults.get(info.position);
        bmiResults.remove(info.position);
        adapter.notifyDataSetChanged();
        return dbHandler.deleteResult(result);
    }
    public  void bmiCategoryBold(double bmi){
        if(bmi < 18.50){
            underweightText.setTypeface(null, Typeface.BOLD);
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
            return 33;
        }
        else if(bmi<24.99) {
            return 120;
        }
        else if(bmi<30){
            return 198;
        }
        else{
            return 285;
        }
    }
    // Converting from dp to px for arrow display
    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

}
