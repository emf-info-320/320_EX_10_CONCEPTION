package views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;
import models.Element;

/**
 * Fenêtre Swing textuelle du système de fichiers.
 * La vue affiche les données et transmet les événements au contrôleur.
 */
public final class VueExplorateur extends JFrame {
    private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private final DefaultListModel<Element> modeleListe = new DefaultListModel<>();
    private final JList<Element> liste = new JList<>(modeleListe);
    private final JComboBox<String> tri = new JComboBox<>(
            new String[] { "Nom", "Taille", "Date puis taille" });
    private final JButton parent = new JButton("Dossier parent");
    private final JButton ouvrir = new JButton("Ouvrir le dossier");
    private final JButton imprimer = new JButton("Imprimer");
    private final JButton jouer = new JButton("Jouer");
    private final JButton arreter = new JButton("Arrêter");
    private final JLabel chemin = new JLabel();
    private final JLabel statut = new JLabel("Sélectionnez un élément.");
    private final JLabel media = new JLabel();
    private final JPanel lecteur = new JPanel(new FlowLayout(FlowLayout.LEFT));

    /** Construit la fenêtre et ses composants. */
    public VueExplorateur() {
        super("Exercice 10 — Système de fichiers");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 520);
        setLocationRelativeTo(null);

        JPanel commandes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        commandes.add(parent);
        commandes.add(ouvrir);
        commandes.add(new JLabel("Trier par :"));
        commandes.add(tri);

        JPanel haut = new JPanel();
        haut.setLayout(new BoxLayout(haut, BoxLayout.Y_AXIS));
        haut.add(chemin);
        haut.add(commandes);
        haut.add(new JLabel("Date de modification       Octets  Type    Nom"));

        liste.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        liste.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        liste.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(
                    JList<?> composant, Object valeur, int index,
                    boolean selectionne, boolean focus) {
                super.getListCellRendererComponent(
                        composant, valeur, index, selectionne, focus);
                if (valeur instanceof Element) {
                    Element element = (Element) valeur;
                    setText(String.format("%s  %12d  %-6s  %s",
                            DATE.format(element.getDateModification()),
                            element.getTaille(), element.getType(), element.getNom()));
                }
                return this;
            }
        });

        lecteur.add(new JLabel("Lecteur simulé :"));
        lecteur.add(media);
        lecteur.add(jouer);
        lecteur.add(arreter);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actions.add(imprimer);
        JPanel bas = new JPanel(new GridLayout(3, 1, 0, 5));
        bas.add(actions);
        bas.add(lecteur);
        bas.add(statut);

        JPanel contenu = new JPanel(new BorderLayout(0, 10));
        contenu.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        contenu.add(haut, BorderLayout.NORTH);
        contenu.add(new JScrollPane(liste), BorderLayout.CENTER);
        contenu.add(bas, BorderLayout.SOUTH);
        setContentPane(contenu);

        afficherActions(false, false);
        afficherLecteur(false, "");
        parent.setEnabled(false);
    }

    /** @return l'élément sélectionné, ou {@code null} */
    public Element getSelection() {
        return liste.getSelectedValue();
    }

    /** @return l'indice du critère de tri sélectionné */
    public int getIndiceTri() {
        return tri.getSelectedIndex();
    }

    /** @param action action appelée lorsque la sélection change */
    public void surSelection(Runnable action) {
        liste.addListSelectionListener(evenement -> {
            if (!evenement.getValueIsAdjusting()) action.run();
        });
    }

    /** @param action action appelée lorsque le tri change */
    public void surTri(Runnable action) {
        tri.addActionListener(evenement -> action.run());
    }

    /** @param action action du bouton parent */
    public void surParent(Runnable action) {
        parent.addActionListener(evenement -> action.run());
    }

    /** @param action action du bouton d'ouverture */
    public void surOuvrir(Runnable action) {
        ouvrir.addActionListener(evenement -> action.run());
    }

    /** @param action action du bouton d'impression */
    public void surImprimer(Runnable action) {
        imprimer.addActionListener(evenement -> action.run());
    }

    /** @param action action du bouton de lecture */
    public void surJouer(Runnable action) {
        jouer.addActionListener(evenement -> action.run());
    }

    /** @param action action du bouton d'arrêt */
    public void surArreter(Runnable action) {
        arreter.addActionListener(evenement -> action.run());
    }

    /** @param elements éléments à afficher dans l'ordre fourni */
    public void afficherElements(List<Element> elements) {
        liste.clearSelection();
        modeleListe.clear();
        for (Element element : elements) {
            modeleListe.addElement(element);
        }
    }

    /** Affiche le chemin et règle le bouton parent. */
    public void afficherChemin(String emplacement, boolean parentDisponible) {
        chemin.setText(emplacement);
        parent.setEnabled(parentDisponible);
    }

    /** Active les actions disponibles pour la sélection. */
    public void afficherActions(boolean imprimable, boolean dossier) {
        imprimer.setEnabled(imprimable);
        ouvrir.setEnabled(dossier);
    }

    /** Affiche ou masque le lecteur simulé. */
    public void afficherLecteur(boolean visible, String description) {
        lecteur.setVisible(visible);
        media.setText(description);
    }

    /** @param message message à afficher dans la barre de statut */
    public void afficherStatut(String message) {
        statut.setText(message);
    }
}
