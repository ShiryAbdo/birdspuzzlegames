package com.birds.puzzle.games.gameMomery;


import com.birds.puzzle.games.R;

import java.util.ArrayList;
import java.util.Random;



/**
 * Created by falcon on 12/10/2017.
 */

public class EasyData {
//    size image 704 *704


    ArrayList<Integer> numberArray =new ArrayList<>();




    int [] arraImagRtage ={
            R.drawable.a,
            R.drawable.b,
            R.drawable.d,
            R.drawable.f,
            R.drawable.g,
            R.drawable.c,
            R.drawable.i
            , R.drawable.j
            ,R.drawable.k
            ,R.drawable.l
            ,R.drawable.e
            ,R.drawable.h,



            R.drawable.m,
            R.drawable.n,
            R.drawable.o,
            R.drawable.p,
            R.drawable.q,
            R.drawable.r,
            R.drawable.s
            ,R.drawable.t
            ,R.drawable.w
            ,R.drawable.v,



            R.drawable.x,
            R.drawable.y,
            R.drawable.z,
            R.drawable.u,
            R.drawable.ab,
            R.drawable.ac,
            R.drawable.aa
            ,R.drawable.ad
            ,R.drawable.ae
            ,R.drawable.af,

            R.drawable.ag,
            R.drawable.ai,
            R.drawable.aj,
            R.drawable.ak,
            R.drawable.al,
            R.drawable.am,
            R.drawable.an
            ,R.drawable.ao
            ,R.drawable.ap
            ,R.drawable.aq,


            R.drawable.ar,
            R.drawable.as,
            R.drawable.at,
            R.drawable.au,
            R.drawable.av,
             R.drawable.ax
            ,R.drawable.ay
            ,R.drawable.ah

 };


    int range = arraImagRtage.length;
    int myImgCount = 0;

    Random random = new Random();



    public  ArrayList<Integer> getEasyDataArray() {

        for( int i = 0; i < range; ++i)
        {
             if(!numberArray.contains(arraImagRtage[i])){
                numberArray.add(arraImagRtage[i]);
            }
        }

        if(numberArray.size()<range+1){
            for( int i = 0; i < range; ++i)
            {
                 if(!numberArray.contains(arraImagRtage[i])){
                    numberArray.add(arraImagRtage[i]);
                }
            }

        }



        return numberArray;
    }

//
//    public  ArrayList<Integer> getEasyDataArray() {
//
//        for( int i = 0; i < range; ++i)
//        {
//            myImgCount = random.nextInt(range);
//            if(!numberArray.contains(arraImagRtage[myImgCount])){
//                numberArray.add(arraImagRtage[myImgCount]);
//            }
//        }
//
//        if(numberArray.size()<range+1){
//            for( int i = 0; i < range; ++i)
//            {
//                myImgCount = random.nextInt(range);
//                if(!numberArray.contains(arraImagRtage[myImgCount])){
//                    numberArray.add(arraImagRtage[myImgCount]);
//                }
//            }
//
//        }
//
//
//
//        return numberArray;
//    }




}
