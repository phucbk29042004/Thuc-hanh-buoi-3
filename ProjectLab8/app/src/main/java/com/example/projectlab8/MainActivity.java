package com.example.projectlab8;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FrameLayout container;
    private ListView listView;
    private GridView gridView;
    private TextView txtInfo, txtTitle;
    private ImageButton btnAdd;
    private Button btnListView, btnGridView, btnQuanLy;

    private ArrayList<MonAn> danhSach;
    private MonAnAdapter adapter;
    private int currentMode = 0; // 0:List 1:Grid 2:Quản lý
    private int selectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        anhXa();
        khoiTaoDuLieu();
        adapter = new MonAnAdapter(this, danhSach);

        hienThiListView();

        btnListView.setOnClickListener(v -> hienThiListView());
        btnGridView.setOnClickListener(v -> hienThiGridView());
        btnQuanLy.setOnClickListener(v -> hienThiQuanLy());
        btnAdd.setOnClickListener(v -> themMonAn());
    }

    private void anhXa() {
        container = findViewById(R.id.container);
        txtInfo = findViewById(R.id.txtInfo);
        txtTitle = findViewById(R.id.txtTitle);
        btnAdd = findViewById(R.id.btnAdd);
        btnListView = findViewById(R.id.btnListView);
        btnGridView = findViewById(R.id.btnGridView);
        btnQuanLy = findViewById(R.id.btnQuanLy);
    }

    private void khoiTaoDuLieu() {
        danhSach = new ArrayList<>();
        danhSach.add(new MonAn("Phở bò", 45000, "Nước dùng trong", R.drawable.pho));
        danhSach.add(new MonAn("Bún chả", 50000, "Thịt nướng", R.drawable.bun_cha));
        danhSach.add(new MonAn("Cơm tấm", 40000, "Sườn bì chả", R.drawable.com_tam));
        danhSach.add(new MonAn("Bánh mì", 25000, "Pate thịt", R.drawable.banh_mi));
        danhSach.add(new MonAn("Cơm gà", 35000, "Gà ta", R.drawable.com_ga));
    }

    private void capNhatTieuDe() {
        if (currentMode == 0) txtTitle.setText("BÀI 1 - Custom ListView");
        else if (currentMode == 1) txtTitle.setText("BÀI 2 - Custom GridView");
        else txtTitle.setText("BÀI 3 - Quản Lý Món Ăn");
        btnAdd.setVisibility(currentMode == 2 ? View.VISIBLE : View.GONE);
    }

    private void hienThiListView() {
        currentMode = 0; capNhatTieuDe();
        container.removeAllViews();
        View v = getLayoutInflater().inflate(R.layout.layout_listview, container, false);
        listView = v.findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((p, view, pos, id) -> showInfo(pos));
        container.addView(v);
    }

    private void hienThiGridView() {
        currentMode = 1; capNhatTieuDe();
        container.removeAllViews();
        View v = getLayoutInflater().inflate(R.layout.layout_gridview, container, false);
        gridView = v.findViewById(R.id.gridView);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener((p, view, pos, id) -> showInfo(pos));
        container.addView(v);
    }

    private void hienThiQuanLy() {
        currentMode = 2; capNhatTieuDe();
        container.removeAllViews();
        View v = getLayoutInflater().inflate(R.layout.layout_listview, container, false);
        listView = v.findViewById(R.id.listView);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);

        listView.setOnItemLongClickListener((p, view, pos, id) -> {
            selectedPosition = pos;
            return false;
        });

        listView.setOnItemClickListener((p, view, pos, id) -> showInfo(pos));
        container.addView(v);
    }

    private void showInfo(int pos) {
        MonAn m = danhSach.get(pos);
        txtInfo.setText(m.getTenMon() + " - " + m.getGia() + "đ\n" + m.getMoTa());
    }

    // ContextMenu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (currentMode == 2) {
            getMenuInflater().inflate(R.menu.menu_context, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (currentMode != 2 || selectedPosition == -1) return super.onContextItemSelected(item);

        if (item.getItemId() == R.id.menu_edit) {
            suaMonAn(selectedPosition);
        } else if (item.getItemId() == R.id.menu_delete) {
            danhSach.remove(selectedPosition);
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Đã xóa!", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private void themMonAn() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thêm món ăn");
        View view = getLayoutInflater().inflate(R.layout.dialog, null);
        EditText edtTen = view.findViewById(R.id.edtTen);
        EditText edtGia = view.findViewById(R.id.edtGia);
        EditText edtMoTa = view.findViewById(R.id.edtMoTa);
        builder.setView(view);

        builder.setPositiveButton("Thêm", (d, w) -> {
            String ten = edtTen.getText().toString().trim();
            String giaStr = edtGia.getText().toString().trim();
            String moTa = edtMoTa.getText().toString().trim();
            if (!ten.isEmpty() && !giaStr.isEmpty()) {
                int gia = Integer.parseInt(giaStr);
                danhSach.add(new MonAn(ten, gia, moTa, R.drawable.pho)); // tạm dùng hình phở
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "Đã thêm!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    private void suaMonAn(int pos) {
        MonAn m = danhSach.get(pos);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sửa món ăn");
        View view = getLayoutInflater().inflate(R.layout.dialog, null);
        EditText edtTen = view.findViewById(R.id.edtTen);
        EditText edtGia = view.findViewById(R.id.edtGia);
        EditText edtMoTa = view.findViewById(R.id.edtMoTa);
        edtTen.setText(m.getTenMon());
        edtGia.setText(String.valueOf(m.getGia()));
        edtMoTa.setText(m.getMoTa());
        builder.setView(view);

        builder.setPositiveButton("Lưu", (d, w) -> {
            String ten = edtTen.getText().toString().trim();
            String giaStr = edtGia.getText().toString().trim();
            String moTa = edtMoTa.getText().toString().trim();
            if (!ten.isEmpty() && !giaStr.isEmpty()) {
                m.setTenMon(ten);
                m.setGia(Integer.parseInt(giaStr));
                m.setMoTa(moTa);
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "Đã sửa!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Hủy", null);
        builder.show();
    }
}