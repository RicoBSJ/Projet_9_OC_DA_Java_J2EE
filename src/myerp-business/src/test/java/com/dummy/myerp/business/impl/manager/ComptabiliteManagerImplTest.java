package com.dummy.myerp.business.impl.manager;

import java.math.BigDecimal;
import java.util.Date;

import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import org.junit.Assert;
import org.junit.Test;

public class ComptabiliteManagerImplTest {

    private final ComptabiliteManagerImpl manager = new ComptabiliteManagerImpl();


    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnit() throws Exception {
            EcritureComptable vEcritureComptable;
            vEcritureComptable = new EcritureComptable();
            vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
            vEcritureComptable.setDate(new Date());
            vEcritureComptable.setLibelle("Libelle");
            /*Dans le code d'origine, la référence n'est pas setté
            Rajout du setReference au format code du journal dans lequel figure l'écriture,
            suivi de l'année et d'un numéro de séquence*/
            vEcritureComptable.setReference("AC-2019/00001");
            vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                    null, new BigDecimal(123),
                    null));
            vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                    null, null,
                    new BigDecimal(123)));
            manager.checkEcritureComptableUnit(vEcritureComptable);
        }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitViolation() throws Exception {
            EcritureComptable vEcritureComptable;
            vEcritureComptable = new EcritureComptable();
            manager.checkEcritureComptableUnit(vEcritureComptable);
        }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG2() throws Exception {
            EcritureComptable vEcritureComptable;
            vEcritureComptable = new EcritureComptable();
            vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
            vEcritureComptable.setDate(new Date());
            vEcritureComptable.setLibelle("Libelle");
            vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                    null, new BigDecimal(123),
                    null));
            vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                    null, null,
                    new BigDecimal(1234)));
            manager.checkEcritureComptableUnit(vEcritureComptable);
        }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG3() throws Exception {
            EcritureComptable vEcritureComptable;
            vEcritureComptable = new EcritureComptable();
            vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
            vEcritureComptable.setDate(new Date());
            vEcritureComptable.setLibelle("Libelle");
            vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                    null, new BigDecimal(123),
                    null));
            vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                    null, new BigDecimal(123),
                    null));
            manager.checkEcritureComptableUnit(vEcritureComptable);
        }

    @Test
    public void checkEcritureComptable() {
    }

    @Test
    public void addReference() {
        EcritureComptable pEcritureComptable = new EcritureComptable();
        pEcritureComptable.setReference("CC-2021/12345");
        String reference = pEcritureComptable.getReference();
        String anneeRef = reference.substring(3, 7);
        String code = reference.substring(0,2);
        String sequence = reference.substring(8);
        int i = 0;
    }
}
