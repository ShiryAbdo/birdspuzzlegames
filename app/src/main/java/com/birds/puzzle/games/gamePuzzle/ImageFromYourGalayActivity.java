package com.birds.puzzle.games.gamePuzzle;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.birds.puzzle.games.R;
import com.birds.puzzle.games.ui.MainCircleActivity;
import com.dolby.dap.DolbyAudioProcessing;
import com.dolby.dap.OnDolbyAudioProcessingEventListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.MessageFormat;
import static com.birds.puzzle.games.R.id.action_refresh;
import static com.birds.puzzle.games.R.id.selectImage;
import static com.birds.puzzle.games.R.id.removePhoto;




public class ImageFromYourGalayActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener,
        OnDolbyAudioProcessingEventListener, android.support.v7.app.ActionBar.TabListener {
    protected static final int MENU_SCRAMBLE = 0;
    protected static final int MENU_SELECT_IMAGE = 1;
    protected static final int MENU_TAKE_PHOTO = 2;

    protected static final int RESULT_SELECT_IMAGE = 0;
    protected static final int RESULT_TAKE_PHOTO = 1;

    protected static final String KEY_SHOW_NUMBERS = "showNumbers";
    protected static final String KEY_IMAGE_URI = "imageUri";
    protected static final String KEY_PUZZLE = "slidePuzzle";
    protected static final String KEY_PUZZLE_SIZE = "puzzleSize";

    protected static final String FILENAME_DIR = "com.birds.puzzle.games";
    protected static final String FILENAME_PHOTO_DIR = FILENAME_DIR + "/photo";
    protected static final String FILENAME_PHOTO = "photo.jpg";

    protected static  int DEFAULT_SIZE ;
    Bundle bundle ;
    int size ;
    String intentStringNumber ;
    Bitmap bitmap , bmp ;

    private SlidePuzzleView view;
    private SlidePuzzle slidePuzzle;
    private BitmapFactory.Options bitmapOptions;
    private int puzzleWidth = 1;
    private int puzzleHeight = 1;
    private Uri imageUri;
    private boolean portrait;
    private boolean expert;
    private TabLayout tabLayout;

    MediaPlayer mPlayer;
    DolbyAudioProcessing mDolbyAudioProcessing;
    private final java.util.List<String> mActList = new java.util.ArrayList<String>();


    PopupWindow popupWindow  ,pup;
    View popupView ,popup;
    int mCurrentX,mCurrentY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bundle=getIntent().getExtras();

        if(bundle!=null) {
            size=bundle.getInt("DEFAULT_SIZE");
            intentStringNumber= bundle.getString("intentStringNumber");
            byte[] byteArray = getIntent().getByteArrayExtra("image");
            bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        }
        Toast.makeText(getApplicationContext(),bmp.getGenerationId()+"",Toast.LENGTH_LONG).show();

        if(intentStringNumber.equals("Easy")){
            DEFAULT_SIZE=3;
            Toast.makeText(getApplicationContext(),intentStringNumber,Toast.LENGTH_LONG).show();
        }if(intentStringNumber.equals("Medium")){
            Toast.makeText(getApplicationContext(),intentStringNumber,Toast.LENGTH_LONG).show();


            DEFAULT_SIZE=4;
        }if(intentStringNumber.equals("Hard")){
            Toast.makeText(getApplicationContext(),intentStringNumber,Toast.LENGTH_LONG).show();

            DEFAULT_SIZE=5;

        }if(intentStringNumber.equals("Difficult")) {
            DEFAULT_SIZE=6;

        }

        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        //        setTitle("Capture your image");
        setTitle(Html.fromHtml("<small>Galary Image</small>"));
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.icone);






        bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;

        slidePuzzle = new SlidePuzzle();
        ImageFromYourGalayActivity  imageFromYourGalayActivity = new ImageFromYourGalayActivity();

        view = new SlidePuzzleView(this, slidePuzzle ,ImageFromYourGalayActivity.this);

        setContentView(view);


        view.post(new Runnable() {
            public void run() {
                popup = View.inflate(getApplicationContext(), R.layout.buttom,null);
                pup = new PopupWindow(popup, RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);


                mCurrentX = 20;
                mCurrentY = 50;

                pup.showAtLocation(view, Gravity.BOTTOM, mCurrentX, mCurrentY);
                Button btnClose = (Button)popup.findViewById(R.id.btnClose);
                Button dismis = (Button)popup.findViewById(R.id.dismis);
                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick( View v) {



                        view.post(new Runnable() {
                            public void run() {
                                popupView = View.inflate(getApplicationContext(), R.layout.show_image_popup,null);
                                popupWindow = new PopupWindow(popupView, RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);


                                mCurrentX = 20;
                                mCurrentY = 50;

                                popupWindow.showAtLocation( view, Gravity.CENTER, mCurrentX, mCurrentY);
                                ImageView image = (ImageView)popupView.findViewById(R.id.image);
                                image.setImageBitmap(bmp);


                                Button btnClose = (Button)popupView.findViewById(R.id.btnClose);

                                btnClose.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        popupWindow.dismiss();
                                    }
                                });


                                popupView.setOnTouchListener(new View.OnTouchListener() {
                                    private float mDx;
                                    private float mDy;

                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        int action = event.getAction();
                                        if (action == MotionEvent.ACTION_DOWN) {
                                            mDx = mCurrentX - event.getRawX();
                                            mDy = mCurrentY - event.getRawY();
                                        } else
                                        if (action == MotionEvent.ACTION_MOVE) {
                                            mCurrentX = (int) (event.getRawX() + mDx);
                                            mCurrentY = (int) (event.getRawY() + mDy);
                                            popupWindow.update(mCurrentX, mCurrentY, -1, -1);
                                        }
                                        return true;
                                    }
                                });

                            }
                        });

                    }
                });

                dismis.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pup.dismiss();
                     }
                });




            }
        });


