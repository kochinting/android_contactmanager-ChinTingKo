package hw2.tim.ko.contactmanagerhw2bychin_tingko;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by TimKo on 6/10/16.
 */
public class EditFragment extends Fragment {
    private EditText firstName;
    private EditText lastName;
    private EditText homeNumber;
    private EditText workNumber;
    private EditText mobileNumber;
    private EditText email;
    private long id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if(savedInstanceState != null)
            id=savedInstanceState.getLong("id", -1);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_contact_list, container, false);

        firstName= (EditText) view.findViewById(R.id.first_name);
        lastName= (EditText) view.findViewById(R.id.last_name);
        homeNumber= (EditText) view.findViewById(R.id.home_phone);
        workNumber= (EditText) view.findViewById(R.id.work_phone);
        mobileNumber= (EditText) view.findViewById(R.id.mobile_phone);
        email= (EditText) view.findViewById(R.id.email);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("id", id);
    }

    public void setContactId (long id){
        this.id=id;
        if (id==1){
            firstName.setText("");
            lastName.setText("");
            homeNumber.setText("");
            workNumber.setText("");
            mobileNumber.setText("");
            email.setText("");
        }
        else{
            ContactItem item= Util.findContact(getContext(), id);
            if(item==null) {
                firstName.setText("");
                lastName.setText("");
                homeNumber.setText("");
                workNumber.setText("");
                mobileNumber.setText("");
                email.setText("");
                this.id = -1;
            } else{
                firstName.setText(item.getFirstName());
                lastName.setText(item.getLastName());
                homeNumber.setText(item.getHomeNumber());
                workNumber.setText(item.getWorkNumber());
                mobileNumber.setText(item.getMobileNumber());
                email.setText(item.getEmail());
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_edit, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_done:
                saveData();
                if (onEditFragmentListener!= null)
                onEditFragmentListener.onEditFragmentDone(id);
                return true;
            case R.id.action_cancel:
                if (onEditFragmentListener!= null)
                onEditFragmentListener.onEditFragmentCancel(id);
                return true;
            case R.id.action_refresh:
                return true;
            case R.id.action_help:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveData(){

        ContactItem contactItem= new ContactItem();
        contactItem.setId(id);
        contactItem.setFirstName(firstName.getText().toString());
        contactItem.setLastName(lastName.getText().toString());
        contactItem.setHomeNumber(homeNumber.getText().toString());
        contactItem.setWorkNumber(workNumber.getText().toString());
        contactItem.setMobileNumber(mobileNumber.getText().toString());
        contactItem.setEmail(email.getText().toString());

        Util.updateContact(getContext(), contactItem);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof OnEditFragmentListener))
            throw new IllegalStateException("Activities using EditFragment must implement EditFragment.OnEditFragmentListener");
        onEditFragmentListener = (OnEditFragmentListener) context;
    }

    @Override
    public void onDetach() {
        onEditFragmentListener=null;
        super.onDetach();
    }

    private OnEditFragmentListener onEditFragmentListener;

    public interface OnEditFragmentListener{
        void onEditFragmentDone (long id);
        void onEditFragmentCancel (long id);
    }
}
