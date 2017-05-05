package hw2.tim.ko.contactmanagerhw2bychin_tingko;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TimKo on 6/10/16.
 */
public class ContactListFragment extends Fragment {
    private static final int CONTACT_LOADER =42;

    private long nextID = 1000;
    private ContactCursorAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_contact_list,container, false);
        RecyclerView recyclerView= (RecyclerView) view.findViewById(R.id.recyclerView);

        Util.updateContact(getContext(), new ContactItem(-1,"Tim", "Ko,", "213-221-5939","123-456-7890","123-456-7890","kochinting@gmail.com"));
        Util.updateContact(getContext(), new ContactItem(-1,"Tim", "Ko,", "213-221-5939","123-456-7890","123-456-7890","kochinting@gmail.com"));
        Util.updateContact(getContext(), new ContactItem(-1,"Tim", "Ko,", "213-221-5939","123-456-7890","123-456-7890","kochinting@gmail.com"));
        Util.updateContact(getContext(), new ContactItem(-1,"Tim", "Ko,", "213-221-5939","123-456-7890","123-456-7890","kochinting@gmail.com"));
        Util.updateContact(getContext(), new ContactItem(-1,"Tim", "Ko,", "213-221-5939","123-456-7890","123-456-7890","kochinting@gmail.com"));
        Util.updateContact(getContext(), new ContactItem(-1,"Tim", "Ko,", "213-221-5939","123-456-7890","123-456-7890","kochinting@gmail.com"));
        Util.updateContact(getContext(), new ContactItem(-1,"Tim", "Ko,", "213-221-5939","123-456-7890","123-456-7890","kochinting@gmail.com"));
        Util.updateContact(getContext(), new ContactItem(-1,"Tim", "Ko,", "213-221-5939","123-456-7890","123-456-7890","kochinting@gmail.com"));
        Util.updateContact(getContext(), new ContactItem(-1,"Tim", "Ko,", "213-221-5939","123-456-7890","123-456-7890","kochinting@gmail.com"));
        Util.updateContact(getContext(), new ContactItem(-1,"Tim", "Ko,", "213-221-5939","123-456-7890","123-456-7890","kochinting@gmail.com"));
        Util.updateContact(getContext(), new ContactItem(-1,"Tim", "Ko,", "213-221-5939","123-456-7890","123-456-7890","kochinting@gmail.com"));
        Util.updateContact(getContext(), new ContactItem(-1,"Tim", "Ko,", "213-221-5939","123-456-7890","123-456-7890","kochinting@gmail.com"));
        Util.updateContact(getContext(), new ContactItem(-1,"Tim", "Ko,", "213-221-5939","123-456-7890","123-456-7890","kochinting@gmail.com"));
        Util.updateContact(getContext(), new ContactItem(-1,"Tim", "Ko,", "213-221-5939","123-456-7890","123-456-7890","kochinting@gmail.com"));
        Util.updateContact(getContext(), new ContactItem(-1,"Tim", "Ko,", "213-221-5939","123-456-7890","123-456-7890","kochinting@gmail.com"));
        Util.updateContact(getContext(), new ContactItem(-1,"Tim", "Ko,", "213-221-5939","123-456-7890","123-456-7890","kochinting@gmail.com"));

        adapter = new ContactCursorAdapter(getActivity(), getActivity().getLayoutInflater());
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter.setContactListListener(new ContactCursorAdapter.ContactListListener() {
            @Override
            public void itemSelected(long id) {
                if (onContactListFragmentListener!= null)
                    onContactListFragmentListener.onContactListFragmentItemSelected(id);
            }});

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.Callback() {

                    @Override
                    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                        return makeMovementFlags(0, swipeFlags);
                    }

                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        return true;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        Util.delete(getContext(), viewHolder.getItemId());
                    }
                }
        );

        itemTouchHelper.attachToRecyclerView(recyclerView);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        assert fab!= null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContactItem contactItem= new ContactItem(nextID,"","","","","","");
                if (onContactListFragmentListener != null)
                    onContactListFragmentListener.onContactListFragmentCreateItem();
            }
        });
        return view;
    }

    private OnContactListFragmentListener onContactListFragmentListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof OnContactListFragmentListener))
            throw new IllegalStateException("Activities using ContactListFragment must implement ContactListFragment.onContactListFragmentListener");
        onContactListFragmentListener = (OnContactListFragmentListener) context;
        getActivity(). getSupportLoaderManager().initLoader(CONTACT_LOADER, null, loaderCallbacks);

    }

    @Override
    public void onDetach() {
        onContactListFragmentListener=null;
        super.onDetach();
        getActivity().getSupportLoaderManager().destroyLoader(CONTACT_LOADER);
    }

    private LoaderManager.LoaderCallbacks<Cursor> loaderCallbacks= new LoaderManager.LoaderCallbacks<Cursor>() {
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            String [] projection={
                    ContactContentProvider.COLUMN_ID,
                    ContactContentProvider.COLUMN_FIRSTNAME,
                    ContactContentProvider.COLUMN_LASTNAME,
                    ContactContentProvider.COLUMN_MOBILENUMBER
            };

            return new CursorLoader(
                    getActivity(), ContactContentProvider.CONTENT_URI, projection, null, null,
                    ContactContentProvider.COLUMN_FIRSTNAME+ "ASC"
            );
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
            if (adapter != null)
                adapter.changeCursor(cursor);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            if (adapter != null)
            adapter.changeCursor(null);
        }
    };

    public interface OnContactListFragmentListener{
        void onContactListFragmentItemSelected(long id);
        void onContactListFragmentCreateItem();
    }
}
