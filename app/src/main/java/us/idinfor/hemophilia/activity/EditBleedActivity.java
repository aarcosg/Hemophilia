package us.idinfor.hemophilia.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import us.idinfor.hemophilia.asynctask.SaveBleedAsyncTask;
import us.idinfor.hemophilia.model.Bleed;

public class EditBleedActivity extends BaseActivity {

    public static final String EXTRA_BLEED = "extra_bleed";

    Toolbar mToolbar;
    ProgressBar mProgressBar;
    Spinner mMedication;
    EditText mDose;
    EditText mLotNumber;
    static TextView mTime;
    static TextView mDate;
    static Calendar bleedTime;
    EditText mDescription;
    Spinner mReason;
    Spinner mSeverity;
    Spinner mBodyPart;

    boolean disableSaveBtn;
    Bleed bleed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bleed);
        initUI();
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, EditBleedActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }

    public static void launch(Activity activity, us.idinfor.hemophilia.model.Bleed bleed) {
        Intent intent = new Intent(activity, EditBleedActivity.class);
        intent.putExtra(EXTRA_BLEED,bleed);
        ActivityCompat.startActivity(activity, intent, null);
    }


    private void initUI(){
        mToolbar = buildActionBarToolbar(getString(R.string.title_activity_add_bleed), true);
        mProgressBar = (ProgressBar)findViewById(R.id.progressBar);
        mMedication = (Spinner)findViewById(R.id.medicationSpinner);
        mDose = (EditText)findViewById(R.id.doseEdit);
        mLotNumber = (EditText)findViewById(R.id.lotNumberEdit);
        mDescription = (EditText)findViewById(R.id.descriptionEdit);
        mTime = (TextView)findViewById(R.id.timeTV);
        mDate = (TextView)findViewById(R.id.dateTV);
        mReason = (Spinner)findViewById(R.id.bleedReasonSpinner);
        mSeverity = (Spinner)findViewById(R.id.bleedSeveritySpinner);
        mBodyPart = (Spinner)findViewById(R.id.bodyPartSpinner);
        bleedTime = Calendar.getInstance();

        mTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerFragment().show(getSupportFragmentManager(), "timePicker");
            }
        });

        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerFragment().show(getSupportFragmentManager(),"datePicker");
            }
        });

        if(getIntent().getExtras() != null && getIntent().hasExtra(EXTRA_BLEED)){
            //Edit bleed mode
            mToolbar = buildActionBarToolbar(getString(R.string.title_activity_edit_bleed),true);
            bleed = getIntent().getParcelableExtra(EXTRA_BLEED);
            if(bleed != null){
                loadBleed();
            }
        }else{
            //Add bleed mode
            mToolbar = buildActionBarToolbar(getString(R.string.title_activity_add_bleed),true);
            mTime.setText(getString(R.string.time_string, bleedTime.get(Calendar.HOUR_OF_DAY), String.format("%02d", bleedTime.get(Calendar.MINUTE))));
            mDate.setText(getString(R.string.date_string,
                    bleedTime.get(Calendar.DAY_OF_MONTH),
                    bleedTime.get(Calendar.MONTH) + 1,
                    bleedTime.get(Calendar.YEAR)));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_bleed, menu);
        menu.findItem(R.id.action_save).setEnabled(!disableSaveBtn);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_save:
                saveBleed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener{

        @Override
        public Dialog onCreateDialog(@NonNull Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            return new TimePickerDialog(getActivity(),this,hour,minute, DateFormat.is24HourFormat(getActivity()));
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mTime.setText(getString(R.string.time_string,hourOfDay,String.format("%02d",minute)));
            bleedTime.set(Calendar.HOUR_OF_DAY,hourOfDay);
            bleedTime.set(Calendar.MINUTE,minute);

        }
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener{


        @Override
        public Dialog onCreateDialog(@NonNull Bundle savedInstanceState) {
            Calendar c = Calendar.getInstance();
            int day = c.get(Calendar.DAY_OF_MONTH);
            int month = c.get(Calendar.MONTH);
            int year = c.get(Calendar.YEAR);

            return new DatePickerDialog(getActivity(),this,year,month,day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            mDate.setText(getString(R.string.date_string,day,String.format("%02d",month+1),year));
            bleedTime.set(Calendar.YEAR, year);
            bleedTime.set(Calendar.MONTH, month);
            bleedTime.set(Calendar.DAY_OF_MONTH,day);
        }
    }

    private void loadBleed(){
        mMedication.setSelection(Utils.getSpinnerIndex(mMedication, bleed.getMedication()));
        mDose.setText(String.format("%s", bleed.getDose()));
        mLotNumber.setText(bleed.getLotNumber());
        bleedTime.setTimeInMillis(bleed.getTime().getTime());
        mDate.setText(getString(R.string.date_string,
                bleedTime.get(Calendar.DAY_OF_MONTH),
                String.format("%02d", bleedTime.get(Calendar.MONTH) + 1),
                bleedTime.get(Calendar.YEAR)));
        mTime.setText(getString(R.string.time_string,
                bleedTime.get(Calendar.HOUR_OF_DAY),
                String.format("%02d", bleedTime.get(Calendar.MINUTE))));
        mDescription.setText(bleed.getDescription());
        mReason.setSelection(Utils.getSpinnerIndex(mReason, bleed.getReason()));
        mSeverity.setSelection(Utils.getSpinnerIndex(mSeverity,bleed.getSeverity()));
        mBodyPart.setSelection(Utils.getSpinnerIndex(mBodyPart, bleed.getBodyPart()));
    }


    private void saveBleed() {
        mDose.setError(null);
        mLotNumber.setError(null);
        mDescription.setError(null);

        String dose = mDose.getText().toString();
        String lotNumber = mLotNumber.getText().toString();
        String description = mDescription.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if(TextUtils.isEmpty(dose) || Integer.valueOf(dose) <= 0){
            mDose.setError(getString(R.string.error_invalid_dose));
            focusView = mDose;
            cancel = true;
        }

        if(TextUtils.isEmpty(lotNumber)){
            mLotNumber.setError(getString(R.string.error_invalid_lot_number));
            focusView = mLotNumber;
            cancel = true;
        }

        if(TextUtils.isEmpty(description)){
            mDescription.setError(getString(R.string.error_invalid_description));
            focusView = mDescription;
            cancel = true;
        }


        if(cancel){
            focusView.requestFocus();
        }else{
            if(bleed == null){
                bleed = new Bleed();
            }
            bleed.setDeviceId(Utils.getUDID(this));
            bleed.setMedication(mMedication.getSelectedItem().toString());
            bleed.setDose(Integer.valueOf(dose));
            bleed.setLotNumber(lotNumber);
            bleed.setTime(bleedTime.getTime());
            bleed.setDescription(description);
            bleed.setReason(mReason.getSelectedItem().toString());
            bleed.setSeverity(mSeverity.getSelectedItem().toString());
            bleed.setBodyPart(mBodyPart.getSelectedItem().toString());

            new SaveBleedAsyncTask(bleed.getBackendBleed()){
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    mProgressBar.setVisibility(View.VISIBLE);
                    disableSaveBtn = true;
                    invalidateOptionsMenu();
                    Utils.hideKeyboard(mToolbar);
                }

                @Override
                protected void onPostExecute(us.idinfor.hemophilia.backend.bleedApi.model.Bleed bleed) {
                    super.onPostExecute(bleed);
                    mProgressBar.setVisibility(View.GONE);
                    disableSaveBtn = false;
                    invalidateOptionsMenu();
                    if(bleed != null){
                        finish();
                    }else{
                        Snackbar.make(
                                mToolbar,
                                getString(R.string.error_item_not_saved, getString(R.string.bleed)),
                                Snackbar.LENGTH_LONG).show();
                    }
                }
            }.execute();
        }
    }
}
