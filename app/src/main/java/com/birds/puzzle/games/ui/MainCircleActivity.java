package com.birds.puzzle.games.ui;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;

import com.birds.puzzle.games.R;
import com.birds.puzzle.games.gamePuzzle.BaseActivity;
import com.birds.puzzle.games.gamePuzzle.ImageFromYourGalayActivity;
import com.birds.puzzle.games.gamePuzzle.YourImageActivity;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;




public class MainCircleActivity extends Activity {
    SharedPreferences.Editor editor;
    SharedPreferences sharedPref_easy;
    SharedPreferences sharedPref_medium;
    SharedPreferences sharedPref_hard;
    SharedPreferences sharedPref_difficult;

    private int REQUEST_CAMERA = 0 , SELECT_FILE = 1;;
    private Uri imageUri;
    Bitmap thumbnail ,selectedImage;
    Bundle bundle = new Bundle();
 String intentStringNumber ;
	private CircleMenuLayout mCircleMenuLayout;
	private String[] mItemTexts = new String[] { "", "", "","", "", ""};
    String easy ,medium ,hard ,difficult;
    TextDrawable Capture_your_puzzle  ,Easyy ,Medium ,Hard ,Difficult,Your_photo_puzzle,User_Acount;


	private ArrayList<TextDrawable> gg ;

	private ArrayList<Integer> mItemImgs;
//            = new int[] {
//            R.drawable.easy,
//			R.drawable.medium,
//            R.drawable.hard,
//			R.drawable.difficult,
//            R.drawable.capture,
//            R.drawable.photo};

    private ArrayList<Integer> newImage ;
//            new int[] {
//            R.drawable.hard,
//            R.drawable.hard,
//            R.drawable.hard,
//            R.drawable.hard,
//            R.drawable.hard,
//            R.drawable.hard};



