package cathysteatimer.skuldougery.com.cathysteatimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SeekBar timerSeekBar;
    private TextView timerTextView;
    private Boolean counterIsActive = false;
    private Button controlButton;
    private CountDownTimer countDownTimer;
    private MediaPlayer mplayer;

    public void controlButtonHandler(View view)
    {
        counterIsActive = !counterIsActive;

        if (counterIsActive)
        {
            controlButton.setText("Stop!");
            timerSeekBar.setEnabled(false);

            countDownTimer = new CountDownTimer((timerSeekBar.getProgress() * 1000) + 100, 1000) {
                @Override
                public void onTick(long millisUntilFinished)
                {
                    updateControls((int) (millisUntilFinished / 1000));
                }

                @Override
                public void onFinish()
                {
                    updateControls(0);
                    mplayer = MediaPlayer.create(getApplicationContext(),
                                                 R.raw.bell_ringing_01);
                    mplayer.start();
                }
            }.start();
        }
        else
        {
            countDownTimer.cancel();
            controlButton.setText("Start!");
            timerSeekBar.setEnabled(true);
            if (mplayer != null) {
                mplayer.stop();
                mplayer.release();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar = (SeekBar)findViewById(R.id.timerSeekBar);
        timerTextView = (TextView)findViewById(R.id.timerTextView);
        controlButton = (Button)findViewById(R.id.controlButton);

        timerSeekBar.setMax(15 * 60);
        timerSeekBar.setProgress(30);
        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateControls(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void updateControls(int secondsLeft)
    {
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft - (minutes * 60);

        String formattedTime = String.format("%d:%02d", minutes, seconds);

        timerTextView.setText(formattedTime);
        timerSeekBar.setProgress(secondsLeft);
    }
}
