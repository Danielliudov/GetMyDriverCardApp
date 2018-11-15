package com.rachev.getmydrivercardapp.views.cardrequest.preview;

import android.app.Activity;
import com.rachev.getmydrivercardapp.models.BaseRequest;

public interface RequestPreviewContracts
{
    interface View
    {
        void setPresenter(Presenter presenter);
    
        void finalizeRequestReview(BaseRequest baseRequest);
        
        Activity getActivity();
        
        void showProgressBar();
    
        void hideProgressBar();
        
        void setImageViews();
        
        void setTextViews();
        
        void setExtraTextViews();
        
        void navigateToHome(BaseRequest request);
    }
    
    interface Presenter
    {
        void subscribe(View view);
    
        void createRequest(BaseRequest baseRequest);
    
        void updateStatus(BaseRequest baseRequest);
        
        void setNavigator(Navigator navigator);
    }
    
    interface Navigator
    {
        void navigateToHome(BaseRequest request);
    }
}
