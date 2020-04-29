package alles.fastalles.ameisenboss.gome;

import android.content.Context;
import android.os.Vibrator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



public class timer extends AppCompatActivity {
    TextView timerView;
    TextView setsView;
    TextView goRestView;
    long workoutTime;
    long restTime;
    long sets;
    long currentSet = 1;
    boolean goActive = true;
    Button startPause;
    Button reset;
    Button skip;
    Button plus;
    boolean timeActive = false;
    CountDownTimer countdown;
    long currentTime;
    boolean timeReset = true;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer);

        startPause = (Button) findViewById(R.id.startpause);
        reset = (Button) findViewById(R.id.reset);
        skip = (Button) findViewById(R.id.skip);
        plus = (Button) findViewById(R.id.plus);

        skip.setEnabled(false);
        plus.setEnabled(false);
        reset.setEnabled(false);

        timerView = (TextView) findViewById(R.id.timer);
        setsView = (TextView) findViewById(R.id.sets);
        goRestView = (TextView) findViewById(R.id.goRest);

        Bundle extras = getIntent().getExtras();

        sets = Integer.parseInt(extras.getString("sets"));
        workoutTime = Integer.parseInt(extras.getString("workoutTime"));
        restTime = Integer.parseInt(extras.getString("restTime"));

        timerView.setText("-" + workoutTime + "-");
        setsView.setText("Sets: 1/" + sets);

        currentTime = workoutTime;

        startPause.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!timeActive) {
                    //starts the countdown
                    startPause.setEnabled(false);
                    reset.setEnabled(false);
                    int five;
                    if (timeReset) {
                        five = 5000;
                    } else {
                        five = 0;
                    }
                    new CountDownTimer(five ,1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            goRestView.setText(String.valueOf(millisUntilFinished/1000));
                            skip.setEnabled(false);
                        }

                        @Override
                        public void onFinish() {
                            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                            // Vibrate for 500 milliseconds
                            v.vibrate(1000);
                            startPause.setEnabled(true);
                            timeActive= true;
                            plus.setEnabled(true);
                            if (goActive) {
                                goRestView.setText("Go");
                            } else {
                                goRestView.setText("Rest");
                                setsView.setText((sets-currentSet+1) + " Set/s left");
                            }
                            timeReset = false;
                            countdown = new CountDownTimer(currentTime * 1000, 1000) {
                                public void onTick(long millisUntilFinished) {
                                    timerView.setText(String.valueOf((millisUntilFinished / 1000)));
                                    currentTime = millisUntilFinished / 1000;
                                    if(currentTime <= 6){
                                        skip.setEnabled(false);
                                    } else{
                                        skip.setEnabled(true);
                                    }
                                }
                                public void onFinish() {
                                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                    // Vibrate for 500 milliseconds
                                    v.vibrate(1000);
                                    setsView.setText("Sets: " + currentSet + "/" + sets);
                                    if (currentSet < sets || (currentSet == sets && !goActive)) {
                                        if (goActive) {
                                            //set finished, workoutTime over, restTime starts
                                            currentSet++;
                                            setsView.setText("Sets: " + currentSet + "/" + sets);
                                            goActive = false;
                                            goRestView.setText("Rest");
                                            setsView.setText((sets-currentSet+1) + " Set/s left");
                                            currentTime = restTime;
                                            timerView.setText(String.valueOf(currentTime));
                                            timeActive = false;
                                            startPause.performClick();

                                        } else {
                                            //restTime over, workoutTime starts
                                            goActive = true;
                                            goRestView.setText("Go");
                                            currentTime = workoutTime;
                                            timerView.setText(String.valueOf(currentTime));
                                            timeActive = false;
                                            startPause.performClick();
                                        }
                                    } else {
                                        //last Set done
                                        timerView.setText("0");
                                        goRestView.setText("Finish!");
                                        goActive = false;
                                        timeActive = false;
                                        reset.setEnabled(true);

                                    }
                                }
                            }.start();
                        }
                    }.start();
                } else {
                    //pauses the countdown
                    plus.setEnabled(false);
                    countdown.cancel();
                    timerView.setText("-" + currentTime + "-");
                    reset.setEnabled(true);
                    timeActive = false;
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //changes currentSet to 1, currentTime to workoutTime
                timeReset = true;
                currentSet = 1;
                goActive = true;
                setsView.setText("Sets: " + currentSet + "/" + sets);
                goRestView.setText("Paused");
                timerView.setText("-" + String.valueOf(workoutTime) + "-");
                currentTime = workoutTime;
                reset.setEnabled(false);
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (currentTime > 6) {
                    currentTime = 6;
                    //new countdown continues
                    timerView.setText(String.valueOf(currentTime));
                    reset.setEnabled(false);
                    if (countdown != null) {
                        countdown.cancel();
                    }
                    countdown = new CountDownTimer(currentTime * 1000, 1000) {
                        public void onTick(long millisUntilFinished) {
                            timerView.setText(String.valueOf((millisUntilFinished / 1000)));
                            currentTime = millisUntilFinished / 1000;
                            if(currentTime <= 6){
                                skip.setEnabled(false);
                            } else{
                                skip.setEnabled(true);
                            }
                        }
                        public void onFinish() {
                            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                            // Vibrate for 500 milliseconds
                            v.vibrate(1000);
                            setsView.setText("Sets: " + currentSet + "/" + sets);
                            if (currentSet < sets || (currentSet == sets && !goActive)) {
                                if (goActive) {
                                    //set finished, workoutTime over, restTime starts
                                    currentSet++;
                                    setsView.setText("Sets: " + currentSet + "/" + sets);
                                    goActive = false;
                                    goRestView.setText("Rest");
                                    setsView.setText((sets-currentSet+1) + " Set/s left");
                                    currentTime = restTime;
                                    timerView.setText(String.valueOf(currentTime));
                                    timeActive = false;
                                    startPause.performClick();

                                } else {
                                    //restTime over, workoutTime starts
                                    goActive = true;
                                    goRestView.setText("Go");
                                    currentTime = workoutTime;
                                    timerView.setText(String.valueOf(currentTime));
                                    timeActive = false;
                                    startPause.performClick();
                                }
                            } else {
                                //last Set done
                                timerView.setText("0");
                                goRestView.setText("Finish!");
                                goActive = false;
                                timeActive = false;
                                reset.setEnabled(true);
                            }
                        }
                    }.start();
                }
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (currentTime > 0) {
                    currentTime += 10;
                    //new countdown continues
                    timerView.setText(String.valueOf(currentTime));
                    reset.setEnabled(false);
                    if (countdown != null) {
                        countdown.cancel();
                    }
                    countdown = new CountDownTimer(currentTime * 1000, 1000) {
                        public void onTick(long millisUntilFinished) {
                            timerView.setText(String.valueOf((millisUntilFinished / 1000)));
                            currentTime = millisUntilFinished / 1000;
                            if(currentTime <= 6){
                                skip.setEnabled(false);
                            } else{
                                skip.setEnabled(true);
                            }
                        }
                        public void onFinish() {
                            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                            // Vibrate for 500 milliseconds
                            v.vibrate(1000);
                            setsView.setText("Sets: " + currentSet + "/" + sets);
                            if (currentSet < sets || (currentSet == sets && !goActive)) {
                                if (goActive) {
                                    //set finished, workoutTime over, restTime starts
                                    currentSet++;
                                    setsView.setText("Sets: " + currentSet + "/" + sets);
                                    goActive = false;
                                    goRestView.setText("Rest");
                                    setsView.setText((sets-currentSet+1) + " Set/s left");
                                    currentTime = restTime;
                                    timerView.setText(String.valueOf(currentTime));
                                    timeActive = false;
                                    startPause.performClick();

                                } else {
                                    //restTime over, workoutTime starts
                                    goActive = true;
                                    goRestView.setText("Go");
                                    currentTime = workoutTime;
                                    timerView.setText(String.valueOf(currentTime));
                                    timeActive = false;
                                    startPause.performClick();
                                }
                            } else {
                                //last Set done
                                timerView.setText("0");
                                goRestView.setText("Finish!");
                                goActive = false;
                                timeActive = false;
                                reset.setEnabled(true);
                            }
                        }
                    }.start();
                }
            }
        });
    }
}
