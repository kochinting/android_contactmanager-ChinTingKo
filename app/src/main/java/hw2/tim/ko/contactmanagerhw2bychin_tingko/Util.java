package hw2.tim.ko.contactmanagerhw2bychin_tingko;

import android.content.ContentUris;
import android.content.ContentValues;
import android. database.Cursor;
import android.content.Context;
import android.net.Uri;

/**
 * Created by TimKo on 6/12/16.
 */
public class Util {
    public static ContactItem findContact(Context context, long id){
        Uri uri= ContentUris.withAppendedId(ContactContentProvider.CONTENT_URI, id);

        String [] projection = {
                ContactContentProvider.COLUMN_ID,
                ContactContentProvider.COLUMN_FIRSTNAME,
                ContactContentProvider.COLUMN_LASTNAME,
                ContactContentProvider.COLUMN_HOMENUMBER,
                ContactContentProvider.COLUMN_WORKNUMBER,
                ContactContentProvider.COLUMN_MOBILENUMBER,
                ContactContentProvider.COLUMN_EMAIL
        };

        Cursor cursor = null;
        try{
            cursor = context.getContentResolver().query(uri, projection, null, null, null);

            if (cursor ==null|| !cursor.moveToFirst())
                return null;

            return new ContactItem(
                    cursor.getLong(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6)
            );

        }finally{
            if (cursor!= null)
                cursor.close();
        }
    }
    public static void updateContact(Context context, ContactItem contactItem){

        ContentValues contentValues= new ContentValues();

        contentValues.put (ContactContentProvider. COLUMN_FIRSTNAME, contactItem.getFirstName());
        contentValues.put (ContactContentProvider. COLUMN_LASTNAME, contactItem.getLastName());
        contentValues.put (ContactContentProvider. COLUMN_HOMENUMBER, contactItem.getHomeNumber());
        contentValues.put (ContactContentProvider. COLUMN_WORKNUMBER, contactItem.getWorkNumber());
        contentValues.put (ContactContentProvider. COLUMN_MOBILENUMBER, contactItem.getMobileNumber());
        contentValues.put (ContactContentProvider. COLUMN_EMAIL, contactItem.getEmail());

        if (contactItem.getId() != -1) {
            Uri uri = ContentUris.withAppendedId(ContactContentProvider.CONTENT_URI, contactItem.getId());
            context.getContentResolver().update(uri, contentValues, null, null);
        } else{
            Uri uri= context.getContentResolver().insert(ContactContentProvider.CONTENT_URI, contentValues);
            if(uri == null){
                throw new RuntimeException("No uri returned from insert");
            }
            String stringId = uri.getLastPathSegment();
            long id = Long.parseLong(stringId);
            contactItem.setId(id);
        }
    }

    public static void delete(Context context, long itemId){
        Uri uri = ContentUris.withAppendedId(ContactContentProvider.CONTENT_URI, itemId);
        context.getContentResolver().delete(uri, null, null);
    }

}
