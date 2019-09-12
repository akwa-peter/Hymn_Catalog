package ng.com.rad5.hymncatalog;

import android.app.AlertDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import static ng.com.rad5.hymncatalog.AddHymn.*;

public class MainActivity extends AppCompatActivity {


    Toolbar toolbar;
    FloatingActionButton fabAddHymn;

    RecyclerView mRecyclerView;
    HymnAdapter adapter;

    NavigationView navigationView;
    DrawerLayout drawerLayout;

    private HymnViewModel hymnViewModel;
    public static final int NEW_HYMN_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.mainActivity_toolbar);
        setSupportActionBar(toolbar);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null){
            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        fabAddHymn = (FloatingActionButton) findViewById(R.id.fab_add_hymn);
        fabAddHymn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddHymn.class);
                startActivityForResult(intent, NEW_HYMN_ACTIVITY_REQUEST_CODE);
            }
        });

        hymnViewModel = ViewModelProviders.of(this).get(HymnViewModel.class);
        hymnViewModel.getAllHymns().observe(this, new Observer<List<Hymn>>() {
            @Override
            public void onChanged(@Nullable final List<Hymn> hymns) {
                // Update the cached copy of the words in the adapter.
                adapter.setHymns(hymns);
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.my_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class,
                "hymns")
                .allowMainThreadQueries()
                .build();

        adapter = new HymnAdapter(this);

        mRecyclerView.setAdapter(adapter);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        /**
         * set action for when any of the drawer navigation
         * menu item is clicked or selected
         */
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.home:
                                drawerLayout.closeDrawers();
                                break;

                            case R.id.about:
                                drawerLayout.closeDrawers();
                                startActivity(new Intent(MainActivity.this, About.class));
                                break;

                            default:
                        }

                        return true;
                    }
                });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_HYMN_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Hymn hymn = new Hymn(data.getStringExtra(AddHymn.EXTRA_TITLE), data.getStringExtra(AddHymn.EXTRA_BODY));
            hymnViewModel.insert(hymn);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainactivity_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case android.R.id.home:
                drawerLayout.openDrawer(Gravity.START);
                break;

            default:
                //action
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
