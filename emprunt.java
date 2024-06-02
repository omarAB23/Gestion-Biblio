import java.util.Date;
public class emprunt {
    private int idEmprunt;
    private int idUtilisateur;
    private int idLivre;
    private Date dateEmprunt;
    private Date dateRetour;
    private String statut;
    emprunt(){}
    emprunt(int idEmprunt,int idUtilisateur,int idLivre,Date dateEmprunt,Date dateRetour,String statut)
    {
        this.idEmprunt=idEmprunt;
        this.idUtilisateur=idUtilisateur;
        this.dateEmprunt=dateEmprunt;
        this.dateRetour=dateRetour;
        this.idLivre=idLivre;
        this.statut=statut;
    }
    public int getIdlivre() {
        return idLivre;
    }

    public void setIdlivre(int idlivre) {
        this.idLivre = idlivre;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String s) {
        this.statut =s;
    }

    public Date getdateRetour() {
        return dateRetour;
    }

    public void setdateRetour(Date d) {
        this.dateRetour= d;
    }

    public Date getdateEmprunt() {
        return dateEmprunt;
    }

    public void setdateEmprunt(Date de) {
        this.dateEmprunt = de;
    }

    public int getidUtilisateur() {
        return idUtilisateur;
    }

    public void setidUtilisateur(int d) {
        this.idUtilisateur = d;
    }

    public int getidEmprunt() {
        return idEmprunt;
    }

    public void setIdEmprunt(int emp) {
        this.idEmprunt = emp;
    }
    public String toString()
    {
        return "idEmprunt: " +this.getidEmprunt()+","+"id_utilisateur: "+this.getidUtilisateur()+","+"id_livre: "+this.getIdlivre()+","+"date emprunt: "+this.getdateEmprunt()+","+"date_retour: "+this.getdateRetour()+","+"statut: "+this.getStatut();

    }





}

