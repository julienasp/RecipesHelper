package tasker;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
* DownloadImageTask is a child of AsyncTask<String, Void, Bitmap>.
* Was use to run an AsyncTask for the download of images. 
* We now use the picasso library for all future image download.
*
* @author  Julien Aspirot
* @version 1.0
* @since   2017-02-04
* @deprecated Use Picasso Library instead
*/
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    private ImageView bmImage;
    private long startTime;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        //Chrono start
        this.startTime = System.nanoTime();
        String urldisplay = urls[0];
        Bitmap mImg = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mImg = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mImg;
    }

    protected void onPostExecute(Bitmap result) {
        if(result != null) bmImage.setImageBitmap(result);
        long totalTime = System.nanoTime() - startTime;
        Log.i("timer", "DownloadImageTask took: " + totalTime/1e6 + " ms.");
    }
}
