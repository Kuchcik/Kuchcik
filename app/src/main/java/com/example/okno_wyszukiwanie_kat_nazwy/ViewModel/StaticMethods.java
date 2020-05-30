package com.example.okno_wyszukiwanie_kat_nazwy.ViewModel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.TextView;

import com.example.okno_wyszukiwanie_kat_nazwy.Model.Category;
import com.example.okno_wyszukiwanie_kat_nazwy.Model.Country;
import com.example.okno_wyszukiwanie_kat_nazwy.Model.ProductModel;
import com.example.okno_wyszukiwanie_kat_nazwy.Model.TimerModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class StaticMethods {

    public static ArrayList<String> getCategoryNames(ArrayList<Category> source) {
        ArrayList<String> output = new ArrayList<>();

        for(int i = 0; i < source.size(); i++)
        {
            output.add(source.get(i).getCategoryName());
        }

        return output;
    }
    public static ArrayList<String> getProductNames(ArrayList<ProductModel> source) {
        ArrayList<String> output = new ArrayList<>();

        for(int i = 0; i < source.size(); i++)
        {
            output.add(source.get(i).getProductName());
        }

        return output;
    }
    public static ArrayList<String> getQuantity(ArrayList<ProductModel> source) {
        ArrayList<String> output = new ArrayList<>();

        for(int i = 0; i < source.size(); i++)
        {
            output.add(source.get(i).getQuantity());
        }

        return output;
    }
    public static ArrayList<String> getTimerNames(ArrayList<TimerModel> source) {
        ArrayList<String> output = new ArrayList<>();

        for(int i = 0; i < source.size(); i++)
        {
            output.add(source.get(i).getTimerName());
        }

        return output;
    }
    public static ArrayList<String> getTimerTimes(ArrayList<TimerModel> source) {
        ArrayList<String> output = new ArrayList<>();

        for(int i = 0; i < source.size(); i++)
        {
            output.add(source.get(i).getTimerTime());
        }

        return output;
    }


    public static ArrayList<String> getCategoryImages(ArrayList<Category> source) {
        ArrayList<String> output = new ArrayList<>();

        for(int i = 0; i < source.size(); i++)
        {
            output.add(source.get(i).getCategoryImage());
        }

        return output;
    }

    public static ArrayList<String> getCountryNames(ArrayList<Country> source) {
        ArrayList<String> output = new ArrayList<>();

        for(int i = 0; i < source.size(); i++)
        {
            output.add(source.get(i).getCountryName());
        }

        return output;
    }

    public static ArrayList<String> getRecipeIngredients(JSONArray sourceArray, JSONObject sourceObject) throws JSONException {
        ArrayList<String> output = new ArrayList<>();

        for(int i = 1; i <= 20; i++)
        {
            output.add(sourceObject.getString("strIngredient" + i));
        }

        return output;
    }

    public static ArrayList<String> getRecipeMeasures(JSONArray sourceArray, JSONObject sourceObject) throws JSONException {
        ArrayList<String> output = new ArrayList<>();

        for(int i = 1; i <= 20; i++)
        {
            output.add(sourceObject.getString("strMeasure" + i));
        }

        return output;
    }

    public static ArrayList<String> removeEmptyStringsAndNulls(ArrayList<String> source) {
        source.removeAll(Arrays.asList("", "null", " "));
        return source;
    }

    public static void displayArrayListInTextView(String separator, ArrayList<String> source, TextView textView) {
        StringBuilder stringBuilder = new StringBuilder();
        for(String s : source) {
            if(s == "")
            {
                stringBuilder.append(s + "\n");
            }
            else
            {
                stringBuilder.append(separator + s + "\n");
            }

        }
        textView.setText(stringBuilder.toString());
    }

    public static int getScreenWidth() {
       return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public static String convertStringArrayListToString(ArrayList<String> source) {
        String output = "";

        for(String s : source) {
            output += s + ",";
        }

        return output;
    }

    public static ArrayList<String> convertStringToArrayList(String source) {
        ArrayList<String> output = new ArrayList<>(Arrays.asList(source.split(",")));
        return output;
    }

    public static void setAlertDialog(final Activity activity, String title, String message, String buttonText) {
        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, buttonText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        activity.finish();
                    }
                });
        alertDialog.show();
    }
}
