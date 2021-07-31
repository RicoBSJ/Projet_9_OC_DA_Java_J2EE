package com.dummy.myerp.model.bean.comptabilite;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.dummy.myerp.model.validation.constraint.MontantComptable;

/**
 * Bean représentant une Ligne d'écriture comptable.
 */
public class LigneEcritureComptable {

    // ==================== Attributs ====================
    /** Compte Comptable */
    @NotNull
    private CompteComptable compteComptable;

    /** The Libelle. */
    @Size(max = 200)
    private String libelle;

    /** The Debit. */
    @MontantComptable
    private BigDecimal debit;

    /** The Credit. */
    @MontantComptable
    private BigDecimal credit;


    // ==================== Constructeurs ====================
    /**
     * Instantiates a new Ligne ecriture comptable.
     */
    public LigneEcritureComptable() {
    }

    /**
     * Instantiates a new Ligne ecriture comptable.
     *
     * @param pCompteComptable the Compte Comptable
     * @param pLibelle the libelle
     * @param pDebit the debit
     * @param pCredit the credit
     */
    public LigneEcritureComptable(CompteComptable pCompteComptable, String pLibelle,
                                  BigDecimal pDebit, BigDecimal pCredit) {
        compteComptable = pCompteComptable;
        libelle = pLibelle;
        debit = pDebit;
        credit = pCredit;
    }


    // ==================== Getters/Setters ====================
    public CompteComptable getCompteComptable() {
        return compteComptable;
    }
    public void setCompteComptable(CompteComptable pCompteComptable) {
        compteComptable = pCompteComptable;
    }
    public String getLibelle() {
        return libelle;
    }
    public void setLibelle(String pLibelle) {
        libelle = pLibelle;
    }
    public BigDecimal getDebit() {
        return debit;
    }
    public void setDebit(BigDecimal pDebit) {
        debit = pDebit;
    }
    public BigDecimal getCredit() {
        return credit;
    }
    public void setCredit(BigDecimal pCredit) {
        credit = pCredit;
    }


    // ==================== Méthodes ====================
    @Override
    public String toString() {
        final String vSEP = ", ";
        return this.getClass().getSimpleName() + "{" +
                "compteComptable=" + compteComptable +
                vSEP + "libelle='" + libelle + '\'' +
                vSEP + "debit=" + debit +
                vSEP + "credit=" + credit +
                "}";
    }
}
