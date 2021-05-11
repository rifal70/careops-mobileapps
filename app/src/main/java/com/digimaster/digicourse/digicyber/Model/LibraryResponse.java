package com.digimaster.digicourse.digicyber.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class LibraryResponse{

	@SerializedName("library:")
	private List<LibraryItem> library;

	@SerializedName("module")
	private Object module;

	public void setLibrary(List<LibraryItem> library){
		this.library = library;
	}

	public List<LibraryItem> getLibrary(){
		return library;
	}

	public void setModule(Object module){
		this.module = module;
	}

	public Object getModule(){
		return module;
	}

	@Override
	public String toString(){
		return
				"LibraryResponse{" +
						"library: = '" + library + '\'' +
						",module = '" + module + '\'' +
						"}";
	}
}