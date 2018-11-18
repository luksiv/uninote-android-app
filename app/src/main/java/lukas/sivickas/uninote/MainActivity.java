package lukas.sivickas.uninote;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    public static Toolbar mToolbar;
    public static ActionBar mSupportActionBar;

    private BottomNavigationView mNavigation;
    private ArrayList<Fragment> mFragmentsList;

    private String mCurrentFragment;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_modules:
                    mCurrentFragment = "Modules";
                    loadFragment(mFragmentsList.get(0));
                    return true;
                case R.id.navigation_notes:
                    mCurrentFragment = "Notes";
                    loadFragment(mFragmentsList.get(1));
                    return true;
                case R.id.navigation_assignments:
                    mCurrentFragment = "Assignments";
                    loadFragment(mFragmentsList.get(2));
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);

        mSupportActionBar = getSupportActionBar();

        mNavigation = findViewById(R.id.navigation);
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mFragmentsList = new ArrayList<>();
        mFragmentsList.add(new ModulesFragment());
        mFragmentsList.add(new NotesFragment());
        mFragmentsList.add(new AssignmentsFragment());

        mNavigation.setSelectedItemId(R.id.navigation_modules);
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                // hiding bottom nav bar
                mNavigation.setVisibility(View.GONE);
                mCurrentFragment = "About";
                loadFragment(new AboutFragment());
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (mCurrentFragment == "About") {
            // removing top back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            // unhiding bottom nav bar
            mNavigation.setVisibility(View.VISIBLE);
            mCurrentFragment = "NotAbout";
            super.onBackPressed();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
