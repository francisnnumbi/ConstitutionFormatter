package fnn.smirl.format.drc.constitution.core;

public class Article {
 public long id = 1;
 public String content = "";
 public Titre titre = new Titre();
 public Chapitre chapitre = new Chapitre();
 public Section section = new Section();
 public boolean bookmark = false;
 public String note = "";

 @Override
 public String toString() {
	// TODO: Implement this method
	return "Article " + id;
 }

}
