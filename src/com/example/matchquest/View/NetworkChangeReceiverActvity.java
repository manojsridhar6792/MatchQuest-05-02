package com.example.matchquest.View;


import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;


public class NetworkChangeReceiverActvity extends BroadcastReceiver{


	CommonActivity activityObject;
	ProgressDialog mProgressDialog;
	boolean toShowProgressBarOnPreExecute = false;
	
	RemoteDataTask remoteDataTask;
	
	
	public NetworkChangeReceiverActvity(CommonActivity activity,boolean toShow)
	{
		this.activityObject = activity;
		this.toShowProgressBarOnPreExecute = toShow;
	}
	  @Override
	  public void onReceive(final Context context, final Intent intent) {

		  if(CommonViewClass.isNetworkAvailable(context))
		  {
			  Toast.makeText(context, "connected", 1000).show();
			 
			remoteDataTask  = new RemoteDataTask();
			remoteDataTask.execute();
				 
			 
		  }else{
				Toast.makeText(context, "Connectivity Lost ! connect to internet to get latest update", 1000).show();;
		  }
	  }
	  
	  public void updateData()
	  {
		  if(CommonViewClass.isNetworkAvailable(activityObject.getApplicationContext()))
		  {
			  remoteDataTask  = new RemoteDataTask();
			  remoteDataTask.execute();
			 
		  }
				
			 
	  }
	  
	// RemoteDataTask AsyncTask
			public class RemoteDataTask extends AsyncTask<Void, Void, Integer> {
				@Override
				protected void onPreExecute() {
					super.onPreExecute();
					if(!isCancelled())
					{
					if(toShowProgressBarOnPreExecute)
					{
					mProgressDialog = new ProgressDialog(activityObject);
					mProgressDialog.setMessage("Loading...");
					mProgressDialog.setIndeterminate(true);
					mProgressDialog.setCancelable(false);
					mProgressDialog.show();
					}
					}
				}
		 
				@Override
				protected Integer doInBackground(Void... params) {
					if(!isCancelled())
					{
					return activityObject.loadInBackGround();
					}
					return 0;
				}
		 
				@Override
				protected void onPostExecute(Integer result) {
					if(!isCancelled())
					{
					if(!toShowProgressBarOnPreExecute)
					{
						if(!activityObject.isFinishing())
						{
							mProgressDialog = new ProgressDialog(activityObject);
							mProgressDialog.setMessage("Loading...");
							mProgressDialog.setIndeterminate(true);
							mProgressDialog.setCancelable(false);
							mProgressDialog.show();
						}
					}
					if(result == 1)
					{
					activityObject.updateView();
					}else{
						if(activityObject != null)
						{
						Toast.makeText(activityObject, "we are not able to connect to server sry for inconvience!! " + activityObject.getClass().getName(), 1000).show();
						}
					}
					mProgressDialog.dismiss();
					}
				}
			}

			public RemoteDataTask getRemoteDataTask() {
				return remoteDataTask;
			}
			

}
