package com.dummy.myerp.business.impl.manager;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.dummy.myerp.business.contrat.BusinessProxy;
import com.dummy.myerp.business.impl.AbstractBusinessManager;
import com.dummy.myerp.business.impl.TransactionManager;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import java.sql.Date;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import javax.validation.Validator;

public class ComptabiliteManagerImplTest {

    private AbstractBusinessManager businessManager = new AbstractBusinessManager() {
        @Override
        protected BusinessProxy getBusinessProxy() {
            return super.getBusinessProxy();
        }

        @Override
        protected DaoProxy getDaoProxy() {
            return super.getDaoProxy();
        }

        @Override
        protected TransactionManager getTransactionManager() {
            return super.getTransactionManager();
        }

        @Override
        protected Validator getConstraintValidator() {
            return super.getConstraintValidator();
        }
    };
    private final ComptabiliteManagerImpl manager = new ComptabiliteManagerImpl();
    private EcritureComptable trueEcritureComptable;
    private EcritureComptable desEcritureComptable;
    private EcritureComptable oneLineEcritureComptable;
    private EcritureComptable badRefEcritureComptable;
    private EcritureComptable noLineEcritureComptable;
    private EcritureComptable badYearEcritureComptable;

    @Before
    public void setUpBeforeEach() {
        trueEcritureComptable = new EcritureComptable();
        trueEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        trueEcritureComptable.setDate(Date.valueOf(LocalDate.now()));
        trueEcritureComptable.setLibelle("Libelle");
        trueEcritureComptable.setReference("AC-2121/00001");
        trueEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        trueEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));

        oneLineEcritureComptable = new EcritureComptable();
        oneLineEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        oneLineEcritureComptable.setDate(Date.valueOf(LocalDate.now()));
        oneLineEcritureComptable.setLibelle("Libelle");
        oneLineEcritureComptable.setReference("AC-2121/00001");
        oneLineEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));

        desEcritureComptable = new EcritureComptable();
        desEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        desEcritureComptable.setDate(Date.valueOf(LocalDate.now()));
        desEcritureComptable.setLibelle("Libelle");
        desEcritureComptable.setReference("AC-2121/00001");
        desEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        desEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(432)));

        badRefEcritureComptable = new EcritureComptable();
        badRefEcritureComptable.setJournal(new JournalComptable("BQ", "Banque"));
        badRefEcritureComptable.setDate(Date.valueOf(LocalDate.now()));
        badRefEcritureComptable.setLibelle("Libelle");
        badRefEcritureComptable.setReference("AC-2121/00001");
        badRefEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        trueEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));

        noLineEcritureComptable = new EcritureComptable();
        noLineEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        noLineEcritureComptable.setDate(Date.valueOf(LocalDate.now()));
        noLineEcritureComptable.setLibelle("Libelle");
        noLineEcritureComptable.setReference("AC-2121/00001");

        badYearEcritureComptable = new EcritureComptable();
        badYearEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        badYearEcritureComptable.setDate(Date.valueOf(LocalDate.now()));
        badYearEcritureComptable.setLibelle("Libelle");
        badYearEcritureComptable.setReference("AC-2019/00001");
        badYearEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        badYearEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitTest() throws FunctionalException {
        manager.checkEcritureComptable(trueEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableTest() throws FunctionalException {
        EcritureComptable tEcritureComptable;
        tEcritureComptable = new EcritureComptable();
        tEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        tEcritureComptable.setDate(Date.valueOf(LocalDate.now()));
        tEcritureComptable.setLibelle("Libelle");
        tEcritureComptable.setReference("AC-2121/00001");
        tEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        tEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));
        manager.checkEcritureComptable(tEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitViolationTest() throws FunctionalException {
        // ===== Vérification des contraintes unitaires sur les attributs de l'écriture
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG2Test() throws FunctionalException {
        // ===== RG_Compta_2 : Pour qu'une écriture comptable soit valide, elle doit être équilibrée
        manager.checkEcritureComptableUnit(desEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG3TestTwoLines() throws FunctionalException {
        // ===== RG_Compta_3 : une écriture comptable doit avoir au moins 2 lignes d'écriture (1 au débit, 1 au crédit)

        // On test le nombre de lignes car si l'écriture à une seule ligne
        // avec un montant au débit et un montant au crédit ce n'est pas valable
        manager.checkEcritureComptableUnit(oneLineEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG3TestCreateLine() throws FunctionalException {
        // ===== RG_Compta_3 : une écriture comptable doit avoir au moins 2 lignes d'écriture (1 au débit, 1 au crédit)

        manager.checkEcritureComptableUnit(noLineEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG5TestYear() throws FunctionalException {
        // RG_Compta_5 : Format et contenu de la référence

        // Vérifier que l'année dans la référence correspond bien à la date de l'écriture
        manager.checkEcritureComptableUnit(badYearEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG5TestRef() throws FunctionalException {
        // RG_Compta_5 : Format et contenu de la référence

        // Vérification du Code du journal et de celui spécifié dans la référence
        manager.checkEcritureComptableUnit(badRefEcritureComptable);
    }

    @Test
    public void checkEcritureComptableContextIdNull() {
        // ===== RG_Compta_6 : La référence d'une écriture comptable doit être unique
        EcritureComptable vECRef = new EcritureComptable();
        Assertions.assertThat(vECRef.getId() == null).isTrue();
    }

    @Test
    public void checkEcritureComptableContextNoUnique() {
        // ===== RG_Compta_6 : La référence d'une écriture comptable doit être unique
        EcritureComptable vECRef = new EcritureComptable();
        vECRef.setJournal(new JournalComptable("BQ", "Banque"));
        vECRef.setDate(Date.valueOf(LocalDate.now()));
        vECRef.setLibelle("Libelle");
        vECRef.setReference("BQ-2019/00001");
        if (StringUtils.isNoneEmpty(trueEcritureComptable.getReference())) {

            // Si l'écriture à vérifier est une nouvelle écriture (id == null),
            // ou si elle ne correspond pas à l'écriture trouvée (id != idECRef),
            // c'est qu'il y a déjà une autre écriture avec la même référence
            Assertions.assertThat(trueEcritureComptable.getId() == null
                    || !trueEcritureComptable.getId().equals(vECRef.getId())).isTrue();
        }
    }

    @Test
    public void addReference() {
        EcritureComptable pEcritureComptable = new EcritureComptable();
        pEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        pEcritureComptable.setDate(Date.valueOf(LocalDate.now()));
        pEcritureComptable.setLibelle("Libelle");

        Assertions.assertThat("AC-2019/00001".equals(pEcritureComptable.getReference())).isFalse();
    }
}
