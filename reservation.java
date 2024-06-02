import java.util.Date;
public class reservation {
    private int idReservation;
    private int idUtilisateur;
    private int idLivre;
    private Date dateReservation;
    private String statut;

    reservation(){}
    reservation(int idReservation,int idUtilisateur,int idLivre,Date dateReservation,String statut){
        this.idReservation=idReservation;
        this.idUtilisateur=idUtilisateur;
        this.idLivre=idLivre;
        this.dateReservation=dateReservation;
        this.statut=statut;
    }
    public int getIdReservation(){
        return idReservation;
    }
    public void setIdReservation(int i)
    {
        this.idReservation=i;
    }
    public int getidUtilisateur() {
        return idUtilisateur;
    }

    public void setidUtilisateur(int d) {
        this.idUtilisateur = d;
    }
    public int getIdlivre() {
        return idLivre;
    }

    public void setIdlivre(int idlivre) {
        this.idLivre = idlivre;
    }
    public Date getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(Date d) {
        this.dateReservation= d;
    }



}

