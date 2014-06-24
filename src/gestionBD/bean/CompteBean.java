package gestionBD.bean;

public class CompteBean implements BeanAvecImage {
	private int id;
	private String nom;
	private String prenom;
	private String naissance;
	private boolean bip;
	private boolean afficherTexte;
	private int idImage;
	
	public CompteBean() {
		super();
	}

	public CompteBean(int id, String nom, String prenom, String naissance, boolean bip, boolean afficherTexte,
			int idImage) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.naissance = naissance;
		this.bip = bip;
		this.afficherTexte = afficherTexte;
		this.idImage = idImage;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNaissance() {
		return naissance;
	}

	public void setNaissance(String naissance) {
		this.naissance = naissance;
	}

	public boolean isBip() {
		return bip;
	}

	public void setBip(boolean bip) {
		this.bip = bip;
	}
	
	public void setIdImage(int idImage) {
		this.idImage = idImage;
	}
	
	public int getIdImage(){
		return idImage;
	}
	
	@Override
	public String toString() {
		return prenom +" "+ nom;
	}

	public void setAfficherTexte(boolean afficherTexte) {
		this.afficherTexte = afficherTexte;
	}
	
	public boolean isAfficherTexte(){
		return afficherTexte;
	}

	@Override
	public String getName() {
		return this.getPrenom() + " " + this.getNom();
	}

}
