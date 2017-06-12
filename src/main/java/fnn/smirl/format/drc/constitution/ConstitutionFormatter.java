package fnn.smirl.format.drc.constitution;

import android.app.*;
import android.content.*;
import android.os.*;
import android.support.design.widget.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import fnn.smirl.format.drc.constitution.adapter.*;
import fnn.smirl.format.drc.constitution.core.*;
import fnn.smirl.simple.*;
import java.io.*;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import fnn.smirl.format.drc.constitution.activities.*;

public class ConstitutionFormatter extends AppCompatActivity 
implements OnClickListener {
	public static Context ctx;
	public static Activity ACTIVITY;
	static ActionBar actionBar;
	FloatingActionButton fab;

	static Serializer serializer = new Serializer();

	static File sdcard = Environment.getExternalStorageDirectory();
	static File dir = new File(sdcard.getAbsolutePath() + "/constitution/db");
	static File filename = new File(dir, "/constitution.json");

	public static Articles articles;
	public static Article article;
	public static MyRVAdapter adapter;

	private RecyclerView list;

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
		initFab();
		if (!dir.exists())dir.mkdirs();
		articles = new Articles();
		initArticle();
		actionBar.setSubtitle("DB v." + articles.versionId);
		adapter = new MyRVAdapter(ctx, articles.list);
		initDisplay();
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

	private void initDisplay() {
		list = (RecyclerView)findViewById(R.id.home_list);
		list.setHasFixedSize(true);
		LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
		llm.setOrientation(LinearLayoutManager.VERTICAL);
		list.setLayoutManager(llm);

		list.setAdapter(adapter);

	 }

	private void initFab() {
		fab = (FloatingActionButton)findViewById(R.id.fab);
		fab.setOnClickListener(this);
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

	 }
	public static void store() {
		articles.versionId = Utils.getDateVersion();
		showSnack("Mise à jour de la base des données en cours...");
		serializer.serialize(filename.getAbsolutePath(), articles, Articles.class);
		showSnack("Mise à jour terminée.");
		adapter.notifyDataSetChanged();
		actionBar.setSubtitle("DB v." + articles.versionId);
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



	public static void showEditDialog(Article _article, boolean editmode) {
		article = _article;
		Bundle b = new Bundle();
		b.putBoolean("editmode", editmode);
		Intent intent = new Intent(ACTIVITY, fnn.smirl.format.drc.constitution.activities.Modification.class);
		intent.putExtras(b);
		ACTIVITY.startActivity(intent);

	 }

	private static void showSnack(String msg) {
		Snackbar.make(ACTIVITY.findViewById(R.id.coordinatorLayout),
									msg,
									Snackbar.LENGTH_SHORT)
		 .setAction("Action", new ConstitutionFormatter())
		 .show();
	 }

	@Override
	protected void onResume() {
		// TODO: Implement this method
		super.onResume();
		adapter.notifyDataSetChanged();
	 }


 }
