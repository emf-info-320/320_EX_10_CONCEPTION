package app;

import javax.swing.SwingUtilities;
import ctrl.ControleurExplorateur;
import services.DonneesExemple;
import views.VueExplorateur;

/** Point d'entrée Swing de l'application. */
public final class App {
    private App() {
    }

    /**
     * Construit et affiche l'explorateur sur le thread Swing.
     *
     * @param args arguments non utilisés
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VueExplorateur vue = new VueExplorateur();
            new ControleurExplorateur(vue, DonneesExemple.creerRacine());
            vue.setVisible(true);
        });
    }
}
