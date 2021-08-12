package com.dummy.myerp.business.impl.manager;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.*;

import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import javax.validation.*;

public class ComptabiliteManagerImplTest {

    private final ComptabiliteManagerImpl manager = new ComptabiliteManagerImpl();

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitTest() throws Exception {
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
        manager.checkEcritureComptableUnit(pEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableTest() throws Exception {
        // ===== Vérification des contraintes unitaires sur les attributs de l'écriture
        this.checkEcritureComptableUnitViolationTest();
        // ===== RG_Compta_2 : Pour qu'une écriture comptable soit valide, elle doit être équilibrée
        this.checkEcritureComptableUnitRG2Test();
        // ===== RG_Compta_3 : une écriture comptable doit avoir au moins 2 lignes d'écriture (1 au débit, 1 au crédit)
        this.checkEcritureComptableUnitRG3Test();
        // Vérifier que l'année dans la référence correspond bien à la date de l'écriture
        this.checkEcritureComptableUnitRG5Test1();
        // Vérification du Code du journal et de celui spécifié dans la référence
        this.checkEcritureComptableUnitRG5Test2();
        // ===== RG_Compta_6 : La référence d'une écriture comptable doit être unique
        this.checkEcritureComptableUnitRG6Test();
    }

    protected Validator getConstraintValidator() {
        Configuration<?> vConfiguration = Validation.byDefaultProvider().configure();
        ValidatorFactory vFactory = vConfiguration.buildValidatorFactory();
        return vFactory.getValidator();
    }

    @Test
    public void checkEcritureComptableUnitViolationTest() {
        // ===== Vérification des contraintes unitaires sur les attributs de l'écriture
        EcritureComptable pEcritureComptable;
        pEcritureComptable = new EcritureComptable();
        Set<ConstraintViolation<EcritureComptable>> vViolations = getConstraintValidator().validate(pEcritureComptable);
        Assertions.assertThat(!vViolations.isEmpty()).isTrue();
    }

    @Test
    public void checkEcritureComptableUnitRG2Test() {
        // ===== RG_Compta_2 : Pour qu'une écriture comptable soit valide, elle doit être équilibrée
        EcritureComptable pEcritureComptable = new EcritureComptable();
        pEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123), null));
        pEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null, new BigDecimal(123)));

        Assertions.assertThat(!pEcritureComptable.isEquilibree()).isFalse();
    }

    @Test
    public void checkEcritureComptableUnitRG3Test() {
        // ===== RG_Compta_3 : une écriture comptable doit avoir au moins 2 lignes d'écriture (1 au débit, 1 au crédit)
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

        int vNbrCredit = 0;
        int vNbrDebit = 0;
        for (LigneEcritureComptable vLigneEcritureComptable : pEcritureComptable.getListLigneEcriture()) {
            if (BigDecimal.ZERO.compareTo(ObjectUtils.defaultIfNull(vLigneEcritureComptable.getCredit(),
                    BigDecimal.ZERO)) != 0) {
                vNbrCredit++;
            }
            if (BigDecimal.ZERO.compareTo(ObjectUtils.defaultIfNull(vLigneEcritureComptable.getDebit(),
                    BigDecimal.ZERO)) != 0) {
                vNbrDebit++;
            }
        }
        // On test le nombre de lignes car si l'écriture à une seule ligne
        // avec un montant au débit et un montant au crédit ce n'est pas valable
        Assertions.assertThat(pEcritureComptable.getListLigneEcriture().size() < 2
                || vNbrCredit < 1
                || vNbrDebit < 1).isFalse();
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG5Test1() throws Exception {
        // RG_Compta_5 : Format et contenu de la référence
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

        // Vérifier que l'année dans la référence correspond bien à la date de l'écriture
        manager.checkEcritureComptableUnit(pEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG5Test2() throws Exception {
        // RG_Compta_5 : Format et contenu de la référence
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

        // Vérification du Code du journal et de celui spécifié dans la référence
        manager.checkEcritureComptableUnit(pEcritureComptable);
    }

    @Test
    public void checkEcritureComptableUnitRG6Test() {
        // ===== RG_Compta_6 : La référence d'une écriture comptable doit être unique
        EcritureComptable pEcritureComptable = new EcritureComptable();
        pEcritureComptable.setId(1);
        pEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        pEcritureComptable.setDate(new Date());
        pEcritureComptable.setLibelle("Libelle");
        pEcritureComptable.setReference("AC-2019/00001");
        EcritureComptable vECRef = new EcritureComptable();
        vECRef.setId(2);
        pEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        pEcritureComptable.setDate(new Date());
        pEcritureComptable.setLibelle("Libelle");
        pEcritureComptable.setReference("AC-2020/00002");
        if (StringUtils.isNoneEmpty(pEcritureComptable.getReference(), vECRef.getReference())) {
            Assertions.assertThat(pEcritureComptable.getId() == null || vECRef.getId() == null
                    || !pEcritureComptable.getId().equals(vECRef.getId())).isFalse();
        }
    }

    @Test
    public void addReferenceTest() {
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
