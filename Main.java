//import com.mysql.cj.protocol.Resultset;
package projet_java;
import java.sql.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
package projet_java;
import java.util.Scanner;
//import java.util.Date;
public class Main {
    public static void main(String[] args) {
        try {
            String url = "jdbc:mysql://127.0.0.1:3306/projet_java";
            String username = "root";
            String password = "asmamiled50*";
            Connection cnx = DriverManager.getConnection(url, username, password);
            System.out.println("connexion established successfully");
            System.out.println("Bonjour dans notre bibliotheque en ligne");
            System.out.println("Options d'authentification :");
            System.out.println("1. Se connecter");
            System.out.println("2. S'inscrire");
            System.out.print("Choisissez une option : ");
            Scanner sc = new Scanner(System.in);
            int choixAuth = sc.nextInt();
            switch (choixAuth) {
                case 1:
                    System.out.println("veillez entrer vos donnee sous la forme suivante (login, pwd, role)");
                    Scanner sc10 = new Scanner(System.in);
                    String ch = sc10.nextLine();
                    String[] list10 = ch.split(",");
                    String login = list10[0];
                    String pwd = list10[1];
                    String role = list10[2];
                    try (ResultSet rs = utilisateur.sign_in(cnx, login, pwd, role)) {

                        if (rs.next()) {
                            int id_utilisateur = rs.getInt("id_utilisateur");
                            String nom = rs.getString("nom");
                            String prenom = rs.getString("prenom");
                            String login2 = rs.getString("login");
                            String pwd3 = rs.getString("pwd");
                            String role4 = rs.getString("role");
                            utilisateur user = new utilisateur(id_utilisateur, nom, prenom, login2, pwd3, role4);
                            System.out.println("connexion reussite,bienvenue " + rs.getString("nom") + " " + rs.getString("prenom"));
                            if (role4.equals("biblio")) {
                                System.out.println("pour consulter les rapports statistiques sur les livres les plus empruntés tapez 1");
                                System.out.println("pour consulter les rapports statistiques sur les utilisateurs les plus assidus tapez 2");
                                int x = sc10.nextInt();
                                List<String> list_livres;
                                List<String> list_utilisateur;
                                switch (x) {
                                    case 1:
                                        list_livres = biblio.rapportsLivresPlusEmpruntes(cnx);
                                        for (String s : list_livres) {
                                            System.out.println(s);
                                        }
                                        break;
                                    case 2:
                                        list_utilisateur = biblio.rapportsutilisateurPlusAssidus(cnx);
                                        for (String su : list_utilisateur) {
                                            System.out.println(su);
                                        }
                                        break;
                                    default:
                                        System.out.println("erreur");
                                }
                            } else {
                                accederAuxFonctionnalites(cnx, user);
                            }
                        }


                    } catch (SQLException se) {
                        System.out.println("echec de la connexion,verifier vos donnes ");
                    }
                    break;
                case 2:
                    System.out.println(" veillez saisir vos donnes sous la forme suivante: nom, prenom, login, pwd, role)");
                    Scanner sc1 = new Scanner(System.in);
                    String ch2 = sc1.nextLine();
                    String[] list2 = ch2.split(",");
                    String nom = list2[0];
                    String prenom = list2[1];
                    String login3 = list2[2];
                    String pwd3= list2[3];
                    String role3 = list2[4];
                    utilisateur user = new utilisateur(nom, prenom, login3, pwd3, role3);
                    user.ajouter_utilisateur(cnx);
                    System.out.println("bonjour Mr/Mme "+user.getPrenom());
                    user=utilisateur.login_utilisateur(cnx,login3,pwd3);
                    System.out.println(user);
                    if (role3.equals("biblio")) {
                        while(true) {
                            System.out.println("pour consulter les rapports statistiques sur les livres les plus empruntés tapez 1");
                            System.out.println("pour consulter les rapports statistiques sur les utilisateurs les plus assidus tapez 2");
                            System.out.println("pour quitter tapez 0");
                            int x = sc1.nextInt();
                            List<String> list_livres;
                            List<String> list_utilisateur;
                            switch (x) {
                                case 1:
                                    list_livres = biblio.rapportsLivresPlusEmpruntes(cnx);
                                    for (String s : list_livres) {
                                        System.out.println(s);
                                    }
                                    break;
                                case 2:
                                    list_utilisateur = biblio.rapportsutilisateurPlusAssidus(cnx);
                                    for (String su : list_utilisateur) {
                                        System.out.println(su);
                                    }
                                    break;
                                case 0:
                                    System.exit(0);
                                default:
                                    System.out.println("erreur");


                            }
                        }
                    } else {
                        accederAuxFonctionnalites(cnx, user);
                    }
                    accederAuxFonctionnalites(cnx,user);
                    break;
                default:
                    System.out.println("Option invalide. Veuillez choisir une option valide.");

            }
            cnx.close();
        } catch (SQLException e) {
        	e.printStackTrace();
            System.out.println("Erreur lors de la connexion a� la base de donn�es");
        }
    }
    public  static int getEtudiantchoix() throws MyException
    {
        Scanner sc=new Scanner(System.in);
        int choix;
        do{
            System.out.println("*********MENU PRINCIPALE*******");
            System.out.println("1: consulter le catalogue");
            System.out.println("2: gestion des emprunts");
            System.out.println("3: rechercher un livre");
            System.out.println("4: resérvation de livres indisponibles");
            System.out.println("5: consulter votre historique d emprunt");
            System.out.println("0:quitter");
            choix=sc.nextInt();
        }while(choix<=0 || choix>5);
        return choix;

    }

