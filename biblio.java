import javax.xml.transform.Result;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class biblio extends utilisateur {
    public biblio()
    {
        super();
    }
    public static List <String>  rapportsLivresPlusEmpruntes(Connection cnx)
    {
        List<String>livresPlusEmpruntes=new ArrayList<>();
        String query="select id_livre,count(id_livre) as nb_emprunt from emprunt group by id_livre order by nb_emprunt desc";
        try(PreparedStatement ps=cnx.prepareStatement(query)){
            ResultSet rs=ps.executeQuery();
            while(rs.next())
            {
                int id_l=rs.getInt("id_livre");
                String titre=getTitreLivreById(cnx,id_l);
                int nb_empr=rs.getInt("nb_emprunt");
                livresPlusEmpruntes.add("id_livre :"+id_l+", Titre : " + titre + ", Nombre d'emprunts : " + nb_empr);
            }
        }catch(SQLException se)
        {
            se.printStackTrace();
            System.out.println("erreur lors l affichage de statistique livres");
        }
        return livresPlusEmpruntes;
    }

    public static List <String>  rapportsutilisateurPlusAssidus(Connection cnx)
    {
        List<String>usersPlusAssidus=new ArrayList<>();
        String query="select id_utilisateur,count(id_utilisateur) as nb_assidus from emprunt group by id_utilisateur order by nb_assidus desc";
        try(Statement ps=cnx.createStatement()){
            ResultSet rs=ps.executeQuery(query);
            while(rs.next())
            {
                int id_utilisateur=rs.getInt("id_utilisateur");
                String []nomPre=getNomPrenomBYId(cnx,id_utilisateur);
                String nom=nomPre[0];
                String prenom=nomPre[1];
                int nb_assidus=rs.getInt("nb_assidus");
                usersPlusAssidus.add("id_utilisateur :"+id_utilisateur+",nom : " + nom + ", prenom : " +prenom+ ", nb_assidus: " +nb_assidus);
            }
        }catch(SQLException se)
        {
            se.printStackTrace();
            System.out.println("erreur lors l affichage de statistique personnes+");
        }
        return usersPlusAssidus;
    }
    public static String getTitreLivreById(Connection connection, int idLivre) throws SQLException {
        String query = "SELECT titre FROM Livre WHERE id_livre = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idLivre);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("titre");
            }
        }
        return null;
    }
    public static String[] getNomPrenomBYId(Connection cnx,int id_utilisateur) throws SQLException
    {
        String query="select nom,prenom from utilisateur where id_utilisateur=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setInt(1, id_utilisateur);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String n= resultSet.getString("nom");
                String p= resultSet.getString("prenom");
                return new String[]{n,p};
            }

        }
        return null;

    }


}
