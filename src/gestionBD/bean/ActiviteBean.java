package gestionBD.bean;

public class ActiviteBean implements BeanAvecImage {
	private int id;
	private String name;
	private int idImage;
	private int nbUtil;

	public ActiviteBean() {
		super();
	}

	public ActiviteBean(int id, String name, int idImage) {
		super();
		this.id = id;
		this.name = name;
		this.idImage = idImage;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIdImage() {
		return this.idImage;
	}

	public void setIdImage(int idImage) {
		this.idImage = idImage;
	}
	
	public int getNbUtil() {
		return nbUtil;
	}
	
	public void setNbUtil(int nbUtil){
		this.nbUtil = nbUtil;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + idImage;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActiviteBean other = (ActiviteBean) obj;
		if (id != other.id)
			return false;
		if (idImage != other.idImage)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
}
