package com.example.classproject_anshup;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class Tab3Fragment extends Fragment {
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return (LinearLayout) inflater.inflate(R.layout.tab3, container, false);
    }
}
