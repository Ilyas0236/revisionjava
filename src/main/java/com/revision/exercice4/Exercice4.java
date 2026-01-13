package com.revision.exercice4;

class Etudiant {
    public double moy;

    public Etudiant(double moy) {
        this.moy = moy;
    }
}

public class Exercice4 {
    public static void main(String[] args) {
        Etudiant[] students = new Etudiant[3];
        // Cas 1: Tableau contenant des nuls (students[0] est null)
        // Cas 2: Tableau null (students = null)

        // Pour reproduire l'erreur "NullPointerException" attrapée, il faut envelopper
        // le code.
        // L'exo demande "ajoutez les instructions nécessaires pour (...) affiche le
        // message".
        // Le code fourni dans l'image est un bloc de logique. Je vais le mettre dans un
        // try-catch comme demandé.

        try {
            // Simulation du scénario qui plante.
            // Si on laisse le tableau initialisé avec des nulls par défaut:
            // "Etudiant best = students[0];" -> va être null.
            // "if (e.moy > best.moy)" -> si e est non-null, best.moy plante car best est
            // null.
            // Ou simplement, "Etudiant best = students[0]" -> best est null.
            // Ensuite la boucle: premier 'e' est null. "if (e.moy ...)" ->
            // NullPointerException imédiat.

            // Pour garantir le crash visible tel que décrit par l'exo (comprendre le code):
            // Le code fait "Etudiant best = students[0];". Si students est vide ou null ->
            // crash.
            // Si students[0] est null -> best est null.
            // Boucle for(Etudiant e : students).
            // if(e.moy > best.moy)... best.moy throw NullPointer si best est null.

            System.out.println("Execution du code...");

            // CAS QUI PROVOQUE L'ERREUR:
            // students[0] est null, donc best est null.
            // Ensuite la boucle tente d'accéder à best.moy ou e.moy.

            // Code de l'image (reconstitué)
            if (students == null || students.length == 0)
                throw new NullPointerException("Tableau vide ou null"); // Protection optionnelle, mais le code original
                                                                        // planterait ici si students null.

            Etudiant best = students[0];

            for (Etudiant e : students) {
                if (e.moy > best.moy) { // Si e est null -> CRASH. Si best est null -> CRASH.
                    best = e;
                }
            }
            System.out.println(" + " + best.moy);

        } catch (NullPointerException e) {
            System.out.println("Erreur Null pointer trouvée");
        }
    }
}
