package controller;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class Getnet{


    public Context mContext;
    public boolean retorno = false;


    public Getnet(Context context) {
        this.mContext = context;
    }

    public boolean getInternet(){
        try{
            ConnectivityManager conMgr = (ConnectivityManager) mContext.getSystemService(mContext.CONNECTIVITY_SERVICE);

            if (conMgr != null)
            {
                NetworkInfo[] info = conMgr.getAllNetworkInfo();
                if (info != null)
                    for (int i = 0; i < info.length; i++)
                        if (info[i].getState() == NetworkInfo.State.CONNECTED)
                        {
                            retorno = true;
                        }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return retorno;

    }

}