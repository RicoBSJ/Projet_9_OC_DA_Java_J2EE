package com.dummy.myerp.model.bean.comptabilite;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class JournalComptableTest {

    @Test
    public void getByCodeTest() {
        JournalComptable journalComptable1 = new JournalComptable();
        journalComptable1.setCode("AC");
        JournalComptable journalComptable2 = new JournalComptable();
        journalComptable2.setCode("VE");
        JournalComptable journalComptable3 = new JournalComptable();
        journalComptable3.setCode("AC");
        JournalComptable journalComptable4 = new JournalComptable();
        journalComptable4.setCode("OD");
        List<JournalComptable> list = new ArrayList<>();
        list.add(journalComptable1);
        list.add(journalComptable2);
        list.add(journalComptable3);
        list.add(journalComptable4);
        JournalComptable.getByCode(list, "AC");
    }
}
