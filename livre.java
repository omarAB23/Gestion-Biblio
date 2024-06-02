import java.sql.*;
public class livre {
        private int idLivre;
        private String titre;
        private String auteur;
        private String genre;
        private String disponibilite;
        livre(){

        }
        livre(int idLivre,String titre,String auteur,String genre,String disponibilite)
        {
            this.idLivre=idLivre;
            this.titre=titre;
            this.auteur=auteur;
            this.genre=genre;
            this.disponibilite=disponibilite;

        }
    public int getIdlivre() {
        return idLivre;
    }

    public void setIdlivre(int idlivre) {
        this.idLivre = idlivre;
    }

    public String gettitre() {
        return titre;
    }

    public void settitre(String titre) {
        this.titre =titre;
    }

    public String getauteur() {
        return auteur;
    }

    public void setauteur(String au) {
        this.auteur= au;
    }

    public String getgenre() {
        return genre;
    }

    public void setgenre(String g) {
        this.genre = g;
    }

    public String getdispo() {
        return disponibilite;
    }

    public void setdispo(String d) {
        this.disponibilite = d;
    }
    public String toString()
    {
        return "=>"+this.getIdlivre()+","+this.gettitre()+","+this.getauteur()+","+this.getgenre()+","+this.getdispo();
    }

    public static boolean is_dispo2(Connection cnx,int id_livre) {
        String query = "select disponibilite from livre where id_livre=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(query);
            ps.setInt(1, id_livre);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String x = rs.getString("disponibilite");
                if (x.equals("non"))
                    return false;


            }
        } catch (SQLException se) {
            System.out.println("erreur lors de faire l emprunte");
        }
        return true;
    }



}

