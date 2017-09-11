//
// CREATED BY SEBASTIAN NILSSON
//

package bettercallnilsson.com.csgo;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    TextView myText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        spinner = (Spinner)findViewById(R.id.mapSpinner);
        adapter = ArrayAdapter.createFromResource(this,R.array.mapsarray,android.R.layout.simple_selectable_list_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }

    /**for spinner*/
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        myText = (TextView) view;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**get value from spinner and send to second activity*/
    public void goClick(View view){

        Intent intent = new Intent(MainActivity.this,SecondActivity.class);

        String myString = myText.getText().toString();
        intent.putExtra("qString",myString);
        startActivity(intent);

    }

    /**add and delete from database*/
    public void addNewStrategy(View view){
        Intent intent = new Intent(MainActivity.this,AddStrategyActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity,menu);
        return true;
    }

    //actionbar items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.about:
                String url = "http://blog.counter-strike.net/index.php/about/";
                Intent web = new Intent(Intent.ACTION_VIEW);
                web.setData(Uri.parse(url));
                startActivity(web);
                break;
            case R.id.help:
                AlertDialog help = new AlertDialog.Builder(MainActivity.this).create();
                help.setTitle("Help");
                help.setMessage("To start, simply press the plus button and create a strategy. " +
                        "When the strategy has been made you can access it from the GO button with your chosen map.");
                help.setButton(AlertDialog.BUTTON_POSITIVE, "Affirmative",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                help.show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

}
