package demo.com.glidesample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView iv_hello = findViewById(R.id.iv_hello);
        Glide.with(this)
                .load("https://m.media-amazon.com/images/M/MV5BODcwOTg2MDE3NF5BMl5BanBnXkFtZTgwNTUyNTY1NjM@._V1_UY1200_CR90,0,630,1200_AL_.jpg")
                .into(iv_hello);
    }
}
