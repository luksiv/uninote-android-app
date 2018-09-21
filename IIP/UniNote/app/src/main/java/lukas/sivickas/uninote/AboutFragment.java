package lukas.sivickas.uninote;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Calendar;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;


public class AboutFragment extends Fragment {

    public AboutFragment() {
        // Required empty public constructor
    }

    public static AboutFragment newInstance() {
        AboutFragment fragment = new AboutFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        MainActivity.mToolbar.setTitle("About");
        View aboutPage = new AboutPage(AboutFragment.super.getContext())
                .isRTL(false)
                .setImage(R.drawable.ic_home_black_24dp)
                .setDescription(getString(R.string.about_description))
                .addItem(new Element().setTitle("Version 0.1"))
                .addGroup("Contact me")
                .addItem(getEmailElement())
                .addItem(getLinkedInElement())
                .addItem(getGithubElement())
                .addItem(getCopyRightsElement())
                .create();

        setHasOptionsMenu(true);

        return aboutPage;
    }

    Element getCopyRightsElement() {
        Element element = new Element();
        final String copyrights = String.format("MIT", Calendar.getInstance().get(Calendar.YEAR));
        element.setTitle(copyrights);
        element.setIconDrawable(R.drawable.ic_information_black_24dp);
        element.setIconTint(mehdi.sakout.aboutpage.R.color.about_item_icon_color);
        element.setIconNightTint(android.R.color.white);
        element.setGravity(Gravity.CENTER);
        element.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AboutFragment.super.getContext(), copyrights, Toast.LENGTH_SHORT).show();
            }
        });
        return element;
    }

    Element getEmailElement() {
        Element element = new Element();
        element.setTitle("Email me at l.sivickas@gmail.com");
        element.setIconDrawable(R.drawable.about_icon_email);
//        element.setIconTint(mehdi.sakout.aboutpage.R.color.about_background_color);
//        element.setIconNightTint(android.R.color.white);
        element.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AboutFragment.super.getContext(), "Email", Toast.LENGTH_SHORT).show();
            }
        });
        return element;
    }

    Element getGithubElement() {
        Element element = new Element();
        element.setTitle("Check out my other projects on Github");
        element.setIconDrawable(R.drawable.about_icon_github);
//        element.setIconTint(mehdi.sakout.aboutpage.R.color.about_github_color);
//        element.setIconNightTint(android.R.color.white);
        element.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AboutFragment.super.getContext(), "Github", Toast.LENGTH_SHORT).show();
            }
        });
        return element;
    }

    Element getLinkedInElement() {
        Element element = new Element();
        element.setTitle("Connect with me through LinkedIn");
        element.setIconDrawable(R.drawable.about_icon_link);
//        element.setIconTint(mehdi.sakout.aboutpage.R.color.about_background_color);
//        element.setIconNightTint(android.R.color.white);
        element.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AboutFragment.super.getContext(), "LinkedIn", Toast.LENGTH_SHORT).show();
            }
        });
        return element;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.action_bar, menu);
        for (int i = 0; i < menu.size(); i++) {
            menu.getItem(i).setVisible(false);
        }
    }
}
