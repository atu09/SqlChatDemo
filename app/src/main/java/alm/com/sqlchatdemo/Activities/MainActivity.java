package alm.com.sqlchatdemo.Activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import alm.com.sqlchatdemo.Adapters.ChatAdapter;
import alm.com.sqlchatdemo.R;
import alm.com.sqlchatdemo.RowItems.ChatItem;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase database;

    public static ArrayList<ChatItem> arrayList = new ArrayList<>();

    EditText editText_msgContent;
    Button btn_send;
    ListView listView;
    ChatAdapter adapter;
    String messeger = "Sender";
    String userTable = null;

    private int mYear, mMonth, mDay, mHour, mMinute, mAmPm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        userTable = intent.getExtras().getString("userName");

        //----------------------------------------------------------------------------------------------------------------------

        database = openOrCreateDatabase("SqlChatDemo.db", MODE_PRIVATE, null);
        database.execSQL("create table if not exists '" + userTable + "'(messeger text,msg text, timeStamp text, userType text, msgType text)");

        //----------------------------------------------------------------------------------------------------------------------

        editText_msgContent = (EditText) findViewById(R.id.et_chat_msg);
        btn_send = (Button) findViewById(R.id.btn_chat_send);
        listView = (ListView) findViewById(R.id.lv_chat);

        //----------------------------------------------------------------------------------------------------------------------

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editText_msgContent.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Type Something Duffer...", Toast.LENGTH_SHORT).show();
                } else {
                    store(messeger, "text", messeger, editText_msgContent.getText().toString());

                }

            }
        });

        adapter = new ChatAdapter(this);
        listView.setAdapter(adapter);

        fetchData();

    }

    public void store(String messeger, String msgType, String userType, String msgContent) {

        if (database != null) {

            database.execSQL("create table if not exists '" + userTable + "'(messeger text,msg text, timeStamp text, userType text, msgType text)");

            ContentValues contentValues = new ContentValues();
            contentValues.put("messeger", messeger);
            contentValues.put("msg", msgContent);
            contentValues.put("timeStamp", fetchTimeStamp());
            contentValues.put("userType", userType);
            contentValues.put("msgType", msgType);
            database.insert(userTable, null, contentValues);

            //database.execSQL("insert into chats values('" + messeger + "', '" + msgContent + "','" + fetchTimeStamp() + "', '" + userType + "', '" + msgType + "')");

            ChatItem item = new ChatItem(messeger, msgContent, fetchTimeStamp(), userType, msgType);
            arrayList.add(item);

            adapter.notifyDataSetChanged();

            editText_msgContent.setText("");
            Toast.makeText(getApplicationContext(), "Message Sent...", Toast.LENGTH_SHORT).show();


        } else {

            Toast.makeText(getApplicationContext(), "Database Not Found...", Toast.LENGTH_SHORT).show();
        }

    }

    public String fetchTimeStamp() {
        // Get Current Date
        final Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH) + 1;
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        mHour = calendar.get(Calendar.HOUR);
        mMinute = calendar.get(Calendar.MINUTE);
        mAmPm = calendar.get(Calendar.AM_PM);

        if (mAmPm == 0) {

            return (mDay + "/" + mMonth + "/" + mYear + ", " + mHour + ":" + mMinute + " am");

        } else {

            return (mDay + "/" + mMonth + "/" + mYear + ", " + mHour + ":" + mMinute + " pm");

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_item, menu);

        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.itemSender:
                messeger = "Sender";
                Toast.makeText(MainActivity.this, messeger + " is Selected...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.itemReceiver:
                messeger = "Receiver";
                Toast.makeText(MainActivity.this, messeger + " is Selected...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.itemClear:
                database.execSQL("DROP TABLE IF EXISTS '" + userTable + "'");
                arrayList.clear();
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Message List is Cleared...", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;

        }

        return super.onOptionsItemSelected(item);


    }

    public void fetchData() {

        arrayList.clear();

        if (database != null) {
            Cursor cursor = database.rawQuery("select * from '" + userTable + "'", null);

            if (cursor.getCount() > 0) {

                cursor.moveToFirst();
                do {
                    //Getting data using column name
                    String messger_name = cursor.getString(cursor.getColumnIndex("messeger"));
                    String text_msg = cursor.getString(cursor.getColumnIndex("msg"));
                    String time_stamp = cursor.getString(cursor.getColumnIndex("timeStamp"));
                    String user_type = cursor.getString(cursor.getColumnIndex("userType"));
                    String msg_type = cursor.getString(cursor.getColumnIndex("msgType"));

                    ChatItem item = new ChatItem(messger_name, text_msg, time_stamp, user_type, msg_type);

                    arrayList.add(item);

                } while (cursor.moveToNext());

                adapter.notifyDataSetChanged();

                Toast.makeText(getApplicationContext(), "List is Populated...", Toast.LENGTH_SHORT).show();

            } else {

                Toast.makeText(getApplicationContext(), "List is Empty...", Toast.LENGTH_SHORT).show();

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        database.close();

    }
}
