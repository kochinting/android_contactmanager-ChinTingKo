package hw2.tim.ko.contactmanagerhw2bychin_tingko;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by TimKo on 6/5/16.
 */
public class ContactListAdapter extends RecyclerView.Adapter< ContactListAdapter.ContactViewHolder >{
    private List<ContactItem> items;

    private LayoutInflater layoutInflater;

    public ContactListAdapter(LayoutInflater layoutInflater, List<ContactItem> items){
        this.layoutInflater= layoutInflater;
        this.items= items;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= layoutInflater.inflate(R.layout.item, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        final ContactItem contactItem =items.get(position);

        //holder.view.setBackgroundColor(position %2 ==0 ? Color.LTGRAY: Color.WHITE);
        holder.firstName.setText(contactItem.getFirstName());
        holder.lastName.setText(contactItem.getLastName());
        holder.mobileNumber.setText(contactItem.getMobileNumber());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                contactListListener.itemSelected(contactItem);
            }});
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void onItemMoved(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

        int fromPosition = viewHolder.getAdapterPosition();
        int toPosition = target.getAdapterPosition();

        if (fromPosition < toPosition) {
            for(int i = fromPosition; i < toPosition; i++) {
                Collections.swap(items, i, i+1);
            }
        } else {
            for(int i = fromPosition; i > toPosition; i--) {
                Collections.swap(items, i, i-1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    public void onItemDismissed(RecyclerView.ViewHolder viewHolder) {

        int position = viewHolder.getAdapterPosition();
        items.remove(position);
        notifyItemRemoved(position);
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

    public void update(ContactItem contactItem){
        boolean found = false;
        for (int i=0; i< items.size(); i++){
            ContactItem item= items.get(i);
            if (item.getId()== contactItem.getId()){
                items.set(i, contactItem);
                found= true;
                notifyItemChanged(i);
                break;
            }

            if (!found){
                items.add (contactItem);
                notifyItemInserted(items.size()-1);
            }

        }
    }
    private ContactListListener contactListListener;

    public void setContactListListener(ContactListListener contactListListener) {
        this.contactListListener = contactListListener;
    }

    public interface ContactListListener{
        void itemSelected (ContactItem contactItem);
    }

}
