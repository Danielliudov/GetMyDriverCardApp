package com.rachev.getmydrivercardapp.views.home;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.rachev.getmydrivercardapp.GetMyDriverCardApplication;
import com.rachev.getmydrivercardapp.R;
import com.rachev.getmydrivercardapp.models.User;
import com.rachev.getmydrivercardapp.utils.Constants;
import com.rachev.getmydrivercardapp.utils.Methods;
import com.rachev.getmydrivercardapp.views.BaseActivity;
import com.rachev.getmydrivercardapp.views.cardrequest.applicantdetails.BaseApplicantDetailsActivity;
import com.rachev.getmydrivercardapp.views.cardrequest.lists.RequestsListsActivity;
import com.rachev.getmydrivercardapp.views.login.LoginActivity;
import de.keyboardsurfer.android.widget.crouton.Style;
import studios.codelight.smartloginlibrary.LoginType;
import studios.codelight.smartloginlibrary.SmartLogin;
import studios.codelight.smartloginlibrary.SmartLoginFactory;
import studios.codelight.smartloginlibrary.UserSessionManager;
import studios.codelight.smartloginlibrary.users.SmartFacebookUser;
import studios.codelight.smartloginlibrary.users.SmartGoogleUser;
import studios.codelight.smartloginlibrary.users.SmartUser;

public class HomeActivity extends BaseActivity implements View.OnClickListener
{
    private User mCurrentUser;
    
    @BindView(R.id.grid_layout)
    GridLayout mGridLayout;
    
    @BindView(R.id.my_profile_card_view)
    CardView mMyProfileCardView;
    
    @BindView(R.id.new_request_card_view)
    CardView mNewRequestCardView;
    
    @BindView(R.id.my_requests_card_view)
    CardView mMyRequestsCardView;
    
    @BindView(R.id.logout_card_view)
    CardView mLogoutCardView;
    
    @BindView(R.id.my_profile_button)
    ImageButton mMyProfileButton;
    
    @BindView(R.id.new_request_button)
    ImageButton mNewRequestButton;
    
    @BindView(R.id.my_requests_button)
    ImageButton mMyRequestsButton;
    
    @BindView(R.id.logout_button)
    ImageButton mLogoutButton;
    
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        ButterKnife.bind(this);
        
        mMyProfileCardView.setOnClickListener(this);
        mNewRequestCardView.setOnClickListener(this);
        mMyRequestsCardView.setOnClickListener(this);
        mLogoutCardView.setOnClickListener(this);
        mMyProfileButton.setOnClickListener(this);
        mNewRequestButton.setOnClickListener(this);
        mMyRequestsButton.setOnClickListener(this);
        mLogoutButton.setOnClickListener(this);
        
        if (getIntent().getBooleanExtra("hasLoggedIn", false))
        {
            Methods.showCrouton(this,
                    Constants.Strings.USER_LOGGED_IN,
                    Style.CONFIRM, false);
            mCurrentUser = (User) getIntent().getSerializableExtra("user");
            setTitle("User: " + mCurrentUser.getUsername());
            
            if (mCurrentUser.getRoles().size() == 2)
                ((TextView) ((LinearLayout) mMyRequestsCardView
                        .getChildAt(0))
                        .getChildAt(1))
                        .setText("All Requests");
        }
        
