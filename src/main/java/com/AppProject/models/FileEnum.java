package com.AppProject.models;

public enum FileEnum {

	APK(1),
	IOS(2),
	SERVER(3),
	SOURCE_ANDROID(4),
	SOURCE_IOS(5);

	private final int value;

	private FileEnum(int value) {
		this.value = value;
	}

	public int get() {
		return value;
	}
	
	public static  boolean isCheck(int aName) {
		FileEnum[] aFruits = FileEnum.values();
	       for (FileEnum mfile : aFruits) {
	           if (mfile.get()==aName)
	               return true;
	           
	       }
	       return false;
	   }
}
