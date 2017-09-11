//
// CREATED BY SEBASTIAN NILSSON
//

package bettercallnilsson.com.csgo;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddStrategyActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner mapSpinner;
    Spinner teamSpinner;
    ArrayAdapter<CharSequence> adapter;
    ArrayAdapter<CharSequence> adaptertwo;
    TextView myMap;
    TextView myTeam;
    EditText title;
    EditText strategyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_strategy);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        mapSpinner = (Spinner)findViewById(R.id.mapSpinner);
        adapter = ArrayAdapter.createFromResource(this,R.array.mapsarray,android.R.layout.simple_selectable_list_item);
        mapSpinner.setAdapter(adapter);
        mapSpinner.setOnItemSelectedListener(this);
        teamSpinner = (Spinner)findViewById(R.id.teamSpinner);
        adaptertwo = ArrayAdapter.createFromResource(this,R.array.teamsarray,android.R.layout.simple_selectable_list_item);
        teamSpinner.setAdapter(adaptertwo);
        teamSpinner.setOnItemSelectedListener(this);

        title = (EditText)findViewById(R.id.strategyTitle);
        strategyText = (EditText)findViewById(R.id.strategyText);

    }
    //Handle spinner activities
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                               long arg3) {
        switch(arg0.getId()) {
            case R.id.teamSpinner:
                myTeam = (TextView) arg1;
                break;
            case R.id.mapSpinner:
                myMap = (TextView) arg1;
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }


    //Add strategy to db and check if empty
    public void addNewStrategy(View view) {
        Database mydbHandler = new Database(this, null, null, 1);

        String thetitle = (title.getText().toString());
        String themap = (myMap.getText().toString());
        String theteam = (myTeam.getText().toString());
        String thedescription = (strategyText.getText().toString());

        Strategy strategy = new Strategy(thetitle, themap, theteam, thedescription);

        if (!thetitle.equals("") && !thedescription.equals("")) {
            mydbHandler.addStrategy(strategy);
            title.setText("");
            strategyText.setText("");

            AlertDialog alert = new AlertDialog.Builder(AddStrategyActivity.this).create();
            alert.setTitle("Strategy: " + thetitle + " saved.");
            alert.setMessage("Map: " + themap + "\nTeam: " + theteam);
            alert.show();
        }
        else{
            AlertDialog alert = new AlertDialog.Builder(AddStrategyActivity.this).create();
            alert.setTitle("Whoops...");
            alert.setMessage("Did you set a title and a description?");
            alert.show();
        }
    }

    public void deleteAll(View view){

        final Database mydbHandler = new Database(this,null,null,1);

        AlertDialog delete = new AlertDialog.Builder(AddStrategyActivity.this).create();
        delete.setTitle("DELETE ALL");
        delete.setMessage("ARE YOU SURE YOU WANT TO DELETE ALL ENTRIES IN THE DATABASE?");
        delete.setButton(AlertDialog.BUTTON_NEGATIVE, "DELETE",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mydbHandler.deleteStrategy();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.about:

                break;
            case R.id.help:
                AlertDialog help = new AlertDialog.Builder(AddStrategyActivity.this).create();
                help.setTitle("Help");
                help.setMessage("To start, press the plus button and create a strategy. " +
                        "When strategy has been made you can access it from the GO button");
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
        Intent intent = new Intent(AddStrategyActivity.this,MainActivity.class);
        startActivity(intent);
    }

}
