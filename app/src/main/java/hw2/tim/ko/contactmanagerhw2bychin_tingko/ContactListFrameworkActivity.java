package hw2.tim.ko.contactmanagerhw2bychin_tingko;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class ContactListFrameworkActivity extends FragmentFrameworkActivity<ContactListFrameworkActivity.State, ContactListFrameworkActivity.Event, Long>
        implements ContactListFragment.OnContactListFragmentListener,
    EditFragment.OnEditFragmentListener {

    public enum State implements FragmentFrameworkActivity.State{
        List, Edit, Exit;
    }
    public enum Event implements FragmentFrameworkActivity.Event{
        ItemSelected, Done, Cancel, NewItem, Back;
    }

    private ContactListFragment contactListFragment;
    private EditFragment editFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list_framework);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        contactListFragment = (ContactListFragment) getSupportFragmentManager().findFragmentById(R.id.contactListFragment);
        editFragment = (EditFragment) getSupportFragmentManager().findFragmentById(R.id.editFragment);

        stateMachine()
                .fragmentContainer(R.id.fragmentContainer1)
                .fragmentContainer(R.id.fragmentContainer2)

                .stateType(State.class)
                .initialState(State.List)
                .exitState(State.Exit)
                .backEvent(Event.Back)

                .state(State.List)
                    .fragmentPriority(R.id.contactListFragment, R.id.editFragment)
                    .on(Event.ItemSelected).goTo(State.Edit)
                    .on(Event.Done).goTo(State.List)
                    .on(Event.Cancel).goTo(State.List)
                    .on(Event.NewItem).goTo(State.Edit)
                    .on(Event.Back).goTo(State.Exit)

                .state (State.Edit)
                    .fragmentPriority(R.id.editFragment, R.id.contactListFragment)
                    .on(Event.ItemSelected).goTo(State.Edit)
                    .on(Event.Done).goTo(State.List)
                    .on(Event.Cancel).goTo(State.List)
                    .on(Event.NewItem).goTo(State.Edit)
                    .on(Event.Back).goTo(State.Exit)

                .state(State.Exit);
    }

    @Override
    protected void onStateChanged(State state, Long id) {
        if (id == null)
            id= -1L;
            editFragment.setContactId(id);
    }

    @Override
    public void onContactListFragmentItemSelected(long id) {
        handleEvent(Event.ItemSelected, id);
    }

    @Override
    public void onContactListFragmentCreateItem() {
        handleEvent(Event.ItemSelected, -1L);
    }

    @Override
    public void onEditFragmentDone(long id) {
        handleEvent(Event.Done, id);
    }

    @Override
    public void onEditFragmentCancel(long id) {
        handleEvent(Event.Cancel, id);
        // do nothing!
    }
}
