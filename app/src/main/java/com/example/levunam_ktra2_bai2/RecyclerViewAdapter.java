package com.example.levunam_ktra2_bai2;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CalendarViewHolder> {
    private List<Calendar> list;

    public RecyclerViewAdapter() {
        list = new ArrayList<>();
    }

    public void setCalendars(List<Calendar> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.hienthilich, parent, false);
        return new CalendarViewHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        Calendar calendar = list.get(position);
        holder.tvIdName.setText(calendar.getId() + " - " + calendar.getTenMon());
        holder.tvDate.setText("Ngày thi: " + calendar.getNgayThi());
        holder.tvTime.setText("Giờ thi: " + calendar.getGioBatDau());
        holder.tvType.setText("Kiểu thi: " + calendar.getKieuThi());
    }

    @Override
    public int getItemCount() {
        if (list != null)
            return list.size();
        else
            return 0;
    }

    class CalendarViewHolder extends RecyclerView.ViewHolder {
        private TextView tvIdName;
        private TextView tvDate;
        private TextView tvTime;
        private TextView tvType;

        public CalendarViewHolder(@NonNull View v) {
            super(v);
            tvIdName = v.findViewById(R.id.idMon);
            tvDate = v.findViewById(R.id.ngayThi);
            tvTime = v.findViewById(R.id.gioBD);
            tvType = v.findViewById(R.id.thiViet);
        }
    }
}
