package com.example.goquizz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class TopicAdapter extends BaseAdapter { // kế thừa BaseAdapter - Adapter đơn giản nhất trong ListView

    private Context context; // truyền vào màn hình (activity) sẽ hiển thị
    private int layout; // layout trả về kiểu int
    private List<Topic> TopicList;

    public TopicAdapter(Context context, int layout, List<Topic> traiCayList) {
        this.context = context;
        this.layout = layout;
        this.TopicList = traiCayList;
    }

    @Override
    public int getCount() {
        return TopicList.size();
    } // để xác định được số item có trong listview

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        ImageView imgViewHinh;
        TextView txtTen, txtMoTa;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); // Lấy layout chèn vào

            viewHolder = new ViewHolder();

            convertView = inflater.inflate(layout, null);

            // ánh xạ view
            viewHolder.txtTen = (TextView) convertView.findViewById(R.id.textViewTen);
            viewHolder.txtMoTa = (TextView) convertView.findViewById(R.id.textViewMoTa);
            viewHolder.imgViewHinh = (ImageView) convertView.findViewById(R.id.imageViewHinh);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // gán giá trị
        Topic traiCay = TopicList.get(position);
        viewHolder.txtTen.setText(traiCay.getTen());
        viewHolder.txtMoTa.setText(traiCay.getMota());
        viewHolder.imgViewHinh.setImageResource(traiCay.getHinh());

        return convertView;
    }
}
