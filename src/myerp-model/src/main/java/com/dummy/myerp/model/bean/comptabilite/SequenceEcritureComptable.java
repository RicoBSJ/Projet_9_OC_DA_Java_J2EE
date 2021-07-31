package com.dummy.myerp.model.bean.comptabilite;

import com.dummy.myerp.technical.annotations.ExcludeFromJacocoGeneratedReport;

/**
 * Bean représentant une séquence pour les références d'écriture comptable
 */
public class SequenceEcritureComptable {

    // ==================== Attributs ====================
    /** L'année */
    private Integer annee;
    /** La dernière valeur utilisée */
    private Integer derniereValeur;

    // ==================== Constructeurs ====================
    /**
     * Constructeur
     */
    public SequenceEcritureComptable() {
    }

    /**
     * Constructeur
     *
     * @param pAnnee -
     * @param pDerniereValeur -
     */
    public SequenceEcritureComptable(Integer pAnnee, Integer pDerniereValeur) {
        annee = pAnnee;
        derniereValeur = pDerniereValeur;
    }


    // ==================== Getters/Setters ====================
    @ExcludeFromJacocoGeneratedReport
    public Integer getAnnee() {
        return annee;
    }
    @ExcludeFromJacocoGeneratedReport
    public void setAnnee(Integer pAnnee) {
        annee = pAnnee;
    }
    @ExcludeFromJacocoGeneratedReport
    public Integer getDerniereValeur() {
        return derniereValeur;
    }
    @ExcludeFromJacocoGeneratedReport
    public void setDerniereValeur(Integer pDerniereValeur) {
        derniereValeur = pDerniereValeur;
    }


    // ==================== Méthodes ====================
    @Override
    @ExcludeFromJacocoGeneratedReport
    public String toString() {
        final String vSEP = ", ";
        return this.getClass().getSimpleName() + "{" +
                "annee=" + annee +
                vSEP + "derniereValeur=" + derniereValeur +
                "}";
    }
}
