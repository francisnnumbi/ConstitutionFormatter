package fnn.smirl.format.drc.constitution.adapter;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.Fragment;
import fnn.smirl.format.drc.constitution.fragment.Home;

public class TabsPagerAdapter extends FragmentPagerAdapter
{
 public TabsPagerAdapter(FragmentManager fm) {
	super(fm);
 }
 @Override
 public Fragment getItem(int p1) {
	// TODO: Implement this method
	switch (p1) {
	 case 0:
		return new Home();
	 
	}
	return null;
 }

 @Override
 public CharSequence getPageTitle(int position) {
	// TODO: Implement this method
	String title = null;
	switch(position){
	 case 0:
		title = "Home";
	}
	return title;
 }
 

 @Override
 public int getCount() {
	// TODO: Implement this method
	return 1;
 }
}
