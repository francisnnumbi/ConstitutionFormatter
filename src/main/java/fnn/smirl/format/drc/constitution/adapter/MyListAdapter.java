package fnn.smirl.format.drc.constitution.adapter;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import android.content.Context;
import fnn.smirl.format.drc.constitution.core.Article;
import android.view.ViewGroup;
import android.view.View;
import android.view.LayoutInflater;
import fnn.smirl.format.drc.constitution.R;
import android.widget.TextView;
import android.view.View.OnClickListener;
import fnn.smirl.format.drc.constitution.ConstitutionFormatter;
import android.view.View.OnLongClickListener;
import android.view.MenuItem;
import android.widget.PopupMenu;

public class MyListAdapter extends ArrayAdapter<Article> {
 Context ctx;

 public MyListAdapter(Context ctx, ArrayList<Article> list) {
	super(ctx, 0, list);
	this.ctx = ctx;
	setNotifyOnChange(true);
 }

 @Override
 public View getView(int position, View convertView, ViewGroup parent) {
	// TODO: Implement this method
	final Article article = getItem(position);
	if (convertView == null) {
	 convertView = LayoutInflater.from(ctx)
		.inflate(R.layout.tamplate, parent, false);
	}
	TextView tv = (TextView)convertView.findViewById(R.id.item1);
	tv.setText(article.toString());

	convertView.setOnLongClickListener(new OnLongClickListener(){

		@Override
		public boolean onLongClick(View p1) {
		 // TODO: Implement this method
		 PopupMenu popup = new PopupMenu(ctx, p1);
		 popup.inflate(R.menu.popup_menu);
		 popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){

			 @Override
			 public boolean onMenuItemClick(MenuItem p1) {
				// TODO: Implement this method
				switch (p1.getItemId()) {
				 case R.id.pop_new:
					ConstitutionFormatter.showEditDialog(new Article(), false);
					break;
				 case R.id.pop_edit:
					ConstitutionFormatter.showEditDialog(article, true);
					break;
				 case R.id.pop_delete:
					ConstitutionFormatter.delete(article);
					break;
				}
				return false;
			 }
			});
			
		 popup.show();
		 return false;
		}

	 });
	return convertView;
 }



}