      FloatingActionMenu rightLowerMenu;
      FloatingActionMenu rightLoerMenu ;
    LinearLayout  LinearLayout ;
    @Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main02);
        mItemImgs= new ArrayList<>();
        newImage= new ArrayList<>();
        mItemImgs.add(R.drawable.easy);
        mItemImgs.add(R.drawable.medium);
        mItemImgs.add(R.drawable.hard);
        mItemImgs.add(R.drawable.difficult);
        mItemImgs.add(R.drawable.capture);
        mItemImgs.add(R.drawable.galary);

        newImage.add( R.drawable.easy_star);
        newImage.add( R.drawable.medium_star);
        newImage.add( R.drawable.hard_star);
        newImage.add( R.drawable.difficult_star);
        newImage.add( R.drawable.vvvv);
        newImage.add( R.drawable.vvvv);

        mCircleMenuLayout = (CircleMenuLayout) findViewById(R.id.id_menulayout);
        bundle = new Bundle();

		gg= new ArrayList<>();
        easy="Easy";
        medium="Medium";
        hard ="Hard";
        difficult ="Difficult";
        sharedPref_easy = getApplicationContext().getSharedPreferences("catogerys", Context.MODE_PRIVATE);
        editor = sharedPref_easy.edit();
        String eyc= sharedPref_easy.getString(easy,"");
        String myc= sharedPref_easy.getString(medium,"");
        String hyc= sharedPref_easy.getString(hard,"");
        String dyc= sharedPref_easy.getString(difficult,"");
        ArrayList<String>checed =new ArrayList<>();
        if(!eyc.equals("")){
            checed.add(eyc);
        }
        if(!myc.equals("")){
            checed.add(myc);
        }
        if(!hyc.equals("")){
            checed.add(hyc);
        }
        if(!dyc.equals("")){
            checed.add(dyc);
        }
        if(!checed.isEmpty()){
            for ( int i =0;i<checed.size();i++){
                mItemImgs.set(i,newImage.get(i));


            }

        }

        mCircleMenuLayout.setMenuItemIconsAndTexts(mItemImgs, mItemTexts);



		  Easyy = TextDrawable.builder()
				.beginConfig()
                .textColor(Color.BLACK)
                .useFont(Typeface.createFromAsset(this.getAssets(),"fonts/gothic_bold.otf"))
                .fontSize(25)
                .withBorder(5)
                .width(120)  // width in px
                .height(90) // height in px
                .endConfig()
                .buildRoundRect("Easy",Color.rgb(128, 204, 51), 30);

		  Medium = TextDrawable.builder()
                .beginConfig()
                .textColor(Color.BLACK)
                .useFont(Typeface.createFromAsset(this.getAssets(),"fonts/gothic_bold.otf"))
                .fontSize(25)
                .withBorder(5)
                .width(120)  // width in px
                .height(90) // height in px
                .endConfig()
                .buildRoundRect("Medium",Color.rgb(128, 204, 51), 30);
 		  Hard = TextDrawable.builder()
                .beginConfig()
                .textColor(Color.BLACK)
                .useFont(Typeface.createFromAsset(this.getAssets(),"fonts/gothic_bold.otf"))
                .fontSize(25)
                .withBorder(5)
                .width(120)  // width in px
                .height(90) // height in px
                .endConfig()
                .buildRoundRect("Hard", Color.rgb(128, 204, 51), 30);
          Difficult = TextDrawable.builder()
                .beginConfig()
                 .textColor(Color.BLACK)
                .useFont(Typeface.createFromAsset(this.getAssets(),"fonts/gothic_bold.otf"))
                .fontSize(25)
                .withBorder(5)
                .width(120)  // width in px
                .height(90) // height in px
                .endConfig()
                .buildRoundRect("Difficult", Color.rgb(128, 204, 51), 30);


         Capture_your_puzzle = TextDrawable.builder()
                .beginConfig()
                .textColor(Color.BLACK)
                .useFont(Typeface.createFromAsset(this.getAssets(),"fonts/gothic_bold.otf"))
                .fontSize(25)
                .withBorder(5)
                .width(120)  // width in px
                .height(90) // height in px
                .endConfig()
                .buildRoundRect("Capture", Color.rgb(128, 204, 51), 30);

          Your_photo_puzzle = TextDrawable.builder()
                .beginConfig()
                .textColor(Color.BLACK)
                .useFont(Typeface.createFromAsset(this.getAssets(),"fonts/gothic_bold.otf"))
                .fontSize(20)
                .withBorder(5)
                .width(150)  // width in px
                .height(90) // height in px
                .endConfig()
                .buildRoundRect("Galary Photo",Color.rgb(128, 204, 51), 30);

          User_Acount = TextDrawable.builder()
                .beginConfig()
                .textColor(Color.BLACK)
                .useFont(Typeface.createFromAsset(this.getAssets(),"fonts/gothic_bold.otf"))
                .fontSize(25)
                .withBorder(5)
                .width(120)  // width in px
                .height(90) // height in px
                .endConfig()
                .buildRoundRect("Acount", Color.rgb(128, 204, 51), 30);


		gg.add(Easyy);
		gg.add(Medium);
        gg.add(Hard);
        gg.add(Difficult);
        gg.add(Capture_your_puzzle);
        gg.add(Your_photo_puzzle);

        TextDrawable image1 = TextDrawable.builder()
                .beginConfig()
                .textColor(Color.BLACK)
                .useFont(Typeface.createFromAsset(this.getAssets(),"fonts/gothic_bold.otf"))
                .fontSize(25)
                .withBorder(5)
                .width(130)  // width in px
                .height(100) // height in px
                .endConfig()
                .buildRoundRect("Easy", Color.rgb(128, 204, 51), 30);


        // more or less with default parameter
        final ImageView fabIconNew = new ImageView(this);
        fabIconNew.setImageDrawable(getResources().getDrawable(R.drawable.refresh));
        final TextDrawable image2 = TextDrawable.builder()
                .beginConfig()
                .textColor(Color.BLACK)
                .useFont(Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/gothic_bold.otf"))
                .fontSize(25)
                .withBorder(5)
                .width(130)  // width in px
                .height(100) // height in px
                .endConfig()
                .buildRoundRect("Medium",Color.rgb(128, 204, 51), 30);
        final TextDrawable image3 = TextDrawable.builder()
                .beginConfig()
                .textColor(Color.BLACK)
                .useFont(Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/gothic_bold.otf"))
                .fontSize(25)
                .withBorder(5)
                .width(130)  // width in px
                .height(100) // height in px
                .endConfig()
                .buildRoundRect("Hard",Color.rgb(128, 204, 51), 30);
        final TextDrawable image4 = TextDrawable.builder()
                .beginConfig()
                .textColor(Color.BLACK)
                .useFont(Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/gothic_bold.otf"))
                .fontSize(25)
                .withBorder(5)
                .width(130)  // width in px
                .height(100) // height in px
                .endConfig()
                .buildRoundRect("Difficult",Color.rgb(128, 204, 51), 30);



        final SubActionButton.Builder rLSubBuilder = new SubActionButton.Builder(this);
        final ImageView rlIcon1 = new ImageView(this);
        final ImageView rlIcon2 = new ImageView(this);
        final ImageView rlIcon3 = new ImageView(this);
        final ImageView rlIcon4 = new ImageView(this);
        final ImageView rlIcon11 = new ImageView(this);
        final ImageView rlIcon22 = new ImageView(this);
        final ImageView rlIcon33 = new ImageView(this);
        final ImageView rlIcon44 = new ImageView(this);

//        rlIcon1.setImageDrawable(image1);
        rlIcon1.setImageResource(R.drawable.easy_small);
        rlIcon2.setImageResource(R.drawable.hard_smal);
        rlIcon3.setImageResource(R.drawable.medium_smal);
        rlIcon4.setImageResource(R.drawable.difficult_smale);
        rlIcon11.setImageResource(R.drawable.easy_small);
        rlIcon22.setImageResource(R.drawable.hard_smal);
        rlIcon33.setImageResource(R.drawable.medium_smal);
        rlIcon44.setImageResource(R.drawable.difficult_smale);

		mCircleMenuLayout.setOnMenuItemClickListener(new CircleMenuLayout.OnMenuItemClickListener()
		{

			@Override
			public void itemClick(final View view, final int pos)


			{

                Intent intent ;

                switch (pos) {
                    case 0:

                         intent = new Intent(MainCircleActivity.this,BaseActivity.class);
                        intent.putExtra("catogery",easy);
                         startActivity(intent);
                        finish();


                        break;
                    case 1:

                        intent = new Intent(MainCircleActivity.this,BaseActivity.class);
                        intent.putExtra("catogery",medium);
                        startActivity(intent);
                        finish();

                        break;
                    case 2:

                        intent = new Intent(MainCircleActivity.this,BaseActivity.class);
                        intent.putExtra("catogery",hard);
                        startActivity(intent);
                        finish();
                        break;
                    case 3:

                          intent = new Intent(MainCircleActivity.this,BaseActivity.class);
                        intent.putExtra("catogery",difficult);
                        intent.putExtra("DEFAULT_SIZE",5);
                        startActivity(intent);
                        finish();
                        break;
                    case 4:
                        rightLowerMenu = new FloatingActionMenu.Builder(MainCircleActivity.this)
                                .addSubActionView(rLSubBuilder.setContentView(rlIcon11).build(),150,150)
                                .addSubActionView(rLSubBuilder.setContentView(rlIcon22).build(),150,150)
                                .addSubActionView(rLSubBuilder.setContentView(rlIcon33).build(),150,150)
                                .addSubActionView(rLSubBuilder.setContentView(rlIcon44).build(),150,150)
                                 .attachTo(view)
                                .build();
                        rlIcon11.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                intentStringNumber ="Easy";
                                cameraIntent();
                                //Convert to byte array




                            }
                        });
                        rlIcon22.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                intentStringNumber ="Medium";
                                cameraIntent();
                            }
                        });
                        rlIcon33.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                intentStringNumber ="Hard";
                                cameraIntent();

                            }
                        });
                        rlIcon44.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                intentStringNumber ="Difficult";
                                cameraIntent();

                            }
                        });

 //                         // Listen menu open and close events to animate the button content view
                        rightLowerMenu.setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
                            @Override
                            public void onMenuOpened(FloatingActionMenu menu) {

//                                 Rotate the icon of rightLowerButton 45 degrees clockwise
                                fabIconNew.setRotation(0);
                                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 10);
                                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIconNew, pvhR);
                                animation.start();
                            }

                            @Override
                            public void onMenuClosed(FloatingActionMenu menu) {


                                // Rotate the icon of rightLowerButton 45 degrees counter-clockwise
                                fabIconNew.setRotation(45);
                                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 0);
                                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIconNew, pvhR);
                                animation.start();
                            }
                        });



                        break;
                    case 5:

                          rightLoerMenu = new FloatingActionMenu.Builder(MainCircleActivity.this)
                                .addSubActionView(rLSubBuilder.setContentView(rlIcon1).build(),150,150)
                                .addSubActionView(rLSubBuilder.setContentView(rlIcon2).build(),150,150)
                                .addSubActionView(rLSubBuilder.setContentView(rlIcon3).build(),150,150)
                                .addSubActionView(rLSubBuilder.setContentView(rlIcon4).build(),150,150)
                                .attachTo(view)
                                .build();

                        rlIcon1.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                intentStringNumber ="Easy";
                                galleryIntent();
