package ip.histospot.android.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import ip.histospot.android.R;
import ip.histospot.android.activities.main.MainPagerAdapter;
import ip.histospot.android.controllers.MainController;

/**
 * Created by Denisa on 5/4/2018.
 */

public class LearnActivity extends AppCompatActivity{

    public static final String LESSON_EXTRA = "lessonExtra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);
        int id=getIntent().getIntExtra(LearnActivity.LESSON_EXTRA, 1);
        WebView t = findViewById(R.id.test);

        t.getSettings().setJavaScriptEnabled(true);
        t.getSettings().setPluginState(WebSettings.PluginState.ON);
        t.loadUrl(MainController.SERVER_ADRESS + "/faces/" + id + ".xhtml");
    }
}