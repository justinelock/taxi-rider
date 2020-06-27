package com.futureit.hitaxi.user.net;

import android.text.TextUtils;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by monir.sobuj on 9/26/2018.
 */

public class RequestServices {

    String TAG = RequestServices.class.getSimpleName();
    CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    //public LoadingDialog loadingDialog;
    StringBuilder errorMsg;

    public void setErrorMsg(String msg){
        if(TextUtils.isEmpty(errorMsg)){
            errorMsg.append(msg);
        } else {
            errorMsg.append(" ,"+msg);
        }
    }

    /*public void getClient(Context context, APIServices apiServices, ResponseListener listener){
        loadingDialog = LoadingDialog.newInstance(context, "Please wait...");
        loadingDialog.show();
        mCompositeDisposable.add(apiServices.getClients() //test jsonblob
                .subscribeOn(Schedulers.io())  // Run on a background thread
                .observeOn(AndroidSchedulers.mainThread()) // Be notified on the main thread
                .subscribeWith(new DisposableObserver<ClientResponse>() {
                    @Override
                    public void onComplete() {
                        getZone(apiServices,listener);
                    }
                    @Override
                    public void onError(Throwable e) {
                        getZone(apiServices,listener);
                    }
                    @Override
                    public void onNext(ClientResponse value) {
                        if (value.getClientModels() != null && value.getClientModels().size() > 0){
                            listener.onSuccessClient(value.getClientModels());
                        } else{
                            listener.onFailed();
                        }
                    }
                }));
    }*/


    /*public void updateClientInfo(Context context, APIServices apiServices, ResponseListener listener, ClientUIModel clientUIModel){
        loadingDialog = LoadingDialog.newInstance(context, "Please wait...");
        loadingDialog.show();
        mCompositeDisposable.add(apiServices.updateClient(
                clientUIModel.getClientId(),
                clientUIModel.getName(),
                clientUIModel.getAddress(),
                clientUIModel.getPhoneNo(),
                clientUIModel.getZoneId(),
                clientUIModel.getUrlPhoto(),
                clientUIModel.getUrlSign(),
                clientUIModel.getUrlNID()
                ) //test jsonblob
                .subscribeOn(Schedulers.io())  // Run on a background thread
                .observeOn(AndroidSchedulers.mainThread()) // Be notified on the main thread
                .subscribeWith(new DisposableObserver<UpdateResponse>() {
                    @Override
                    public void onComplete() {
                        getZone(apiServices,listener);
                    }
                    @Override
                    public void onError(Throwable e) {
                        getZone(apiServices,listener);
                    }
                    @Override
                    public void onNext(UpdateResponse value) {
                        if (value.getMessage() != null && value.getMessage().equalsIgnoreCase(StringConstant.UPDATE_SUCCESS)){
                            listener.onSuccessUpdate(value);
                        } else{
                            listener.onFailed();
                        }
                    }
                }));

    }*/




}
