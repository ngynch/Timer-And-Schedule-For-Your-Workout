package alles.fastalles.ameisenboss.gome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button customTimer = (Button) findViewById(R.id.customTimer);
        customTimer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //in gym_main.xml
                startActivity(new Intent(v.getContext(), customTimer.class));

            }
        });

    }
}
