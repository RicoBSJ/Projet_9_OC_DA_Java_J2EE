package com.dummy.myerp.business.impl.manager;

import java.math.BigDecimal;

import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;

import java.time.ZoneId;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import static com.dummy.myerp.consumer.ConsumerHelper.getDaoProxy;

@RunWith(MockitoJUnitRunner.class)
public class ComptabiliteManagerImplTest {

    private final ComptabiliteManagerImpl manager = new ComptabiliteManagerImpl();
    private EcritureComptable vEcritureComptable;

    @Before
    public void setUpBeforeEach() {

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

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableTest() throws FunctionalException {
        vEcritureComptable.setReference("AC-2121/00001");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));
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
    public void checkEcritureComptableUnitRG2Test() throws FunctionalException {
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

    @Test
    public void checkEcritureComptableContextIdNull() {
        // ===== RG_Compta_6 : La référence d'une écriture comptable doit être unique
        EcritureComptable vECRef = new EcritureComptable();
        assertThat(vECRef.getId() == null).isTrue();
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableContextNoUnique() throws FunctionalException {
        // ===== RG_Compta_6 : La référence d'une écriture comptable doit être unique
        EcritureComptable vECRef = new EcritureComptable();
        vECRef.setId(7);
        vECRef.setJournal(new JournalComptable("BQ", "Banque"));
        vECRef.setDate(new Date());
        vECRef.setLibelle("Libelle");
        vECRef.setReference("BQ-2021/00001");

        vEcritureComptable.setReference("AC-2121/00001");

        manager.checkEcritureComptable(vECRef);
        assertThat(vECRef.getReference().equals(vEcritureComptable.getReference())).isFalse();
    }

    public List<EcritureComptable> getListEcritureComptable() {
        return getDaoProxy().getComptabiliteDao().getListEcritureComptable();
    }


    public void addReference() {

        EcritureComptable nEcritureComptable = new EcritureComptable();
        nEcritureComptable.setId(12);
        nEcritureComptable.setJournal(new JournalComptable("BQ", "Banque"));
        nEcritureComptable.setDate(new Date());
        nEcritureComptable.setLibelle("Libelle");
        nEcritureComptable.setReference("BQ-2121/00002");
        nEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(342),
                null));
        nEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(342)));
        getDaoProxy().getComptabiliteDao().insertEcritureComptable(nEcritureComptable);
        vEcritureComptable.setReference("AC-2121/00001");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));
        getDaoProxy().getComptabiliteDao().insertEcritureComptable(vEcritureComptable);

        nEcritureComptable = getListEcritureComptable().get(getListEcritureComptable().size() - 1);
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

    @Mock
    List<String> mockedList;

    @Test
    public void whenUseMockAnnotation_thenMockIsInjected() {
        mockedList.add("one");
        Mockito.verify(mockedList).add("one");
        assertThat(0).isEqualTo(mockedList.size());

        Mockito.when(mockedList.size()).thenReturn(100);
        assertThat(100).isEqualTo(mockedList.size());
    }

    /*@Spy
    List<String> spiedList = new ArrayList<>();

    @Test
    public void whenUseSpyAnnotation_thenSpyIsInjectedCorrectly() {
        spiedList.add("one");
        spiedList.add("two");

        Mockito.verify(spiedList).add("one");
        Mockito.verify(spiedList).add("two");

        assertThat(2).isEqualTo(spiedList.size());

        Mockito.doReturn(100).when(spiedList).size();
        assertThat(100).isEqualTo(spiedList.size());
    }*/

    @Captor
    ArgumentCaptor argCaptor;

    @Test
    public void whenUseCaptorAnnotation_thenTheSam() {
        mockedList.add("one");
        Mockito.verify(mockedList).add((String) argCaptor.capture());

        assertThat("one").isEqualTo(argCaptor.getValue());
    }

    public static class MyDictionary {
        Map<String, String> wordMap;

        public MyDictionary() {
            wordMap = new HashMap<>();
        }
        public void add(final String word, final String meaning) {
            wordMap.put(word, meaning);
        }
        public String getMeaning(final String word) {
            return wordMap.get(word);
        }
    }

    @Mock
    Map<String, String> wordMap;

    @InjectMocks
    MyDictionary dic = new MyDictionary();

    @Test
    public void whenUseInjectMocksAnnotation_thenCorrect() {
        Mockito.when(wordMap.get("aWord")).thenReturn("aMeaning");

        assertThat("aMeaning").isEqualTo(dic.getMeaning("aWord"));
    }
}
