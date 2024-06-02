
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class utilisateur {
    private int id_utilisateur;
    private String nom;
    private String prenom;
    private String login;
    private String pwd;
    private String role;
    public utilisateur(){}

    public utilisateur(String nom,String prenom,String login,String pwd,String role){
        this.nom=nom;
        this.prenom=prenom;
        this.login=login;
        this.pwd=pwd;
        this.role=role;
    }

    public utilisateur(int id_utilisateur,String nom,String prenom,String login,String pwd,String role) {
        this.id_utilisateur=id_utilisateur;
        this.nom=nom;
        this.prenom=prenom;
        this.login=login;
        this.pwd=pwd;
        this.role=role;
    }

    public int getIdUtilisateur() {
        return id_utilisateur;
    }

    public void setIdUtilisateur(int id_Utilisateur) {
        this.id_utilisateur = id_Utilisateur;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMotDePasse() {
        return pwd;
    }

    public void setMotDePasse(String motDePasse) {
        this.pwd = motDePasse;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public String toString()
    {
        return "=>"+this.getIdUtilisateur()+","+this.getNom()+","+this.getPrenom()+","+this.getLogin()+","+this.getRole();
    }

public void ajouter_utilisateur(Connection cnx)
{
String query="insert into utilisateur (nom,prenom,login,pwd,role) values(?,?,?,?,?)";
try{
    PreparedStatement PreparedStatement= cnx.prepareStatement(query);

    PreparedStatement.setString(1,nom);
    PreparedStatement.setString(2,prenom);
    PreparedStatement.setString(3,login);
    PreparedStatement.setString(4,pwd);
    PreparedStatement.setString(5,role);
    PreparedStatement.executeUpdate();
}catch(SQLException e){
    System.out.println("erreur au niveau de l ajout "+e);
}
}
public static utilisateur login_utilisateur(Connection cnx,String login,String pwd) {
    String query = "select * from utilisateur where login=? and pwd=?";
    try {
        PreparedStatement preparedStatement = cnx.prepareStatement(query);
        preparedStatement.setString(1,login);
        preparedStatement.setString(2,pwd);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            System.out.println("connexion avec succes ,voici vos ccordonees");
            return transResultSetToUtilisateur(rs);
        }


    } catch (SQLException e) {
        System.out.println("erreur lors de connexion avec votre compte veuillez verifier vos donnee" + e);
    }
    return null;
}

private static utilisateur transResultSetToUtilisateur(ResultSet rs)throws SQLException
{
    utilisateur user=new utilisateur();
    user.setIdUtilisateur(rs.getInt("id_utilisateur"));
    user.setNom(rs.getString("nom"));
    user.setPrenom(rs.getString(3));
    user.setLogin(rs.getString(4));
    user.setMotDePasse(rs.getString(5));
    user.setRole(rs.getString(6));
    return user;
}
public static ResultSet sign_in(Connection cnx,String log,String pwd,String role)
{
    String query="select * from utilisateur where login=? and pwd=? and role=?";
    try{
        PreparedStatement preparedStatement=cnx.prepareStatement(query);
        preparedStatement.setString(1,log);
        preparedStatement.setString(2,pwd);
        preparedStatement.setString(3,role);
        ResultSet rs=preparedStatement.executeQuery();
        return rs;
        //rs.close();
        //preparedStatement.close();
        //cnx.close();
    }catch(SQLException se)
    {
        se.printStackTrace();
    }

    return null;
}
public static ResultSet consulter_catalogue(Connection cnx)throws SQLException {
    String query = "select * from livre ";
    ResultSet rs = null;
    Statement st= null;
    try {
        st= cnx.createStatement();
        rs = st.executeQuery(query);
        return rs;
    } catch (SQLException se) {
        System.out.println("probleme lors d affichage"+se);
    } finally {
        //rs.close();
        //st.close();
        //cnx.close();
    }

    return null;
}
public static ResultSet recherche_livre(Connection cnx,String ch)
{
    String query="select * from livre where titre like ? or auteur like ?";

try{
    PreparedStatement preparedStatement=cnx.prepareStatement(query);
    preparedStatement.setString(1,"%"+ch+"%");
    preparedStatement.setString(2,"%"+ch+"%");
    return preparedStatement.executeQuery();
}catch(SQLException se)
{
    System.out.println("probleme lors de la recherche");
}
    return null;
}
public void reserver_un_livre(Connection cnx,int id_l) {
    if (!livre.is_dispo2(cnx,id_l)) {
        String query = "insert into reservation (id_utilisateur, id_livre, date_reservation, statut) values (?, ?, ?, ?)";
        //String queryMettreAJour = "update Livre set disponibilite = false WHERE id_livre = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(query);
            ps.setInt(1, this.getIdUtilisateur());
            ps.setInt(2, id_l);
            ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            ps.setString(4, "En attente");
            ps.executeUpdate();
            System.out.println("le livre a été bien reservé");
            } catch (SQLException e) {
                System.out.println("erreur lors du reservation");
            }
        }else{
        System.out.println(" desolé,le livre est deja reservée nous allons vous informer dés qu il soit disponible ");
    }
    }
    public void emprunter_livre(Connection cnx, int id_livre) throws SQLException
    {
        if(livre.is_dispo2(cnx,id_livre))
        {
            String query="insert into emprunt (id_utilisateur, id_livre, date_emprunt,date_retour, statut) values (?,?, NOW(), DATE_ADD(NOW(), INTERVAL 14 DAY), ?)";
            String query_misJour="update livre set disponibilite ='non' where id_livre=?";


            try(PreparedStatement ps1=cnx.prepareStatement(query);

            PreparedStatement ps2=cnx.prepareStatement(query_misJour))
            {
                try{
                ps1.setInt(1, this.getIdUtilisateur());
                ps1.setInt(2, id_livre);
                ps1.setString(3, "En cours");
                ps1.executeUpdate();
                ps2.setInt(1, id_livre);
                ps2.executeUpdate();
                System.out.println("le livre a ete emprunté");

                }catch(SQLException se){
                    System.out.println("erreur lors du l empreinte");}
            }
        }else{
            System.out.println("Le livre n'est pas disponible pour l'emprunt");
        }

    }
    public void retour_livre(Connection cnx,int id_livre)
    {
        String query="update emprunt set date_retour=?,statut=? where id_utilisateur=? and id_livre=? and statut=? ";
        String query2="update livre set disponibilite=? where id_livre=?";
        try(PreparedStatement ps=cnx.prepareStatement(query);
            PreparedStatement ps2=cnx.prepareStatement(query2)
        )
        {
            ps.setTimestamp(1,new Timestamp(System.currentTimeMillis()));
            ps.setString(2,"terminee");
            ps.setInt(3,this.getIdUtilisateur());
            ps.setInt(4,id_livre);
            ps.setString(5,"en cours");
            ps2.setString(1,"oui");
            ps2.setInt(2, id_livre);
            ps.executeUpdate();
            ps2.executeUpdate();

        }catch(SQLException se)
        {
            se.printStackTrace();
            System.out.println("erreur lors du l operation retour livre");
        }


    }
    // méthode pour gérer les emprunts en retard
    public void gererEmpruntsEnRetard(Connection cnx) throws SQLException {
        String query = "select id_livre from emprunt where statut = 'en cours' and date_retour < NOW() and id_utilisateur=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)){
            preparedStatement.setInt(1,this.getIdUtilisateur());
             ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int idLivre = resultSet.getInt("id_livre");
                livre livre = recupererLivreParId(cnx, idLivre);

                System.out.println("Vous avez un emprunt en retard pour le livre : " + livre.gettitre());
                String query22="update emprunt set statut ='en retard' where id_livre=? and id_utilisateur=?";
                PreparedStatement ps=cnx.prepareStatement(query22);
                ps.setInt(1,livre.getIdlivre());
                ps.setInt(2,this.getIdUtilisateur());
                ps.executeUpdate();

            }
        }
    }

    // Méthode pour récupérer les informations sur un livre par son ID
    private livre recupererLivreParId(Connection cnx, int idLivre) throws SQLException {
        String query = "select * from livre where id_livre = ?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setInt(1, idLivre);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new livre(
                            resultSet.getInt("id_livre"),
                            resultSet.getString("titre"),
                            resultSet.getString("auteur"),
                            resultSet.getString("genre"),
                            resultSet.getString("disponibilite")
                    );
                }
            }
        }
        return null;
    }
    public static List<emprunt> consulter_historique_emprunt(Connection cnx, utilisateur user) {
        List<emprunt> historiqueEmprunts = new ArrayList<>();

        String query = "select * from emprunt where id_utilisateur=?";

        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, user.getIdUtilisateur());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id_emprunt = rs.getInt("id_emprunt");
                    int id_utilisateur = rs.getInt("id_utilisateur");
                    int id_livre = rs.getInt("id_livre");
                    Date date = rs.getDate("date_emprunt");
                    Date date2 = rs.getDate("date_retour");
                    String s = rs.getString("statut");

                    emprunt emp = new emprunt(id_emprunt, id_utilisateur, id_livre, date, date2, s);
                    historiqueEmprunts.add(emp);
                }
            }
        } catch (SQLException se) {
            System.out.println("Erreur lors de la consultation de l'historique des emprunts : " + se.getMessage());
        }

        return historiqueEmprunts;
    }



}


