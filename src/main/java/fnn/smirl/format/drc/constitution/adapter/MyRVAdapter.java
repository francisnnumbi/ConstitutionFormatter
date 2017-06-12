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
import android.support.v7.widget.*;
import android.graphics.*;

public class MyRVAdapter extends RecyclerView.Adapter<MyRVAdapter.ArticleHolder> {

	Context ctx;
	private ArrayList<Article> list;

	public MyRVAdapter(Context ctx, ArrayList<Article> list) {
		this.ctx = ctx;
		this.list = list;
	}

 	@Override
	public MyRVAdapter.ArticleHolder onCreateViewHolder(ViewGroup p1, int p2) {
		// TODO: Implement this method
		View v = LayoutInflater.from(ctx)
		.inflate(R.layout.tamplate, p1, false);
		return new ArticleHolder(v);
	}

	@Override
	public void onBindViewHolder(MyRVAdapter.ArticleHolder p1, int p2) {
		// TODO: Implement this method
		Article a = list.get(p2);
		p1.item1.setText(a.toString());
		p1.item2.setText(a.titre.toString());
		p1.item3.setText(a.chapitre.toString());
		p1.item4.setText(a.section.toString());
	}

	@Override
	public int getItemCount() {
		// TODO: Implement this method
		return list.size();
	}

	class ArticleHolder extends RecyclerView.ViewHolder {
		CardView cardView;
		TextView item1, item2, item3, item4;
		public ArticleHolder(View v) {
			super(v); 
			
			cardView = (CardView)v.findViewById(R.id.cv);

			cardView.setCardBackgroundColor(Color.GRAY);

			item1 = (TextView)v.findViewById(R.id.t_item1);
			item2 = (TextView)v.findViewById(R.id.t_item2);
			item3 = (TextView)v.findViewById(R.id.t_item3);
			item4 = (TextView)v.findViewById(R.id.t_item4);
			
			v.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1) {
					// TODO: Implement this method
					Article article = list.get(getAdapterPosition());
					ConstitutionFormatter.showEditDialog(article, true);
				}	
			});
			
			v.setOnLongClickListener(new OnLongClickListener(){

				@Override
				public boolean onLongClick(View p1) {
					// TODO: Implement this method
					final Article article = list.get(getAdapterPosition());
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

		}
	}

}
