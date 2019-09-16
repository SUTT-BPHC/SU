package com.example.su;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class UpdateAvailabilityFragment extends Fragment {

	CardView markAvailable;
	CardView markUnavailable;

	public UpdateAvailabilityFragment() {}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_availability_update, container,false);

		markAvailable = rootView.findViewById(R.id.mark_available_card_view);
		markUnavailable = rootView.findViewById(R.id.mark_unavailable_card_view);

		markAvailable.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//TODO: Add code to update availability
			}
		});


		markUnavailable.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//TODO: Add code to update availability
			}
		});
		return rootView;
	}
}