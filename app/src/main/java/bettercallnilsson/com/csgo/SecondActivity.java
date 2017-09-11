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
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    int idStrategy;
    String qString;
    ImageView mapImage;
    TextView mapText, titleText, teamText,viewMap;
    Button tButton, ctButton, allButton;

        @Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_second);
            Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
            setSupportActionBar(myToolbar);

            /**retrieve all items from layout*/
            mapText = (TextView)findViewById(R.id.mapText);
            mapText.setMovementMethod(new ScrollingMovementMethod());
            titleText = (TextView)findViewById(R.id.titleText);
            teamText = (TextView)findViewById(R.id.teamText);
            tButton = (Button)findViewById(R.id.tButton);
            ctButton = (Button)findViewById(R.id.ctButton);
            allButton = (Button)findViewById(R.id.allButton);
            viewMap = (TextView)findViewById(R.id.viewMap);

            /**Get the spinnerOption from mainActivity*/
            Bundle extras = getIntent().getExtras();
            if (extras == null)
                return;
            qString = extras.getString("qString");
            viewMap.setText(String.valueOf(qString));

            /** Start Methods */
            setMap();
            printData();
            setTeam();

        }

    //button next clicked, get random item from database
    public void nextStrategyClick(View view){
        Database myDBHandler = new Database(this, null, null, 1);
        Strategy strategy = myDBHandler.nextStrategy(qString);

        if (strategy != null) {
            mapText.setText(String.valueOf(strategy.getDescription()));
            titleText.setText(String.valueOf(strategy.getTitle()));
            teamText.setText(String.valueOf(strategy.getTeam()));
            idStrategy = (strategy.getID());
            setTeam();
        } else {
            mapText.setText("No data in the database." +
                    "\nYou can add a strategy from the main menu ");
        }

    }

    //print data from database
    public void printData() {
        Database myDBHandler = new Database(this, null, null, 1);
        Strategy strategy = myDBHandler.findStrategy(qString);

            if (strategy != null) {
                mapText.setText(String.valueOf(strategy.getDescription()));
                titleText.setText(String.valueOf(strategy.getTitle()));
                teamText.setText(String.valueOf(strategy.getTeam()));
                idStrategy = (strategy.getID());

            } else {
                mapText.setText("No strategies in the Database");
                titleText.setText("");
            }
        }

    public void deleteSelected(View view){

        final Database dbHandler = new Database(this, null, null, 1);

        AlertDialog delete = new AlertDialog.Builder(SecondActivity.this).create();
        delete.setTitle("DELETE");
        delete.setMessage("ARE YOU SURE YOU WANT TO DELETE THIS STRATEGY?");
        delete.setButton(AlertDialog.BUTTON_NEGATIVE, "DELETE",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dbHandler.deleteEntry(idStrategy);
                        printData();
                        dialog.dismiss();
                    }
                });
        delete.setButton(AlertDialog.BUTTON_POSITIVE, "CANCEL",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //cancelled
                    }
                });
        delete.show();

    }

    /**will show button for the team selected*/
    public void setTeam(){

        while(teamText.getText().length()>1) {
            if (teamText.getText().toString().contentEquals("Terrorists")) {
                tButton.setVisibility(View.VISIBLE);
                ctButton.setVisibility(View.INVISIBLE);
                allButton.setVisibility(View.INVISIBLE);
            } else if (teamText.getText().toString().contentEquals("Counter-Terrorists")) {
                ctButton.setVisibility(View.VISIBLE);
                tButton.setVisibility(View.INVISIBLE);
                allButton.setVisibility(View.INVISIBLE);
            } else if (teamText.getText().toString().contentEquals("All")) {
                allButton.setVisibility(View.VISIBLE);
                tButton.setVisibility(View.INVISIBLE);
                ctButton.setVisibility(View.INVISIBLE);
            }

            return;
        }
    }

    /**set mapdrawable to imageview*/
    public void setMap() {

                mapImage = (ImageView) findViewById(R.id.mapsImage);

                switch (qString) {
                    case "Dust2":
                        mapImage.setImageResource(R.drawable.dust2map);
                        break;
                    case "Cobblestone":
                        mapImage.setImageResource(R.drawable.cobblestonemap);
                        break;
                    case "Cache":
                        mapImage.setImageResource(R.drawable.cachemap);
                        break;
                    case "Mirage":
                        mapImage.setImageResource(R.drawable.mirage);
                        break;
                    case "Inferno":
                        mapImage.setImageResource(R.drawable.inferno);
                        break;
                    case "Overpass":
                        mapImage.setImageResource(R.drawable.overpass);
                        break;
                    case "Train":
                        mapImage.setImageResource(R.drawable.train);
                        break;
                    case "Nuke":
                        mapImage.setImageResource(R.drawable.nuke);
                        break;

                }

            }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity,menu);
        return true;
    }

    //actionbar
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
                AlertDialog help = new AlertDialog.Builder(SecondActivity.this).create();
                help.setTitle("Help");
                help.setMessage("Down below you see your last strategy from database. " +
                        "To show next strategy press NEXT");
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

    public void backClick(View view){
        Intent intent = new Intent(SecondActivity.this,MainActivity.class);
        startActivity(intent);
    }

}
