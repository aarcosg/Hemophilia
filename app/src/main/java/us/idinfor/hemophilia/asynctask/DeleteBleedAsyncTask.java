package us.idinfor.hemophilia.asynctask;


import android.os.AsyncTask;

//TODO
public class DeleteBleedAsyncTask extends AsyncTask<Void,Void,Void>{

    Long bleedId;

    public DeleteBleedAsyncTask(Long bleedId) {
        this.bleedId = bleedId;
    }

    @Override
    protected Void doInBackground(Void... params) {
        return null;
    }
}
