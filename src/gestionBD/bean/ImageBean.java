package gestionBD.bean;



public class ImageBean {
	private int id;
	private String imagePath;
	
	public String getImagePath() {
		return this.imagePath;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId(){
		return this.id;
	}
	
	public void setImagePath(String path) {
		this.imagePath = path;
	}

}