if(bmp==null){
    view.post(new Runnable() {
        public void run() {
            popupView = View.inflate(getApplicationContext(), R.layout.popup,null);
            popupWindow = new PopupWindow(popupView, RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);


            mCurrentX = 20;
            mCurrentY = 50;

            popupWindow.showAtLocation(view, Gravity.CENTER, mCurrentX, mCurrentY);
            Button btnClose = (Button)popupView.findViewById(R.id.btnClose);

            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupWindow.dismiss();
                    selectImage();

                }
            });


            popupView.setOnTouchListener(new View.OnTouchListener() {
                private float mDx;
                private float mDy;

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();
                    if (action == MotionEvent.ACTION_DOWN) {
                        mDx = mCurrentX - event.getRawX();
                        mDy = mCurrentY - event.getRawY();
                    } else
                    if (action == MotionEvent.ACTION_MOVE) {
                        mCurrentX = (int) (event.getRawX() + mDx);
                        mCurrentY = (int) (event.getRawY() + mDy);
                        popupWindow.update(mCurrentX, mCurrentY, -1, -1);
                    }
                    return true;
                }
            });

        }
    });

}









        shuffle();

        if(!loadPreferences())
        {
            setPuzzleSize(DEFAULT_SIZE, true);
        }


        setBitmap(bmp);
//        Uri path = Uri.parse("android.resource://benten.puzzle.games/" + R.drawable.dabdob);

