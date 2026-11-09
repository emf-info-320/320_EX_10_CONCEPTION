package ctrl;

import java.util.ArrayList;
import java.util.Comparator;
import models.*;
import services.ComparateurDatePuisTaille;
import services.ComparateurTaille;
import views.VueExplorateur;

/**
 * Coordonne les événements de la vue, la navigation, les tris et les actions du
 * modèle.
 * Les capacités sont reconnues par leurs interfaces, indépendamment du format
 * des fichiers.
 */
public final class ControleurExplorateur {

    // Référence de la vue
    private final VueExplorateur vue;

    // Référence du dossier courant. TODO: mettre le bon type
    // private         courant;

    // Référence du media en cours de lecture; null si aucun. TODO: mettre le bon
    // type. Ce type devra avoir une méthode "arreter()", "jouer()" et
    // "getDureeSecondes()"
    // private       enLecture;

    /**
     * Relie les callbacks de la vue et affiche le dossier initial.
     * À appeler sur le thread de gestion des événements Swing.
     *
     * @param vue    vue non nulle à piloter
     * @param racine dossier initial non nul
     */
    public ControleurExplorateur(VueExplorateur vue, Dossier racine) {
        this.vue = vue;
        courant = racine;
        vue.surSelection(this::selectionner);
        vue.surTri(this::afficher);
        vue.surParent(this::remonter);
        vue.surOuvrir(this::ouvrir);
        vue.surImprimer(this::imprimer);
        vue.surJouer(this::jouer);
        vue.surArreter(this::arreter);
        afficher();
    }

    /**
     * Arrête la lecture en cours et adapte les actions à la nouvelle sélection.
     */
    private void selectionner() {
        if (enLecture != null) {
            vue.afficherStatut(enLecture.arreter());
            enLecture = null;
        } else {
            vue.afficherStatut("Sélectionnez une action.");
        }
        actualiserActions();
    }

    /**
     * Active les commandes et affiche le lecteur selon les interfaces de l'élément
     * sélectionné.
     */
    private void actualiserActions() {
        Element element = vue.getSelection();

        // TODO: écrire le code pour renvoyer un boolean true si l'élément est imprimable, false sinon
        boolean imprimable = false;
        // TODO: écrire le code pour renvoyer un boolean true si l'élément est un dossier, false sinon
        boolean estUnDossier = false; 

        vue.afficherActions(imprimable, estUnDossier);

        // TODO: écrire le code pour tester si  l'élément est jouable
        if (false) {
            vue.afficherLecteur(true, element.getNom() + " — "
                    + jouable.getDureeSecondes() + " secondes");
        } else {
            vue.afficherLecteur(false, "");
        }
    }

    /**
     * Affiche le parent du dossier courant, s'il existe.
     */
    private void remonter() {
        if (courant.getParent() != null) {
            courant = courant.getParent();
            afficher();
        }
    }

    /**
     * Ouvre la sélection si elle désigne un dossier.
     */
    private void ouvrir() {
        
        // TODO: écrire le code pour tester si  l'élément est un dossier
        if (false) {
            courant = dossier;
            afficher();
        }
    }

    /**
     * Demande une impression simulée si la sélection est imprimable.
     */
    private void imprimer() {
        Element e = vue.getSelection();
        // TODO: écrire le code pour tester si  l'élément peut être imprimé
        if (false) {
            
            // TODO: écrire le code pour convertir l'élément en imprimable
            // TYPE imprimable = ...;
            vue.afficherStatut(imprimable.imprimer());
        }
    }

    /**
     * Mémorise le média sélectionné et affiche son message de lecture simulée.
     */
    private void jouer() {
        Element e = vue.getSelection();
        
        // TODO: écrire le code pour tester si  l'élément est jouable
        if (false) {
            
            // TODO: écrire le code pour convertir l'élément en jouable
            // TYPE jouable = ...;
            enLecture = jouable;
            vue.afficherStatut(jouable.jouer());
        }
    }

    /**
     * Arrête la simulation du média sélectionné et oublie la lecture en cours.
     */
    private void arreter() {
        Element e = vue.getSelection();
        // TODO: écrire le code pour tester si  l'élément est jouable
        if (false) {
            
            // TODO: écrire le code pour convertir l'élément en jouable
            // TYPE jouable = ...;
            vue.afficherStatut(jouable.arreter());
            enLecture = null;
        }
    }

    /**
     * Trie une copie du contenu, réinitialise la sélection et actualise le chemin.
     * Le contenu du modèle conserve son ordre d'insertion.
     */
    private void afficher() {
        // Le tri d'une copie préserve l'ordre d'insertion du dossier.
        ArrayList<Element> elements = new ArrayList<>(courant.getContenu());
        int typeTri = vue.getIndiceTri();
        Comparator<Element> comparateur;
        switch (typeTri) {
            case 1:
                comparateur = new ComparateurTaille();
                break;
            case 2:
                comparateur = new ComparateurDatePuisTaille();
                break;
            default:
                comparateur = Comparator.naturalOrder();
        }

        elements.sort(comparateur);
        vue.afficherElements(elements);

        String emplacement = courant.getNom();
        for (Dossier p = courant.getParent(); p != null; p = p.getParent()) {
            emplacement = p.getNom() + "/" + emplacement;
        }
        vue.afficherChemin(emplacement + "/", courant.getParent() != null);
        actualiserActions();
    }
}
