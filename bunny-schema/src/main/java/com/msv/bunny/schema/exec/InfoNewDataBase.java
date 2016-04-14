package com.msv.bunny.schema.exec;

public class InfoNewDataBase {

	private String name;

	private InfoNewDataBase(String name) {
		this.name = name;
	}

	public static class InfoNewDataBaseBuild {
		
		private String name;

		public InfoNewDataBaseBuild name(String name){
			this.name = name;
			return this;
		}
		
		public InfoNewDataBase build(){
			return new InfoNewDataBase(this.name);
		}
	}

	public String getName() {
		return name;
	}

}
