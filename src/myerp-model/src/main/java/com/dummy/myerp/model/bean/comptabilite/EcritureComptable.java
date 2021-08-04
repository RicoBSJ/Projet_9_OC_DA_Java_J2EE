package com.dummy.myerp.model.bean.comptabilite;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.dummy.myerp.technical.annotations.ExcludeFromJacocoGeneratedReport;
import org.apache.commons.lang3.StringUtils;

/**
 * Bean représentant une Écriture Comptable
 */
public class EcritureComptable {

    // ==================== Attributs ====================
    /** The Id. */
    private Integer id;
    /** Journal comptable */
    @NotNull private JournalComptable journal;
    /** The Reference. */
    @Pattern(regexp = "\\d{1,5}-\\d{4}/\\d{5}")
    private String reference;
    /** The Date. */
    @NotNull private Date date;

    /** The Libelle. */
    @NotNull
    @Size(min = 1, max = 200)
    private String libelle;

    /** La liste des lignes d'écriture comptable. */
    @Valid
    @Size(min = 2)
    private final List<LigneEcritureComptable> listLigneEcriture = new ArrayList<>();


    // ==================== Getters/Setters ====================
    @ExcludeFromJacocoGeneratedReport
    public Integer getId() {
        return id;
    }
    @ExcludeFromJacocoGeneratedReport
    public void setId(Integer pId) {
        id = pId;
    }
    @ExcludeFromJacocoGeneratedReport
    public JournalComptable getJournal() {
        return journal;
    }
    @ExcludeFromJacocoGeneratedReport
    public void setJournal(JournalComptable pJournal) {
        journal = pJournal;
    }
    @ExcludeFromJacocoGeneratedReport
    public String getReference() {
        return reference;
    }
    @ExcludeFromJacocoGeneratedReport
    public void setReference(String pReference) {
        reference = pReference;
    }
    @ExcludeFromJacocoGeneratedReport
    public Date getDate() {
        return date;
    }
    @ExcludeFromJacocoGeneratedReport
    public void setDate(Date pDate) {
        date = pDate;
    }
    @ExcludeFromJacocoGeneratedReport
    public String getLibelle() {
        return libelle;
    }
    @ExcludeFromJacocoGeneratedReport
    public void setLibelle(String pLibelle) {
        libelle = pLibelle;
    }
    @ExcludeFromJacocoGeneratedReport
    public List<LigneEcritureComptable> getListLigneEcriture() {
        return listLigneEcriture;
    }

    /**
     * Calcul et renvoie le total des montants au débit des lignes d'écriture
     *
     * @return {@link BigDecimal}, {@link BigDecimal#ZERO} si aucun montant au débit
     */
    // TODO à tester
    public BigDecimal getTotalDebit() {
        BigDecimal vRetour = BigDecimal.ZERO;
        for (LigneEcritureComptable vLigneEcritureComptable : listLigneEcriture) {
            if (vLigneEcritureComptable.getDebit() != null) {
                vRetour = vRetour.add(vLigneEcritureComptable.getDebit());
            }
        }
        return vRetour;
    }

    /**
     * Calcul et renvoie le total des montants au crédit des lignes d'écriture
     *
     * @return {@link BigDecimal}, {@link BigDecimal#ZERO} si aucun montant au crédit
     */
    public BigDecimal getTotalCredit() {
        BigDecimal vRetour = BigDecimal.ZERO;
        for (LigneEcritureComptable vLigneEcritureComptable : listLigneEcriture) {
            /*Dans le code d'origine, on va chercher la ligne d'écriture comptable en débit
            or nous sommes dans la méthode du crédit, il convient donc de remplacer
            vLigneEcritureComptable.getDebit() par vLigneEcritureComptable.getCredit()*/
            if (vLigneEcritureComptable.getCredit() != null) {
                vRetour = vRetour.add(vLigneEcritureComptable.getCredit());
            }
        }
        return vRetour;
    }

    /**
     * Renvoie si l'écriture est équilibrée (TotalDebit = TotalCrédit)
     * @return boolean
     */
    public boolean isEquilibree() {
        /*
        Dans le code d'origine, nous avons ceci dans le corps de la méthode
        boolean vRetour = this.getTotalDebit().equals(getTotalCredit()); return vRetour;
        ce qui implique que le retour est un booleén, true ou false sans que
        l'on sache si le débit est supérieur ou inférieur au crédit
        En utilisant la méthode compareTo, on renvoie un int, +1 en faveur du crédit
        -1 en faveur du débit et 0 si les deux montants sont égaux
        */
        return this.getTotalDebit().compareTo(getTotalCredit()) == 0;
    }

    // ==================== Méthodes ====================
    @Override
    @ExcludeFromJacocoGeneratedReport
    public String toString() {
        final String vSEP = ", ";
        String vStB;
        vStB = this.getClass().getSimpleName() + "{" +
                "id=" + id +
                vSEP + "journal=" + journal +
                vSEP + "reference='" + reference + '\'' +
                vSEP + "date=" + date +
                vSEP + "libelle='" + libelle + '\'' +
                vSEP + "totalDebit=" + this.getTotalDebit().toPlainString() +
                vSEP + "totalCredit=" + this.getTotalCredit().toPlainString() +
                vSEP + "listLigneEcriture=[\n" +
                StringUtils.join(listLigneEcriture, "\n") + "\n]" +
                "}";
        return vStB;
    }
}
