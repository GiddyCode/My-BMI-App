package coding.guillermo.bmiapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Result implements Parcelable{
    private long id;
    private String name;
    private double weight;
    private double height;
    private String date;
    private String weightMeasurement;

    public Result(String name, double weight, double height,String weightMeasurement, String date){
        this.name=name;
        this.weight=weight;
        this.height=height;
        this.date=date;
        this.weightMeasurement = weightMeasurement;
    }
    @Override
    public int describeContents(){
        return 0;
    }
    @Override
    public void writeToParcel(Parcel parcel, int i){
        parcel.writeString(this.name);
        parcel.writeDouble(this.weight);
        parcel.writeDouble(this.height);
        parcel.writeString(this.weightMeasurement);
    }
    public Result(Parcel parcel){
        this.name=parcel.readString();
        this.weight=parcel.readDouble();
        this.height=parcel.readDouble();
        this.weightMeasurement = parcel.readString();
    }
    public static final Creator<Result> CREATOR= new Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel parcel) {
            return new Result(parcel);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[0];
        }
    };
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getWeight() {
        return weight;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }
    public double getHeight() {
        return height;
    }
    public void setHeight(double height){
        this.height=height;
    }
    public long getId(){
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getDate(){
        return date;
    }
    public void setDate(String date){
        this.date = date;
    }
    public String getWeightMeasurement(){
        return weightMeasurement;
    }
    public void setWeightMeasurement(String weightMeasurement){
        this.weightMeasurement = weightMeasurement;
    }
    // Calculating BMI
    public double getBMI(){
      double bmi=0;
    if(weightMeasurement.equals("lb")){
        bmi = (getWeight()/Math.pow(getHeight(),2)*703);
        bmi = (Math.round(bmi*100))/100.0;
        return bmi;
    }
    else if(weightMeasurement.equals("kg")){
        // converting from kg to lb
        double pounds = getWeight() * 2.2046;
        pounds = Math.round(pounds * 100) / 100.0;
        bmi = (pounds/Math.pow(getHeight(),2)*703);
        bmi = (Math.round(bmi*100))/100.0;
        return bmi;
        }
    return bmi;
    }
    public String getBmiCategory(){
        if(getBMI() < 18.50){
            return "Underweight";
        }
        else if(getBMI() <= 24.99){
            return "Normal weight";
        }
        else if(getBMI()<=29.99){
            return "Overweight";
        }
        else{
            return "Obese";
        }
    }
    @Override
    public String toString(){
        return name+" "+getWeight()+" "+getHeight()+" "+getWeightMeasurement()+" "+getDate();
    }
}
