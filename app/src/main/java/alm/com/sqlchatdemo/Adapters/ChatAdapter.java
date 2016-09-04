package alm.com.sqlchatdemo.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import alm.com.sqlchatdemo.Activities.MainActivity;
import alm.com.sqlchatdemo.R;

/**
 * Created by alivemind on 16/07/16.
 */
public class ChatAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;

    public ChatAdapter(Context context) {

        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    class ViewHolder {
        TextView tv_msgContent;
        TextView tv_timeStamp;
    }

    @Override
    public int getCount() {
        return MainActivity.arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return MainActivity.arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if(convertView == null){
            holder = new ViewHolder();


            if(MainActivity.arrayList.get(position).getUserType().equals("Sender")) {
                convertView = View.inflate(context, R.layout.sender_item, null);
            } else {
                convertView = View.inflate(context, R.layout.receiver_item, null);
            }

            holder.tv_msgContent = (TextView) convertView.findViewById(R.id.tv_chatMsg);
            holder.tv_timeStamp = (TextView) convertView.findViewById(R.id.tv_chatTimeStamp);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_msgContent.setText(MainActivity.arrayList.get(position).getMsgContent());
        holder.tv_timeStamp.setText(MainActivity.arrayList.get(position).getTimeStamp());

        return convertView;
    }
}