//                                Intent intent = new Intent(getApplicationContext(),ImageFromYourGalayActivity.class);
//                                intent.putExtra("DEFAULT_SIZE",3);
//                                startActivity(intent);
//                                finish();


                            }
                        });
                        rlIcon2.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                intentStringNumber ="Medium";
                                galleryIntent();

                            }
                        });
                        rlIcon3.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                intentStringNumber ="Hard";
                                galleryIntent();
                            }
                        });
                        rlIcon4.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                intentStringNumber ="Difficult";
                                galleryIntent();

                            }
                        });

                        //                         // Listen menu open and close events to animate the button content view
                        rightLoerMenu.setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
                            @Override
                            public void onMenuOpened(FloatingActionMenu menu) {



                                // Rotate the icon of rightLowerButton 45 degrees clockwise
                                fabIconNew.setRotation(0);
                                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 45);
                                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIconNew, pvhR);
                                animation.start();
                            }

                            @Override
                            public void onMenuClosed(FloatingActionMenu menu) {
                                gg.set(pos,Capture_your_puzzle);
//                                // Rotate the icon of rightLowerButton 45 degrees counter-clockwise
                                fabIconNew.setRotation(45);
                                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 0);
                                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIconNew, pvhR);
                                animation.start();
                            }
                        });
                        break;

                    default: System.out.println("Invalid month.");break;
                }

			}

			@Override
			public void itemCenterClick(View view)
			{
				Toast.makeText(MainCircleActivity.this,"Chose Game",Toast.LENGTH_SHORT).show();
			}
		});




	}


    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private void cameraIntent()
    {
//         isStoragePermissionGranted();
//        ContentValues  values = new ContentValues();
//        values.put(MediaStore.Images.Media.TITLE, "New Picture");
//        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
//        imageUri = getContentResolver().insert(
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//        startActivityForResult(intent, REQUEST_CAMERA);
        File f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");


        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE){
                onSelectFromGalleryResult(data);
            }else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        selectedImage = getResizedBitmap(bm, 500);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        selectedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        Intent intent = new Intent(getApplicationContext(),ImageFromYourGalayActivity.class);
        intent.putExtra("image",byteArray);
        intent.putExtra("DEFAULT_SIZE",3);
        intent.putExtra("intentStringNumber",intentStringNumber);
        startActivity(intent);
        finish();
    }

    private void onCaptureImageResult(Intent data) {
        thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        if(thumbnail!=null){
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        }



        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        selectedImage = getResizedBitmap(thumbnail, 500);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        selectedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        Intent intent = new Intent(getApplicationContext(),YourImageActivity.class);
        intent.putExtra("image",byteArray);
        intent.putExtra("DEFAULT_SIZE",3);
        intent.putExtra("intentStringNumber",intentStringNumber);
        startActivity(intent);
        finish();

    }


    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
}