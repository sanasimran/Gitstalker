package com.example.gitstalker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import io.branch.indexing.BranchUniversalObject;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;
import io.branch.referral.SharingHelper;
import io.branch.referral.util.BranchEvent;
import io.branch.referral.util.ContentMetadata;
import io.branch.referral.util.LinkProperties;
import io.branch.referral.util.ShareSheetStyle;

public class MainActivity extends AppCompatActivity {

    private Button login;
    private EditText inputUsername;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = (Button) findViewById(R.id.login_github);
        inputUsername = (EditText) findViewById(R.id.git_username);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUser();
            }
        });
        new BranchEvent("Search")
                .addCustomDataProperty("Custom_Event_Property_Key11", "Custom_Event_Property_val11")
                .addCustomDataProperty("Custom_Event_Property_Key22", "Custom_Event_Property_val22")
                .setCustomerEventAlias("my_custom_alias")
                .logEvent(MainActivity.this);
    }

    private void getUser() {
        intent = new Intent(MainActivity.this, UserActivity.class);
        intent.putExtra("STRING_I_NEED", inputUsername.getText().toString());
        startActivity(intent);
    }
    @Override public void onStart() {
        super.onStart();
        Branch.sessionBuilder(this).withCallback(branchReferralInitListener).withData(getIntent() != null ? getIntent().getData() : null).init();
    }

        private Branch.BranchReferralInitListener branchReferralInitListener = new Branch.BranchReferralInitListener() {
            @Override
            public void onInitFinished(JSONObject linkProperties, BranchError error) {
                // do stuff with deep link data (nav to page, display content, etc)
                    try {
                        // get user name from branch link
                        String git_username = linkProperties.getString("git_username");
                        Intent intent = new Intent(MainActivity.this, Repositories.class);
                        intent.putExtra("STRING_I_NEED",git_username);
                        startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        };

        @Override
        public void onNewIntent(Intent intent) {
            super.onNewIntent(intent);
            this.setIntent(intent);
    }
}
