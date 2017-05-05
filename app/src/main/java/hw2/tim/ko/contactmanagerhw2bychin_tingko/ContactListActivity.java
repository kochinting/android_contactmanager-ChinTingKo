package hw2.tim.ko.contactmanagerhw2bychin_tingko;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


public class ContactListActivity extends AppCompatActivity
        implements ContactListFragment.OnContactListFragmentListener,
    EditFragment.OnEditFragmentListener{

    private ContactListFragment contactListFragment;
    private EditFragment editFragment;
    private boolean sideBySide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        contactListFragment = (ContactListFragment) getSupportFragmentManager().findFragmentById(R.id.contactListFragment);
        editFragment = (EditFragment) getSupportFragmentManager().findFragmentById(R.id.editFragment);

        sideBySide= (editFragment != null && editFragment.isInLayout());
    }

    @Override
    public void onContactListFragmentItemSelected(long id) {
        if (sideBySide) {
        editFragment.setContactId(id);
        } else {

            Intent intent = new Intent(ContactListActivity.this, EditActivity.class);
            intent.putExtra("itemId", id);
            startActivity(intent);
        }
    }

    @Override
    public void onContactListFragmentCreateItem() {
        if(sideBySide){
            editFragment.setContactId(-1);
        } else {

            Intent intent = new Intent(ContactListActivity.this, EditActivity.class);
            intent.putExtra("itemId", -1L);
            startActivity(intent);
        }
    }

    @Override
    public void onEditFragmentDone(long id) {
        // no longer need to do anything
    }

    @Override
    public void onEditFragmentCancel(long id) {
        // do nothing!
    }
}
