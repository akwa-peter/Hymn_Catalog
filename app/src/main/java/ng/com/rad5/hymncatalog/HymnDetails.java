package ng.com.rad5.hymncatalog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class HymnDetails extends AppCompatActivity {

    Toolbar toolbar;

    TextView txt_hymnBody;
    String title;
    String body;

    AppDatabase database;

    public static final String DELETE_HYMN = "com.example.android.deletehymn.REPLY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hymn_details);

        database = AppDatabase.getDatabase(this);

        toolbar = (Toolbar) findViewById(R.id.hymnDetails_toolbar);
        setSupportActionBar(toolbar);

        txt_hymnBody = (TextView) findViewById(R.id.hymn_body);

        Intent preceedingIntent = getIntent();
        title = preceedingIntent.getStringExtra("intent");
        body = preceedingIntent.getStringExtra("hymnBody");

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null){
            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setTitle(title);
        }

        txt_hymnBody.setText(body);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.action_delete:

                final AlertDialog.Builder dialog = new AlertDialog.Builder(this);

                dialog.setMessage("You will lose your hymn, are sure you want to delete?");
                dialog.setIcon(R.drawable.ic_warning_black_24dp);
                dialog.setPositiveButton(this.getString(R.string.dialog_action_delete), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new PopulateDbAsync(database, title).execute();
                        finish();
                        Toast.makeText(getApplicationContext(), "Hymn deleted successfully", Toast.LENGTH_LONG).show();
                    }
                });
                dialog.setNegativeButton(this.getString(R.string.dialog_action_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                dialog.create();
                dialog.show();

                break;

            default:
                //action
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final UserDao mDao;
        String mTitle;

        PopulateDbAsync(AppDatabase db, String title) {
            mDao = db.userDao();
            mTitle = title;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteHymn(mTitle);
            return null;
        }
    }
}