//        loadBitmap(path);
    }

    private void shuffle() {
        slidePuzzle.init(puzzleWidth, puzzleHeight);
        slidePuzzle.shuffle();
        view.invalidate();
        expert = view.getShowNumbers() == SlidePuzzleView.ShowNumbers.NONE;
    }

    protected void loadBitmap(Uri uri) {
        try
        {
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;

            InputStream imageStream = getContentResolver().openInputStream(uri);
            BitmapFactory.decodeStream(imageStream, null, o);

            int targetWidth = view.getTargetWidth();
            int targetHeight = view.getTargetHeight();

            if(o.outWidth > o.outHeight && targetWidth < targetHeight)
            {
                int i = targetWidth;
                targetWidth = targetHeight;
                targetHeight = i;
            }

            if(targetWidth < o.outWidth || targetHeight < o.outHeight)
            {
                double widthRatio = (double) targetWidth / (double) o.outWidth;
                double heightRatio = (double) targetHeight / (double) o.outHeight;
                double ratio = Math.max(widthRatio, heightRatio);

                o.inSampleSize = (int) Math.pow(2, (int) Math.round(Math.log(ratio) / Math.log(0.5)));
            }
            else
            {
                o.inSampleSize = 1;
            }

            o.inScaled = false;
            o.inJustDecodeBounds = false;

            imageStream = getContentResolver().openInputStream(uri);
             bitmap = BitmapFactory.decodeStream(imageStream, null, o);

            if(bitmap == null)
            {
                Toast.makeText(this, getString(R.string.error_could_not_load_image), Toast.LENGTH_LONG).show();
                return;
            }

            int rotate = 0;

            Cursor cursor = getContentResolver().query(uri, new String[] {MediaStore.Images.ImageColumns.ORIENTATION}, null, null, null);

            if(cursor != null)
            {
                try
                {
                    if(cursor.moveToFirst())
                    {
                        rotate = cursor.getInt(0);

                        if(rotate == -1)
                        {
                            rotate = 0;
                        }
                    }
                }
                finally
                {
                    cursor.close();
                }
            }

            if(rotate != 0)
            {
                Matrix matrix = new Matrix();
                matrix.postRotate(rotate);

                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            }

            setBitmap(bitmap);
            imageUri = uri;
        }
        catch(FileNotFoundException ex)
        {
            Toast.makeText(this, MessageFormat.format(getString(R.string.error_could_not_load_image_error), ex.getMessage()), Toast.LENGTH_LONG).show();
            return;
        }
    }

    private void setBitmap(Bitmap bitmap) {
        portrait = bitmap.getWidth() < bitmap.getHeight();

        view.setBitmap(bitmap);
        setPuzzleSize(Math.min(puzzleWidth, puzzleHeight), true);

//        setRequestedOrientation(portrait ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT : ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    private void selectImage() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, RESULT_SELECT_IMAGE);
    }

    private void takePicture()
    {
        File dir = getSaveDirectory();

        if(dir == null)
        {
            Toast.makeText(this, getString(R.string.error_could_not_create_directory_to_store_photo), Toast.LENGTH_SHORT).show();
            return;
        }

        File file = new File(dir, FILENAME_PHOTO);
        Intent photoPickerIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoPickerIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(photoPickerIntent, RESULT_TAKE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent)
    {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode)
        {
            case RESULT_SELECT_IMAGE:
            {
                if(resultCode == RESULT_OK)
                {
                    Uri selectedImage = imageReturnedIntent.getData();
                    loadBitmap(selectedImage);
                }

                break;
            }

            case RESULT_TAKE_PHOTO:
            {
                if(resultCode == RESULT_OK)
                {
                    File file = new File(getSaveDirectory(), FILENAME_PHOTO);

                    if(file.exists())
                    {
                        Uri uri = Uri.fromFile(file);

                        if(uri != null)
                        {
                            loadBitmap(uri);
                        }
                    }
                }

                break;
            }
        }
    }

    private File getSaveDirectory()
    {
        File root = new File(Environment.getExternalStorageDirectory().getPath());
        File dir = new File(root, FILENAME_PHOTO_DIR);

        if(!dir.exists())
        {
            if(!root.exists() || !dir.mkdirs())
            {
                return null;
            }
        }

        return dir;
    }

    private float getImageAspectRatio()
    {
        Bitmap bitmap = view.getBitmap();

        if(bitmap == null)
        {
            return 1;
        }

        float width = bitmap.getWidth();
        float height = bitmap.getHeight();

        return width / height;
    }

    protected void setPuzzleSize(int size, boolean scramble)
    {
        float ratio = getImageAspectRatio();

        if(ratio < 1)
        {
            ratio = 1f /ratio;
        }

        int newWidth;
        int newHeight;

        if(portrait)
        {
            newWidth = size;
            newHeight = (int) (size * ratio);
        }
        else
        {
            newWidth = (int) (size * ratio);
            newHeight = size;
        }

        if(scramble || newWidth != puzzleWidth || newHeight != puzzleHeight)
        {
            puzzleWidth = newWidth;
            puzzleHeight = newHeight;
            shuffle();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);

        onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_image, menu);

        return true;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        return onOptionsItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case action_refresh:
                shuffle();
                return true;

            case removePhoto:
                popupWindow.dismiss();
                ((ViewGroup)view.getParent()).removeView(view);
                final Dialog dialog = new Dialog(ImageFromYourGalayActivity.this, R.style.custom_dialog_theme);
                dialog.setContentView(R.layout.popup_capture);
                Button dialogButton = (Button) dialog.findViewById(R.id.btnClose);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectImage();
                        setContentView(view);
                        dialog.dismiss();
                    }
                });

                dialog.show();
                return true;

            case selectImage:
                setContentView(view);
                selectImage();
                return true;



            default:
                setContentView(view);
                return super.onOptionsItemSelected(item);
        }
    }

    protected SharedPreferences getPreferences()
    {
        return getSharedPreferences(MainPuzzle.class.getName(), Activity.MODE_PRIVATE);
    }
    @Override
    protected void onStop()
    {
        super.onStop();

        if (mPlayer != null) {
            mPlayer.pause();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mPlayer != null)
        {
            mPlayer.start();
        }
    }

    protected boolean loadPreferences()
    {
        SharedPreferences prefs = getPreferences();

        try
        {

            String s = prefs.getString(KEY_IMAGE_URI, null);

            if(s == null)
            {
                imageUri = null;
            }
            else
            {
                loadBitmap(Uri.parse(s));
            }

            int size = prefs.getInt(KEY_PUZZLE_SIZE, 0);
            s = prefs.getString(KEY_PUZZLE, null);

            if(size > 0 && s != null)
            {
                String[] tileStrings = s.split("\\;");

                if(tileStrings.length / size > 1)
                {
                    setPuzzleSize(size, false);
                    slidePuzzle.init(puzzleWidth, puzzleHeight);

                    int[] tiles = new int[tileStrings.length];

                    for(int i = 0; i < tiles.length; i++)
                    {
                        try
                        {
                            tiles[i] = Integer.parseInt(tileStrings[i]);
                        }
                        catch(NumberFormatException ex)
                        {
                        }
                    }

                    slidePuzzle.setTiles(tiles);
                }
            }

            return prefs.contains(KEY_SHOW_NUMBERS);
        }
        catch(ClassCastException ex)
        {
            // ignore broken settings
            return false;
        }
    }

    public void playSound()
    {
        if(mPlayer == null) {
            mPlayer = MediaPlayer.create(
                    ImageFromYourGalayActivity.this,
                    R.raw.slide);
            mPlayer.start();
        } else {
            mPlayer.release();
            mPlayer = null;
            mPlayer = MediaPlayer.create(
                    ImageFromYourGalayActivity.this,
                    R.raw.slide);
            mPlayer.start();
        }

        mDolbyAudioProcessing = DolbyAudioProcessing.getDolbyAudioProcessing(this, DolbyAudioProcessing.PROFILE.GAME, this);
        if (mDolbyAudioProcessing == null) {
            return;
        }
    }

    public void onFinish()
    {
        if(mPlayer == null) {
            mPlayer = MediaPlayer.create(
                    ImageFromYourGalayActivity.this,
                    R.raw.fireworks);
            mPlayer.start();
        } else {
            mPlayer.release();
            mPlayer = null;
            mPlayer = MediaPlayer.create(
                    ImageFromYourGalayActivity.this,
                    R.raw.fireworks);
            mPlayer.start();
        }

        mDolbyAudioProcessing = DolbyAudioProcessing.getDolbyAudioProcessing(this, DolbyAudioProcessing.PROFILE.GAME, this);
        if (mDolbyAudioProcessing == null) {
            Toast.makeText(this,
                    "Dolby Audio Processing not available on this device.",
                    Toast.LENGTH_SHORT).show();
            shuffle();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if(mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    @Override
    public void onDolbyAudioProcessingClientConnected() {
        mDolbyAudioProcessing.setEnabled(true);
    }

    @Override
    public void onDolbyAudioProcessingClientDisconnected() {
        mDolbyAudioProcessing.setEnabled(false);
    }

    @Override
    public void onDolbyAudioProcessingEnabled(boolean b) {
    }

    @Override
    public void onDolbyAudioProcessingProfileSelected(DolbyAudioProcessing.PROFILE profile) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("Dolby processing", "onDestroy()");

        // Release Media Player instance
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }

        this.releaseDolbyAudioProcessing();

    }

    @Override
    protected void onResume() {
        super.onResume();
        restartSession();

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Dolby processing", "The application is in background, supsendSession");
        //
        // If audio playback is not required while your application is in the background, restore the Dolby audio processing system
        // configuration to its original state by suspendSession().
        // This ensures that the use of the system-wide audio processing is sandboxed to your application.
        suspendSession();
    }

    public void releaseDolbyAudioProcessing() {
        if (mDolbyAudioProcessing != null) {
            try {
                mDolbyAudioProcessing.release();
                mDolbyAudioProcessing = null;
            } catch (IllegalStateException ex) {
                handleIllegalStateException(ex);
            } catch (RuntimeException ex) {
                handleRuntimeException(ex);
            }
        }

    }

    // Backup the system-wide audio effect configuration and restore the application configuration
    public void restartSession() {
        if (mDolbyAudioProcessing != null) {
            try{
                mDolbyAudioProcessing.restartSession();
            } catch (IllegalStateException ex) {
                handleIllegalStateException(ex);
            } catch (RuntimeException ex) {
                handleRuntimeException(ex);
            }
        }
    }

    // Backup the application Dolby Audio Processing configuration and restore the system-wide configuration
    public void suspendSession() {

        if (mDolbyAudioProcessing != null) {
            try{
                mDolbyAudioProcessing.suspendSession();
            } catch (IllegalStateException ex) {
                handleIllegalStateException(ex);
            } catch (RuntimeException ex) {
                handleRuntimeException(ex);
            }
        }
    }

    /** Generic handler for IllegalStateException */
    private void handleIllegalStateException(Exception ex)
    {
        Log.e("Dolby processing", "Dolby Audio Processing has a wrong state");
        handleGenericException(ex);
    }

    /** Generic handler for IllegalArgumentException */
    private void handleIllegalArgumentException(Exception ex)
    {
        Log.e("Dolby processing","One of the passed arguments is invalid");
        handleGenericException(ex);
    }

    /** Generic handler for RuntimeException */
    private void handleRuntimeException(Exception ex)
    {
        Log.e("Dolby processing", "Internal error occured in Dolby Audio Processing");
        handleGenericException(ex);
    }

    private void handleGenericException(Exception ex)
    {
        Log.e("Dolby processing", Log.getStackTraceString(ex));
    }

    @Override
    public void onTabSelected(android.support.v7.app.ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabUnselected(android.support.v7.app.ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(android.support.v7.app.ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onBackPressed() {
        pup.dismiss();
         Intent intent = new Intent(ImageFromYourGalayActivity.this,   MainCircleActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}