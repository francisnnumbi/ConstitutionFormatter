package fnn.smirl.format.drc.constitution.fragment;
import android.view.*;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ListView;
import fnn.smirl.format.drc.constitution.ConstitutionFormatter;
import fnn.smirl.format.drc.constitution.R;

public class Home extends Fragment {
 View view;
 ListView list;

 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	// TODO: Implement this method
	view = inflater.inflate(R.layout.home, container, false);
	return view;
 }

 @Override
 public void onViewCreated(View view, Bundle savedInstanceState) {
	// TODO: Implement this method
	super.onViewCreated(view, savedInstanceState);
	list = (ListView)view.findViewById(R.id.home_list);
	list.setAdapter(ConstitutionFormatter.adapter);
 }
}
