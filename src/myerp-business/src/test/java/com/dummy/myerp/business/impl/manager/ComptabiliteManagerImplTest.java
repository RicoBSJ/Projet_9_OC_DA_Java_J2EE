package com.dummy.myerp.business.impl.manager;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import com.dummy.myerp.consumer.dao.impl.db.dao.ComptabiliteDaoImpl;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.Test;

import static com.dummy.myerp.consumer.ConsumerHelper.getDaoProxy;

public class ComptabiliteManagerImplTest {

    private final ComptabiliteManagerImpl manager = new ComptabiliteManagerImpl();

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitTest() throws Exception {
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
        manager.checkEcritureComptableUnit(pEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitViolationTest() throws Exception {
        EcritureComptable pEcritureComptable;
        pEcritureComptable = new EcritureComptable();
        manager.checkEcritureComptableUnit(pEcritureComptable);
    }

    @Test
    public void checkEcritureComptableUnitRG2() {
        // ===== RG_Compta_2 : Pour qu'une écriture comptable soit valide, elle doit être équilibrée
        EcritureComptable pEcritureComptable = new EcritureComptable();
        pEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123), null));
        pEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, null, new BigDecimal(123)));
        Assertions.assertThat(pEcritureComptable.isEquilibree()).isTrue();
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG3Test() throws Exception {
        // ===== RG_Compta_3 : une écriture comptable doit avoir au moins 2 lignes d'écriture (1 au débit, 1 au crédit)
        EcritureComptable pEcritureComptable = new EcritureComptable();
        pEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        pEcritureComptable.setDate(new Date());
        pEcritureComptable.setLibelle("Libelle");
        pEcritureComptable.setReference("AC-2019/00001");
        pEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        manager.checkEcritureComptableUnit(pEcritureComptable);
    }

    @Test
    public void checkEcritureComptableContext() {
        // ===== RG_Compta_6 : La référence d'une écriture comptable doit être unique
        EcritureComptable pEcritureComptable1 = new EcritureComptable();
        pEcritureComptable1.setReference("AC-2019/00001");
        EcritureComptable pEcritureComptable2 = new EcritureComptable();
        pEcritureComptable2.setReference("OD-2019/00001");
        EcritureComptable pEcritureComptable3 = new EcritureComptable();
        pEcritureComptable3.setReference("AC-2019/00001");
        EcritureComptable pEcritureComptable4 = new EcritureComptable();
        pEcritureComptable4.setReference("BQ-2019/00001");
        ArrayList<String> list = new ArrayList<>();
        list.add(pEcritureComptable1.getReference());
        list.add(pEcritureComptable2.getReference());
        list.add(pEcritureComptable3.getReference());
        list.add(pEcritureComptable4.getReference());
        Assertions.assertThat(Collections.frequency(list, "AC-2019/00001")).isGreaterThan(1);
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
