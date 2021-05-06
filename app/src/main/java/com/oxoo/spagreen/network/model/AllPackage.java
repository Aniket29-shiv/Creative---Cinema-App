package com.oxoo.spagreen.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.oxoo.spagreen.network.model.AllPackage;

import java.util.List;

public class AllPackage {

    @SerializedName("package")
    @Expose
    private List<Package> _package = null;

    public List<Package> getPackage() {
        return _package;
    }

    public void setPackage(List<Package> _package) {
        this._package = _package;
    }

}
