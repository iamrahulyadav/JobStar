package com.papercutlabs.jobstar.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.papercutlabs.jobstar.R;
import com.papercutlabs.jobstar.model.Job;
import com.papercutlabs.jobstar.util.Util;
import com.papercutlabs.jobstar.view.CustomTextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by knowalluser on 23-04-2018.
 */

public class ActivityPostJob extends AppCompatActivity {

    private Context mContext;
    private EditText et_poition_title, et_location, et_description, et_what_submit, et_keyskills;
    private RadioGroup rg_contract_type, rg_category;
    private CustomTextView ctv_date;
    private CheckBox chkbx_relocation;
    private String contractType;
    private String category;
    DatePickerDialog datePickerDialog;
    Calendar newDate;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job);
        mContext = ActivityPostJob.this;
        dateFormatter = new SimpleDateFormat("yyyy-mm-dd", Locale.US);
        et_poition_title = (EditText) findViewById(R.id.et_poition_title);
        et_location = (EditText) findViewById(R.id.et_location);
        et_description = (EditText) findViewById(R.id.et_description);
        et_what_submit = (EditText) findViewById(R.id.et_what_submit);
        et_keyskills = (EditText) findViewById(R.id.et_keyskills);

        rg_contract_type = (RadioGroup) findViewById(R.id.rg_contract_type);
        rg_category = (RadioGroup) findViewById(R.id.rg_category);

        chkbx_relocation = (CheckBox) findViewById(R.id.chkbx_relocation);
        ctv_date = (CustomTextView) findViewById(R.id.ctv_date);

        rg_contract_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup arg0, int id) {
                switch (id) {

                    case R.id.rb_contract_ft:
                        contractType = "fullTime";
                        break;
                    case R.id.rb_contract_pt:
                        contractType = "partTime";
                        break;
                    case R.id.rb_contract_st:
                        contractType = "shortTerm";
                        break;
                }
            }
        });
        rg_category.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup arg0, int id) {
                switch (id) {

                    case R.id.rb_category_programming:
                        category = "Programming";
                        break;
                    case R.id.rb_category_design:
                        category = "Design";
                        break;
                    case R.id.rb_category_electronics:
                        category = "Electronics";
                        break;
                    case R.id.rb_category_testing:
                        category = "Testing";
                        break;
                    case R.id.rb_category_sysadmin:
                        category = "System Admin";
                        break;
                    case R.id.rb_category_writer:
                        category = "Writer";
                        break;
                    case R.id.rb_category_developer:
                        category = "Developer";
                        break;
                    case R.id.rb_category_operations:
                        category = "Operations";
                        break;
                    case R.id.rb_category_custsupport:
                        category = "Customer Support";
                        break;
                    case R.id.rb_category_hr:
                        category = "Human Resources";
                        break;
                    case R.id.rb_category_bm:
                        category = "Business Management";
                        break;

                }
            }
        });
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                ctv_date.setText(dateFormatter.format(newDate.getTime()));

            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        ctv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });
    }

    public void onNextClick(View v) {

        String positionTitle = et_poition_title.getText().toString().trim();
        String location = et_location.getText().toString().trim();
        String description = et_description.getText().toString().trim();
        String whatSubmit = et_what_submit.getText().toString().trim();
        String keySkills = et_keyskills.getText().toString().trim();
        String datePositionFilling = ctv_date.getText().toString().trim();
        if (TextUtils.isEmpty(positionTitle)) {
            Util.showMessageWithOk(ActivityPostJob.this, "Please enter poition.");
            return;
        } else if (TextUtils.isEmpty(location)) {
            Util.showMessageWithOk(ActivityPostJob.this, "Please enter the job location.");
            return;
        } else if (TextUtils.isEmpty(keySkills)) {
            Util.showMessageWithOk(ActivityPostJob.this, "Please enter key skills");
            return;
        } else if (TextUtils.isEmpty(description)) {
            Util.showMessageWithOk(ActivityPostJob.this, "Please enter the job description.");
            return;
        } else if (TextUtils.isEmpty(whatSubmit)) {
            Util.showMessageWithOk(ActivityPostJob.this, "Please enter cv or resume type.");
            return;
        } else if (TextUtils.isEmpty(datePositionFilling) || datePositionFilling.equalsIgnoreCase("date")) {
            Util.showMessageWithOk(ActivityPostJob.this, "Please enter date.");
            return;
        } else if (rg_contract_type.getCheckedRadioButtonId() == -1) {
            // no radio buttons are checked
            Util.showMessageWithOk(ActivityPostJob.this, "Please select contract type.");
            return;
        } else if (rg_category.getCheckedRadioButtonId() == -1) {
            // no radio buttons are checked
            Util.showMessageWithOk(ActivityPostJob.this, "Please select a category.");
            return;
        }
        Job job = new Job();
        job.setJobTitle(positionTitle);
        job.setJobLocation(location);
        job.setJobDescription(description);
        job.setResumeType(whatSubmit);
        job.setContractType(contractType);
        job.setCategory(category);
        job.setKeySkills(keySkills);
        job.setDatePositionFilling(datePositionFilling);
        job.setUserID(Util.fetchUserClass(ActivityPostJob.this).getUserId());
        job.setRelocationAvailable(chkbx_relocation.isChecked());

        Intent intent = new Intent(ActivityPostJob.this, SelectEmployerActivity.class);
        intent.putExtra("jobDetails", job);
        startActivity(intent);
    }
}
