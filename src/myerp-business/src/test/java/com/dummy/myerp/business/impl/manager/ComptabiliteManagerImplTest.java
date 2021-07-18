package com.dummy.myerp.business.impl.manager;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import org.junit.Test;

import static com.dummy.myerp.consumer.ConsumerHelper.getDaoProxy;

public class ComptabiliteManagerImplTest {

    private final ComptabiliteManagerImpl manager = new ComptabiliteManagerImpl();

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnit() throws Exception {
            EcritureComptable vEcritureComptable = new EcritureComptable();
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

    public List<EcritureComptable> getListEcritureComptable() {
        return getDaoProxy().getComptabiliteDao().getListEcritureComptable();
    }

    @Test
    public void addReference() {
        Calendar calendar = new GregorianCalendar(2017, 0 , 25);
        EcritureComptable pEcritureComptable = new EcritureComptable();
        pEcritureComptable.setReference("CC-2021/12345");
        pEcritureComptable.setDate(calendar.getTime());
        /*String year = String.valueOf(pEcritureComptable.getDate());
        String dateEcriture = year.substring(3);*/
        String reference = pEcritureComptable.getReference();
        /*String anneeRef = reference.substring(3, 7);
        String code = reference.substring(0,2);*/
        String sequence = reference.substring(8);
        pEcritureComptable.getListLigneEcriture().get(getListEcritureComptable().size() - 1);
        String currentYear = String.valueOf(pEcritureComptable.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear());
        String vRef = pEcritureComptable.getJournal().getCode() +"-"+ currentYear +"/";

        if(pEcritureComptable.getReference().contains(currentYear)){
            sequence = pEcritureComptable.getReference().substring(8);
            Integer sequencenb = Integer.parseInt(sequence)+1;
            vRef += String.format("%05d", sequencenb);
        } else {
            vRef += "00001";
        }
        pEcritureComptable.setReference(vRef);
        getDaoProxy().getComptabiliteDao().insertEcritureComptable(pEcritureComptable);
        System.out.println(pEcritureComptable);
    }
}
