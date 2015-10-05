package us.idinfor.hemophilia.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import us.idinfor.hemophilia.R;
import us.idinfor.hemophilia.Utils;
import us.idinfor.hemophilia.asynctask.SaveInfusionAsyncTask;
import us.idinfor.hemophilia.model.Infusion;

public class EditInfusionActivity extends BaseActivity {

    public static final String EXTRA_INFUSION = "extra_infusion";

    Toolbar mToolbar;
    ProgressBar mProgressBar;
    Spinner mMedication;
    EditText mDose;
    EditText mLotNumber;
    static TextView mTime;
    static TextView mDate;
    static Calendar infusionTime;
    EditText mDescription;

    boolean disableSaveBtn;
    Infusion infusion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_infusion);
        initUI();
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, EditInfusionActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }

    public static void launch(Activity activity, us.idinfor.hemophilia.model.Infusion infusion) {
        Intent intent = new Intent(activity, EditInfusionActivity.class);
        intent.putExtra(EXTRA_INFUSION,infusion);
        ActivityCompat.startActivity(activity, intent, null);
    }

    private void initUI(){
        mProgressBar = (ProgressBar)findViewById(R.id.progressBar);
        mMedication = (Spinner)findViewById(R.id.medicationSpinner);
        mDose = (EditText)findViewById(R.id.doseEdit);
        mLotNumber = (EditText)findViewById(R.id.lotNumberEdit);
        mDescription = (EditText)findViewById(R.id.descriptionEdit);
        mTime = (TextView)findViewById(R.id.timeTV);
        mDate = (TextView)findViewById(R.id.dateTV);
        infusionTime = Calendar.getInstance();

        if(getIntent().getExtras() == null){
            //Add mode
            mToolbar = buildActionBarToolbar(getString(R.string.title_activity_add_infusion), true);
            mTime.setText(getString(R.string.time_string, infusionTime.get(Calendar.HOUR_OF_DAY), String.format("%02d", infusionTime.get(Calendar.MINUTE))));
            mTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new TimePickerFragment().show(getSupportFragmentManager(), "timePicker");
                }
            });

            mDate.setText(getString(R.string.date_string,
                    infusionTime.get(Calendar.DAY_OF_MONTH),
                    infusionTime.get(Calendar.MONTH)+1,
                    infusionTime.get(Calendar.YEAR)));
            mDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DatePickerFragment().show(getSupportFragmentManager(),"datePicker");
                }
            });

        }else{
            //Edit mode
            mToolbar = buildActionBarToolbar(getString(R.string.title_activity_edit_infusion),true);
            infusion = getIntent().getParcelableExtra(EXTRA_INFUSION);
            if(infusion != null){
                loadInfusion();
            }
        }
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mTime.setText(getString(R.string.time_string,hourOfDay,String.format("%02d",minute)));
            infusionTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            infusionTime.set(Calendar.MINUTE,minute);
        }
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            mDate.setText(getString(R.string.date_string,day,String.format("%02d",month+1),year));
            infusionTime.set(Calendar.YEAR, year);
            infusionTime.set(Calendar.MONTH, month);
            infusionTime.set(Calendar.DAY_OF_MONTH,day);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_infusion, menu);
        menu.findItem(R.id.action_save).setEnabled(!disableSaveBtn);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_save:
                saveInfusion();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void resetForm() {
        mDose.setText(null);
        mLotNumber.setText(null);
        mDescription.setText(null);
    }

    private void saveInfusion() {
        // Reset errors.
        mDose.setError(null);
        mLotNumber.setError(null);
        mDescription.setError(null);

        String dose = mDose.getText().toString();
        String lotNumber = mLotNumber.getText().toString();
        String description = mDescription.getText().toString();

        boolean cancel = false;
        View focusView = null;

        //Check for valid dose
        if(TextUtils.isEmpty(dose) || Integer.valueOf(dose) <= 0){
            mDose.setError(getString(R.string.error_invalid_dose));
            focusView = mDose;
            cancel = true;
        }

        //Check for valid lot number
        if(TextUtils.isEmpty(lotNumber)){
            mLotNumber.setError(getString(R.string.error_invalid_lot_number));
            focusView = mLotNumber;
            cancel = true;
        }

        //Check for valid description
        if(TextUtils.isEmpty(description)){
            mDescription.setError(getString(R.string.error_invalid_description));
            focusView = mDescription;
            cancel = true;
        }

        if(cancel){
            focusView.requestFocus();
        }else{
            if(infusion == null){
                infusion = new Infusion();
            }
            infusion.setDeviceId(Utils.getUDID(this));
            infusion.setMedication(mMedication.getSelectedItem().toString());
            infusion.setDose(Integer.valueOf(dose));
            infusion.setLotNumber(lotNumber);
            infusion.setTime(infusionTime.getTime());
            infusion.setDescription(description);
            new SaveInfusionAsyncTask(infusion.getBackendInfusion()){
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    mProgressBar.setVisibility(View.VISIBLE);
                    disableSaveBtn = true;
                    //Disable save button to avoid double click
                    invalidateOptionsMenu();
                    Utils.hideKeyboard(mToolbar);
                }

                @Override
                protected void onPostExecute(us.idinfor.hemophilia.backend.infusionApi.model.Infusion infusion) {
                    super.onPostExecute(infusion);
                    mProgressBar.setVisibility(View.GONE);
                    disableSaveBtn = false;
                    invalidateOptionsMenu();
                    if(infusion != null){
                        finish();
                    }else{
                        Snackbar.make(mToolbar, getString(R.string.error_item_not_saved, getString(R.string.infusion)), Snackbar.LENGTH_LONG).show();
                    }
                }
            }.execute();
        }
    }

    private void loadInfusion(){
        mMedication.setSelection(Utils.getSpinnerIndex(mMedication, infusion.getMedication()));
        mDose.setText(infusion.getDose().toString());
        mLotNumber.setText(infusion.getLotNumber());
        infusionTime.setTimeInMillis(infusion.getTime().getTime());
        mDate.setText(getString(R.string.date_string,
                infusionTime.get(Calendar.DAY_OF_MONTH),
                String.format("%02d", infusionTime.get(Calendar.MONTH) + 1),
                infusionTime.get(Calendar.YEAR)));
        mTime.setText(getString(R.string.time_string,
                infusionTime.get(Calendar.HOUR_OF_DAY),
                String.format("%02d", infusionTime.get(Calendar.MINUTE))));
        mDescription.setText(infusion.getDescription());
    }
}
