package fnn.smirl.format.drc.constitution;

import android.support.design.widget.*;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import fnn.smirl.format.drc.constitution.core.Article;
import android.content.Context;
import fnn.smirl.format.drc.constitution.adapter.TabsPagerAdapter;
import fnn.smirl.format.drc.constitution.core.Articles;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import android.app.Activity;
import fnn.smirl.format.drc.constitution.adapter.MyListAdapter;
import java.io.File;
import android.os.Environment;
import android.util.Log;
import fnn.smirl.simple.Serializer;

public class ConstitutionFormatter extends AppCompatActivity 
implements OnClickListener {
 public static Context ctx;
 public static Activity ACTIVITY;
 ActionBar actionBar;
 FloatingActionButton fab;
 TabLayout tabbar;
 ViewPager pager;
 TabsPagerAdapter tabAdapter;

 static Serializer serializer = new Serializer();

 static File sdcard = Environment.getExternalStorageDirectory();
 static File dir = new File(sdcard.getAbsolutePath() + "/constitution/db");
 static File filename = new File(dir, "/constitution.json");

 public static Articles articles;
 public static MyListAdapter adapter;

 @Override
 protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);
	ctx = getApplicationContext();
	ACTIVITY = this;
	init();
 }

 private void init() {
	initToolbar();
	initTabbar();
	initViewPager();
	initFab();
	if (!dir.exists())dir.mkdirs();
	articles = new Articles();
	initArticle();
	adapter = new MyListAdapter(ctx, articles.list);

 }

 private boolean canWriteOnExternalStorage() {
	String state =
	 Environment.getExternalStorageState();
	if (Environment.MEDIA_MOUNTED.equals(state)) {
	 Log.v("Constitution", "Yes, write on SD Card possible");
	 return true;
	}
	return false;
 }

 private void initToolbar() {
	Toolbar tb = (Toolbar)findViewById(R.id.toolbar);
	setSupportActionBar(tb);
	actionBar = getSupportActionBar();
	actionBar.setHomeAsUpIndicator(R.drawable.ic_const_edit);
	actionBar.setDisplayHomeAsUpEnabled(true);

 }

 private void initTabbar() {
	tabbar = (TabLayout)findViewById(R.id.tabLayout);
	// add tabs;
	tabbar.addTab(tabbar.newTab());
 }

 private void initFab() {
	fab = (FloatingActionButton)findViewById(R.id.fab);
	fab.setOnClickListener(this);
 }

 private void initViewPager() {
	pager = (ViewPager)findViewById(R.id.my_pager);
	tabAdapter = new TabsPagerAdapter(getSupportFragmentManager());
	pager.setAdapter(tabAdapter);
	tabbar.setupWithViewPager(pager);
 }

 @Override
 public void onClick(View p1) {
	// TODO: Implement this method
	switch (p1.getId()) {
	 case R.id.fab:

		showSnack("New article");
		//loadArticleRes();
		showEditDialog(new Article(), false);
		break;
	}
 }

 public static void delete(Article article) {
	articles.list.remove(article);
	store();
	adapter.notifyDataSetChanged();
 }
 private static void store() {
	showSnack("Mise à jour de la base des données en cours...");
	serializer.serialize(filename.getAbsolutePath(), articles, Articles.class);
	showSnack("Mise à jour terminée.");
 }

 private static void retrieve() {
	articles = serializer.deserialize(filename.getAbsolutePath(), Articles.class);
 }

 private void initArticle() {
	if (!filename.exists()) {
	 Article a = new Article();
	 a.id = 1;
	 a.content = "new";
	 a.titre.id = 1;
	 a.titre.title = "this title";
	 a.chapitre.id = 1;
	 a.chapitre.title = "this chapter";
	 a.section.id = 1;
	 a.section.title = "this title";

	 articles.list.add(a);
	 store();
	}
	retrieve();
 }

 public void loadArticleRes() {
	for (int i = 1; i <= 229; i++) {
	 int er = getResources().getIdentifier("Article_" + i, "string", getPackageName());
	 String t = getResources().getString(er);
	 Article ar = new Article();
	 ar.id = i;
	 ar.content = t;
	 articles.list.add(ar);
	}
	store();
	adapter.notifyDataSetChanged();
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
	articles.list.add(article);
 }

 public static void showEditDialog(final Article article, final boolean editmode) {
	String dialTitle=editmode ?"Modifier...": "Nouveau";
	final Dialog dialog = new Dialog(ACTIVITY);
	dialog.setContentView(R.layout.edit_article_dialog);
	dialog.setTitle(dialTitle);

	final EditText edit_art_num = (EditText)dialog.findViewById(R.id.edit_art_num);
	final EditText edit_tit_num = (EditText)dialog.findViewById(R.id.edit_tit_num);
	final EditText edit_tit_text = (EditText)dialog.findViewById(R.id.edit_tit_text);
	final EditText edit_chap_num = (EditText)dialog.findViewById(R.id.edit_chap_num);
	final EditText edit_chap_text = (EditText)dialog.findViewById(R.id.edit_chap_text);
	final EditText edit_sect_num = (EditText)dialog.findViewById(R.id.edit_sect_num);
	final EditText edit_sect_text = (EditText)dialog.findViewById(R.id.edit_sect_text);
	final EditText edit_text_content = (EditText)dialog.findViewById(R.id.edit_text_content);

	edit_art_num.setText(article.id + "");
	edit_text_content.setText(article.content);
	edit_tit_num.setText(article.titre.id + "");
	edit_tit_text.setText(article.titre.title);
	edit_chap_num.setText(article.chapitre.id + "");
	edit_chap_text.setText(article.chapitre.title);
	edit_sect_num.setText(article.section.id + "");
	edit_sect_text.setText(article.section.title);


	Button confirm_btn = (Button)dialog.findViewById(R.id.confirm_btn);
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
		 store();
		 adapter.notifyDataSetChanged();
		 dialog.dismiss();
		}


	 });
	Button cancel_btn = (Button)dialog.findViewById(R.id.cancel_btn);
	cancel_btn.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View p1) {
		 // TODO: Implement this method
		 dialog.dismiss();
		}


	 });
	dialog.show();
 }

 private static void showSnack(String msg) {
	Snackbar.make(ACTIVITY.findViewById(R.id.coordinatorLayout),
								msg,
								Snackbar.LENGTH_SHORT)
	 .setAction("Action", new ConstitutionFormatter())
	 .show();
 }
}
