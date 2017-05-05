package hw2.tim.ko.contactmanagerhw2bychin_tingko;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;


public class ContactContentProvider extends ContentProvider {
    public static final String AUTHORITY = "contact.chintingko.com";
    public static final String BASE= "contact";
    public static final Uri CONTENT_URI = Uri.parse ("content://"+ AUTHORITY+ '/'+ BASE);

    public static final String TABLE = "CONTACT";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FIRSTNAME = "FIRSTNAME";
    public static final String COLUMN_LASTNAME = "LASTNAME";
    public static final String COLUMN_HOMENUMBER = "HOMENUMBER";
    public static final String COLUMN_WORKNUMBER = "WORKNUMBER";
    public static final String COLUMN_MOBILENUMBER = "MOBILENUMBER";
    public static final String COLUMN_EMAIL= "EMAIL";
//    public static final String COLUMN_STATE = "STATE";

    public static final int DB_VERSION =1;
    public static final int ALL_CONTACTS =1;
    public static final int CONTACT_ITEM =2;

    public static final String MIME_ALL_CONTACTS = ContentResolver.CURSOR_DIR_BASE_TYPE +"/vnd.contact.chintingko.com";
    public static final String MIME_CONTACT_ITEM = ContentResolver.CURSOR_ITEM_BASE_TYPE +"/vnd.contact.chintingko.com";

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        // content:// contact.chintingko.com/contact
        URI_MATCHER.addURI(AUTHORITY, BASE, ALL_CONTACTS);
        // content:// contact.chintingko.com/contact/42
        URI_MATCHER.addURI(AUTHORITY, BASE + "/#", CONTACT_ITEM);
    }

    private static class OpenHelper extends SQLiteOpenHelper{

        public OpenHelper (Context context){
            super (context, "CONTACT", null, DB_VERSION);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try{
                db.beginTransaction();

                String sql = String.format(
                        "CREATE TABLE %s (%s integer primary key autoincrement, %s text, %s text)",
                        TABLE, COLUMN_ID, COLUMN_FIRSTNAME, COLUMN_LASTNAME, COLUMN_HOMENUMBER, COLUMN_WORKNUMBER, COLUMN_MOBILENUMBER, COLUMN_EMAIL
                );

                db.execSQL(sql);
                onUpgrade(db, 1, DB_VERSION);

                db.setTransactionSuccessful();
            }finally{
                db.endTransaction();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
 /*           try{
                db.beginTransaction();

                switch (oldVersion){
                    default: throw new IllegalStateException("Unexpected old version");
                    case 1:
                        db.execSQL(String. format ("ALTER TABLE %s ADD %s INTEGER",
                                TABLE, COLUMN_STATE));
                    case 2:
                        db.execSQL(String. format ("ALTER TABLE %s ADD %s INTEGER",
                                TABLE, COLUMN_STATE));

                }

                db.setTransactionSuccessful();
            }finally{
                db.endTransaction();
            }
*/
        }
    }

    private SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        db = new OpenHelper(getContext()).getWritableDatabase();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        switch (URI_MATCHER.match(uri)) {
            case ALL_CONTACTS: {
                Cursor c = db.query(TABLE, projection, selection, selectionArgs, null, null, sortOrder);
                if (getContext() == null) {
                    throw new RuntimeException("No content available!");
                }
                c.setNotificationUri(getContext().getContentResolver(), uri);
                return c;
            }
            case CONTACT_ITEM: {
                String id = uri.getLastPathSegment();
                Cursor c = db.query(
                        TABLE,
                        projection,
                        COLUMN_ID + " = ?",
                        new String[]{id},
                        null, null,
                        sortOrder);
                if (getContext() == null) {
                    throw new RuntimeException("No content available!");
                }
                c.setNotificationUri(getContext().getContentResolver(), uri);
                return c;
            }
            default:
                return null;
        }
    }

    @Override
    public String getType(Uri uri) {
        switch (URI_MATCHER.match(uri)){
            case ALL_CONTACTS:
                return MIME_ALL_CONTACTS;
            case CONTACT_ITEM:
                return MIME_CONTACT_ITEM;
            default:
                return null;
            }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id= db. insert(TABLE, null, values);
        notifyChange (uri);
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        int numChanges=0;
        switch (URI_MATCHER.match(uri)){
            case ALL_CONTACTS:
                numChanges = db.update(TABLE, values, selection, selectionArgs);
                break;
            case CONTACT_ITEM: {
                String id =uri.getLastPathSegment();
                numChanges = db.update(TABLE, values, COLUMN_ID +"=?", new String[] {id});
            }
        }

        if (numChanges !=0) {
            notifyChange(uri);
        }
        return numChanges;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int numDeleted=0;
        switch (URI_MATCHER.match(uri)){
        case ALL_CONTACTS:
            numDeleted = db.delete(TABLE, selection, selectionArgs);
            break;

        case CONTACT_ITEM: {
            String id =uri.getLastPathSegment();
            numDeleted = db.delete(TABLE, COLUMN_ID +"=?", new String[] {id});
            }
        }
        if (numDeleted !=0) {
            notifyChange(uri);
        }
        return numDeleted;
    }
    private void notifyChange(Uri uri){
        if (getContext() == null) {
            throw new RuntimeException("No content available!");
        }
        getContext().getContentResolver().notifyChange(uri, null);
        }
}
