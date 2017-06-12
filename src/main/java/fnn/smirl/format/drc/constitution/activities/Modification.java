package fnn.smirl.format.drc.constitution.activities;
import android.support.v7.app.*;
import android.os.*;
import fnn.smirl.simple.*;
import android.view.View.*;
import android.view.*;
import fnn.smirl.format.drc.constitution.*;
import fnn.smirl.format.drc.constitution.core.*;
import android.widget.*;

public class Modification extends AppCompatActivity {

	public boolean editmode = false;
	private Article article;

	private ActionBar actionBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_article_dialog);
		editmode = getIntent().getBooleanExtra("editmode", false);
		article = ConstitutionFormatter.article;
		initToolbar();
		init();
	}

	private void init() {
		// TODO: Implement this method
		final EditText edit_art_num = (EditText)findViewById(R.id.edit_art_num);
		final EditText edit_tit_num = (EditText)findViewById(R.id.edit_tit_num);
		final EditText edit_tit_text = (EditText)findViewById(R.id.edit_tit_text);
		final EditText edit_chap_num = (EditText)findViewById(R.id.edit_chap_num);
		final EditText edit_chap_text = (EditText)findViewById(R.id.edit_chap_text);
		final EditText edit_sect_num = (EditText)findViewById(R.id.edit_sect_num);
		final EditText edit_sect_text = (EditText)findViewById(R.id.edit_sect_text);
		final EditText edit_text_content = (EditText)findViewById(R.id.edit_text_content);

		edit_art_num.setText(article.id + "");
		edit_text_content.setText(article.content);
		edit_tit_num.setText(article.titre.id + "");
		edit_tit_text.setText(article.titre.title);
		edit_chap_num.setText(article.chapitre.id + "");
		edit_chap_text.setText(article.chapitre.title);
		edit_sect_num.setText(article.section.id + "");
		edit_sect_text.setText(article.section.title);


		Button confirm_btn = (Button)findViewById(R.id.confirm_btn);
		confirm_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View p1) {
				// TODO: Implement this method
				String aa = edit_art_num.getText().toString();
				long art_num = Long.parseLong(aa.length() < 1 ? "0" : aa);
				aa = edit_tit_num.getText().toString();
				long tit_num = Long.parseLong(aa.length() < 1 ? "0" : aa);
				aa = edit_chap_num.getText().toString();
				long chap_num = Long.parseLong(aa.length() < 1 ? "0" : aa);
				aa = edit_sect_num.getText().toString();
				long sect_num = Long.parseLong(aa.length() < 1 ? "0" : aa);
				String tit_txt= edit_tit_text.getText().toString();
				String chap_txt= edit_chap_text.getText().toString();
				String sect_txt= edit_sect_text.getText().toString();
				String art_txt= edit_text_content.getText().toString();

				if (!editmode) {
					saveArticle(art_num, art_txt, tit_num, tit_txt, 
					chap_num, chap_txt, sect_num, sect_txt);
				} else {
					article.id = art_num;
					article.content = art_txt;
					article.titre.id = tit_num;
					article.titre.title = tit_txt;
					article.chapitre.id = chap_num;
					article.chapitre.title = chap_txt;
					article.section.id = sect_num;
					article.section.title = sect_txt;
				}
				ConstitutionFormatter.store();
				finish();
			}


		});
		Button cancel_btn = (Button)findViewById(R.id.cancel_btn);
		cancel_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View p1) {
				// TODO: Implement this method
				finish();
			}
		});
	}
	
	private void initToolbar() {
	android.support.v7.widget.Toolbar tb = (android.support.v7.widget.Toolbar)findViewById(R.id.e_toolbar);
		setSupportActionBar(tb);
		actionBar = getSupportActionBar();
		actionBar.setHomeAsUpIndicator(R.drawable.ic_const_edit);
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	private static void saveArticle(long art_num, String art_txt,
	long tit_num, String tit_txt,
	long chap_num, String chap_txt,
	long sect_num, String sect_txt) {
		Article article = new Article();
		article.id = art_num;
		article.content = art_txt;
		article.titre.id = tit_num;
		article.titre.title = tit_txt;
		article.chapitre.id = chap_num;
		article.chapitre.title = chap_txt;
		article.section.id = sect_num;
		article.section.title = sect_txt;
		ConstitutionFormatter.articles.list.add(article);
	}


}
