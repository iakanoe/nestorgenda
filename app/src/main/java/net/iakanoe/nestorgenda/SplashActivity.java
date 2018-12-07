package net.iakanoe.nestorgenda;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity{
	@Override protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		(new Timer()).schedule(new TimerTask(){
			@Override public void run(){
			runOnUiThread(new Runnable(){@Override public void run(){
				startActivity(new Intent(getApplicationContext(), LoginActivity.class));
				finish();
			}});
			}
		}, 1000);
	}
}
