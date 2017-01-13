package top.flyyoung.www.flyyoung.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import top.flyyoung.www.flyyoung.Datas.Message;
import top.flyyoung.www.flyyoung.R;

/**
 * Created by 69133 on 2017/1/13.
 */

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {
    private List<Message> mMessages;

    public class  ViewHolder extends RecyclerView.ViewHolder{
        TextView messageContent;
        TextView messageIP;
        TextView messageDate;

        public ViewHolder(View view){
            super(view);

            messageContent=(TextView)view.findViewById(R.id.messages_list_Content);
            messageIP=(TextView)view.findViewById(R.id.messages_list_ip);
            messageDate=(TextView)view.findViewById(R.id.messages_list_date);

        }

    }

    public  MessagesAdapter(List<Message> messages){
        mMessages=messages;

    }


    @Override
    public void onBindViewHolder(MessagesAdapter.ViewHolder holder, int position) {
        //super.onBindViewHolder(holder, position, payloads);
        Message message=mMessages.get(position);
        holder.messageDate.setText(message.getCreateDate().toString()+" "+message.getCreateTime());
        holder.messageIP.setText(message.getIPAddress().toString());
        holder.messageContent.setText(message.getMessageContent().toString());
    }

    @Override
    public MessagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.messages_list_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return  viewHolder;
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }
}
