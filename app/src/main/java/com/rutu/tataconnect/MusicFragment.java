package com.rutu.tataconnect;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MusicFragment extends Fragment {
    TextView tvsongname,tvstarttim,tvTotalTime;
    ImageView ivsongimage,ivnext,ivbackward,ivplay,ivforward,ivprevious;
    SeekBar sbsongtime;
    private int currentindex=0;
    MediaPlayer mediaPlayer;
    private static int sTime=0,tTime=0,oTime=0,bTime=5000,fTime=5000;
    Handler h=new Handler();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_music, container, false);
        tvsongname=view.findViewById(R.id.tvmusicplayersongname);
        tvstarttim=view.findViewById(R.id.tvmusicplayerstarttime);
        tvTotalTime=view.findViewById(R.id.tvmusicplayerendtime);
        ivsongimage=view.findViewById(R.id.ivmusiccenterphoto);
        ivnext=view.findViewById(R.id.ivmusicnext);
        ivbackward=view.findViewById(R.id.ivmusicbackward);
        ivforward=view.findViewById(R.id.ivmusicforward);
        ivplay=view.findViewById(R.id.ivmusicpause);
        ivprevious=view.findViewById(R.id.ivmusicprevious);
        sbsongtime=view.findViewById(R.id.sbmusicplayer);

        ArrayList<Integer> songarraylist = new ArrayList<>();
        songarraylist.add(0,R.raw.tenu_khabar_nhi);
        songarraylist.add(1,R.raw.onmyway);
        songarraylist.add(2,R.raw.pasoori);
        songarraylist.add(3,R.raw.man_mera);
        songarraylist.add(4,R.raw.sochahai);
        // songarraylist.add(5,R.raw.mera_yaar

        mediaPlayer=MediaPlayer.create(getActivity(),songarraylist.get(currentindex));
        ivplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer!=null&&mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    ivplay.setImageResource(R.drawable.icon_play);
                }
                else {
                    mediaPlayer.start();
                    ivplay.setImageResource(R.drawable.icon_pause);
                }
                tTime=mediaPlayer.getDuration();
                sTime=mediaPlayer.getCurrentPosition();
                if (oTime==0){
                    sbsongtime.setMax(tTime);
                    oTime=1;
                }
                tvTotalTime.setText(String.format("%d:%d",
                        TimeUnit.MILLISECONDS.toMinutes(tTime),
                        TimeUnit.MILLISECONDS.toSeconds(tTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(tTime))
                ));
                tvstarttim.setText(String.format("%d:%d",
                        TimeUnit.MILLISECONDS.toMinutes(sTime),
                        TimeUnit.MILLISECONDS.toSeconds(sTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(sTime))
                ));
                h.postDelayed(UpdateSongTime,1000);
                songDetails();
            }
        });
        sbsongtime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b){
                    mediaPlayer.seekTo(i);
                    sbsongtime.setProgress(i);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        ivnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentindex<songarraylist.size()-1){
                    currentindex++;
                }else {
                    currentindex=0;

                }
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                if (mediaPlayer!=null){
                    ivplay.setImageResource(R.drawable.icon_pause);
                }
                mediaPlayer=MediaPlayer.create(getActivity(),songarraylist.get(currentindex));
                tTime=mediaPlayer.getDuration();
                sTime=mediaPlayer.getCurrentPosition();
                oTime=0;
                if (oTime==0){
                    sbsongtime.setMax(tTime);
                    oTime=1;
                }
                tvTotalTime.setText(String.format("%d:%d ",
                        TimeUnit.MILLISECONDS.toMinutes(tTime),
                        TimeUnit.MILLISECONDS.toSeconds(tTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(tTime))
                ));
                tvstarttim.setText(String.format("%d:%d",
                        TimeUnit.MILLISECONDS.toMinutes(sTime),
                        TimeUnit.MILLISECONDS.toSeconds(sTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(sTime))
                ));
                h.postDelayed(UpdateSongTime,1000);
                mediaPlayer.start();
                songDetails();
            }
        });
        ivprevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentindex>0){
                    currentindex--;
                }else {
                    currentindex=songarraylist.size()-1;

                }
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                if (mediaPlayer!=null){
                    ivplay.setImageResource(R.drawable.icon_pause);
                }
                mediaPlayer=MediaPlayer.create(getActivity(),songarraylist.get(currentindex));
                tTime=mediaPlayer.getDuration();
                sTime=mediaPlayer.getCurrentPosition();
                oTime=0;
                if (oTime==0){
                    sbsongtime.setMax(tTime);
                    oTime=1;
                }
                tvTotalTime.setText(String.format("%d:%d",
                        TimeUnit.MILLISECONDS.toMinutes(tTime),
                        TimeUnit.MILLISECONDS.toSeconds(tTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(tTime))
                ));
                tvstarttim.setText(String.format("%d:%d",
                        TimeUnit.MILLISECONDS.toMinutes(sTime),
                        TimeUnit.MILLISECONDS.toSeconds(sTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(sTime))
                ));
                h.postDelayed(UpdateSongTime,1000);
                mediaPlayer.start();
                songDetails();
            }
        });
        ivbackward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((sTime-bTime)>0){
                    sTime=sTime-bTime;
                    mediaPlayer.seekTo(sTime);
                }else {
                    Toast.makeText(getActivity(),"Cannot Jump backward for 5 Seconds",Toast.LENGTH_SHORT).show();
                }
            }
        });
        ivforward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((sTime+fTime)<tTime){
                    sTime=sTime+fTime;
                    mediaPlayer.seekTo(sTime);
                }else {
                    Toast.makeText(getActivity(),"Cannot Jump Forward for 5 Seconds",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
    private void songDetails(){
        if(currentindex==0)
        {
            tvsongname.setText("Tainu Khabar Nahi ('Munjya')...");
            ivsongimage.setImageResource(R.drawable.tenu_khabar_nahi_img);
        } else if (currentindex==1) {
            tvsongname.setText("On My Way By 'Alan Walker'...");
            ivsongimage.setImageResource(R.drawable.onmyway_img);
        }else if (currentindex==2) {
            tvsongname.setText("Pasoori...");
            ivsongimage.setImageResource(R.drawable.pasoori_img);
        }else if (currentindex==3) {
            tvsongname.setText("Mann Mera...");
            ivsongimage.setImageResource(R.drawable.man_mera_img);
        }else if (currentindex==4) {
            tvsongname.setText("Socha hai From('Badshaho')");
            ivsongimage.setImageResource(R.drawable.sochahai_img);
        }
    }
    private Runnable UpdateSongTime=new Runnable() {
        @Override
        public void run() {
            sTime=mediaPlayer.getCurrentPosition();
            tvstarttim.setText(String.format("%d:%d",
                    TimeUnit.MILLISECONDS.toMinutes(sTime),
                    TimeUnit.MILLISECONDS.toSeconds(sTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(sTime))  ));
            sbsongtime.setProgress(sTime);
            h.postDelayed(this,1000);
        }
    };
}

