package com.info.dummycontacts;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.onegravity.contactpicker.ContactElement;
import com.onegravity.contactpicker.contact.Contact;
import com.onegravity.contactpicker.contact.ContactDescription;
import com.onegravity.contactpicker.contact.ContactSortOrder;
import com.onegravity.contactpicker.core.ContactPickerActivity;
import com.onegravity.contactpicker.group.Group;
import com.onegravity.contactpicker.picture.ContactPictureType;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private ViewPagerAdapter adapter;
    private static final String EXTRA_DARK_THEME = "EXTRA_DARK_THEME";
    private static final String EXTRA_GROUPS = "EXTRA_GROUPS";
    private static final String EXTRA_CONTACTS = "EXTRA_CONTACTS";

    private static final int REQUEST_CONTACT = 0;

    private boolean mDarkTheme;
    private List<Contact> mContacts;
    private List<Group> mGroups;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    ContactListFragment contactListFragment;
    SettingsFragment settingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        contactListFragment = ContactListFragment.newInstance("", "");
        settingsFragment = SettingsFragment.newInstance("", "");
        adapter.addFragment(contactListFragment, "Contacts");
        adapter.addFragment(settingsFragment, "Settings");
        mViewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickContact();
            }
        });

    }


    public void pickContact() {

        Intent intent = new Intent(MainActivity.this, ContactPickerActivity.class)
                .putExtra(ContactPickerActivity.EXTRA_THEME, mDarkTheme ?
                        R.style.Theme_Dark : R.style.Theme_Light)

                .putExtra(ContactPickerActivity.EXTRA_CONTACT_BADGE_TYPE,
                        ContactPictureType.ROUND.name())

                .putExtra(ContactPickerActivity.EXTRA_CONTACT_DESCRIPTION,
                        ContactDescription.ADDRESS.name())
                .putExtra(ContactPickerActivity.EXTRA_SHOW_CHECK_ALL, false)
                .putExtra(ContactPickerActivity.EXTRA_SELECT_CONTACTS_LIMIT, 5)
                .putExtra(ContactPickerActivity.EXTRA_ONLY_CONTACTS_WITH_PHONE, true)


                .putExtra(ContactPickerActivity.EXTRA_CONTACT_DESCRIPTION_TYPE,
                        ContactsContract.CommonDataKinds.Email.TYPE_WORK)

                .putExtra(ContactPickerActivity.EXTRA_CONTACT_SORT_ORDER,
                        ContactSortOrder.AUTOMATIC.name());

        startActivityForResult(intent, REQUEST_CONTACT);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CONTACT && resultCode == Activity.RESULT_OK && data != null &&
                (data.hasExtra(ContactPickerActivity.RESULT_GROUP_DATA) ||
                        data.hasExtra(ContactPickerActivity.RESULT_CONTACT_DATA))) {

            // we got a result from the contact picker --> show the picked contacts
            mGroups = (List<Group>) data.getSerializableExtra(ContactPickerActivity.RESULT_GROUP_DATA);
            mContacts = (List<Contact>) data.getSerializableExtra(ContactPickerActivity.RESULT_CONTACT_DATA);
            populateContactList(mGroups, mContacts);
        }
    }

    private void populateContactList(List<Group> groups, List<Contact> contacts) {
        // we got a result from the contact picker --> show the picked contacts

        try {
          /*  if (groups != null && ! groups.isEmpty()) {
                result.append("GROUPS\n");
                for (Group group : groups) {
                    //populateContact(result, group, "");
                    for (Contact contact : group.getContacts()) {
                        populateContact(result, contact, "    ");
                    }
                }
            }*/
            if (contacts != null && !contacts.isEmpty()) {
                ContactLog.deleteAll(ContactLog.class);
                for (Contact contact : contacts) {
                    ContactLog contactLog = new ContactLog();
                    contactLog.setDisplayName(contact.getDisplayName());
                    contactLog.setPhotoUri(contact.getPhotoUri()+"");
                    contactLog.setContactNumber(contact.getPhone(1 | 2 | 3 | 4));
                    contactLog.setNameChar(String.valueOf(contact.getContactLetter()));
                    contactLog.save();
                }
                contactListFragment.refreshContactList();
            }
        } catch (Exception e) {

        }
    }

    private void populateContact(SpannableStringBuilder result, Contact element, String prefix) {
        //int start = result.length();
        String displayName = element.getDisplayName();
        Uri photoUri = element.getPhotoUri();
        char contactLatter = element.getContactLetter();
        long id = element.getId();

        String mobileNumber = element.getPhone(1 | 2 | 3 | 4);
        result.append(prefix);
        result.append(displayName + "," + mobileNumber + "\n");
        //result.setSpan(new BulletSpan(15), start, result.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    }

}
