

package com.document.allreader.allofficefilereader.simpletext.control;

import android.os.AsyncTask;
import java.util.concurrent.RejectedExecutionException;



public abstract class SafeAsyncTask<Params, Progress, Result> extends
    AsyncTask<Params, Progress, Result>
{
    /**
     * 
     * @param params
     */
    public void safeExecute(Params...params)
    {
        try
        {
            execute(params);
        }
        catch(RejectedExecutionException e)
        {
            //MainControl.sysKit.getErrorKit().writerLog(e);
            onPreExecute();
            if (isCancelled())
            {
                onCancelled();
            }
            else
            {
                onPostExecute(doInBackground(params));
            }
        }
    }
}
