package com.example.su;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.su.Adapters.ProfessorAdapter;
import com.example.su.Items.Professor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class ProfessorAvailabilityFragment extends Fragment {

	public ProfessorAvailabilityFragment() {}

	private RecyclerView recyclerView;
	private ProgressBar progressBar;
	private DatabaseReference profref;
	ArrayList<Professor> professors;
	ProfessorAdapter adapter;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_professor_availability, container, false);

		recyclerView = rootView.findViewById(R.id.professor_availability_recycler_view);
		progressBar = rootView.findViewById(R.id.progress_circular_professor_availability);
		profref = FirebaseDatabase.getInstance().getReference().child("Professors");

		professors = new ArrayList<>();
		profref.keepSynced(true);

		setLoadingView();

		profref.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
					Professor professor = dataSnapshot1.getValue(Professor.class);
					professors.add(professor);
				}
				setRecyclerView();
				adapter = new ProfessorAdapter(professors);
				recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
				recyclerView.setAdapter(adapter);
			}

			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {
				Toast.makeText(getContext(),"Something went wrong!",Toast.LENGTH_SHORT).show();
			}
		});
		return rootView;
	}

	private void setLoadingView()
	{
		recyclerView.setVisibility(View.INVISIBLE);
		progressBar.setVisibility(View.VISIBLE);
	}

	private void setRecyclerView()
	{
		recyclerView.setVisibility(View.VISIBLE);
		progressBar.setVisibility(View.INVISIBLE);
	}

}
