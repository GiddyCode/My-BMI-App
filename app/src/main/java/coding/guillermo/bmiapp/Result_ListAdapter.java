package coding.guillermo.bmiapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Result_ListAdapter extends ArrayAdapter<Result> {
    private LayoutInflater layoutInflater;
    private ArrayList<Result> results;
    private int viewResourceId;

    public Result_ListAdapter(Context context, int textViewResourceId, ArrayList<Result> results){
        super(context,textViewResourceId,results);
        this.results = results;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewResourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parents){
        convertView = layoutInflater.inflate(viewResourceId,null);
        Result result = results.get(position);

        if(result!=null){
            TextView name = (TextView) convertView.findViewById(R.id.textName);
            TextView BMI = (TextView) convertView.findViewById(R.id.textBMI);
            TextView weight = (TextView) convertView.findViewById(R.id.textWeight);
            TextView date = (TextView) convertView.findViewById(R.id.textDate);

            if(name != null){
                name.setText(result.getName());
            }
            if(BMI != null){
                BMI.setText(Double.toString(result.getBMI()));
            }
            if(weight != null){
                weight.setText(Double.toString(result.getWeight())+" "+result.getWeightMeasurement());
            }
            if(date != null){
                date.setText(result.getDate());
            }
        }

        return convertView;
    }

}
