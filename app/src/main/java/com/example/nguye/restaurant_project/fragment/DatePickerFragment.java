package com.example.nguye.restaurant_project.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.nguye.restaurant_project.R;

import java.util.Calendar;

/**
 * Created by Admin on 4/21/2018.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int iNam = calendar.get(Calendar.YEAR);
        int iThang = calendar.get(Calendar.MONTH);
        int iNgay = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, iNgay, iThang, iNam);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        EditText edNgaySinh = (EditText) getActivity().findViewById(R.id.edNgaySinh);
        String ngaySinh = dayOfMonth + "/" + (monthOfYear+1) + "/" + year;
        edNgaySinh.setText(ngaySinh);
    }
}
