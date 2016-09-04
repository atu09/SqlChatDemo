package alm.com.sqlchatdemo.Activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import alm.com.sqlchatdemo.Adapters.InboxAdapter;
import alm.com.sqlchatdemo.R;
import alm.com.sqlchatdemo.RowItems.InboxItem;
import alm.com.sqlchatdemo.Services.RegistrationIntentService;

/**
 * Created by alivemind on 18/07/16.
 */
public class InboxActivity extends AppCompatActivity implements ListView.OnItemClickListener {

    SQLiteDatabase database;

    public static ArrayList<InboxItem> arrayList = new ArrayList<>();
    InboxAdapter adapter;

    ListView listView;

    private int mYear, mMonth, mDay, mHour, mMinute, mAmPm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);

        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);

        database = openOrCreateDatabase("SqlChatDemo.db", MODE_PRIVATE, null);
        database.execSQL("create table if not exists inbox(userName text, timeStamp text)");

        listView = (ListView) findViewById(R.id.lv_inbox);

        adapter = new InboxAdapter(this);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);

        addingData("Atirek");
        addingData("Dipti");

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

    public void addingData(String userName) {

        if (database != null) {

            database.execSQL("create table if not exists inbox(userName text, timeStamp text)");

            ContentValues contentValues = new ContentValues();
            contentValues.put("userName", userName);
            contentValues.put("timeStamp", fetchTimeStamp());
            database.insert("inbox", null, contentValues);

            //database.execSQL("insert into chats values('" + messeger + "', '" + msgContent + "','" + fetchTimeStamp() + "', '" + userType + "', '" + msgType + "')");
            database.execSQL("create table if not exists '" + userName + "'(messeger text,msg text, timeStamp text, userType text, msgType text)");

            InboxItem item = new InboxItem(userName, fetchTimeStamp());
            arrayList.add(item);

            adapter.notifyDataSetChanged();


        } else {

            Toast.makeText(getApplicationContext(), "Database Not Found...", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("userName", arrayList.get(position).getUserName());
        startActivity(intent);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        arrayList.clear();

    }
}
