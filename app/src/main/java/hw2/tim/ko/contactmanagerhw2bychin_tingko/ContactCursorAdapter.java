package hw2.tim.ko.contactmanagerhw2bychin_tingko;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by TimKo on 6/12/16.
 */
public class ContactCursorAdapter extends CursorRecyclerViewAdapter<ContactCursorAdapter.ContactViewHolder> {

        private LayoutInflater layoutInflater;

        public ContactCursorAdapter(Context context, LayoutInflater layoutInflater){
            super(context, null);
            this.layoutInflater= layoutInflater;
        }

        @Override
        public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= layoutInflater.inflate(R.layout.item, parent, false);
            return new ContactViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ContactViewHolder holder, Cursor cursor) {
            final ContactItem contactItem =new ContactItem();
            int idCol= cursor.getColumnIndex(ContactContentProvider.COLUMN_ID);
            int firstNameCol= cursor.getColumnIndex(ContactContentProvider.COLUMN_FIRSTNAME);
            int lastNameCol= cursor.getColumnIndex(ContactContentProvider.COLUMN_LASTNAME);
            int mobileNumberCol= cursor.getColumnIndex(ContactContentProvider.COLUMN_MOBILENUMBER);

            contactItem.setId(cursor.getLong(idCol));
            contactItem.setFirstName(cursor.getString(firstNameCol));
            contactItem.setLastName(cursor.getString(lastNameCol));
            contactItem.setMobileNumber(cursor.getString(mobileNumberCol));

            //holder.view.setBackgroundColor(position %2 ==0 ? Color.LTGRAY: Color.WHITE);
            holder.firstName.setText(contactItem.getFirstName());
            holder.lastName.setText(contactItem.getLastName());
            holder.mobileNumber.setText(contactItem.getMobileNumber());

            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    contactListListener.itemSelected(contactItem.getId());
                }});
        }

        public static class ContactViewHolder extends RecyclerView.ViewHolder{
            private TextView firstName;
            private TextView lastName;
            private TextView mobileNumber;
            //private long id;
            private View view;
            public ContactViewHolder(View view) {
                super (view);
                this.view=view;
                firstName = (TextView) view.findViewById(R.id.first_name);
                lastName = (TextView) view.findViewById(R.id.last_name);
                mobileNumber = (TextView) view.findViewById(R.id.mobile_phone);
            }
        }

        private ContactListListener contactListListener;

        public void setContactListListener(ContactListListener contactListListener) {
            this.contactListListener = contactListListener;
        }

        public interface ContactListListener{
            void itemSelected (long id);
        }
    }


