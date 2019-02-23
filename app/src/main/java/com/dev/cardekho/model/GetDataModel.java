package com.dev.cardekho.model;

import com.google.gson.annotations.SerializedName;

public class GetDataModel{
	@SerializedName("TABLE_DATA")
	private TABLEDATA tABLEDATA;

	public void setTABLEDATA(TABLEDATA tABLEDATA){
		this.tABLEDATA = tABLEDATA;
	}

	public TABLEDATA getTABLEDATA(){
		return tABLEDATA;
	}

}
