package us.idinfor.hemophilia;


import android.content.Context;
import android.provider.Settings;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Spinner;

public class Utils {

    public static void hideKeyboard(final View aView){
        aView.post(new Runnable() {
            @Override
            public void run(){

                InputMethodManager inputManager = (InputMethodManager) aView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(aView.getApplicationWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
    }

    public static String getUDID(Context context){
        return Settings.Secure.getString(context.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static int getSpinnerIndex(Spinner spinner, String value)
    {
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(value)){
                index = i;
                break;
            }
        }
        return index;
    }
}
