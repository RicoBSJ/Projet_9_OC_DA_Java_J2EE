package com.dummy.myerp.model.bean.comptabilite;

import com.dummy.myerp.technical.annotations.ExcludeConstructorFromJacocoGeneratedReport;
import com.dummy.myerp.technical.annotations.ExcludeFromJacocoGeneratedReport;

import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Bean représentant un Journal Comptable
 */
public class JournalComptable {

    // ==================== Attributs ====================
    /** code */
    @NotNull
    @Size(min = 1, max = 5)
    private String code;

    /** libelle */
    @NotNull
    @Size(min = 1, max = 150)
    private String libelle;


    // ==================== Constructeurs ====================
    /**
     * Instantiates a new Journal comptable.
     */
    public JournalComptable() {
    }

    /**
     * Instantiates a new Journal comptable.
     *
     * @param pCode the p code
     * @param pLibelle the p libelle
     */
    @ExcludeConstructorFromJacocoGeneratedReport
    public JournalComptable(String pCode, String pLibelle) {
        code = pCode;
        libelle = pLibelle;
    }


    // ==================== Getters/Setters ====================
    public String getCode() {
        return code;
    }
    @ExcludeFromJacocoGeneratedReport
    public void setCode(String pCode) {
        code = pCode;
    }
    @ExcludeFromJacocoGeneratedReport
    public String getLibelle() {
        return libelle;
    }
    @ExcludeFromJacocoGeneratedReport
    public void setLibelle(String pLibelle) {
        libelle = pLibelle;
    }


    // ==================== Méthodes ====================
    @Override
    @ExcludeFromJacocoGeneratedReport
    public String toString() {
        final String vSEP = ", ";
        return this.getClass().getSimpleName() + "{" +
                "code='" + code + '\'' +
                vSEP + "libelle='" + libelle + '\'' +
                "}";
    }


    // ==================== Méthodes STATIC ====================
    /**
     * Renvoie le {@link JournalComptable} de code {@code pCode} s'il est présent dans la liste
     *
     * @param pList la liste où chercher le {@link JournalComptable}
     * @param pCode le code du {@link JournalComptable} à chercher
     * @return {@link JournalComptable} ou {@code null}
     */
    public static JournalComptable getByCode(List<? extends JournalComptable> pList, String pCode) {
        JournalComptable vRetour = null;
        for (JournalComptable vBean : pList) {
            if (vBean != null && Objects.equals(vBean.getCode(), pCode)) {
                vRetour = vBean;
                break;
            }
        }
        return vRetour;
    }
}
