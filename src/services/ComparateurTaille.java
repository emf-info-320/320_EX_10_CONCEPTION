package services;

import java.util.Comparator;
import models.Element;

/**
 * Compare les tailles croissantes, y compris celles calculées des dossiers.
 * Les éléments de même taille sont équivalents pour ce comparateur.
 */
public final class ComparateurTaille implements Comparator<Element> {
    /**
     * Compare deux éléments selon leur taille.
     * @param premier le premier élément non nul
     * @param second le second élément non nul
     * @return un entier négatif, nul ou positif selon l'ordre des critères
     */
    @Override public int compare(Element premier, Element second) {
        return 0;
    }
}
