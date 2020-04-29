package alles.fastalles.ameisenboss.gome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class customTimer extends AppCompatActivity {
    int restTime = 90;
    int workoutTime = 40;
    int sets = 3;
    Button plusTenR;
    Button minusTenR;
    Button plusTenW;
    Button minusTenW;
    Button plusOne;
    Button minusOne;
    Button done;
    TextView restTimeView;
    TextView workoutTimeView;
    TextView setsView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_timer);
        plusTenR = (Button) findViewById(R.id.plusrest);
        minusTenR = (Button) findViewById(R.id.minusrest);
        plusTenW = (Button) findViewById(R.id.plusworkout);
        minusTenW = (Button) findViewById(R.id.minusworkout);
        plusOne = (Button) findViewById(R.id.plusset);
        minusOne = (Button) findViewById(R.id.minusset);
        done = (Button) findViewById(R.id.done);

        restTimeView = (TextView) findViewById(R.id.rest);
        workoutTimeView = (TextView) findViewById(R.id.workout);
        setsView = (TextView) findViewById(R.id.sets);

        plusTenR.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                restTime += 10;
                restTimeView.setText("Rest\n" + restTime + " sec");
            }
        });

        plusTenW.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                workoutTime += 10;
                workoutTimeView.setText("Workout\n" + workoutTime + " sec");
            }
        });

        plusOne.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sets += 1;
                setsView.setText("Sets\n" + sets);
            }
        });


        minusTenR.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (restTime > 10) {
                    restTime -= 10;
                    restTimeView.setText("Rest\n" + restTime + " sec");
                }
            }
        });

        minusTenW.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (workoutTime > 10) {
                    workoutTime -= 10;
                    workoutTimeView.setText("Workout\n" + workoutTime + " sec");
                }
            }
        });

        minusOne.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (sets > 1) {
                    sets -= 1;
                    setsView.setText("Sets\n" + sets);
                }
            }
        });


        done.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), timer.class);
                Bundle extras = new Bundle();
                extras.putString("restTime", Integer.toString(restTime));
                extras.putString("workoutTime", Integer.toString(workoutTime));
                extras.putString("sets", Integer.toString(sets));
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

    }
}
