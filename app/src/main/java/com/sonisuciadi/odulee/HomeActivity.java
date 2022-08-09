package com.sonisuciadi.odulee;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sonisuciadi.odulee.DataBaseHelper.ScheduleDataBaseHelper;
import com.sonisuciadi.odulee.Model.Schedule;
import com.sonisuciadi.odulee.Model.Users;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {
    private Toolbar toolbar;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    List<Users> listUser=new ArrayList<>();
    List<Schedule> listSchedule;
    View dialogView;
    FloatingActionButton fabAdd;
    EditText etJudul,etTempat,etDeskripsi;
    TextView tvTanggal, tvTitle;
    Spinner sUser;
    Button btnSimpan,btnHapus;
    RecyclerView rvSchedule;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormatter;
    ScheduleAdapter scheduleAdapter;
    ScheduleDataBaseHelper scheduleDataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        fabAdd=findViewById(R.id.fab_add);
        tvTitle=findViewById(R.id.tv_title);
        tvTitle.setText("Odulee - "+getIntent().getExtras().get("username").toString());
        toolbar = findViewById(R.id.toolBar);
        rvSchedule=findViewById(R.id.rv_schedule);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.option_menu);
        dummyUser();
//        dummySchedule();
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFormSchedule(null,"","","","","");
            }
        });
        setRecyclerView();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.btn_logout){
            startActivity(new Intent(HomeActivity.this,LoginActivity.class));
            }


        return true;
    }
    public void setRecyclerView(){

        retriveSchedule();
        rvSchedule.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
        scheduleAdapter=new ScheduleAdapter(listSchedule, new ScheduleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Schedule item, int position) {
                showFormSchedule(item.getId(),item.getJudul(),item.getTempat(),item.getTanggal(),item.getDeskripsi(),item.getUser());
                Toast.makeText(HomeActivity.this, "YEY", Toast.LENGTH_SHORT).show();
            }
        });
        rvSchedule.setAdapter(scheduleAdapter);
        scheduleAdapter.notifyDataSetChanged();
    }
    public void retriveSchedule() {
        List<Schedule> tampung=new ArrayList<>();
        listSchedule=new ArrayList<>();
        scheduleDataBaseHelper = new ScheduleDataBaseHelper(HomeActivity.this);
        Cursor allData = scheduleDataBaseHelper.retriveSchedule();
        if (allData.getCount() == 0) {
            Toast.makeText(this, "Tidak Ada Data", Toast.LENGTH_SHORT).show();
        } else {
            Schedule schedule = new Schedule();
            while (allData.moveToNext()) {
                schedule = new Schedule();
                schedule.setId(allData.getInt(0));
                schedule.setJudul(allData.getString(1));
                schedule.setTempat(allData.getString(2));
                schedule.setTanggal(allData.getString(3));
                schedule.setDeskripsi(allData.getString(4));
                schedule.setUser(allData.getString(5));
                tampung.add(schedule);
            }
        }
        for (int i=0;i<tampung.size();i++){
            Schedule ischedule=tampung.get(i);
            Schedule schedule = new Schedule();
            if (ischedule.getUser().equals(getIntent().getExtras().get("username").toString())){
                schedule.setId(ischedule.getId());
                schedule.setJudul(ischedule.getJudul());
                schedule.setTempat(ischedule.getTempat());
                schedule.setTanggal(ischedule.getTanggal());
                schedule.setDeskripsi(ischedule.getDeskripsi());
                schedule.setUser(ischedule.getUser());
                listSchedule.add(schedule);
            }
        }
    }
    public void showFormSchedule(Integer id, String judul, String tempat, String tanggal, String deskripsi, String pembuat){
        ArrayList user=new ArrayList();
        dialog = new AlertDialog.Builder(HomeActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.manage_list_schedule, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setTitle("Form Schedule");
        etJudul=dialogView.findViewById(R.id.et_judul);
        etTempat=dialogView.findViewById(R.id.et_tempat);
        tvTanggal=dialogView.findViewById(R.id.tv_tanggal);
        etDeskripsi=dialogView.findViewById(R.id.et_deskripsi);
        sUser=dialogView.findViewById(R.id.s_users);
//        btnHapus=dialogView.findViewById(R.id.btn_delete);

        etJudul.setText(judul);
        etTempat.setText(tempat);
        tvTanggal.setText(tanggal);
        etDeskripsi.setText(deskripsi);


//        btnSimpan=dialogView.findViewById(R.id.btn_exc);
        dialog.setPositiveButton("Simpan", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                scheduleDataBaseHelper = new ScheduleDataBaseHelper(HomeActivity.this);
                if (id==null){
                    long hsl=scheduleDataBaseHelper.insertSchedule(etJudul.getText().toString(),etTempat.getText().toString(),tvTanggal.getText().toString(),etDeskripsi.getText().toString(),sUser.getSelectedItem().toString());
                    if (hsl!=-1){
                        Toast.makeText(HomeActivity.this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(HomeActivity.this, "Data Gagal Disimpan", Toast.LENGTH_SHORT).show();
                    }
                }else if (id!=null){
                    if (sUser.getSelectedItem().toString().equals(getIntent().getExtras().get("username").toString())){
                        long hsl=scheduleDataBaseHelper.updateSchedule(String.valueOf(id),etJudul.getText().toString(),etTempat.getText().toString(),tvTanggal.getText().toString(),etDeskripsi.getText().toString(),sUser.getSelectedItem().toString());
                        if (hsl!=-1){
                            Toast.makeText(HomeActivity.this, "Data Berhasil Diupdate", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(HomeActivity.this, "Data Gagal Diupdate", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        long hsl=scheduleDataBaseHelper.insertSchedule(etJudul.getText().toString(),etTempat.getText().toString(),tvTanggal.getText().toString(),etDeskripsi.getText().toString(),sUser.getSelectedItem().toString());
                        if (hsl!=-1){
                            Toast.makeText(HomeActivity.this, "Data Berhasil Dibagi ke "+sUser.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(HomeActivity.this, "Data Gagal Dibagikan", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
                dialog.dismiss();
                setRecyclerView();

            }
        });

        dialog.setNegativeButton("Hapus", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                scheduleDataBaseHelper = new ScheduleDataBaseHelper(HomeActivity.this);
                long hsl=scheduleDataBaseHelper.deleteSchedule(String.valueOf(id));
                if (hsl!=-1){
                    Toast.makeText(HomeActivity.this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(HomeActivity.this, "Data Gagal Dihapus", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
                setRecyclerView();

            }
        });


        for (int i=0;i<listUser.size();i++){
            Users users=listUser.get(i);
            user.add(users.getNama());
        }
        sUser.setAdapter(new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item,user
        ));
        tvTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimeDialog();
            }
        });





//        dialog.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                Toast.makeText(HomeActivity.this, "Berhasil Disimpan YEE !!!", Toast.LENGTH_SHORT).show();
//            }
//        });
//        dialog.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                Toast.makeText(HomeActivity.this, "GAGAL! :(", Toast.LENGTH_SHORT).show();
//            }
//        });
        dialog.show();
    }
    public void dummyUser(){
        Users users=new Users();
        users.setId(1);
        users.setNama("Soni Suciadi");
        users.setAlamat("11 Ulu");
        users.setPassword("1");
        listUser.add(users);

        users=new Users();
        users.setId(2);
        users.setNama("Juan");
        users.setAlamat("");
        users.setPassword("2");
        listUser.add(users);

        users=new Users();
        users.setId(3);
        users.setNama("Jesicca");
        users.setAlamat("");
        users.setPassword("3");
        listUser.add(users);

        users=new Users();
        users.setId(4);
        users.setNama("Ricky");
        users.setAlamat("");
        users.setPassword("4");
        listUser.add(users);
    }
    public void dummySchedule(){
        Schedule schedule=new Schedule();
        schedule.setId(1);
        schedule.setJudul("Nongki");
        schedule.setTempat("Palembang");
        schedule.setTanggal("2021-06-20");
        schedule.setDeskripsi("test");
        schedule.setUser("Soni Suciadi");
        listSchedule.add(schedule);

        schedule=new Schedule();
        schedule.setId(2);
        schedule.setJudul("Nongki");
        schedule.setTempat("Palembang");
        schedule.setTanggal("2021-06-20");
        schedule.setDeskripsi("test");
        schedule.setUser("Soni Suciadi");
        listSchedule.add(schedule);

        schedule=new Schedule();
        schedule.setId(3);
        schedule.setJudul("Nongki");
        schedule.setTempat("Palembang");
        schedule.setTanggal("2021-06-20");
        schedule.setDeskripsi("test");
        schedule.setUser("Soni Suciadi");
        listSchedule.add(schedule);

    }
    public void showTimeDialog(){
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                /**
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 */

                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */
                tvTanggal.setText(dateFormatter.format(newDate.getTime()));
            }

        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();
    }
}