        if (getIntent().getBooleanExtra("updated", false))
            Methods.showToast(getApplicationContext(),
                    "Request status changed",
                    false);
        else
        {
            if (getIntent().getBooleanExtra("request_created", false))
            {
                Methods.showToast(getApplicationContext(),
                        "Card request was sent",
                        false);
            }
        }
    }
    
    @Override
    public void onClick(View v)
    {
        final Intent[] intent = {null};
        
        switch (v.getId())
        {
            case R.id.my_profile_card_view:
                mMyProfileButton.performClick();
                break;
            case R.id.new_request_card_view:
                mNewRequestButton.performClick();
                break;
            case R.id.my_requests_card_view:
                mMyRequestsButton.performClick();
                break;
            case R.id.logout_card_view:
                mLogoutButton.performClick();
                break;
            case R.id.my_profile_button:
                Methods.showCrouton(this, "WIP", Style.INFO, false);
                break;
            case R.id.new_request_button:
                String[] choiceList = {"New", "Renew", "Replace"};
                
                intent[0] = new Intent(HomeActivity.this,
                        BaseApplicantDetailsActivity.class);
                intent[0].setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent[0].putExtra("user", mCurrentUser);
                
                new AlertDialog.Builder(this)
                        .setTitle("Select driver card request type")
                        .setSingleChoiceItems(choiceList, -1, null)
                        .setPositiveButton("Next", (dialog, which) ->
                        {
                            ListView listView = ((AlertDialog) dialog).getListView();
                            String checkedItem =
                                    (String) listView.getAdapter()
                                            .getItem(listView.getCheckedItemPosition());
                            
                            switch (checkedItem)
                            {
                                case "New":
                                    intent[0].putExtra("card_type", "new");
                                    startActivity(intent[0]);
                                    break;
                                case "Renew":
                                    intent[0].putExtra("card_type", "renew");
                                    
                                    String[] renewChoices =
                                            {
                                                    "Expired", "Suspended/Withdrawn",
                                                    "Change of name", "Change of address"
                                            };
                                    
                                    new AlertDialog.Builder(this)
                                            .setTitle("Select reason for card renewal")
                                            .setSingleChoiceItems(renewChoices, -1, null)
                                            .setPositiveButton("Next", (dialog1, which1) ->
                                            {
                                                ListView listView1 = ((AlertDialog) dialog1).getListView();
                                                String checkedItem1 =
                                                        (String) listView1.getAdapter()
                                                                .getItem(listView1.getCheckedItemPosition());
                                                
                                                switch (checkedItem1)
                                                {
                                                    case "Expired":
                                                        intent[0].putExtra("renewal_reason", "expired");
                                                        break;
                                                    case "Suspended/Withdrawn":
                                                        intent[0].putExtra("renewal_reason", "suspended/withdrawn");
                                                        break;
                                                    case "Change of name":
                                                        intent[0].putExtra("renewal_reason", "change_name");
                                                        String[] newName = getRenewalNameStringExtra();
                                                        intent[0].putExtra("new_name", newName);
                                                        break;
                                                    case "Change of address":
                                                        intent[0].putExtra("renewal_reason", "change_address");
                                                        String newAddres = getRenewalAddressStringExtra();
                                                        intent[0].putExtra("new_address", newAddres);
                                                        break;
                                                }
                                                startActivity(intent[0]);
                                            })
                                            .setNegativeButton("Cancel", (dialog1, which1) -> dialog1.dismiss())
                                            .create()
                                            .show();
                                    
                                    break;
                                case "Replace":
                                    intent[0].putExtra("card_type", "replace");
                                    
                                    String[] replaceChoices =
                                            {
                                                    "Exchange for BG card", "Lost",
                                                    "Stolen", "Malfunctioned", "Damaged"
                                            };
                                    
                                    new AlertDialog.Builder(this)
                                            .setTitle("Select reason for card renewal")
                                            .setSingleChoiceItems(replaceChoices, -1, null)
                                            .setPositiveButton("Next", (dialog2, which2) ->
                                            {
                                                ListView listView2 = ((AlertDialog) dialog2).getListView();
                                                String checkedItem2 =
                                                        (String) listView2.getAdapter()
                                                                .getItem(listView2.getCheckedItemPosition());
                                                
                                                switch (checkedItem2)
                                                {
                                                    case "Exchange for BG card":
                                                        intent[0].putExtra("replacement_reason", "exchange_for_bg_card");
                                                        String[] extras = getExchangeCardExtraInfo();
                                                        intent[0].putExtra("exchange_extras", extras);
                                                        break;
                                                    case "Lost":
                                                        intent[0].putExtra("replacement_reason", "lost");
                                                        String[] extras1 = getLostOrStolenExtraDialogData();
                                                        intent[0].putExtra("incident_extras", extras1);
                                                        break;
                                                    case "Stolen":
                                                        intent[0].putExtra("replacement_reason", "stolen");
                                                        String[] extras2 = getLostOrStolenExtraDialogData();
                                                        intent[0].putExtra("incident_extras", extras2);
                                                        break;
                                                    case "Malfunctioned":
                                                        intent[0].putExtra("replacement_reason", "malfunctioned");
                                                        break;
                                                    case "Damaged":
                                                        intent[0].putExtra("replacement_reason", "damaged");
                                                        break;
                                                }
                                                startActivity(intent[0]);
                                            })
                                            .setNegativeButton("Cancel", (dialog2, which2) -> dialog2.dismiss())
                                            .create()
                                            .show();
                                    break;
                            }
                            dialog.dismiss();
                        })
                        .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                        .create()
                        .show();
                break;
            case R.id.my_requests_button:
                intent[0] = new Intent(this, RequestsListsActivity.class);
                intent[0].putExtra("user", mCurrentUser);
                startActivity(intent[0]);
                break;
            case R.id.logout_button:
                new android.support.v7.app.AlertDialog.Builder(this).
                        setMessage("Are you sure you want to log out?")
                        .setPositiveButton("Yes", (dialog, which) ->
                        {
                            SmartUser currentUser = UserSessionManager.getCurrentUser(this);
                            SmartLogin smartLogin;
                            
                            if (currentUser instanceof SmartFacebookUser)
                                smartLogin = SmartLoginFactory.build(LoginType.Facebook);
                            else if (currentUser instanceof SmartGoogleUser)
                                smartLogin = SmartLoginFactory.build(LoginType.Google);
                            else
                                smartLogin = SmartLoginFactory.build(LoginType.CustomLogin);
                            
                            smartLogin.logout(getApplicationContext());
                            
                            intent[0] = new Intent(this, LoginActivity.class);
                            intent[0].setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent[0].putExtra("isHomeOrigin", true);
                            intent[0].putExtra("hasLoggedOut", true);
                            startActivity(intent[0]);
                            finish();
                            GetMyDriverCardApplication.getCookieJar(this).clear();
                        })
                        .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                        .setCancelable(true)
                        .create()
                        .show();
                break;
            default:
                break;
        }
    }
    
    private String[] getRenewalNameStringExtra()
    {
        Context context = getApplicationContext();
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        
        final EditText newFirstName = new EditText(context);
        newFirstName.setHint("New first name");
        newFirstName.setTextColor(getColor(R.color.primaryColorLime));
        newFirstName.setHintTextColor(getColor(R.color.primaryColorLime));
        layout.addView(newFirstName);
        
        final EditText newMidleName = new EditText(context);
        newMidleName.setHint("New middle name");
        newMidleName.setTextColor(getColor(R.color.primaryColorLime));
        newMidleName.setHintTextColor(getColor(R.color.primaryColorLime));
        layout.addView(newMidleName);
        
        final EditText newLastName = new EditText(context);
        newLastName.setHint("New last name");
        newLastName.setTextColor(getColor(R.color.primaryColorLime));
        newLastName.setHintTextColor(getColor(R.color.primaryColorLime));
        layout.addView(newLastName);
        
        @SuppressLint("HandlerLeak") final Handler handler = new Handler()
        {
            @Override
            public synchronized void handleMessage(Message msg)
            {
                throw new RuntimeException();
            }
        };
        
        final String[] data = new String[3];
        new AlertDialog.Builder(this)
                .setTitle("Provide you new names[only fill changed names(other leave blank)]")
                .setView(layout)
                .setPositiveButton("Next", (dialog, which) ->
                {
                    data[0] = ((EditText) layout.getChildAt(0)).getText().toString();
                    data[1] = ((EditText) layout.getChildAt(0)).getText().toString();
                    data[2] = ((EditText) layout.getChildAt(0)).getText().toString();
                    handler.sendMessage(handler.obtainMessage());
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
        
        try
        {
            Looper.loop();
        } catch (RuntimeException ignored)
        {
        }
        
        return data;
    }
    
    private String getRenewalAddressStringExtra()
    {
        Context context = getApplicationContext();
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        
        final EditText renewalBox = new EditText(context);
        renewalBox.setHint("New address");
        renewalBox.setTextColor(getColor(R.color.primaryColorLime));
        renewalBox.setHintTextColor(getColor(R.color.primaryColorLime));
        layout.addView(renewalBox);
        
        @SuppressLint("HandlerLeak") final Handler handler = new Handler()
        {
            @Override
            public synchronized void handleMessage(Message msg)
            {
                throw new RuntimeException();
            }
        };
        
        final String[] data = {null};
        new AlertDialog.Builder(this)
                .setTitle("Provide your new details")
                .setView(layout)
                .setPositiveButton("Next", (dialog, which) ->
                {
                    data[0] = ((EditText) layout.getChildAt(0)).getText().toString();
                    handler.sendMessage(handler.obtainMessage());
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
        
        try
        {
            Looper.loop();
        } catch (RuntimeException ignored)
        {
        }
        
        return data[0];
    }
    
    private String[] getLostOrStolenExtraDialogData()
    {
        Context context = getApplicationContext();
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        
        final EditText dateBox = new EditText(context);
        dateBox.setHint("Date");
        dateBox.setTextColor(getColor(R.color.primaryColorLime));
        dateBox.setHintTextColor(getColor(R.color.primaryColorLime));
        layout.addView(dateBox);
        
        final EditText placeBox = new EditText(context);
        placeBox.setHint("Place");
        placeBox.setTextColor(getColor(R.color.primaryColorLime));
        placeBox.setHintTextColor(getColor(R.color.primaryColorLime));
        layout.addView(placeBox);
        
        @SuppressLint("HandlerLeak") final Handler handler = new Handler()
        {
            @Override
            public synchronized void handleMessage(Message msg)
            {
                throw new RuntimeException();
            }
        };
        
        final String[] data = new String[2];
        new AlertDialog.Builder(this)
                .setTitle("Provide information about the incident")
                .setView(layout)
                .setPositiveButton("Next", (dialog, which) ->
                {
                    data[0] = ((EditText) layout.getChildAt(0)).getText().toString();
                    data[1] = ((EditText) layout.getChildAt(1)).getText().toString();
                    handler.sendMessage(handler.obtainMessage());
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
        
        try
        {
            Looper.loop();
        } catch (RuntimeException ignored)
        {
        }
        
        return data;
    }
    
    private String[] getExchangeCardExtraInfo()
    {
        Context context = getApplicationContext();
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        
        final EditText issueCountryTachCard = new EditText(context);
        issueCountryTachCard.setHint("Country of tachograph card issuing");
        issueCountryTachCard.setTextColor(getColor(R.color.primaryColorLime));
        issueCountryTachCard.setHintTextColor(getColor(R.color.primaryColorLime));
        layout.addView(issueCountryTachCard);
        
        final EditText prevTachCardNumber = new EditText(context);
        prevTachCardNumber.setHint("Tachograph card number");
        prevTachCardNumber.setTextColor(getColor(R.color.primaryColorLime));
        prevTachCardNumber.setHintTextColor(getColor(R.color.primaryColorLime));
        layout.addView(prevTachCardNumber);
        
        final EditText issueCountryDriverLic = new EditText(context);
        issueCountryDriverLic.setHint("Country of driving license issuing");
        issueCountryDriverLic.setTextColor(getColor(R.color.primaryColorLime));
        issueCountryDriverLic.setHintTextColor(getColor(R.color.primaryColorLime));
        layout.addView(issueCountryDriverLic);
        
        final EditText drivingLicNumber = new EditText(context);
        drivingLicNumber.setHint("Driving license number");
        drivingLicNumber.setTextColor(getColor(R.color.primaryColorLime));
        drivingLicNumber.setHintTextColor(getColor(R.color.primaryColorLime));
        layout.addView(drivingLicNumber);
        
        @SuppressLint("HandlerLeak") final Handler handler = new Handler()
        {
            @Override
            public synchronized void handleMessage(Message msg)
            {
                throw new RuntimeException();
            }
        };
        
        final String[] data = new String[4];
        new AlertDialog.Builder(this)
                .setTitle("Provide information about the incident")
                .setView(layout)
                .setPositiveButton("Next", (dialog, which) ->
                {
                    data[0] = ((EditText) layout.getChildAt(0)).getText().toString();
                    data[1] = ((EditText) layout.getChildAt(1)).getText().toString();
                    data[2] = ((EditText) layout.getChildAt(2)).getText().toString();
                    data[3] = ((EditText) layout.getChildAt(3)).getText().toString();
                    handler.sendMessage(handler.obtainMessage());
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
        
        try
        {
            Looper.loop();
        } catch (RuntimeException ignored)
        {
        }
        
        return data;
    }
}
