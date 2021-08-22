package com.dummy.myerp.business.impl.manager;

import java.math.BigDecimal;

import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import com.dummy.myerp.consumer.dao.impl.db.dao.ComptabiliteDaoImpl;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;

import java.time.ZoneId;
import java.util.*;

import static org.mockito.Mockito.*;

import com.dummy.myerp.technical.exception.NotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ComptabiliteManagerImplTest {

    private ComptabiliteManagerImpl manager = spy(new ComptabiliteManagerImpl());
    private EcritureComptable vEcritureComptable;
    private DaoProxy daoProxy;
    private DaoProxy daoProxySpy;
    private ComptabiliteDaoImpl comptabiliteDao;
    private ComptabiliteDaoImpl comptabiliteDaoSpy;

    @Before
    public void setUpBeforeEach() {
        comptabiliteDao = mock(ComptabiliteDaoImpl.class);
        comptabiliteDaoSpy = spy(comptabiliteDao);
        /*daoProxy = () -> comptabiliteDao;*/
        daoProxySpy = spy(daoProxy);
        /*doReturn(daoProxy).when(manager).getDaoProxy();*/
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setId(11);
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitTest() throws FunctionalException {
        vEcritureComptable.setReference("AC-2121/00001");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    @Test
    public void checkEcritureComptableTest() throws NotFoundException, FunctionalException {
        vEcritureComptable.setReference("AC-2021/00001");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));
        doReturn(comptabiliteDao).when(manager).getDaoProxy();
        doReturn(comptabiliteDaoSpy).when(daoProxySpy).getComptabiliteDao();
        doReturn(null).when(comptabiliteDaoSpy).getEcritureComptableByRef(vEcritureComptable.getReference());
        /*
        doReturn(vDaoProxyMock).when(vComptabiliteManagerSpy).getDaoProxy();
        doReturn(vComptabiliteDaoSpy).when(vDaoProxySpy).getComptabiliteDao();
        doReturn(null).when(vComptabiliteDaoSpy).getEcritureComptableByRef(anyString());
         */
        manager.checkEcritureComptable(vEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitViolationTest() throws FunctionalException {
        // ===== Vérification des contraintes unitaires sur les attributs de l'écriture
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG2TestEquil() throws FunctionalException {
        // ===== RG_Compta_2 : Pour qu'une écriture comptable soit valide, elle doit être équilibrée
        vEcritureComptable.setReference("AC-2121/00003");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(432)));
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG3TestTwoLines() throws FunctionalException {
        // ===== RG_Compta_3 : une écriture comptable doit avoir au moins 2 lignes d'écriture (1 au débit, 1 au crédit)

        // On test le nombre de lignes car si l'écriture à une seule ligne
        // avec un montant au débit et un montant au crédit ce n'est pas valable
        vEcritureComptable.setReference("AC-2121/00002");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG3TestCreateLine() throws FunctionalException {
        // ===== RG_Compta_3 : une écriture comptable doit avoir au moins 2 lignes d'écriture (1 au débit, 1 au crédit)
        vEcritureComptable.setReference("AC-2121/00005");
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG5TestYear() throws FunctionalException {
        // RG_Compta_5 : Format et contenu de la référence

        // Vérifier que l'année dans la référence correspond bien à la date de l'écriture
        vEcritureComptable.setReference("AC-2019/00006");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG5TestRef() throws FunctionalException {
        // RG_Compta_5 : Format et contenu de la référence

        // Vérification du Code du journal et de celui spécifié dans la référence
        EcritureComptable badRefEcritureComptable;
        badRefEcritureComptable = new EcritureComptable();
        badRefEcritureComptable.setId(14);
        badRefEcritureComptable.setJournal(new JournalComptable("BQ", "Banque"));
        badRefEcritureComptable.setDate(new Date());
        badRefEcritureComptable.setLibelle("Libelle");
        badRefEcritureComptable.setReference("AC-2121/00004");
        badRefEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        badRefEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));
        manager.checkEcritureComptableUnit(badRefEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableContextTestGetSameRef() throws NotFoundException, FunctionalException {
        // ===== RG_Compta_6 : La référence d'une écriture comptable doit être unique
        EcritureComptable vECRef = new EcritureComptable();
        vECRef.setId(7);
        vECRef.setJournal(new JournalComptable("BQ", "Banque"));
        vECRef.setDate(new Date());
        vECRef.setLibelle("Libelle");
        vECRef.setReference("BQ-2021/00001");
        vEcritureComptable.setReference("AC-2121/00001");
        if (StringUtils.isNoneEmpty(vEcritureComptable.getReference())) {
            // Recherche d'une écriture ayant la même référence
            doReturn(vECRef).when(comptabiliteDao).getEcritureComptableByRef(vEcritureComptable.getReference());
        }
        manager.checkEcritureComptableContext(vEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableContextTestIdNullOrEqual() throws NotFoundException, FunctionalException {
        // ===== RG_Compta_6 : La référence d'une écriture comptable doit être unique
        EcritureComptable vECRef = new EcritureComptable();
        vECRef.setId(7);
        vECRef.setJournal(new JournalComptable("BQ", "Banque"));
        vECRef.setDate(new Date());
        vECRef.setLibelle("Libelle");
        vECRef.setReference("BQ-2021/00001");
        vEcritureComptable.setReference("AC-2121/00001");
        if (vEcritureComptable.getId() == null
                || !vEcritureComptable.getId().equals(vECRef.getId())) {
            // Recherche d'une écriture ayant la même référence
            doReturn(vECRef).when(comptabiliteDao).getEcritureComptableByRef(vEcritureComptable.getReference());
        }
        manager.checkEcritureComptableContext(vEcritureComptable);
    }

    @Test
    public void addReferenceTest() {
        //First scripture
        EcritureComptable nEcritureComptable = new EcritureComptable();
        nEcritureComptable.setId(12);
        nEcritureComptable.setJournal(new JournalComptable("BQ", "Banque"));
        nEcritureComptable.setDate(new Date());
        nEcritureComptable.setLibelle("Libelle");
        nEcritureComptable.setReference("BQ-2021/00002");
        nEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(342),
                null));
        nEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(342)));
        //Second scripture
        vEcritureComptable.setReference("AC-2021/00001");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));
        doNothing().when(comptabiliteDao).insertEcritureComptable(any());
        doReturn(List.of(nEcritureComptable)).when(manager).getListEcritureComptable();

        String currentYear = String.valueOf(vEcritureComptable.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear());
        String vRef = vEcritureComptable.getJournal().getCode() + "-" + currentYear + "/";
        if (nEcritureComptable.getReference().contains(currentYear)) {
            String sequence = nEcritureComptable.getReference().substring(8);
            Integer sequencenb = Integer.parseInt(sequence) + 1;
            vRef += String.format("%05d", sequencenb);
        } else {
            vRef += "00001";
        }
        vEcritureComptable.setReference(vRef);
        manager.addReference(vEcritureComptable);
    }
}
