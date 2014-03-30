package com.sciters.mytheater;

import android.app.*;
import android.content.Intent;
import android.net.Uri;
import android.os.*;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Vincenda Diaz on 1/26/14.
 */
public class About extends Activity {

    private TextView klasemenjkt48;
    private TextView emailgilang;
    private TextView emailbyhaqi;
    private TextView emailvince;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView (R.layout.about);

        klasemenjkt48 = (TextView) findViewById(R.id.linkklasemenjkt48);
        klasemenjkt48.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_VIEW);
                myIntent.setData(Uri.parse("http://mytheater.klasemenjkt48.com"));
                startActivity(myIntent);
            }
        });

        emailgilang = (TextView) findViewById(R.id.emailgilang);
        emailgilang.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"sciters@gmail.com"});
                myIntent.setType("message/rfc822");
                startActivity(Intent.createChooser(myIntent, "Choose an Email client :"));
            }
        });

        emailbyhaqi = (TextView) findViewById(R.id.emailbyhaqi);
        emailbyhaqi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"achmad.byhaqi@gmail.com"});
                myIntent.setType("message/rfc822");
                startActivity(Intent.createChooser(myIntent, "Choose an Email client :"));
            }
        });

        emailvince = (TextView) findViewById(R.id.emailvince);
        emailvince.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"vincendadiaz7@gmail.com"});
                myIntent.setType("message/rfc822");
                startActivity(Intent.createChooser(myIntent, "Choose an Email client :"));
            }
        });
    }
}