    public static void accederAuxFonctionnalites(Connection cnx,utilisateur user1) {
        Scanner sc = new Scanner(System.in);
        try {
            while (true) {
                int choix = getEtudiantchoix();
                switch (choix) {
                    case 1:
                        consulter_catalg_main(cnx);
                        //accederAuxFonctionnalites(cnx,user1);
                        break;
                    case 2:
                        gerer_les_emprunt_main(cnx, user1);
                        break;
                    case 3:
                        rechercher_livre_main(cnx);
                        break;
                    case 4:
                        System.out.println("veillez donner les données du livre tq son id_livre");
                        int id2 = sc.nextInt();
                        user1.reserver_un_livre(cnx, id2);
                        break;
                    case 5:
                        //emprunt em = consulter_historique_emprunt_main(cnx,user1);
                        List<emprunt>hist_emp=utilisateur.consulter_historique_emprunt(cnx,user1);
                        System.out.println("voici votre hisorique :");
                        for (emprunt emp : hist_emp) {
                            System.out.println(emp.toString());
                        }
                        break;
                    case 0:
                        System.out.println("Merci d'avoir utilisé l'application. Au revoir !");
                        //cnx.close();
                        System.exit(0);
                    default:
                        System.out.println("Option invalide. Veuillez choisir une option valide.");

                }
            }

            }catch(MyException m){
                System.out.println("erreur dans le choix");
            }

        }
    public static void consulter_catalg_main(Connection cnx) {
        try (ResultSet r = utilisateur.consulter_catalogue(cnx)) {
            while (r.next()) {
                int id = r.getInt("id_livre");
                String titre = r.getString("titre");
                String auteur = r.getString("auteur");
                String genre = r.getString("genre");
                String disponibilite = r.getString("disponibilite");

                System.out.println("ID: " + id);
                System.out.println("Titre: " + titre);
                System.out.println("Auteur: " + auteur);
                System.out.println("Genre: " + genre);
                System.out.println("Disponibilité: " + disponibilite);
                System.out.println("----------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void gerer_les_emprunt_main(Connection cnx,utilisateur user1)
    {
        System.out.println("1: emprunter un livre ");
        System.out.println("2: retour d un livre");
        Scanner sc=new Scanner(System.in);
        int x=sc.nextInt();
        switch(x){
            case 1:
                System.out.println("veillez donner les données du livre tq son id_livre");
                int id=sc.nextInt();
                try {
                    user1.gererEmpruntsEnRetard(cnx);
                    user1.emprunter_livre(cnx, id);
                }catch(SQLException se)
                {
                    System.out.println("erreur dans l operation de l emprunt");
                }
                break;
            case 2:
                System.out.println("veillez donner les données du livre tq son id_livre");
                int id_l=sc.nextInt();
                try {
                    user1.gererEmpruntsEnRetard(cnx);
                    user1.retour_livre(cnx, id_l);
                    System.out.println("le livre a eté bien retournée :))");
                }catch(SQLException e)
                {
                    System.out.println("erreur dans les empreinte en retard dans main");
                }
                break;
            default:
                System.out.println("Option invalide. Veuillez choisir une option valide.");
        }
    }
    public static void rechercher_livre_main(Connection cnx)
        {
                Scanner sc1 = new Scanner(System.in);
                System.out.println("veillez donner le titre ou l auteur que vous voudrez le rechercher :)");
                String ch=sc1.nextLine();
                try(ResultSet rs=utilisateur.recherche_livre(cnx,ch))
                {
                    while(rs.next())
                    {
                        int id = rs.getInt("id_livre");
                        String titre = rs.getString("titre");
                        String auteur = rs.getString("auteur");
                        String genre = rs.getString("genre");
                        String disponibilite = rs.getString("disponibilite");


                        System.out.println("ID: " + id);
                        System.out.println("Titre: " + titre);
                        System.out.println("Auteur: " + auteur);
                        System.out.println("Genre: " + genre);
                        System.out.println("Disponibilité: " + disponibilite);
                        System.out.println("----------------------");
                    }
                    }catch (SQLException se)
                {
                    System.out.println("erreur dans la recherche du livre");
                }




        }


}
