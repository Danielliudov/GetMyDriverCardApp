package com.rachev.getmydrivercardapp.views.login;

import android.annotation.SuppressLint;
import android.content.Context;
import com.rachev.getmydrivercardapp.GetMyDriverCardApplication;
import com.rachev.getmydrivercardapp.async.base.SchedulerProvider;
import com.rachev.getmydrivercardapp.models.User;
import com.rachev.getmydrivercardapp.services.base.UsersService;
import com.rachev.getmydrivercardapp.utils.Constants;
import com.rachev.getmydrivercardapp.utils.Methods;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

import java.io.IOException;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class LoginPresenter implements LoginContracts.Presenter
{
    private LoginContracts.View mView;
    private LoginContracts.Navigator mNavigator;
    private final UsersService<User> mUsersService;
    private final SchedulerProvider mSchedulerProvider;
    
    protected LoginPresenter(Context context)
    {
        mUsersService = GetMyDriverCardApplication.getUsersService(context);
        mSchedulerProvider = GetMyDriverCardApplication.getSchedulerProvider();
    }
    
    @Override
    public void subscribe(LoginContracts.View view)
    {
        mView = view;
    }
    
    @Override
    public void setNavigator(LoginContracts.Navigator navigator)
    {
        mNavigator = navigator;
    }
    
    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("CheckResult")
    @Override
    public void createUser(final User user)
    {
        Observable.create((ObservableOnSubscribe<User>) emitter ->
        {
            User userToAdd = mUsersService.create(user);
            
            if (userToAdd != null)
                emitter.onNext(userToAdd);
            emitter.onComplete();
        })
                .subscribeOn(mSchedulerProvider.background())
                .observeOn(mSchedulerProvider.ui())
                .doFinally(mView::dismissSignupDialog)
                .subscribe(u ->
                {
                    if (u != null)
                    {
                        Methods.showToast(mView.getActivity(),
                                Constants.Strings.USER_SIGNED_UP,
                                true);
                        mNavigator.navigateToHome(u);
                    }
                }, err ->
                {
                    if (err instanceof IOException)
                        Methods.showToast(mView.getActivity(),
                                Constants.Strings.CONNECTION_TO_SERVER_TIMED_OUT,
                                true);
                    else
                        Methods.showToast(mView.getActivity(),
                                err.getMessage(), true);
                });
    }
    
    @SuppressLint("CheckResult")
    @Override
    public void login(String username, String password)
    {
        if (username.isEmpty() || password.isEmpty())
        {
            Methods.showToast(mView.getActivity(),
                    Constants.Strings.NOT_ALL_FIELDS_FILLED,
                    true);
            return;
        }
        
        final String url = Constants.Strings.BASE_SERVER_URL +
                Constants.Strings.USERS_URL_SUFFIX +
                Constants.Strings.USER_ME_SUFFIX;
        
        mView.showProgressBar();
        Observable.create((ObservableOnSubscribe<User>) emitter ->
        {
            User currentUser = mUsersService.login(username, password);
            
            if (currentUser == null)
                throw new IllegalArgumentException(Constants.Strings.INCORRECT_CREDENTIALS);
            
            emitter.onNext(currentUser);
            emitter.onComplete();
        })
                .subscribeOn(mSchedulerProvider.background())
                .observeOn(mSchedulerProvider.ui())
                .doFinally(mView::hideProgressBar)
                .subscribe(u ->
                        {
                            if (u != null)
                                mNavigator.navigateToHome(u);
                        },
                        e ->
                        {
                            if (e instanceof IllegalArgumentException)
                                Methods.showToast(mView.getActivity(),
                                        e.getMessage(),
                                        true);
                            else if (e instanceof IOException)
                                Methods.showToast(mView.getActivity(),
                                        Constants.Strings.CONNECTION_TO_SERVER_TIMED_OUT,
                                        true);
                        });
    }
}
