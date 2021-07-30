package com.dummy.myerp.business.impl.manager;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.*;

import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import org.junit.Test;

public class ComptabiliteManagerImplTest {

    private final ComptabiliteManagerImpl manager = new ComptabiliteManagerImpl();

    @Test
    public void checkEcritureComptableUnit() {
        EcritureComptable pEcritureComptable = new EcritureComptable();
        pEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        pEcritureComptable.setDate(new Date());
        pEcritureComptable.setLibelle("Libelle");
        /*Dans le code d'origine, la référence n'est pas setté
        Rajout du setReference au format code du journal dans lequel figure l'écriture,
        suivi de l'année et d'un numéro de séquence*/
        pEcritureComptable.setReference("AC-2019/00001");
        pEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        pEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitViolation() throws FunctionalException {
        EcritureComptable pEcritureComptable;
        pEcritureComptable = new EcritureComptable();
        manager.checkEcritureComptableUnit(pEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG2() throws FunctionalException {
        EcritureComptable pEcritureComptable;
        pEcritureComptable = new EcritureComptable();
        pEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        pEcritureComptable.setDate(new Date());
        pEcritureComptable.setLibelle("Libelle");
        pEcritureComptable.setReference("AC-2019/00001");
        pEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        pEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(1234)));
        manager.checkEcritureComptableUnit(pEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG3() throws FunctionalException {
        EcritureComptable pEcritureComptable;
        pEcritureComptable = new EcritureComptable();
        pEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        pEcritureComptable.setDate(new Date());
        pEcritureComptable.setLibelle("Libelle");
        pEcritureComptable.setReference("AC-2019/00001");
        pEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        pEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        manager.checkEcritureComptableUnit(pEcritureComptable);
    }

    @Test
    public void checkEcritureComptableContext() throws Exception {
        // ===== RG_Compta_6 : La référence d'une écriture comptable doit être unique
        EcritureComptable firstEcritureComptable = new EcritureComptable();
        firstEcritureComptable.setReference("AC-2019/00001");
        EcritureComptable secondEcritureComptable = new EcritureComptable();
        secondEcritureComptable.setReference("AC-2019/00002");
        if (firstEcritureComptable.getReference().equals(secondEcritureComptable.getReference())){
            throw new FunctionalException("La référence de l'écriture comptable n'est pas unique");
        } else {
            String s = "La référence de l'écriture comptable est unique";
        }
    }

    @Test
    public void checkEcritureComptable() throws Exception {
        this.checkEcritureComptableUnit();
        this.checkEcritureComptableContext();
    }

    @Test
    public void addReference() {
        EcritureComptable pEcritureComptable = new EcritureComptable();
        pEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        pEcritureComptable.setDate(new Date());
        pEcritureComptable.setLibelle("Libelle");
        pEcritureComptable.setReference("AC-2019/00001");
        pEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        pEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));
        String currentYear = String.valueOf(pEcritureComptable.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear());
        String vRef = pEcritureComptable.getJournal().getCode() + "-" + currentYear + "/";

        if (pEcritureComptable.getReference().contains(currentYear)) {
            String sequence = pEcritureComptable.getReference().substring(8);
            Integer sequencenb = Integer.parseInt(sequence) + 1;
            vRef += String.format("%05d", sequencenb);
        } else {
            vRef += "00001";
        }
        pEcritureComptable.setReference(vRef);
    }
}
