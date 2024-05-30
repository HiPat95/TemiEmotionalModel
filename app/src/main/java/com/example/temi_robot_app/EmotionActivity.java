package com.example.temi_robot_app;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.robotemi.sdk.NlpResult;
import com.robotemi.sdk.Robot;
import com.robotemi.sdk.TtsRequest;
import com.robotemi.sdk.activitystream.ActivityStreamPublishMessage;
import com.robotemi.sdk.listeners.OnBeWithMeStatusChangedListener;
import com.robotemi.sdk.listeners.OnDetectionDataChangedListener;
import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener;
import com.robotemi.sdk.listeners.OnLocationsUpdatedListener;
import com.robotemi.sdk.listeners.OnRobotReadyListener;
import com.robotemi.sdk.model.DetectionData;

import android.content.Intent;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Handler;

public class EmotionActivity extends AppCompatActivity  implements
        Robot.NlpListener,
        OnRobotReadyListener,
        Robot.ConversationViewAttachesListener,
        Robot.WakeupWordListener,
        Robot.ActivityStreamPublishListener,
        Robot.TtsListener,
        OnBeWithMeStatusChangedListener,
        OnGoToLocationStatusChangedListener,
        OnLocationsUpdatedListener,
        OnDetectionDataChangedListener {

    Robot robot = Robot.getInstance();
    ImageView left_led, right_led, eyes, eyes1;
    VideoView Disgust;
    VideoView Happyness;
    VideoView Sadness;
    VideoView Fear;
    VideoView Anger;
    VideoView Surprise;

    VideoView Neutral;

    VideoView Relaxation;

    DetectionData detData = null;

    String DisgustPath = "android.resource://com.example.temi_robot_app/"+R.raw.disgust;
    String HappynessPath = "android.resource://com.example.temi_robot_app/"+R.raw.happyness;

    String AngerPath = "android.resource://com.example.temi_robot_app/"+R.raw.anger;

    String SadnessPath = "android.resource://com.example.temi_robot_app/"+R.raw.sadness;

    String FearPath = "android.resource://com.example.temi_robot_app/"+R.raw.fear;

    String SurprisePath = "android.resource://com.example.temi_robot_app/"+R.raw.surprise;

    String NeutralPath = "android.resource://com.example.temi_robot_app/"+R.raw.neutral;

    String RelaxationPath = "android.resource://com.example.temi_robot_app/"+R.raw.relaxation;

    Uri uri_disgust = Uri.parse(DisgustPath);

    Uri uri_happyness = Uri.parse(HappynessPath);

    Uri uri_anger = Uri.parse(AngerPath);

    Uri uri_sadness = Uri.parse(SadnessPath);

    Uri uri_fear = Uri.parse(FearPath);

    Uri uri_surprise = Uri.parse(SurprisePath);

    Uri uri_neutral = Uri.parse(NeutralPath);

    Uri uri_relaxation = Uri.parse(RelaxationPath);

    private MediaPlayer mediaPlayer_disgust;
    private MediaPlayer mediaPlayer_happyness;

    private MediaPlayer mediaPlayer_sadness;

    private MediaPlayer mediaPlayer_anger;

    private MediaPlayer mediaPlayer_fear;

    private MediaPlayer mediaPlayer_surprise;

    private MediaPlayer mediaPlayer_relaxation;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        robot.hideTopBar();
        robot.tiltAngle(0);
        setContentView(R.layout.emotion_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String emotion = getIntent().getStringExtra("emotion");
        left_led = findViewById(R.id.left_led);
        right_led = findViewById(R.id.right_led);
        eyes = findViewById(R.id.eyes);
        eyes1 = findViewById(R.id.eyes1);
        mediaPlayer_disgust = MediaPlayer.create(this, R.raw.bleah);
        mediaPlayer_happyness = MediaPlayer.create(this, R.raw.ye);
        mediaPlayer_anger = MediaPlayer.create(this, R.raw.grrr);
        mediaPlayer_sadness = MediaPlayer.create(this, R.raw.sigh);
        mediaPlayer_surprise = MediaPlayer.create(this, R.raw.wow);
        mediaPlayer_fear = MediaPlayer.create(this, R.raw.ahh);
        mediaPlayer_relaxation = MediaPlayer.create(this, R.raw.hewa);

        Disgust = findViewById(R.id.disgust_video);
        Disgust.setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // not playVideo
                // playVideo();
                Disgust.setVisibility(View.INVISIBLE);
            }
        });
        Happyness = findViewById(R.id.happyness_video);
        Happyness.setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // not playVideo
                // playVideo();
                Happyness.setVisibility(View.INVISIBLE);
            }
        });

        Sadness = findViewById(R.id.sadness_video);
        Sadness.setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // not playVideo
                // playVideo();
                Sadness.setVisibility(View.INVISIBLE);
            }
        });

        Anger = findViewById(R.id.anger_video);
        Anger.setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // not playVideo
                // playVideo();
                Anger.setVisibility(View.INVISIBLE);
            }
        });

        Fear = findViewById(R.id.fear_video);
        Fear.setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // not playVideo
                // playVideo();
                Fear.setVisibility(View.INVISIBLE);
            }
        });

        Surprise = findViewById(R.id.surprise_video);
        Surprise.setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // not playVideo
                // playVideo();
                Surprise.setVisibility(View.INVISIBLE);
            }
        });

        Neutral = findViewById(R.id.neutral_video);
        Neutral.setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // not playVideo
                // playVideo();
                Neutral.setVisibility(View.INVISIBLE);
            }
        });

        Relaxation = findViewById(R.id.relaxation_video);
        Relaxation.setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // not playVideo
                // playVideo();
                Relaxation.setVisibility(View.INVISIBLE);
            }
        });


        startDoing();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    doSomething(emotion);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 3000);

    }

    protected void onStart() {
        super.onStart();
        Robot.getInstance().addOnRobotReadyListener(this);
        Robot.getInstance().addNlpListener(this);
        Robot.getInstance().addOnBeWithMeStatusChangedListener(this);
        Robot.getInstance().addOnGoToLocationStatusChangedListener(this);
        Robot.getInstance().addConversationViewAttachesListenerListener(this);
        Robot.getInstance().addWakeupWordListener(this);
        Robot.getInstance().addTtsListener(this);
        Robot.getInstance().addOnLocationsUpdatedListener(this);
    }

    protected void onStop() {
        super.onStop();
        Robot.getInstance().removeOnRobotReadyListener(this);
        Robot.getInstance().removeNlpListener(this);
        Robot.getInstance().removeOnBeWithMeStatusChangedListener(this);
        Robot.getInstance().removeOnGoToLocationStatusChangedListener(this);
        Robot.getInstance().removeConversationViewAttachesListenerListener(this);
        Robot.getInstance().removeWakeupWordListener(this);
        Robot.getInstance().removeTtsListener(this);
        Robot.getInstance().removeOnLocationsUpdateListener(this);
    }

    @Override
    public void onPublish(@NonNull ActivityStreamPublishMessage activityStreamPublishMessage) {

    }

    @Override
    public void onConversationAttaches(boolean b) {

    }

    @Override
    public void onNlpCompleted(@NonNull NlpResult nlpResult) {

    }

    @Override
    public void onTtsStatusChanged(@NonNull TtsRequest ttsRequest) {

    }

    @Override
    public void onWakeupWord(@NonNull String s, int i) {

    }

    @Override
    public void onBeWithMeStatusChanged(@NonNull String s) {

    }

    @Override
    public void onGoToLocationStatusChanged(@NonNull String s, @NonNull String s1, int i, @NonNull String s2) {

    }

    @Override
    public void onLocationsUpdated(@NonNull List<String> list) {

    }

    @Override
    public void onRobotReady(boolean b) {
        if (robot.isReady()) {
            refreshTemiUi();
        }
    }
    public void startDoing() {


        //eyes.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                robot.hideTopBar();
                robot.tiltAngle(10);
            }
        }, 200);
    }
    public void doSomething(String emotion) throws InterruptedException {
        // Color.parseColor("#4CAF50")  #E91E63   #313131
        ColorDrawable viewColor = (ColorDrawable) left_led.getBackground();
        int colorId = viewColor.getColor();
        String hexColor = String.format("#%06X", (0xFFFFFF & colorId));

        if("disgust".equals(emotion)) {
            robot.tiltAngle(50);
            robot.turnBy(30);
            startBlinking("disgust");


            Disgust.setVisibility(View.VISIBLE);
            Disgust.setVideoURI(uri_disgust);
            Disgust.requestFocus();
            Disgust.start();
            Disgust.setOnCompletionListener(new OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    Disgust.seekTo(Disgust.getDuration());
                }
            });


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer_disgust != null && !mediaPlayer_disgust.isPlaying()) {
                        mediaPlayer_disgust.start();
                    }
                }
            }, 50);

           new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    robot.turnBy(-30);
                    robot.tiltAngle(10);
                    onBackPressed();
                }
            }, 7000);

        }
        if("happiness".equals(emotion)) {
            robot.tiltAngle(50);
            startBlinking("happiness");


            Happyness.setVisibility(View.VISIBLE);
            Happyness.setVideoURI(uri_happyness);
            Happyness.requestFocus();
            Happyness.start();
            Happyness.setOnCompletionListener(new OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    Happyness.seekTo(Happyness.getDuration());
                }
            });

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer_happyness != null && !mediaPlayer_happyness.isPlaying()) {
                        mediaPlayer_happyness.start();
                    }
                }
            }, 50);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    robot.tiltAngle(10);
                    onBackPressed();
                }
            }, 7000);


        }

        if("anger".equals(emotion)) {
            robot.tiltAngle(-10);
            startBlinking("anger");


            Anger.setVisibility(View.VISIBLE);
            Anger.setVideoURI(uri_anger);
            Anger.requestFocus();
            Anger.start();
            Anger.setOnCompletionListener(new OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    Anger.seekTo(Anger.getDuration());
                }
            });


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer_anger != null && !mediaPlayer_anger.isPlaying()) {
                        mediaPlayer_anger.start();
                    }
                }
            }, 50);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    robot.tiltAngle(10);
                    onBackPressed();
                }
            }, 7000);


        }

        if("sadness".equals(emotion)) {
            robot.tiltAngle(-50);
            startBlinking("sadness");


            Sadness.setVisibility(View.VISIBLE);
            Sadness.setVideoURI(uri_sadness);
            Sadness.requestFocus();
            Sadness.start();
            Sadness.setOnCompletionListener(new OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    Sadness.seekTo(Sadness.getDuration());
                }
            });


           new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer_sadness != null && !mediaPlayer_sadness.isPlaying()) {
                        mediaPlayer_sadness.start();
                    }
                }
            }, 50);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    robot.tiltAngle(10);
                    onBackPressed();
                }
            }, 7000);


        }

        if("fear".equals(emotion)) {

            robot.turnBy(-50);
            robot.tiltAngle(-10);

            startBlinking("fear");


            Fear.setVisibility(View.VISIBLE);
            Fear.setVideoURI(uri_fear);
            Fear.requestFocus();
            Fear.start();
            Fear.setOnCompletionListener(new OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    Fear.seekTo(Fear.getDuration());
                }
            });


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer_fear != null && !mediaPlayer_fear.isPlaying()) {
                        mediaPlayer_fear.start();
                    }
                }
            }, 50);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    robot.turnBy(50);
                    robot.tiltAngle(10);
                    onBackPressed();
                }
            }, 7000);


        }

        if("surprise".equals(emotion)) {
            robot.tiltAngle(20);
            startBlinking("surprise");


            Surprise.setVisibility(View.VISIBLE);
            Surprise.setVideoURI(uri_surprise);
            Surprise.requestFocus();
            Surprise.start();
            Surprise.setOnCompletionListener(new OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    Surprise.seekTo(Surprise.getDuration());
                }
            });


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer_surprise != null && !mediaPlayer_surprise.isPlaying()) {
                        mediaPlayer_surprise.start();
                    }
                }
            }, 50);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    robot.tiltAngle(10);
                    onBackPressed();
                }
            }, 7000);

        }

            if ("relaxation".equals(emotion)) {
                robot.tiltAngle(20);
                startBlinking("relaxation");


                Relaxation.setVisibility(View.VISIBLE);
                Relaxation.setVideoURI(uri_relaxation);
                Relaxation.requestFocus();
                Relaxation.start();
                Relaxation.setOnCompletionListener(new OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        Relaxation.seekTo(Relaxation.getDuration());
                    }
                });


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mediaPlayer_relaxation != null && !mediaPlayer_relaxation.isPlaying()) {
                            mediaPlayer_relaxation.start();
                        }
                    }
                }, 50);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        robot.tiltAngle(10);
                        onBackPressed();
                    }
                }, 7000);


            }

            if ("neutral".equals(emotion)) {

                Neutral.setVisibility(View.VISIBLE);
                Neutral.setVideoURI(uri_neutral);
                Neutral.requestFocus();
                Neutral.start();
                Neutral.setOnCompletionListener(new OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        Neutral.seekTo(Neutral.getDuration());
                    }
                });


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onBackPressed();
                    }
                }, 7000);


            }
        if ("tracking".equals(emotion)) {

            eyes.setVisibility(View.VISIBLE);
            if(!robot.isDetectionModeOn()){
                robot.setDetectionModeOn(true);
                System.out.println("Detection is ON");
                robot.setTrackUserOn(true);
            }else{
                System.out.println("Detection already ON");
                robot.setTrackUserOn(true);
            }
/*
           if(detData != null){
               eyes1.setVisibility(View.VISIBLE);
           }

/*
            while(detData.isDetected()) {
                eyes.setVisibility(View.VISIBLE);
                double angle = detData.getAngle();
                if(angle>0 && angle<5) eyes1.setVisibility(View.VISIBLE);
            }*/


        }
        }

    public boolean isON = false;

    public void startBlinking(String emotion){

        if("disgust".equals(emotion)) {
            //Creating timer
            Timer timer = new Timer();
            TimerTask timer_task = new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (isON) {
                                left_led.setBackgroundColor(Color.parseColor("#000000"));
                                right_led.setBackgroundColor(Color.parseColor("#000000"));
                            } else {
                                left_led.setBackgroundColor(Color.parseColor("#55FF00"));
                                right_led.setBackgroundColor(Color.parseColor("#55FF00"));
                            }
                            isON = !isON;
                        }
                    });
                }
            };

            timer.scheduleAtFixedRate(timer_task, 0, 1200);
        }
        if("happiness".equals(emotion)) {
            //Creating timer
            Timer timer = new Timer();
            TimerTask timer_task = new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (isON) {
                                left_led.setBackgroundColor(Color.parseColor("#000000"));
                                right_led.setBackgroundColor(Color.parseColor("#000000"));
                            } else {
                                left_led.setBackgroundColor(Color.parseColor("#FFFF00"));
                                right_led.setBackgroundColor(Color.parseColor("#FFFF00"));
                            }
                            isON = !isON;
                        }
                    });
                }
            };

            timer.scheduleAtFixedRate(timer_task, 0, 400);
        }

        if("anger".equals(emotion)) {
            //Creating timer
            Timer timer = new Timer();
            TimerTask timer_task = new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (isON) {
                                left_led.setBackgroundColor(Color.parseColor("#000000"));
                                right_led.setBackgroundColor(Color.parseColor("#000000"));
                            } else {
                                left_led.setBackgroundColor(Color.parseColor("#FF0000"));
                                right_led.setBackgroundColor(Color.parseColor("#FF0000"));
                            }
                            isON = !isON;
                        }
                    });
                }
            };

            timer.scheduleAtFixedRate(timer_task, 0, 400);
        }

        if("sadness".equals(emotion)) {
            //Creating timer
            Timer timer = new Timer();
            TimerTask timer_task = new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (isON) {
                                left_led.setBackgroundColor(Color.parseColor("#000000"));
                                right_led.setBackgroundColor(Color.parseColor("#000000"));
                            } else {
                                left_led.setBackgroundColor(Color.parseColor("#0000FF"));
                                right_led.setBackgroundColor(Color.parseColor("#0000FF"));
                            }
                            isON = !isON;
                        }
                    });
                }
            };

            timer.scheduleAtFixedRate(timer_task, 0, 1100);
        }

        if("fear".equals(emotion)) {
            //Creating timer
            Timer timer = new Timer();
            TimerTask timer_task = new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (isON) {
                                left_led.setBackgroundColor(Color.parseColor("#000000"));
                                right_led.setBackgroundColor(Color.parseColor("#000000"));
                            } else {
                                left_led.setBackgroundColor(Color.parseColor("#800080"));
                                right_led.setBackgroundColor(Color.parseColor("#800080"));
                            }
                            isON = !isON;
                        }
                    });
                }
            };

            timer.scheduleAtFixedRate(timer_task, 0, 200);
        }

        if("surprise".equals(emotion)) {
            //Creating timer
            Timer timer = new Timer();
            TimerTask timer_task = new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (isON) {
                                left_led.setBackgroundColor(Color.parseColor("#000000"));
                                right_led.setBackgroundColor(Color.parseColor("#000000"));
                            } else {
                                left_led.setBackgroundColor(Color.parseColor("#FFFFFF"));
                                right_led.setBackgroundColor(Color.parseColor("#FFFFFF"));
                            }
                            isON = !isON;
                        }
                    });
                }
            };

            timer.scheduleAtFixedRate(timer_task, 0, 300);
        }


        if("relaxation".equals(emotion)) {
                    //Creating timer
                    Timer timer = new Timer();
                    TimerTask timer_task = new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (isON) {
                                        left_led.setBackgroundColor(Color.parseColor("#000000"));
                                        right_led.setBackgroundColor(Color.parseColor("#000000"));
                                    } else {
                                        left_led.setBackgroundColor(Color.parseColor("#80A0F0"));
                                        right_led.setBackgroundColor(Color.parseColor("#80A0F0"));
                                    }
                                    isON = !isON;
                                }
                            });
                        }
                    };

                    timer.scheduleAtFixedRate(timer_task, 0, 1400);
                }
    }






    private void refreshTemiUi() {
        try {
            ActivityInfo activityInfo = getPackageManager()
                    .getActivityInfo(getComponentName(), PackageManager.GET_META_DATA);
            Robot.getInstance().onStart(activityInfo);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void kill_me(View view) {
        finishAffinity();
    }

    @Override
    public void onDetectionDataChanged(@NonNull DetectionData detectionData) {
        detData = detectionData;
    }
}