package com.example.projectlab8;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class MonAnAdapter extends BaseAdapter {
    private Context context;
    private List<MonAn> list;

    public MonAnAdapter(Context context, List<MonAn> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() { return list.size(); }

    @Override
    public Object getItem(int position) { return list.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.row_item, parent, false);
        }

        // Ánh xạ view
        ImageView imgMonAn = convertView.findViewById(R.id.imgMonAn);
        TextView txtTenMon = convertView.findViewById(R.id.txtTenMon);
        TextView txtGia = convertView.findViewById(R.id.txtGia);
        TextView txtMoTa = convertView.findViewById(R.id.txtMoTa);

        // Lấy dữ liệu
        MonAn monAn = list.get(position);

        // Gán dữ liệu
        imgMonAn.setImageResource(monAn.getHinhAnh());
        txtTenMon.setText(monAn.getTenMon());
        txtMoTa.setText(monAn.getMoTa());

        // Định dạng giá tiền
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String giaTien = formatter.format(monAn.getGia()).replace("₫", "đ");
        txtGia.setText(giaTien);

        return convertView;
    }
}