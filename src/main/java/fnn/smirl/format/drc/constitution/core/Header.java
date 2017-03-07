package fnn.smirl.format.drc.constitution.core;

public abstract class Header
{
 private transient String className;
 public long id=0;
 public String title="";

 public Header(String className) {
	this.className = className;
 }

 @Override
 public String toString(){
	return className+" "+id+ " : "+ title;
 }
}
