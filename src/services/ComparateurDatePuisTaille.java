package services;

import java.util.Comparator;
import models.Element;

/**
 * Compare les dates croissantes, puis les tailles lorsque les dates sont égales.
 */
public final class ComparateurDatePuisTaille implements Comparator<Element> {
    private final Comparator<Element> taille = new ComparateurTaille();

    /**
     * Compare deux éléments selon leur date, puis leur taille en cas d'égalité.
     * @param premier le premier élément non nul
     * @param second le second élément non nul
     * @return un entier négatif, nul ou positif selon l'ordre des critères
     */
    @Override public int compare(Element premier, Element second) {
        return 0;
    }
}
