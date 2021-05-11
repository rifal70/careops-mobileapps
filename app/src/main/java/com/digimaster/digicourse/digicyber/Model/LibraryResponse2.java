package com.digimaster.digicourse.digicyber.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class LibraryResponse2{

    @SerializedName("library:")
    private List<LibraryItem2> library;

    @SerializedName("module")
    private Object module;

    public void setLibrary2(List<LibraryItem2> library){
        this.library = library;
    }

    public List<LibraryItem2> getLibrary2(){
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