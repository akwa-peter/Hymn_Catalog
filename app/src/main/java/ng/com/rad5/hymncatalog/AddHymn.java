package ng.com.rad5.hymncatalog;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.Serializable;

public class AddHymn extends AppCompatActivity {

    Toolbar toolbar;

    EditText edtHymnTitle;
    EditText edtHymn;
    Button btnSaveHymn;

    public static final String EXTRA_TITLE = "com.example.android.hymntitle.REPLY";
    public static final String EXTRA_BODY = "com.example.android.hymnbody.REPLY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hymn);

        toolbar = (Toolbar) findViewById(R.id.addHymn_toolbar);
        setSupportActionBar(toolbar);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null){
            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        edtHymnTitle = (EditText) findViewById(R.id.edt_hymn_title);
        edtHymn = (EditText) findViewById(R.id.edt_hymn);

        final AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database").build();

        btnSaveHymn = (Button) findViewById(R.id.btn_save_hymn);
        btnSaveHymn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent replyIntent = new Intent();

                if (edtHymnTitle.getText().toString().isEmpty() || edtHymn.getText().toString().isEmpty()){
                    setResult(RESULT_CANCELED, replyIntent);
                    Snackbar.make(toolbar, "Cannot save, fields are empty.", Snackbar.LENGTH_LONG).show();
                }
                else {
                    String hymnTitle = edtHymnTitle.getText().toString();
                    String hymnBody = edtHymn.getText().toString();

                    replyIntent.putExtra(EXTRA_TITLE, hymnTitle);
                    replyIntent.putExtra(EXTRA_BODY, hymnBody);
                    setResult(RESULT_OK, replyIntent);
                }

                finish();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

}
