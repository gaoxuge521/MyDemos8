package com.gxg.demo8.mydemo8.androidgausspager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gxg.demo8.mydemo8.R;

import java.util.ArrayList;
import java.util.List;

public class TabFragment extends Fragment
{
	public static final String TITLE = "title";
	private String mTitle = "Defaut Value";
	private RecyclerView rv;
	private List<String> titles = new ArrayList<>();

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		if (getArguments() != null)
		{
			mTitle = getArguments().getString(TITLE);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_tab, container, false);
		rv = (RecyclerView) view.findViewById(R.id.rv);
		initData();
		return view;

	}

	private void initData() {
		for (int i = 0; i < 20; i++) {
			titles.add(mTitle+i);
		}
		rv.setLayoutManager(new LinearLayoutManager(getActivity()));
		TabTestAdapter adapter = new TabTestAdapter(titles);
		rv.setAdapter(adapter);
	}

	public static TabFragment newInstance(String title)
	{
		TabFragment tabFragment = new TabFragment();
		Bundle bundle = new Bundle();
		bundle.putString(TITLE, title);
		tabFragment.setArguments(bundle);
		return tabFragment;
	}

}
