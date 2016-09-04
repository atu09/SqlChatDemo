package alm.com.sqlchatdemo.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import alm.com.sqlchatdemo.Activities.InboxActivity;
import alm.com.sqlchatdemo.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by alivemind on 18/07/16.
 */
public class InboxAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;

    public InboxAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    class ViewHolder {
        TextView tv_userName;
        TextView tv_contactTime;
        CircleImageView iv_profilePic;
    }

    @Override
    public int getCount() {
        return InboxActivity.arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return InboxActivity.arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();

            convertView = View.inflate(context, R.layout.inbox_item, null);

            holder.iv_profilePic = (CircleImageView) convertView.findViewById(R.id.iv_inboxUserProfile);
            holder.tv_userName = (TextView) convertView.findViewById(R.id.tv_inboxUserName);
            holder.tv_contactTime = (TextView) convertView.findViewById(R.id.tv_inboxTime);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_userName.setText(InboxActivity.arrayList.get(position).getUserName());
        holder.tv_contactTime.setText(InboxActivity.arrayList.get(position).getContactTime());

        return convertView;
    }
}