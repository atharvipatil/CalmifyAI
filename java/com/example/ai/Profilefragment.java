package com.example.ai;  // ✅ CORRECT!
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Profilefragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        SharedPreferences prefs = requireContext()
                .getSharedPreferences("CalmifyPrefs", Context.MODE_PRIVATE);

        TextView tvName       = view.findViewById(R.id.tvProfileName);
        TextView tvAge        = view.findViewById(R.id.tvProfileAge);
        TextView tvProfession = view.findViewById(R.id.tvProfileProfession);

        tvName.setText(prefs.getString("userName", "User"));
        tvAge.setText("Age: " + prefs.getInt("userAge", 0));
        tvProfession.setText("Profession: " + prefs.getString("userProfession", "—"));

        return view;
    }
}