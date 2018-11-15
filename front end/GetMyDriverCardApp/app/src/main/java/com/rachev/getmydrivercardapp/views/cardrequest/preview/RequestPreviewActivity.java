package com.rachev.getmydrivercardapp.views.cardrequest.preview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.rachev.getmydrivercardapp.R;
import com.rachev.getmydrivercardapp.models.BaseRequest;
import com.rachev.getmydrivercardapp.utils.Methods;
import com.rachev.getmydrivercardapp.utils.enums.RequestStatus;
import com.rachev.getmydrivercardapp.views.BaseActivity;
import com.rachev.getmydrivercardapp.views.home.HomeActivity;

public class RequestPreviewActivity extends BaseActivity implements AdapterView.OnItemSelectedListener,
        RequestPreviewContracts.View, RequestPreviewContracts.Navigator
{
    private static BaseRequest mFinalRequest;
    private RequestPreviewContracts.Presenter mPresenter;
    
    @BindView(R.id.admin_panel)
    android.support.v7.widget.Toolbar mAdminToolbar;
    
    @BindView(R.id.admin_panel_change_status_spinner)
    Spinner mAdminPanelSpinner;
    
    @BindView(R.id.admin_panel_request_status_text)
    TextView mAdminPanelStatusText;
    
    @BindView(R.id.egn_text)
    TextView mEgnText;
    
    @BindView(R.id.first_name_text)
    TextView mFirstNameText;
    
    @BindView(R.id.middle_name_text)
    TextView mMiddleNameText;
    
    @BindView(R.id.last_name_text)
    TextView mLastNameText;
    
    @BindView(R.id.birthdate_text)
    TextView mBirthdateText;
    
    @BindView(R.id.address_text)
    TextView mAddressText;
    
    @BindView(R.id.phone_num_text)
    TextView mPhoneNumText;
    
    @BindView(R.id.email_text)
    TextView mEmailText;
    
    @BindView(R.id.extra_details_view)
    LinearLayout mExtraDetailsLayout;
    
    @BindView(R.id.extra_details_renewal_view)
    LinearLayout mExtraDetailsRenewalLayout;
    
    @BindView(R.id.extra_details_replacement_view)
    LinearLayout mExtraDetailsReplacementLayout;
    
    @BindView(R.id.extra_details_replacement_exchange_view)
    LinearLayout mExtraDetailsReplacementExchangeLayout;
    
    @BindView(R.id.extra_details_replacement_incident_view)
    LinearLayout mExtraDetailsReplacementIncidentLayout;
    
    @BindView(R.id.extra_details_renewal_name_view)
    LinearLayout mExtraDetailsRenewalNameView;
    
    @BindView(R.id.extra_details_renewal_address_view)
    LinearLayout mExtraDetailsRenewalAddressView;
    
    @BindView(R.id.renewal_fname_text)
    TextView mExtraDetailsNewFirstNameText;
    
    @BindView(R.id.renewal_mname_text)
    TextView mExtraDetailsNewMiddleText;
    
    @BindView(R.id.renewal_lname_text)
    TextView mExtraDetailsNewLastNameText;
    
    @BindView(R.id.renewal_address_text)
    TextView mExtraDetailsNewAddressText;
    
    @BindView(R.id.replacement_incident_date)
    TextView mReplacementIncidentDateText;
    
    @BindView(R.id.replacement_incident_place)
    TextView mReplacementIncidentPlaceText;
    
    @BindView(R.id.replacement_exchange_prev_card_country)
    TextView mReplacementExchangePrevCardCountry;
    
    @BindView(R.id.replacement_exchange_prev_card_num)
    TextView mReplacementExchangePrevCardNum;
    
    @BindView(R.id.replacement_exchange_driving_lic_countrty)
    TextView mReplacementExchangeDrivingLicCountry;
    
    @BindView(R.id.replacement_exchange_driving_lic_num)
    TextView getmReplacementExchangeDrivingLicNumber;
    
    @BindView(R.id.renewal_reason_text)
    TextView mRenewalReasonText;
    
    @BindView(R.id.replacement_reason_text)
    TextView mReplacementReasonText;
    
    @BindView(R.id.applicant_selfie_img)
    ImageView mSelfieImage;
    
    @BindView(R.id.applicant_signature_img)
    ImageView mSignatureImage;
    
    @BindView(R.id.applicant_id_card_img)
    ImageView mIdCardImage;
    
    @BindView(R.id.applicant_driving_license_img)
    ImageView mDrivingLicImage;
    
    @BindView(R.id.btn_send)
    Button mSendButton;
    
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_preview);
        setTitle("Preview");
        
        ButterKnife.bind(this);
        setPresenter(new RequestPreviewPresenter(this));
        mPresenter.setNavigator(this);
        
        if (!getIntent().getBooleanExtra("isOriginList", false))
        {
            mFinalRequest = (BaseRequest) getIntent().getSerializableExtra("request");
            if (getIntent().getStringExtra("path1").length() > 0)
                mFinalRequest.getImageAttachment().setIdCardImage(
                        Methods.encodeBitmapToBase64String(getIntent().getStringExtra("path1")));
            if (getIntent().getStringExtra("path2").length() > 0)
                mFinalRequest.getImageAttachment().setDriverLicenseImage(
                        Methods.encodeBitmapToBase64String(getIntent().getStringExtra("path2")));
            if (getIntent().getStringExtra("path3").length() > 0)
                mFinalRequest.getImageAttachment().setPrevDriverCardImage(
                        Methods.encodeBitmapToBase64String(getIntent().getStringExtra("path3")));
            
            mSendButton.setOnClickListener(v -> mPresenter.createRequest(mFinalRequest));
        } else
        {
            mSendButton.setVisibility(View.GONE);
            if (getIntent().getBooleanExtra("admin", false))
            {
                mAdminToolbar.setVisibility(View.VISIBLE);
                
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                        R.array.status_array, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mAdminPanelSpinner.setAdapter(adapter);
                mAdminPanelSpinner.setSelection(0, false);
                mAdminPanelSpinner.setOnItemSelectedListener(this);
                mAdminPanelStatusText.append(mFinalRequest.getStatus());
            }
        }
        
        setTextViews();
        setExtraTextViews();
        setImageViews();
    }
    
    @Override
    protected void onResume()
    {
        super.onResume();
        
        mPresenter.subscribe(this);
    }
    
    @SuppressLint("SimpleDateFormat")
    @Override
    public void setTextViews()
    {
        mEgnText.setText(String.format("EGN: %s", mFinalRequest.getApplicantDetails().getEgn()));
        mFirstNameText.setText(String.format("First name: %s", mFinalRequest.getApplicantDetails().getFirstName()));
        mMiddleNameText.setText(String.format("Middle name: %s", mFinalRequest.getApplicantDetails().getMiddleName()));
        mLastNameText.setText(String.format("Last name: %s", mFinalRequest.getApplicantDetails().getLastName()));
        mBirthdateText.setText(String.format("Birthdate: %s", mFinalRequest.getApplicantDetails().getBirthDate()));
        mAddressText.setText(String.format("Address: %s", mFinalRequest.getApplicantDetails().getAddress()));
        mPhoneNumText.setText(String.format("Phone number: %s", mFinalRequest.getApplicantDetails().getPhoneNumber()));
        mEmailText.setText(String.format("Email: %s", mFinalRequest.getApplicantDetails().getEmail()));
    }
    
    @Override
    public void setExtraTextViews()
    {
        if (!mFinalRequest.getType().equals("new"))
        {
            mExtraDetailsLayout.setVisibility(View.VISIBLE);
            if (mFinalRequest.getType().equals("replace"))
            {
                mExtraDetailsReplacementLayout.setVisibility(View.VISIBLE);
                
                String reason = mFinalRequest.getReplacementDetails().getReplacementReason();
                mReplacementReasonText.setText(String.format("Replacement reason: %s", reason));
                if (reason.equals("lost") || reason.equals("stolen"))
                {
                    mExtraDetailsReplacementIncidentLayout.setVisibility(View.VISIBLE);
                    mReplacementIncidentDateText.setText(String.format(
                            "Incident date: %s", mFinalRequest.getReplacementDetails()
                                    .getReplacementIncidentDate()));
                    mReplacementIncidentPlaceText.setText(String.format(
                            "Incident place: %s", mFinalRequest.getReplacementDetails()
                                    .getReplacementIncidentPlace()));
                } else if (reason.equals("exchange_for_bg_card"))
                {
                    mExtraDetailsReplacementExchangeLayout.setVisibility(View.VISIBLE);
                    mReplacementExchangePrevCardCountry.setText(String.format(
                            "Previous card country: %s", mFinalRequest.getReplacementDetails()
                                    .getTachCardIssuingCountry()));
                    mReplacementExchangePrevCardNum.setText(String.format(
                            "Previous card number: %s", mFinalRequest.getReplacementDetails()
                                    .getTachCardNumber()));
                    mReplacementExchangeDrivingLicCountry.setText(String.format(
                            "Driving license country: %s", mFinalRequest.getReplacementDetails()
                                    .getDrivingLicIssuingCountry()));
                    getmReplacementExchangeDrivingLicNumber.setText(String.format(
                            "Driving license number: %s", mFinalRequest.getReplacementDetails()
                                    .getDrivingLicNumber()));
                }
            } else if (mFinalRequest.getType().equals("renewal"))
            {
                mExtraDetailsRenewalLayout.setVisibility(View.VISIBLE);
                
                String reason = mFinalRequest.getRenewalDetails().getRenewalReason();
                mRenewalReasonText.setText(String.format("Renewal reason: %s", reason));
                if (reason.equals("change_name"))
                {
                    mExtraDetailsRenewalNameView.setVisibility(View.VISIBLE);
                    
                    if (mFinalRequest.getRenewalDetails().getRenewalNewFirstName() != null)
                        mExtraDetailsNewFirstNameText.setText(String.format(
                                "New first name: %s", mFinalRequest.getRenewalDetails()
                                        .getRenewalNewFirstName()));
                    else
                        mExtraDetailsNewFirstNameText.setVisibility(View.GONE);
                    
                    if (mFinalRequest.getRenewalDetails().getRenewalNewMiddleName() != null)
                        mExtraDetailsNewMiddleText.setText(String.format(
                                "New middle name: %s", mFinalRequest.getRenewalDetails()
                                        .getRenewalNewMiddleName()));
                    else
                        mExtraDetailsNewMiddleText.setVisibility(View.GONE);
                    
                    if (mFinalRequest.getRenewalDetails().getRenewalNewLastName() != null)
                        mExtraDetailsNewFirstNameText.setText(String.format(
                                "New middle name: %s", mFinalRequest.getRenewalDetails()
                                        .getRenewalNewLastName()));
                    else
                        mExtraDetailsNewLastNameText.setVisibility(View.GONE);
                } else if (reason.equals("change_address"))
                {
                    mExtraDetailsRenewalAddressView.setVisibility(View.VISIBLE);
                    mExtraDetailsNewAddressText.setText(String.format(
                            "New address: %s", mFinalRequest.getRenewalDetails()
                                    .getRenewalNewAddress()));
                }
            }
        }
    }
    
    @Override
    public void setImageViews()
    {
        if (mFinalRequest.getImageAttachment().getSelfieImage() != null)
            Glide.with(this)
                    .load(Methods.decodeBitmapFromBase64String(
                            mFinalRequest.getImageAttachment().getSelfieImage()))
                    .into(mSelfieImage);
        
        if (mFinalRequest.getImageAttachment().getSignatureScreenshot() != null)
            Glide.with(this)
                    .load(Methods.decodeBitmapFromBase64String(
                            mFinalRequest.getImageAttachment().getSignatureScreenshot()))
                    .into(mSignatureImage);
        
        if (mFinalRequest.getImageAttachment().getIdCardImage() != null)
            Glide.with(this)
                    .load(Methods.decodeBitmapFromBase64String(
                            mFinalRequest.getImageAttachment().getIdCardImage()))
                    .into(mIdCardImage);
        else
            mIdCardImage.setVisibility(View.GONE);
        
        if (mFinalRequest.getImageAttachment().getDriverLicenseImage() != null)
            Glide.with(this)
                    .load(Methods.decodeBitmapFromBase64String(
                            mFinalRequest.getImageAttachment().getDriverLicenseImage()))
                    .into(mDrivingLicImage);
        else
            mDrivingLicImage.setVisibility(View.GONE);
    }
    
    public static void setRequest(BaseRequest request)
    {
        mFinalRequest = request;
    }
    
    @Override
    public void setPresenter(RequestPreviewContracts.Presenter presenter)
    {
        mPresenter = presenter;
    }
    
    @Override
    public void finalizeRequestReview(BaseRequest baseRequest)
    {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("request_id", baseRequest.getId());
        returnIntent.putExtra("request_status", baseRequest.getStatus());
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
    
    @Override
    public Activity getActivity()
    {
        return this;
    }
    
    @Override
    public void showProgressBar()
    {
        mProgressBar.setVisibility(View.VISIBLE);
    }
    
    @Override
    public void hideProgressBar()
    {
        mProgressBar.setVisibility(View.GONE);
    }
    
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        String status = "Request status:" + parent.getItemAtPosition(position).toString();
        String lastKnownStatus = mAdminPanelStatusText.getText().toString();
        
        if (status.equals(lastKnownStatus))
        {
            Methods.showToast(this,
                    "Request already has the same status",
                    false);
            return;
        }
        
        mAdminPanelStatusText.setText(String.format(
                "Request status: %s", parent.getItemAtPosition(position)));
        
        switch (parent.getItemAtPosition(position).toString().trim())
        {
            case "pending":
                mFinalRequest.setStatus(RequestStatus.PENDING.toString());
                break;
            case "approved":
                mFinalRequest.setStatus(RequestStatus.APPROVED.toString());
                break;
            case "disapproved":
                mFinalRequest.setStatus(RequestStatus.DISAPPROVED.toString());
                break;
        }
        
        mPresenter.updateStatus(mFinalRequest);
    }
    
    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {
    
    }
    
    @Override
    public void navigateToHome(BaseRequest request)
    {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("request_created", true);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
