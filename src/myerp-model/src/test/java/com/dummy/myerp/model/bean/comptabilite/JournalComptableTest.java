package com.dummy.myerp.model.bean.comptabilite;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class JournalComptableTest {

    @Test
    public void getByCode() {

        JournalComptable journalComptable1 = new JournalComptable();
        journalComptable1.setCode("AC");
        journalComptable1.setLibelle("Achat");
        JournalComptable journalComptable2 = new JournalComptable();
        journalComptable2.setCode("VE");
        journalComptable2.setLibelle("Vente");
        JournalComptable journalComptable3 = new JournalComptable();
        journalComptable3.setCode("BQ");
        journalComptable3.setLibelle("Banque");
        JournalComptable journalComptable4 = new JournalComptable();
        journalComptable4.setCode("OD");
        journalComptable4.setLibelle("Opérations diverses");
        ArrayList<JournalComptable> list = new ArrayList<>();
        list.add(journalComptable1);
        list.add(journalComptable2);
        list.add(journalComptable3);
        list.add(journalComptable4);
        JournalComptable fourthNumber = list.get(3);
        Assertions.assertThat(JournalComptable.getByCode(list, "OD")).isEqualTo(fourthNumber);
    }
}
