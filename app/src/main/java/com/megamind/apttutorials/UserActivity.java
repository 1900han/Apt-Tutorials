package com.megamind.apttutorials;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.megamind.apttutorials.annotations.Builder;
import com.megamind.apttutorials.annotations.Optional;
import com.megamind.apttutorials.annotations.Required;

@Builder
public class UserActivity extends Activity {

    @Required
    String name;

    @Required
    int age;

    @Optional
    String title;

    @Optional
    String company;

    @Optional
    String workPlace;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ((TextView)findViewById (R.id.nameView)).setText(name);
        ((TextView)findViewById (R.id.ageView)).setText(String.valueOf(age));
        ((TextView)findViewById (R.id.titleView)).setText(title);
        ((TextView)findViewById (R.id.companyView)).setText(company);
        ((TextView)findViewById (R.id.workPlaceView)).setText(workPlace);
    }
}